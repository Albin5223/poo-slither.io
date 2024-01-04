package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import client.GUI.PlayPageSnakeOnline;
import interfaces.Data;
import interfaces.GameBorder;
import interfaces.Observable;
import interfaces.Observer;
import interfaces.Turnable.Turning;
import interfaces.Orientation.Direction;
import model.SnakeData;
import model.coordinate.Coordinate;
import model.foods.Food;
import model.paquet.PaquetSnake;
import model.plateau.PlateauInteger;
import model.plateau.Snake;
import model.plateau.SnakeInteger;
import model.skins.Skin;
import server.Server;

public class Client implements Runnable, Data<Integer,Direction>,Observable<Integer,Direction>{

    //On a besoin de 2 threads, le premier pour envoyer nos messages au serveur
    //et le second pour recevoir les messages du serveur
    private Socket client;
    

    ObjectInputStream ois;
    ObjectOutputStream oos;

    private String pseudo;
    public void setPseudo(String pseudo){this.pseudo = pseudo;}
    private String ip;
    public void setIp(String ip){this.ip = ip;}
    private Skin skin;
    public void setSkin(Skin skin){this.skin = skin;}
    private SnakeInteger snake;
    private PlateauInteger plateau;
    private Turning turning;

    public void setTurning(Turning turning) {
        this.turning = turning;
    }

    PlayPageSnakeOnline playPageSnakeOnline;
    public void setPlayPage(PlayPageSnakeOnline page) {
        playPageSnakeOnline = page;
        observers.add(page);
    }
    
    ArrayList<Observer<Integer, Direction>> observers = new ArrayList<Observer<Integer, Direction>>();
        
    private boolean done = false;

    
    


    @Override
    public void run() {
        try{
            System.out.println("Client started");
            
            done = false;
            client = new Socket(ip, Server.port);
            
            System.out.println("Client connected");
            

            ois = new ObjectInputStream(client.getInputStream());
			oos = new ObjectOutputStream(client.getOutputStream());


            //Envoyer les messages depuis le terminal
            InputHandler input = new InputHandler();
            Thread t = new Thread(input);
            t.start();


            //Recevoir les messages
            PaquetSnake message;
            int i = 0;
            System.out.println("Client is waiting for messages");
            while(!done){
                try{
                    
                    message = (PaquetSnake) ois.readObject();
                    System.out.println(message.getMessage());
                    SnakeInteger subSnake = message.getSnake();

                    if(subSnake == null){
                        System.out.println("Snake is null... in "+i+" th iteration");
                    }
                    else{
                        snake = subSnake;
                        plateau = (PlateauInteger) snake.getPlateau();
                    }
                    
                    System.out.println("Mon snake est en : " + snake.getHead().getCenter().getX() + " " + snake.getHead().getCenter().getY());
                    notifyObservers();
                    i++;
                }
                catch (ClassNotFoundException e){
                }
                
            }
            System.out.println("Client is done");
        }catch(IOException e){
            
        }
    }

    public void shutdown(){
        done = true;
        try {
            oos.writeObject(PaquetSnake.createPaquetToQuit());
            
            ois.close();
            oos.close();
            client.close();
        } catch (IOException e) {
            
        }
    }




    //Gestionnnaire pour envoyer des messages
    public class InputHandler implements Runnable{
        //InputHandler va envoyer un premier message qui correspond a son nom et a son skin
        //Puis il va juste envoyer au serveur son turning
        @Override
        public void run() {
            try{
                oos.writeObject(PaquetSnake.createPaquetWithMessageAndSkin(pseudo,skin));
                while(!done){
                    oos.writeObject(PaquetSnake.createPaquetWithTurning(turning));
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

    public static void main(String[] args) {
        Client client = new Client();
        Thread t = new Thread(client);
        t.start();
    }
}
