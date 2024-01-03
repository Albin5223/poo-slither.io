package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import externData.ImageBank;
import model.paquet.FrameSnake;
import model.paquet.PaquetSnake;
import model.plateau.PlateauInteger;
import model.plateau.SnakeInteger;

public class Server implements Runnable{

    private ArrayList<ConnexionHandle> clients;
    private boolean done;
    private ServerSocket server;
    public final static int port = 3000;

    private ExecutorService pool;

    private PlateauInteger plateau;

    public class ConnexionHandle implements Runnable{

        private Socket client;
        PrintWriter out;
        BufferedReader in;
        ObjectInputStream ois;
        ObjectOutputStream oos;
        String name;
        SnakeInteger snake;

        public ConnexionHandle(Socket client){
            this.client = client;
        }
        @Override
        public void run() {
            try{
                out = new PrintWriter(client.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                oos = new ObjectOutputStream(client.getOutputStream());
                ois = new ObjectInputStream(client.getInputStream());

                try{ 
                    snake = SnakeInteger.createSnakeInteger(plateau);
                    
                    oos.writeObject(PaquetSnake.createPaquetWithMessage("Please enter a nickname"));


                    FrameSnake frame = new FrameSnake (snake.getHead().getCenter(),400,400);
                }
                catch (IOException e){
                    System.out.println("Echec de l'envoie");
                    e.printStackTrace();
                }
                
                name = in.readLine();
                System.out.println("New client : " + name);
                sendAll(name + " has joined the chat");



                //Lis les objets qu'il reçoit
                String message;
                while((message = in.readLine()) != null){
                    sendAll(name + " : " + message);

                    if(message.equals("exit")){
                        sendAll(name + " has left the chat");
                        this.close();
                    }
                }

            }catch(IOException e){
                System.out.println("Echec");
            }
        
        }

        public void sendMessage(String msg){
            PaquetSnake ps = PaquetSnake.createPaquetWithMessage(msg);
            try {
                oos.writeObject(ps);
            } catch (IOException e) {
                
            }
        }

        public void close(){
            try {
                in.close();
                out.close();
                
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
        plateau = PlateauInteger.createPlateauSnake(1000, 1000);
        done = false;
    }


    public void sendAll(String msg){
        for(ConnexionHandle client : clients){
            if(client != null){
                client.sendMessage(msg);
            }
        }
    }

    @Override
    public void run() {
        try {
            server = new ServerSocket(port);
            
            pool = Executors.newCachedThreadPool();
            while(!done){
                Socket client = server.accept();
                ConnexionHandle handle = new ConnexionHandle(client);
                clients.add(handle);
                pool.execute(handle);
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    public void shutdown(){
        done = true;
        try {
            if(!server.isClosed()){
                server.close();
            }
            for(ConnexionHandle client : clients){
                client.close();
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
       
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