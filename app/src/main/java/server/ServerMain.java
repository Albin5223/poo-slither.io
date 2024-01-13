package server;

import model.paquet.snake.PaquetSnakeCtoS;
import model.paquet.snake.PaquetSnakeFirstCtoS;
import model.paquet.snake.PaquetSnakeFirstStoC;
import model.plateau.Snake;
import model.skins.Skin;
import java.net.Socket;
import java.net.SocketException;

import interfaces.Orientation;
import interfaces.Turnable.Turning;

import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class ServerMain<Type extends Number & Comparable<Type>, O extends Orientation<O>> implements Runnable {
    
    public final static int port = 3000;
    ServerFactory<Type,O> server;
    private boolean onlyOneTurn = false;


    public ServerMain(ServerFactory<Type,O> server) {
        this.server = server;
        onlyOneTurn = server.getOnlyOneTurn();
    }


    public class ConnexionHandle implements Runnable{

        private Socket client;

        private int window_width;
        private int window_height;
        
        private ObjectInputStream ois;
        private ObjectOutputStream oos;
        private String name;
        private Snake<Type,O> snake;
        private Skin skin;
        
        
        
        private Thread frameRate = new Thread(new Runnable(){
            @Override
            public void run() {
                while(!Thread.currentThread().isInterrupted()){
                    sendInformationsToDraw();
                    try {
                        Thread.sleep(1000/60);  // 60 fps
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        });

        public ConnexionHandle(Socket client) throws SocketException{
            this.client = client;
            client.setTcpNoDelay(true);
            
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
                    oos.writeObject(new PaquetSnakeFirstStoC<>(server.getBorder()));
                    
                    // Etape 3 : Créer son snake avec le plateau du serveur
                    snake = server.createSnake();
                    snake.setSkin(skin);
                    System.out.println("Snake created in "+ snake.getHead().getCenter().getX() + " " + snake.getHead().getCenter().getY());
                }
                catch (IOException e){
                    System.out.println("Echec de l'envoie");
                    e.printStackTrace();
                }

                // Etape 4 : On ajoute son snake au moteur de jeu
                server.addSnake(snake);
                System.out.println("Snake added to engine");

                // Etape 5 : On lance le thread du frame rate
                frameRate.start();
                
                // Etape 6 : On commence les échanges de données
                /*
                 * Ordre à suivre :
                 * - (le snakeMoverOnline envoie les données du snake au client au même rythme que sa vitesse)
                 * - Attendre que le client donne la direction de son snake, et voir s'il veut booster, ou quitter
                 * - Changer les données du snake du serveur
                 */
                while(!Thread.currentThread().isInterrupted()){
                    //System.out.println("Waiting for "+name+" to send informations");
                    PaquetSnakeCtoS message = (PaquetSnakeCtoS) ois.readObject();
                    if(message.isQuit()){
                        close();
                        break;
                    }
                    snake.setBoosting(message.isBoost());
                    if(onlyOneTurn){
                        if(snake.getCurrentTurning() == Turning.FORWARD){
                            snake.setTurning(message.getTurning());
                        }
                    }
                    else{
                        snake.setTurning(message.getTurning());
                    }
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
            
            try {
                oos.reset();
                
                server.sendObject(oos,snake,window_width,window_height);
                //oos.writeObject(server.getPaquetSnakeStoC(snake, window_width, window_height));
                //System.out.println(">> Snake data sent to "+this.name+" in "+ this.snake.getHead().getCenter().getX() + " " + this.snake.getHead().getCenter().getY());
            } catch (IOException e) {
                System.out.println("Failed sending to "+this.name);
                e.printStackTrace();
            }
        }

        public void close(){
            frameRate.interrupt();
            server.removeClient(this);
            server.removeSnake(snake);
            System.out.println("Client "+name+" disconnected, "+server.sizeOfClient()+" clients remaining");
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

    public String getIp(){
        return server.getIp();
    }

    public void shutdown(){
        server.shutdown();
    }

    @Override
    public void run() {

        try{
            server.run();
            while(!Thread.currentThread().isInterrupted()){
                Socket client;
            
                client = server.getServerSocket().accept();
                ConnexionHandle handle = new ConnexionHandle(client);
                server.add(handle);
            }
        } 
        catch (IOException e) {
            System.out.println("Server closed");
        }
        
    }

    



}
