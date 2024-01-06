package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import interfaces.Orientation.Direction;
import model.FoodData;
import model.SnakeData;
import model.engine.EngineSnakeOnline;
import model.paquet.snake.PaquetSnakeCtoS;
import model.paquet.snake.PaquetSnakeFirstCtoS;
import model.paquet.snake.PaquetSnakeFirstStoC;
import model.paquet.snake.PaquetSnakeStoC;
import model.plateau.PlateauInteger;
import model.plateau.SnakeInteger;
import model.plateau.PlateauInteger.BorderInteger;
import model.skins.Skin;

public class Server implements Runnable{

    private ArrayList<ConnexionHandle> clients;
    private ServerSocket server;
    public final static int port = 3000;

    private ExecutorService pool;
    private EngineSnakeOnline engine;

    public class ConnexionHandle implements Runnable{

        private Socket client;

        private int window_width;
        private int window_height;
        
        private ObjectInputStream ois;
        private ObjectOutputStream oos;
        private String name;
        private SnakeInteger snake;
        private Skin skin;
        

        public ConnexionHandle(Socket client){
            this.client = client;
        }
        @Override
        public void run() {
            try{
                
                oos = new ObjectOutputStream(client.getOutputStream());
                ois = new ObjectInputStream(client.getInputStream());

                /*
                 * Ordre à suivre :
                 * - Attendre que le client envoie son nom, son skin, ainsi que les dimensions de la fenêtre
                 * - Envoyer la border au client
                 * - Créer un snake avec le plateau du serveur
                 */

                try{

                    // Etape 1 : Attendre que le client envoie son nom, skin et les dimensions de la fenêtre
                    PaquetSnakeFirstCtoS paquet = (PaquetSnakeFirstCtoS) ois.readObject();
                    name = paquet.getMessage();
                    skin = paquet.getSkin();
                    window_width = paquet.getWindow_width();
                    window_height = paquet.getWindow_height();
                    System.out.println("New client : " + name + " with skin " + skin + " and window size : [" + window_width + "x" + window_height+"]");

                    // Etape 2 : Envoyer la border au client
                    oos.reset();
                    oos.writeObject(new PaquetSnakeFirstStoC((BorderInteger) engine.getPlateau().getBorder()));
                    
                    // Etape 3 : Créer son snake avec le plateau du serveur
                    snake = SnakeInteger.createSnakeInteger((PlateauInteger) engine.getPlateau());
                    snake.setSkin(skin);
                    System.out.println("Snake created in "+ snake.getHead().getCenter().getX() + " " + snake.getHead().getCenter().getY());
                }
                catch (IOException e){
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
                    System.out.println("Waiting for "+name+" to send informations");
                    PaquetSnakeCtoS message = (PaquetSnakeCtoS) ois.readObject();
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
            catch(ClassNotFoundException e){
                System.out.println("Objet non trouve");
            }
        
        }

        public void sendInformationsToDraw(){
            SnakeData<Integer,Direction> snakeData = new SnakeData<>(snake);
            ArrayList<SnakeData<Integer,Direction>> snakesToDraw = engine.getAllSnake();
            ArrayList<FoodData<Integer,Direction>> foodsToDraw = snake.getPlateau().getFoods().getRenderZone(snake.getHead().getCenter(), Math.max(window_height, window_width));
            try {
                oos.reset();
                oos.writeObject(new PaquetSnakeStoC(snakeData, snakesToDraw, foodsToDraw));
                System.out.println(">> Snake data sent to "+this.name+" in "+ this.snake.getHead().getCenter().getX() + " " + this.snake.getHead().getCenter().getY());
            } catch (IOException e) {
                System.out.println("Failed sending to "+this.name);
                e.printStackTrace();
            }
        }

        public void close(){
            clients.remove(this);
            engine.removeSnake(snake);
            System.out.println("Client "+name+" disconnected, "+clients.size()+" clients remaining");
            try {
                ois.close();
                oos.close();
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
        engine = EngineSnakeOnline.createEngineSnakeOnline(4000, 4000,this);
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