package model.engine;

import java.util.ArrayList;
import java.util.List;

import controleur.KeyboardControler;
import interfaces.Engine;
import interfaces.HumanPlayer;
import interfaces.Observer;
import interfaces.Orientation.Angle;
import javafx.scene.input.KeyEvent;
import model.SnakeData;
import model.coordinate.Coordinate;
import model.foods.Food;
import model.plateau.PlateauDouble;
import model.plateau.SnakeDouble;
import model.plateau.PlateauDouble.BorderDouble;
import model.player.HumanSlitherPlayer;
import model.player.Bot.BotSlitherPlayer;


public class EngineSlither implements Engine<Double,Angle>{
    
    private PlateauDouble plateau;
    ArrayList<SnakeMover<Double,Angle>> snakeMovers;
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
        this.players = new ArrayList<HumanSlitherPlayer>();
    }

    public static EngineSlither createGame(int radius){
        ArrayList<SnakeDouble> s = new ArrayList<>();
        PlateauDouble p = PlateauDouble.createPlateauSlitherio(radius);
        return new EngineSlither(s,p);
    }

    public void addBot(){
        SnakeDouble newSnake = SnakeDouble.createSnakeDouble(plateau);
        BotSlitherPlayer newBot = new BotSlitherPlayer(newSnake, plateau);
        bots.add(newBot);
        snakeMovers.add(new SnakeMover<Double,Angle>(newSnake,this,newBot));
        
    }

    public void addPlayer(KeyboardControler<Double,Angle> snakeControler){
        SnakeDouble newSnake = SnakeDouble.createSnakeDouble(plateau);
        HumanSlitherPlayer newPlayer = new HumanSlitherPlayer(newSnake, snakeControler);
        players.add(newPlayer);
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
    public List<Food<Double,Angle>> getAllFood(Coordinate<Double,Angle> coordinate, double radius) {
        return plateau.getFoods().getRenderZone(coordinate, radius);
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
            allSnake.add(new SnakeData<Double, Angle>(snakeMovers.get(i).getSnake()));
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
        plateau.startAnimation();
        for(SnakeMover<Double,Angle> snakeMover : snakeMovers){
            snakeMover.start();
        }
    }

    @Override
    public void stop() {
        plateau.stopAnimation();
        for(SnakeMover<Double,Angle> snakeMover : snakeMovers){
            snakeMover.stop();
        }
    }

}
