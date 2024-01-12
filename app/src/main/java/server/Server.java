package server;



import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.objenesis.strategy.StdInstantiatorStrategy;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.util.DefaultInstantiatorStrategy;

import configuration.ConfigurationFoodInteger;
import configuration.ConfigurationSnakeInteger;
import externData.OurColors;
import interfaces.GameBorder;
import interfaces.Orientation.Direction;
import interfaces.Turnable;
import model.FoodData;
import model.SnakeData;
import model.coordinate.Coordinate;
import model.coordinate.CoordinateInteger;
import model.engine.EngineSnakeOnline;
import model.paquet.snake.PaquetSnakeCtoS;
import model.paquet.snake.PaquetSnakeFirstCtoS;
import model.paquet.snake.PaquetSnakeFirstStoC;
import model.paquet.snake.PaquetSnakeStoC;
import model.plateau.PlateauInteger;
import model.plateau.SnakeInteger;
import model.plateau.PlateauInteger.BorderInteger;
import model.skins.SkinFactory.SkinType;

public class Server implements Runnable{

    private ConfigurationFoodInteger configFood = new ConfigurationFoodInteger();
    private ConfigurationSnakeInteger configSnake = new ConfigurationSnakeInteger();

    private ArrayList<ConnexionHandle> clients;
    private ServerSocket server;
    public final static int port = 3000;

    private ExecutorService pool;
    private EngineSnakeOnline engine;

    public class ConnexionHandle implements Runnable{

        private Socket client;

        private int window_width;
        private int window_height;
        
        private Output writer;
        private Input reader;
        private String name;
        private SnakeInteger snake;
        private SkinType skin;

        private Kryo kryo = new Kryo();

        public ConnexionHandle(Socket client){
            this.client = client;
        }
        @Override
        public void run() {
            try{
                
                writer = new Output(client.getOutputStream());
                reader = new Input(client.getInputStream());

                kryo.setInstantiatorStrategy(new DefaultInstantiatorStrategy(new StdInstantiatorStrategy()));

                kryo.register(PaquetSnakeFirstCtoS.class);
                kryo.register(PaquetSnakeFirstStoC.class);
                kryo.register(PaquetSnakeCtoS.class);
                kryo.register(PaquetSnakeStoC.class);
                kryo.register(BorderInteger.class);
                kryo.register(SnakeData.class);
                kryo.register(FoodData.class);
                kryo.register(SkinType.class);
                kryo.register(Direction.class);
                kryo.register(ArrayList.class);
                kryo.register(Integer.class);
                kryo.register(Boolean.class);
                kryo.register(Double.class);
                kryo.register(String.class);
                kryo.register(Coordinate.class);
                kryo.register(Turnable.Turning.class);
                kryo.register(OurColors.class);
                kryo.register(CoordinateInteger.class);
                kryo.register(GameBorder.class);

                /*
                 * Ordre à suivre :
                 * - Attendre que le client envoie son nom, son skin, ainsi que les dimensions de la fenêtre
                 * - Envoyer la border au client
                 * - Créer un snake avec le plateau du serveur
                 */

                try{

                    // Etape 1 : Attendre que le client envoie son nom, skin et les dimensions de la fenêtre
                    PaquetSnakeFirstCtoS paquet = kryo.readObject(reader, PaquetSnakeFirstCtoS.class);

                    name = paquet.getMessage();
                    skin = paquet.getSkin();
                    window_width = paquet.getWindow_width();
                    window_height = paquet.getWindow_height();
                    System.out.println("New client : " + name + " with skin " + skin + " and window size : [" + window_width + "x" + window_height+"]");

                    // Etape 2 : Envoyer la border au client
                    kryo.writeObject(writer, new PaquetSnakeFirstStoC((BorderInteger) engine.getPlateau().getBorder()));
                    writer.flush();
                    
                    // Etape 3 : Créer son snake avec le plateau du serveur
                    snake = SnakeInteger.createSnakeInteger((PlateauInteger) engine.getPlateau());
                    snake.setSkin(skin);
                    System.out.println("Snake created in "+ snake.getHead().getCenter().getX() + " " + snake.getHead().getCenter().getY());
                }
                catch (Exception e){
                    System.out.println("Echec de l'envoie");
                    e.printStackTrace();
                }

                // Etape 5 : On ajoute son snake au moteur de jeu
                engine.addSnake(snake);
                System.out.println("Snake added to engine");
                
                // Etape 6 : On commence les échanges de données
                /*
                 * Ordre à suivre :
                 * - (le snakeMoverOnline envoie les données du snake au client au même rythme que sa vitesse)
                 * - Attendre que le client donne la direction de son snake, et voir s'il veut booster, ou quitter
                 * - Changer les données du snake du serveur
                 */
                while(!Thread.currentThread().isInterrupted()){
                    //System.out.println("Waiting for "+name+" to send informations");
                    PaquetSnakeCtoS message = kryo.readObject(reader, PaquetSnakeCtoS.class);
                    if(message.isQuit()){
                        close();
                        break;
                    }
                    snake.setBoosting(message.isBoost());
                    snake.setTurning(message.getTurning());            
                }

            }catch(IOException e){
                System.out.println("Echec");
                e.printStackTrace();        
            }        
        }

