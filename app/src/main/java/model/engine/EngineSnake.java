package model.engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import controleur.KeyboardControler;
import exceptions.ExceptionCollision;
import interfaces.Coordinate;
import interfaces.Engine;
import interfaces.Observer;
import interfaces.Orientation.Direction;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import model.Commestible;
import model.SnakeData;
import model.plateau.PlateauInteger.BorderInteger;
import model.plateau.PlateauInteger;
import model.plateau.SnakeInteger;
import model.player.HumanSnakePlayer;

public class EngineSnake implements Engine<Integer,Direction> {
    /*
     * 
     * 
     * 
     * FAIRE EN SORTE QUE IL Y A AU MAX 60% de nourriture
     */
    private PlateauInteger plateau;
    ArrayList<SnakeInteger> snakes;
    ArrayList<HumanSnakePlayer> players;
    
    ArrayList<Observer<Integer,Direction>> observers;
    ArrayList<Color> colors;

    private EngineSnake(ArrayList<SnakeInteger> snakes, PlateauInteger plateau){
        this.snakes = snakes;
        this.players = new ArrayList<HumanSnakePlayer>();
        this.plateau = plateau;
        this.colors = new ArrayList<Color>();
        this.observers = new ArrayList<Observer<Integer,Direction>>();
    }

    public static EngineSnake createSnake(int width, int height){
        ArrayList<SnakeInteger> s = new ArrayList<SnakeInteger>();
        PlateauInteger p = PlateauInteger.createPlateauSnake(width, height);
        return new EngineSnake(s,p);
    }

    @Override
    public void addObserver(Observer<Integer,Direction> o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(Observer<Integer,Direction> o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers() {
        for (Observer<Integer,Direction> observer : observers) {
            observer.update(this);
        }
    }

    @Override
    public void move() {
        for(SnakeInteger snake : snakes){
            try {
                snake.move();
            } catch (ExceptionCollision e) {
                SnakeInteger.resetSnake(snake);
            }
        }
        Random r = new Random();
        if(r.nextInt(100) < 50){
            ((PlateauInteger) plateau).addOneFood();
        }
        notifyObservers();
    }

    @Override
    public double getRadius() {
        return 0;
    }

    @Override
    public HashMap<Coordinate<Integer,Direction>, Commestible> getAllFood() {
        HashMap<Coordinate<Integer,Direction>, Commestible> copie = new HashMap<Coordinate<Integer,Direction>, Commestible>();
        for(Coordinate<Integer,Direction> coord : plateau.getNourritures().keySet()){
            copie.put(coord, plateau.getNourritures().get(coord));
        }
        return copie;
    }

    @Override
    public BorderInteger getGameBorder() {
        return (BorderInteger) plateau.getBorder();
    }
    
    public ArrayList<HumanSnakePlayer> getPlayers() {
        return players;
    }


    public void addPlayer(KeyboardControler<Integer,Direction> snakeControler){
        SnakeInteger newSnake = SnakeInteger.creatSnakeInteger(plateau);
        HumanSnakePlayer newPlayer = new HumanSnakePlayer(newSnake, snakeControler);
        players.add(newPlayer);
        Random rand = new Random();
        int red = rand.nextInt(255);
        int green = rand.nextInt(255);
        int blue = rand.nextInt(255);
        Color newColor = Color.rgb(red, green, blue);
        colors.add(newColor);
        snakes.add(newSnake);
        notifyObservers();
        
    }

    @Override
    public void makePressed(KeyEvent ev) {
        for(HumanSnakePlayer player : players){
            player.execute(ev);
        }
    }

    @Override
    public void makeReleased(KeyEvent ev) {
    }

    @Override
    public ArrayList<SnakeData<Integer, Direction>> getAllSnake() {
        ArrayList<SnakeData<Integer, Direction>> snakesData = new ArrayList<SnakeData<Integer, Direction>>();
        for(int i = 0; i < snakes.size(); i++){
            snakesData.add(new SnakeData<Integer, Direction>(snakes.get(i), colors.get(i)));
        }
        return snakesData;
    }

    
}
