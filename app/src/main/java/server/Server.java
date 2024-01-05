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

import externData.ImageBank;
import interfaces.Turnable;
import model.engine.EngineSnakeOnline;
import model.paquet.PaquetSnake;
import model.plateau.PlateauInteger;
import model.plateau.SnakeInteger;
import model.skins.Skin;

public class Server implements Runnable{

    private ArrayList<ConnexionHandle> clients;
    private ServerSocket server;
    public final static int port = 3000;

    private ExecutorService pool;
    private EngineSnakeOnline engine;

    public class ConnexionHandle implements Runnable{

        private Socket client;
        
        ObjectInputStream ois;
        ObjectOutputStream oos;
        String name;
        SnakeInteger snake;
        Skin skin;
        Turnable.Turning turning;
        

        public ConnexionHandle(Socket client){
            this.client = client;
        }
        @Override
        public void run() {
            try{
                
                oos = new ObjectOutputStream(client.getOutputStream());
                ois = new ObjectInputStream(client.getInputStream());

                try{

                    snake = SnakeInteger.createSnakeInteger((PlateauInteger) engine.getPlateau());

                    
                    
                    System.out.println("Snake created in "+ snake.getHead().getCenter().getX() + " " + snake.getHead().getCenter().getY());
                    if(client.isClosed()){
                        System.out.println("Client closed");
                        return;
                    }
                    oos.reset();
                    oos.writeObject(PaquetSnake.createPaquetWithSnakeAndMessage("Please enter a nickname",snake));
                    System.out.println("Snake sent to client ");
                }
                catch (IOException e){
                    System.out.println("Echec de l'envoie");
                    e.printStackTrace();
                }
                System.out.println("Waiting for client name");
                PaquetSnake paquetName = (PaquetSnake) ois.readObject();
                name = paquetName.getMessage();
                skin = paquetName.getSkin();
                snake.setSkin(skin);
                System.out.println("New client : " + name);
                //sendAll(name + " has joined the chat");


                engine.addSnake(snake);


                //Lis les objets qu'il reçoit
                
                while(!Thread.currentThread().isInterrupted()){
                    PaquetSnake message = (PaquetSnake) ois.readObject();
                    if(message.isQuit()){
                        close();
                        break;
                    }
                    if (message.getSkin()!=null){
                        skin = message.getSkin();
                        System.out.println("Skin received from "+name);
                    }
                    if (message.getTurning()!=null){
                        turning = message.getTurning();
                        System.out.println("Turning received from "+name);
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

        public void sendMessage(String msg){
            PaquetSnake ps = PaquetSnake.createPaquetWithMessage(msg);
            try {
                oos.reset();
                oos.writeObject(ps);
            } catch (IOException e) {
                
            }
        }

        public void sendSnake(){
            
            PaquetSnake ps = PaquetSnake.createPaquetWithSnake(this.snake);
            try {
                
                oos.reset();
                oos.writeObject(ps);
                System.out.println("Snake sent to "+this.name+" in "+ this.snake.getHead().getCenter().getX() + " " + this.snake.getHead().getCenter().getY());
            } catch (IOException e) {
                
            }
        }

        public void close(){
            System.out.println("Client "+name+" disconnected");
            clients.remove(this);
            engine.removeSnake(snake);
            sendAll(name + " has left the chat");
            System.out.println("Il reste "+clients.size()+" clients");
            try {
                
                ois.close();
                oos.close();    // lui
                
                if(!client.isClosed()){
                    client.close();
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }


    public Server(){
        clients = new ArrayList<ConnexionHandle>();
        engine = EngineSnakeOnline.createEngineSnakeOnline(1000, 1000,this);
    }


    public void sendAll(String msg){
        for(ConnexionHandle client : clients){
            if(client != null){
                client.sendMessage(msg);
            }
        }
    }

    public void sendSnakeAll(){
        for(ConnexionHandle client : clients){
            if(client != null){
                client.sendSnake();
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
            // TODO Auto-generated catch block
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
            // TODO Auto-generated catch block
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

    
    public static void main(String[] args) {
        try {
            new ImageBank().loadImages();
        } catch (NullPointerException e) {
            System.out.println("Error while loading images");
        }

        Server server = new Server();
        server.run();   
    }
}