package model.engine;

import java.util.ArrayList;
import java.util.HashMap;
import controleur.KeyboardControler;
import interfaces.Engine;
import interfaces.HumanPlayer;
import interfaces.Observer;
import interfaces.Orientation.Direction;
import javafx.scene.input.KeyEvent;
import model.SnakeData;
import model.coordinate.Coordinate;
import model.foods.Food;
import model.plateau.PlateauInteger.BorderInteger;
import model.plateau.PlateauInteger;
import model.plateau.SnakeInteger;
import model.player.HumanSnakePlayer;
import model.player.Bot.BotSnakePlayer;

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
    ArrayList<BotSnakePlayer> bots;

    private EngineSnake(ArrayList<SnakeInteger> snakes, PlateauInteger plateau){
        this.snakeMovers = new ArrayList<SnakeMover<Integer,Direction>>();
        for(SnakeInteger snake : snakes){
            this.snakeMovers.add(new SnakeMover<Integer,Direction>(snake,this,null));
        }
        this.players = new ArrayList<HumanSnakePlayer>();
        this.plateau = plateau;
        this.bots = new ArrayList<BotSnakePlayer>();
        this.observers = new ArrayList<Observer<Integer,Direction>>();
    }

    public static EngineSnake createGame(int width, int height){
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
    public HashMap<Coordinate<Integer,Direction>, Food<Integer,Direction>> getAllFood() {
        HashMap<Coordinate<Integer,Direction>, Food<Integer,Direction>> copie = new HashMap<Coordinate<Integer,Direction>, Food<Integer,Direction>>();
        for(Food<Integer,Direction> food : plateau.getFoodTree().valuesCollection()){
            copie.put(food.getCenter(), food);
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

    public void addBot(){
        SnakeInteger newSnake = SnakeInteger.createSnakeInteger(plateau);
        BotSnakePlayer newBot = new BotSnakePlayer(newSnake,plateau);
        bots.add(newBot);
        snakeMovers.add(new SnakeMover<Integer,Direction>(newSnake,this,newBot));
    }


    public void addPlayer(KeyboardControler<Integer,Direction> snakeControler){
        SnakeInteger newSnake = SnakeInteger.createSnakeInteger(plateau);
        HumanSnakePlayer newPlayer = new HumanSnakePlayer(newSnake, snakeControler);
        players.add(newPlayer);
        snakeMovers.add(new SnakeMover<Integer,Direction>(newSnake,this,null));
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
            allSnake.add(new SnakeData<Integer, Direction>(snakeMovers.get(i).getSnake()));
        }
        return allSnake;
    }

    @Override
    public void run() {
        plateau.startAnimation();
        for(SnakeMover<Integer,Direction> snakeMover : snakeMovers){
            snakeMover.start();
        }
    }

    @Override
    public void stop() {
        plateau.stopAnimation();
        for(SnakeMover<Integer,Direction> snakeMover : snakeMovers){
            snakeMover.stop();
        }
    }

    
}
