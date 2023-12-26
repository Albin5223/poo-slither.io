package model.engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import controleur.KeyboardControler;
import interfaces.Coordinate;
import interfaces.Engine;
import interfaces.HumanPlayer;
import interfaces.Observer;
import interfaces.Orientation.Direction;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import model.SnakeData;
import model.foods.FoodHolder;
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
    ArrayList<SnakeMover<Integer,Direction>> snakeMovers;
    ArrayList<HumanSnakePlayer> players;
    
    ArrayList<Observer<Integer,Direction>> observers;
    ArrayList<Color> colors;

    private EngineSnake(ArrayList<SnakeInteger> snakes, PlateauInteger plateau){
        this.snakeMovers = new ArrayList<SnakeMover<Integer,Direction>>();
        for(SnakeInteger snake : snakes){
            this.snakeMovers.add(new SnakeMover<Integer,Direction>(snake,this));
        }
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
    public HashMap<Coordinate<Integer,Direction>, FoodHolder<Integer>> getAllFood() {
        HashMap<Coordinate<Integer,Direction>, FoodHolder<Integer>> copie = new HashMap<Coordinate<Integer,Direction>, FoodHolder<Integer>>();
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
        SnakeInteger newSnake = SnakeInteger.createSnakeInteger(plateau);
        HumanSnakePlayer newPlayer = new HumanSnakePlayer(newSnake, snakeControler);
        players.add(newPlayer);
        Random rand = new Random();
        int red = rand.nextInt(255);
        int green = rand.nextInt(255);
        int blue = rand.nextInt(255);
        Color newColor = Color.rgb(red, green, blue);
        colors.add(newColor);
        snakeMovers.add(new SnakeMover<Integer,Direction>(newSnake,this));
        notifyObservers();
        
    }

    @Override
    public void makePressed(KeyEvent ev, HumanPlayer player) {
        for(HumanSnakePlayer p : players){
            if(p == player){
                p.keyPressed(ev);
                return;
            }
        }
    }

    @Override
    public void makeReleased(KeyEvent ev, HumanPlayer player) {
        for(HumanSnakePlayer p : players){
            if(p == player){
                p.keyReleased(ev);
                return;
            }
        }
        
    }

    @Override
    public ArrayList<SnakeData<Integer, Direction>> getAllSnake() {
        ArrayList<SnakeData<Integer, Direction>> allSnake = new ArrayList<SnakeData<Integer, Direction>>();
        for(int i = 0; i < snakeMovers.size(); i++){
            allSnake.add(new SnakeData<Integer, Direction>(snakeMovers.get(i).getSnake(), colors.get(i)));
        }
        return allSnake;
    }

    @Override
    public void run() {
        for(SnakeMover<Integer,Direction> snakeMover : snakeMovers){
            snakeMover.start();
        }
    }

    @Override
    public void stop() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'stop'");
    }

    
}
