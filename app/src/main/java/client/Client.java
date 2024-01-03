package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import model.paquet.PaquetSnake;
import server.Server;

public class Client implements Runnable{

    //On a besoin de 2 threads, le premier pour envoyer nos messages au serveur
    //et le second pour recevoir les messages du serveur
    private Socket client;
    private PrintWriter out;
    private BufferedReader in;

    ObjectInputStream ois;
    ObjectOutputStream oos;
        


    private boolean done;


    @Override
    public void run() {
        try{
            client = new Socket("localhost", Server.port);
            out = new PrintWriter(client.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            
            Thread.sleep(1000);
            ois = new ObjectInputStream(client.getInputStream());
			oos = new ObjectOutputStream(client.getOutputStream());


            //Envoyer les messages depuis le terminal
            InputHandler input = new InputHandler();
            Thread t = new Thread(input);
            t.start();


            //Recevoir les messages
            PaquetSnake message;
            while(!done){
                try{
                    
                    message = (PaquetSnake) ois.readObject();
                    System.out.println(message.getMessage());
                    
                }
                catch (ClassNotFoundException e){
                }
                
                
            }
        }catch(IOException | InterruptedException e){
            
        }
    }

    public void shutdown(){
        done = true;
        try {
            in.close();
            out.close();
            ois.close();
            oos.close();
            client.close();
        } catch (IOException e) {
            
        }
    }


    //Gestionnnaire pour envoyer des messages
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
