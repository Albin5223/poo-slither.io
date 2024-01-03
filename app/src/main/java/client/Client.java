package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import client.GUI.PlayPageSnakeOnline;
import interfaces.Data;
import interfaces.GameBorder;
import interfaces.Observable;
import interfaces.Observer;
import interfaces.Orientation.Direction;
import model.SnakeData;
import model.coordinate.Coordinate;
import model.foods.Food;
import model.paquet.PaquetSnake;
import model.plateau.PlateauInteger;
import model.plateau.Snake;
import model.plateau.SnakeInteger;
import server.Server;

public class Client implements Runnable, Data<Integer,Direction>,Observable<Integer,Direction>{

    //On a besoin de 2 threads, le premier pour envoyer nos messages au serveur
    //et le second pour recevoir les messages du serveur
    private Socket client;
    private PrintWriter out;
    private BufferedReader in;

    ObjectInputStream ois;
    ObjectOutputStream oos;
    SnakeInteger snake;
    PlateauInteger plateau;
    ArrayList<Observer<Integer, Direction>> observers;
        
    private boolean done;
    PlayPageSnakeOnline playPageSnakeOnline;


    @Override
    public void run() {
        try{
            observers = new ArrayList<Observer<Integer, Direction>>();
            done = false;
            client = new Socket("localhost", Server.port);
            playPageSnakeOnline = new PlayPageSnakeOnline();
            observers.add(playPageSnakeOnline);
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
                    snake = message.getSnake();
                    plateau = (PlateauInteger) snake.getPlateau();
                    System.out.println("Mon snake est en : " + snake.getHead().getCenter().getX() + " " + snake.getHead().getCenter().getY());
                    notifyObservers();

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

    @Override
    public ArrayList<SnakeData<Integer, Direction>> getAllSnake() {
        ArrayList<SnakeData<Integer, Direction>> allSnake = new ArrayList<SnakeData<Integer, Direction>>();
        // TODO : ne donner que les snakes qui sont dans le rayon de vision de l'écran
        return allSnake;
    }

    @Override
    public List<Food<Integer, Direction>> getAllFood(Coordinate<Integer, Direction> coordinate, double radius) {
        return plateau.getFoods().getRenderZone(coordinate, radius);
    }

    @Override
    public GameBorder<Integer, Direction> getGameBorder() {
        return plateau.getBorder();
    }

    @Override
    public Snake<Integer, Direction> getMainSnake() {
        return snake;
    }

    public static void main(String[] args) {
        Client client = new Client();
        Thread t = new Thread(client);
        t.start();
    }

    @Override
    public void addObserver(Observer<Integer, Direction> o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(Observer<Integer, Direction> o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers() {
        for (Observer<Integer, Direction> observer : observers) {
            observer.update(this);
        }
    }    
}
