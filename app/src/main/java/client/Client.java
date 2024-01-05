package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import client.GUI.PlayPageSnakeOnline;
import interfaces.Data;
import interfaces.GameBorder;
import interfaces.Observable;
import interfaces.Observer;
import interfaces.Turnable.Turning;
import javafx.application.Platform;
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
    public PlayPageSnakeOnline getPlayPageSnakeOnline() {
        return playPageSnakeOnline;
    }
    ArrayList<Observer<Integer, Direction>> observers = new ArrayList<Observer<Integer, Direction>>();

    public Client(){
        playPageSnakeOnline = new PlayPageSnakeOnline();
        observers.add(playPageSnakeOnline);
    }
    


    @Override
    public void run() {
        try {
            System.out.println("Client started");

            client = new Socket(ip, Server.port);
    
            System.out.println("Client connected");
            
            ois = new ObjectInputStream(client.getInputStream());
            oos = new ObjectOutputStream(client.getOutputStream());
    
            PaquetSnake msg = (PaquetSnake) ois.readObject();
            System.out.println(msg.getMessage());
            snake = msg.getSnake();
            System.out.println("Mon first snake est en : " + snake.getHead().getCenter().getX() + " " + snake.getHead().getCenter().getY());
            plateau = (PlateauInteger) snake.getPlateau();

            oos.reset();
            oos.writeObject(PaquetSnake.createPaquetWithMessageAndSkin(pseudo,skin));
    
            //Recevoir les messages
    
            System.out.println("Client is waiting for messages");
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    PaquetSnake message = (PaquetSnake) ois.readObject();
                    this.snake = message.getSnake();
                    this.plateau = (PlateauInteger) this.snake.getPlateau();
    
                    System.out.println("Mon snake est en : " + this.snake.getHead().getCenter().getX() + " " + this.snake.getHead().getCenter().getY());
                    Platform.runLater(() -> notifyObservers());

                    oos.reset();
                    oos.writeObject(PaquetSnake.createPaquetWithTurning(turning));
    
                } catch (ClassNotFoundException e) {
                    System.out.println("Echec de la lecture");
                    e.printStackTrace();
                }
            }
            shutdown(); // Au cas où le Thread est interrompu avant d'avoir pu faire appel à shutdown()
        } catch (IOException | ClassNotFoundException e) {
    
        }
    }

    public void shutdown(){
        if(!Thread.currentThread().isInterrupted()){
            Thread.currentThread().interrupt();
        }
        try {
            if(client != null && !client.isClosed()){
                System.out.println("Client is shuting down");
                oos.reset();
                oos.writeObject(PaquetSnake.createPaquetToQuit());
                ois.close();
                oos.close();
                client.close();
            }
        } catch (IOException e) {
            
        }
    }

    @Override
    public ArrayList<SnakeData<Integer, Direction>> getAllSnake() {
            return plateau.getSnakes().stream()
                   .map(snake -> new SnakeData<Integer, Direction>(snake))
                   .collect(Collectors.toCollection(ArrayList::new));
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
