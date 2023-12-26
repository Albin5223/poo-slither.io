package model.engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import controleur.KeyboardControler;
import interfaces.Coordinate;
import interfaces.Engine;
import interfaces.HumanPlayer;
import interfaces.Observer;
import interfaces.Orientation.Angle;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import model.SnakeData;
import model.foods.FoodHolder;
import model.plateau.PlateauDouble;
import model.plateau.SnakeDouble;
import model.plateau.PlateauDouble.BorderDouble;
import model.player.HumanSlitherPlayer;
import model.player.Bot.BotSlitherPlayer;


public class EngineSlither implements Engine<Double,Angle>{
    
    private PlateauDouble plateau;
    ArrayList<SnakeMover<Double,Angle>> snakeMovers;
    ArrayList<Color> colors; 
    ArrayList<Observer<Double,Angle>> observers;
    ArrayList<HumanSlitherPlayer> players;
    ArrayList<BotSlitherPlayer> bots;

    private EngineSlither(ArrayList<SnakeDouble> snakes, PlateauDouble plateau){
        this.snakeMovers = new ArrayList<SnakeMover<Double,Angle>>();
        for(SnakeDouble snake : snakes){
            this.snakeMovers.add(new SnakeMover<Double,Angle>(snake,this,null));
        }
        this.plateau = plateau;
        this.bots = new ArrayList<BotSlitherPlayer>();
        this.observers = new ArrayList<Observer<Double,Angle>>();
        this.colors = new ArrayList<Color>();
        this.players = new ArrayList<HumanSlitherPlayer>();
    }

    public static EngineSlither createSnake(int n){
        ArrayList<SnakeDouble> s = new ArrayList<>();
        PlateauDouble p = PlateauDouble.createPlateauSlitherio();
        return new EngineSlither(s,p);
    }

    public void addBot(){
        SnakeDouble newSnake = SnakeDouble.createSnakeDouble(plateau);
        BotSlitherPlayer newBot = new BotSlitherPlayer(newSnake, plateau);
        Random rand = new Random();
        int red = rand.nextInt(255);
        int green = rand.nextInt(255);
        int blue = rand.nextInt(255);
        Color newColor = Color.rgb(red, green, blue);
        colors.add(newColor);
        bots.add(newBot);
        snakeMovers.add(new SnakeMover<Double,Angle>(newSnake,this,newBot));
        
    }

    public void addPlayer(KeyboardControler<Double,Angle> snakeControler){
        SnakeDouble newSnake = SnakeDouble.createSnakeDouble(plateau);
        HumanSlitherPlayer newPlayer = new HumanSlitherPlayer(newSnake, snakeControler);
        players.add(newPlayer);
        Random rand = new Random();
        int red = rand.nextInt(255);
        int green = rand.nextInt(255);
        int blue = rand.nextInt(255);
        Color newColor = Color.rgb(red, green, blue);
        colors.add(newColor);
        snakeMovers.add(new SnakeMover<Double,Angle>(newSnake,this,null));
        notifyObservers();
    }

    @Override
    public void addObserver(Observer<Double,Angle> o) {
        observers.add(o);
    }


    @Override
    public void removeObserver(Observer<Double,Angle> o) {
        observers.remove(o);
    }


    @Override
    public void notifyObservers() {
        for (Observer<Double,Angle> observer : observers) {
            observer.update(this);
        }
    }

    @Override
    public HashMap<Coordinate<Double,Angle>, FoodHolder<Double>> getAllFood() {
        HashMap<Coordinate<Double,Angle>, FoodHolder<Double>> copie = new HashMap<Coordinate<Double,Angle>, FoodHolder<Double>>();
        for(Coordinate<Double,Angle> coord : plateau.getNourritures().keySet()){
            copie.put(coord, plateau.getNourritures().get(coord));
        }
        return copie;
    }

    @Override
    public BorderDouble getGameBorder() {
        return (BorderDouble) plateau.getBorder();
    }

    public ArrayList<HumanSlitherPlayer> getPlayers() {
        return players;
    }

   
    @Override
    public ArrayList<SnakeData<Double, Angle>> getAllSnake() {
        ArrayList<SnakeData<Double, Angle>> allSnake = new ArrayList<SnakeData<Double, Angle>>();
        for(int i = 0; i < snakeMovers.size(); i++){
            allSnake.add(new SnakeData<Double, Angle>(snakeMovers.get(i).getSnake(), colors.get(i)));
        }
        return allSnake;
    }

    @Override
    public void makePressed(KeyEvent ev, HumanPlayer player) {
        for(HumanSlitherPlayer p : players){
            if(p == player){
                p.keyPressed(ev);
                return;
            }
        }
    }

    @Override
    public void makeReleased(KeyEvent ev, HumanPlayer player) {
        for(HumanSlitherPlayer p : players){
            if(p == player){
                p.keyReleased(ev);
                return;
            }
        }
    }

    @Override
    public void run() {
        for(SnakeMover<Double,Angle> snakeMover : snakeMovers){
            snakeMover.start();
        }
    }

    @Override
    public void stop() {
        for(SnakeMover<Double,Angle> snakeMover : snakeMovers){
            snakeMover.stop();
        }
    }

}
