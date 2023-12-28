package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements Runnable{

    private ArrayList<ConnexionHandle> clients;
    private boolean done;
    private ServerSocket server;
    public final static int port = 3000;

    private ExecutorService pool;



    public class ConnexionHandle implements Runnable{

        private Socket client;
        PrintWriter out;
        BufferedReader in;
        String name;

        public ConnexionHandle(Socket client){
            this.client = client;
        }
        @Override
        public void run() {
            try{
                out = new PrintWriter(client.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(client.getInputStream()));

                out.println("Please enter a nickename");
                name = in.readLine();
                System.out.println("New client : " + name);
                sendAll(name + " has joined the chat");


                String message;
                while((message = in.readLine()) != null){
                    sendAll(name + " : " + message);

                    if(message.equals("exit")){
                        sendAll(name + " has left the chat");
                        this.close();
                    }
                }

            }catch(IOException e){
                
            }
        
        }

        public void send(String msg){
            out.println(msg);
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
        done = false;
    }


    public void sendAll(String msg){
        for(ConnexionHandle client : clients){
            if(client != null){
                client.send(msg);
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
        Server server = new Server();
        server.run();   
    }
}