        public void sendInformationsToDraw(){
            SnakeData<Integer,Direction> snakeData = new SnakeData<Integer,Direction>(snake);
            ArrayList<SnakeData<Integer,Direction>> snakesToDraw = engine.getAllSnake();
            ArrayList<FoodData<Integer,Direction>> foodsToDraw = snake.getPlateau().getFoods().getRenderZone(snake.getHead().getCenter(), Math.max(window_height, window_width));
            try {
                kryo.writeObject(writer, new PaquetSnakeStoC(snakeData, snakesToDraw, foodsToDraw));
                writer.flush();
                //System.out.println(">> Snake data sent to "+this.name+" in "+ this.snake.getHead().getCenter().getX() + " " + this.snake.getHead().getCenter().getY());
            } catch (Exception e) {
                System.out.println("Failed sending to "+this.name);
                e.printStackTrace();
            }
        }

        public void close(){
            clients.remove(this);
            engine.removeSnake(snake);
            System.out.println("Client "+name+" disconnected, "+clients.size()+" clients remaining");
            try {
                reader.close();
                writer.close();
                if(!client.isClosed()){
                    client.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public Server(){
        clients = new ArrayList<ConnexionHandle>();
        engine = EngineSnakeOnline.createEngineSnakeOnline(4000, 4000, configFood, configSnake ,this);
    }

    public void sendInformationsToDrawToAll(){
        for(ConnexionHandle client : clients){
            if(client != null){
                client.sendInformationsToDraw();
            }
        }
    }

    @Override
    public void run() {
        try {
            server = new ServerSocket(port);

            engine.run();
            
            pool = Executors.newCachedThreadPool();
            while(!Thread.currentThread().isInterrupted()){
                Socket client = server.accept();
                ConnexionHandle handle = new ConnexionHandle(client);
                clients.add(handle);
                pool.execute(handle);
            }

        } catch (IOException e) {
            System.out.println("Server closed");
        }
    }


    public void shutdown(){

        if(!Thread.currentThread().isInterrupted()){    // Garantie
            Thread.currentThread().interrupt();
        }
        try {
            ArrayList<ConnexionHandle> clients_copy = new ArrayList<ConnexionHandle>(this.clients);
            for(ConnexionHandle client : clients_copy){
                client.close();
            }
            if(server != null && !server.isClosed()){
                server.close();
            }
            engine.stop();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getIp(){
        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            return inetAddress.getHostAddress().toString();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return "ERROR : IP NOT FOUND";
    }
}