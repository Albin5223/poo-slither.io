package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import GUI.Window;
import interfaces.Data;
import interfaces.GameBorder;
import interfaces.Observable;
import interfaces.Observer;
import interfaces.Orientation;
import interfaces.Turnable.Turning;
import javafx.application.Platform;
import javafx.scene.input.KeyEvent;
import model.FoodData;
import model.SnakeData;
import model.coordinate.Coordinate;
import model.paquet.snake.PaquetSnakeCtoS;
import model.paquet.snake.PaquetSnakeFirstCtoS;
import model.paquet.snake.PaquetSnakeFirstStoC;
import model.skins.Skin;
import server.ServerMain;

public final class ClientMain <Type extends Number & Comparable<Type>, O extends Orientation<O>> implements Runnable, Data<Type,O>,Observable<Type,O> {

    private String pseudo;
    private String ip;
    private Skin skin;

    private Object lock = new Object();

    private ArrayList<Observer<Type, O>> observers = new ArrayList<Observer<Type, O>>();
    private ClientFactory<Type,O> clientFactory;
    private Socket client;
    private ObjectInputStream ois;
    private ObjectOutputStream oos; 

    public ClientMain(ClientFactory<Type,O> clientFactory){
        this.clientFactory = clientFactory;
        this.addObserver(clientFactory.getPlayPage());
    }

    @Override
    @SuppressWarnings("unchecked")
    public void run() {
        try {
            client = new Socket(ip, ServerMain.port);
            
            ois = new ObjectInputStream(client.getInputStream());
            oos = new ObjectOutputStream(client.getOutputStream());

            /*
             * Ordre à suivre :
             * - On envoie au serveur notre pseudo, skin, et la taille de notre écran
             * - Le serveur nous envoie la border qu'on va stocker pour de bon
             */

            // Etape 1 : On envoie au serveur notre pseudo, skin, et la taille de notre écran
            oos.writeObject(new PaquetSnakeFirstCtoS(pseudo,skin, Window.WITDH, Window.HEIGHT));

            // Etape 2 : On reçoit la border du serveur
            PaquetSnakeFirstStoC<Type,O> paquet = (PaquetSnakeFirstStoC<Type,O>) ois.readObject();
            clientFactory.setBorder(paquet.getBorder());
            System.out.println("Border received : " + clientFactory.getBorder());
    
            // On commence les échanges avec le serveur
            /*
             * Ordre à suivre :
             * - On recoit nos informations pour dessiner notre page (foods, snakes,...)
             * - On met à jour notre page
             */
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    // On recoit nos informations pour dessiner notre page (foods, snakes,...)
                    //System.out.println("Client : waiting for info");
                    
                    clientFactory.readObject(ois);
                    //System.out.println("Client : info received");
                    
                    // On met à jour notre page
                    Platform.runLater(() -> notifyObservers());

                    // On envoie au serveur nos informations (turning, boosting, ...)
                    oos.reset();
                    clientFactory.writeObject(oos);
                    clientFactory.setTurning(Turning.FORWARD);
                } catch (ClassNotFoundException e) {
                    System.out.println("Echec de la lecture");
                    e.printStackTrace();
                }
            }
            shutdown(); // Au cas où le Thread est interrompu avant d'avoir pu faire appel à shutdown()
        } catch (IOException | ClassNotFoundException e) {
        }
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }
    public void setIp(String ip) {
        this.ip = ip;
    }
    public String getIp() {
        return ip;
    }

    public void setSkin(Skin skin) {
        this.skin = skin;
    }

    public Skin getSkin() {
        return skin;
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
    public void addObserver(Observer<Type, O> o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(Observer<Type, O> o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers() {
        for (Observer<Type, O> observer : observers) {
            observer.update(this);
        }
    }

    @Override
    public ArrayList<SnakeData<Type, O>> getAllSnake() {
        return clientFactory.getAllSnake();
    }

    @Override
    public List<FoodData<Type, O>> getAllFood(Coordinate<Type, O> coordinate, double radius) {
        return clientFactory.getAllFood();
    }

    @Override
    public GameBorder<Type, O> getGameBorder() {
        return clientFactory.getBorder();
    }

    @Override
    public Coordinate<Type, O> getMainSnakeCenter() {
        return clientFactory.getMainSnakeCenter();
    }

    public void setKeyCode(KeyEvent ev){
        synchronized(lock){
            clientFactory.setKeyCode(ev);
        }
    }

    public void setReleasedKeyCode(KeyEvent ev){
        synchronized(lock){
            clientFactory.setReleasedKeyCode(ev);
        }
    }
    
}
