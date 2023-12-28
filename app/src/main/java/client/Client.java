package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import server.Server;

public class Client implements Runnable{

    //On a besoin de 2 threads, le premier pour envoyer nos messages au serveur
    //et le second pour recevoir les messages du serveur
    private Socket client;
    private PrintWriter out;
    private BufferedReader in;

    private boolean done;


    @Override
    public void run() {
        try{
            client = new Socket("127.0.0.1", Server.port);
            out = new PrintWriter(client.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));

            InputHandler input = new InputHandler();
            Thread t = new Thread(input);
            t.start();


            String message;
            while((message = in.readLine()) != null){
                System.out.println(message);
            }
        }catch(IOException e){
            
        }
    }

    public void shutdown(){
        done = true;
        try {
            in.close();
            out.close();
            client.close();
        } catch (IOException e) {
            
        }
    }



    public class InputHandler implements Runnable{

        @Override
        public void run() {
            try{
                BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
                String message;
                while(!done){
                    message = stdIn.readLine();
                    if(message.equals("exit")){
                        stdIn.close();
                        shutdown();
                    }
                    else{
                        out.println(message);
                    }
                    
                }
                
            }catch(IOException e){
                
            }
        }
    }

    public static void main(String[] args) {
        Client client = new Client();
        Thread t = new Thread(client);
        t.start();
    }
    
}
