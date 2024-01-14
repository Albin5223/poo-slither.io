package model.engine;

import java.util.ArrayList;
import java.util.List;

import configuration.ConfigurationFoodInteger;
import configuration.ConfigurationSnakeInteger;
import controleur.KeyboardControler;
import interfaces.Engine;
import interfaces.HumanPlayer;
import interfaces.Observer;
import interfaces.Orientation.Direction;
import javafx.animation.AnimationTimer;
import javafx.scene.input.KeyEvent;
import model.FoodData;
import model.SnakeData;
import model.coordinate.Coordinate;
import model.plateau.PlateauInteger.BorderInteger;
import model.plateau.Snake;
import model.plateau.Plateau;
import model.plateau.PlateauInteger;
import model.plateau.SnakeInteger;
import model.player.HumanSnakePlayer;
import model.player.Bot.BotSnakePlayer;

public class EngineSnake implements Engine<Integer,Direction> {

    protected PlateauInteger plateau;
    protected ArrayList<SnakeMover<Integer,Direction>> snakeMovers = new ArrayList<SnakeMover<Integer,Direction>>();
    protected ArrayList<HumanSnakePlayer> players;
    protected ArrayList<Observer<Integer,Direction>> observers;
    protected ArrayList<BotSnakePlayer> bots;

    private AnimationTimer animationEffect;
    private long lastUpdate = 0;

    protected EngineSnake(ArrayList<SnakeInteger> snakes, PlateauInteger plateau){
        for(SnakeInteger snake : snakes){
            this.snakeMovers.add(new SnakeMover<Integer,Direction>(snake,this,null));
        }
        this.players = new ArrayList<HumanSnakePlayer>();
        this.plateau = plateau;
        this.bots = new ArrayList<BotSnakePlayer>();
        this.observers = new ArrayList<Observer<Integer,Direction>>();

        animationEffect = new AnimationTimer() {
            @Override
            public void handle(long now) {
                
                if((now-lastUpdate) >= 1_000_000_000){
                    //System.out.println("Update");
                    List<Snake<Integer,Direction>> snakes = new ArrayList<>(plateau.getHashMap().values());
                    for(Snake<Integer,Direction> snake : snakes){
                        if(snake.underEffect()){
                            snake.applyEffect();
                        }
                    }
                    lastUpdate = now;
                }
                
            }
        };
    }


    @Override
    public Plateau<Integer, Direction> getPlateau() {
        return plateau;
    }

    public static EngineSnake createGame(int width, int height, ConfigurationFoodInteger foodConfig, ConfigurationSnakeInteger config){
        ArrayList<SnakeInteger> s = new ArrayList<SnakeInteger>();
        PlateauInteger p = PlateauInteger.createPlateauSnake(width, height, foodConfig, config);
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
    public List<FoodData<Integer,Direction>> getAllFood(Coordinate<Integer,Direction> coord, double radius) {
        return plateau.getFoods().getRenderZone(coord, radius);
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

    public BotSnakePlayer removeRandomBot(){
        if(bots.size() > 0){
            int index = (int) (Math.random() * bots.size());
            BotSnakePlayer bot = bots.get(index);
            bots.remove(index);
            SnakeMover<Integer,Direction> botMover = snakeMovers.remove(index);
            botMover.stop();
            plateau.removeSnake(bot.getSnake());
            return bot;
        }
        return null;
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
        animationEffect.start();
        for(SnakeMover<Integer,Direction> snakeMover : snakeMovers){
            snakeMover.start();
        }
    }

    @Override
    public void stop() {
        animationEffect.stop();
        for(SnakeMover<Integer,Direction> snakeMover : snakeMovers){
            snakeMover.stop();
        }
    }

    @Override
    public Coordinate<Integer,Direction> getMainSnakeCenter() {
        if(players.size() == 1){
            return players.get(0).getSnake().getHead().getCenter();
        }
        else{
            return null;
        }
    }

    
}
