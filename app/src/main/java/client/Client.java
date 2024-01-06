package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import GUI.Window;
import client.GUI.PlayPageSnakeOnline;
import interfaces.Data;
import interfaces.GameBorder;
import interfaces.Observable;
import interfaces.Observer;
import interfaces.Turnable.Turning;
import javafx.application.Platform;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import interfaces.Orientation.Direction;
import model.FoodData;
import model.SnakeData;
import model.coordinate.Coordinate;
import model.paquet.snake.PaquetSnakeFirstCtoS;
import model.paquet.snake.PaquetSnakeFirstStoC;
import model.paquet.snake.PaquetSnakeCtoS;
import model.paquet.snake.PaquetSnakeStoC;
import model.plateau.PlateauInteger.BorderInteger;
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
    private SnakeData<Integer, Direction> snakeData;
    private BorderInteger border;
    private Turning turning = Turning.FORWARD;
    private boolean boosting;

    private ArrayList<SnakeData<Integer, Direction>> snakesToDraw;
    private ArrayList<FoodData<Integer, Direction>> foodsToDraw;

    private KeyCode UP = KeyCode.UP;
    private KeyCode DOWN = KeyCode.DOWN;
    private KeyCode LEFT =  KeyCode.LEFT;
    private KeyCode RIGHT = KeyCode.RIGHT;
    private KeyCode BOOST = KeyCode.SPACE;
    
    public void setKeyCode(KeyEvent ev){
        if (ev.getCode() == this.LEFT) {
            if (snakeData.getOrientation() != Direction.RIGHT && snakeData.getOrientation() != Direction.LEFT) {
                if (snakeData.getOrientation() == Direction.UP) {
                    turning = Turning.GO_LEFT;
                } else {
                    turning = Turning.GO_RIGHT;
                }
            }
        } else if (ev.getCode() == this.RIGHT) {
            if (snakeData.getOrientation() != Direction.RIGHT && snakeData.getOrientation() != Direction.LEFT) {
                if (snakeData.getOrientation() == Direction.UP) {
                    turning = Turning.GO_RIGHT;
                } else {
                    turning = Turning.GO_LEFT;
                }
            }
        } else if (ev.getCode() == this.UP) {
            if (snakeData.getOrientation() != Direction.UP && snakeData.getOrientation() != Direction.DOWN) {
                if (snakeData.getOrientation() == Direction.LEFT) {
                    turning = Turning.GO_RIGHT;
                } else {
                    turning = Turning.GO_LEFT;
                }
            }
        } else if (ev.getCode() == this.DOWN) {
            if (snakeData.getOrientation() != Direction.UP && snakeData.getOrientation() != Direction.DOWN) {
                if (snakeData.getOrientation() == Direction.LEFT) {
                    turning = Turning.GO_LEFT;
                } else {
                    turning = Turning.GO_RIGHT;
                }
            }
        }
        else if(ev.getCode() == this.BOOST){
            boosting = true;
        }
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
            client = new Socket(ip, Server.port);
            
            ois = new ObjectInputStream(client.getInputStream());
            oos = new ObjectOutputStream(client.getOutputStream());

            /*
             * Ordre à suivre :
             * - On envoie au serveur notre pseudo, skin, et la taille de notre écran
             * - Le serveur nous envoie la border qu'on va stocker pour de bon
             */

            // Etape 1 : On envoie au serveur notre pseudo, skin, et la taille de notre écran
            oos.writeObject(new PaquetSnakeFirstCtoS(pseudo, skin, Window.WITDH, Window.HEIGHT));

            // Etape 2 : On reçoit la border du serveur
            PaquetSnakeFirstStoC paquet = (PaquetSnakeFirstStoC) ois.readObject();
            border = paquet.getBorder();
            System.out.println("Border received : " + border);
    
            // On commence les échanges avec le serveur
            /*
             * Ordre à suivre :
             * - On recoit nos informations pour dessiner notre page (foods, snakes,...)
             * - On met à jour notre page
             */
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    // On recoit nos informations pour dessiner notre page (foods, snakes,...)
                    System.out.println("Client : waiting for info");
                    PaquetSnakeStoC message = (PaquetSnakeStoC) ois.readObject();
                    snakesToDraw = message.getAllSnake();
                    foodsToDraw = message.getAllFood();
                    snakeData = message.getSnakeData();
                    System.out.println("Client : info received");
                    
                    // On met à jour notre page
                    Platform.runLater(() -> notifyObservers());

                    // On envoie au serveur nos informations (turning, boosting, ...)
                    oos.reset();
                    oos.writeObject(new PaquetSnakeCtoS(turning, boosting, false));
                    if(turning != Turning.FORWARD){turning = Turning.FORWARD;}
                    boosting = false;
    
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
                oos.writeObject(new PaquetSnakeCtoS(null, false, true));
                ois.close();
                oos.close();
                client.close();
            }
        } catch (IOException e) {
            
        }
    }

    @Override
    public ArrayList<SnakeData<Integer, Direction>> getAllSnake() {
        return snakesToDraw;
    }

    @Override
    public List<FoodData<Integer, Direction>> getAllFood(Coordinate<Integer, Direction> coordinate, double radius) {
        return foodsToDraw;
    }

    @Override
    public GameBorder<Integer, Direction> getGameBorder() {
        return border;
    }

    @Override
    public Coordinate<Integer, Direction> getMainSnakeCenter() {
        return snakeData.getHead();
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
