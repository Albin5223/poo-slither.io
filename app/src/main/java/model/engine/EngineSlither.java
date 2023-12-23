package model.engine;

import java.util.ArrayList;
import java.util.HashMap;

import controleur.KeyboardControler;
import exceptions.ExceptionCollision;
import interfaces.Coordinate;
import interfaces.Engine;
import interfaces.Observer;
import interfaces.Orientation.Angle;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import model.Commestible;
import model.SnakeData;
import model.plateau.PlateauDouble;
import model.plateau.SnakeDouble;
import model.plateau.PlateauDouble.BorderDouble;
import model.player.HumanSlitherPlayer;


public class EngineSlither implements Engine<Double,Angle>{
    
    private PlateauDouble plateau;
    ArrayList<SnakeDouble> snakes;
    ArrayList<Color> colors; 
    ArrayList<Observer<Double,Angle>> observers;
    ArrayList<HumanSlitherPlayer> players;

    private EngineSlither(ArrayList<SnakeDouble> snakes, PlateauDouble plateau){
        this.snakes = snakes;
        this.plateau = plateau;
        this.observers = new ArrayList<Observer<Double,Angle>>();
        this.colors = new ArrayList<Color>();
        this.players = new ArrayList<HumanSlitherPlayer>();
    }

    public static EngineSlither createSnake(int n){
        ArrayList<SnakeDouble> s = new ArrayList<>();
        PlateauDouble p = PlateauDouble.createPlateauSlitherio();
        return new EngineSlither(s,p);
    }

    public void addPlayer(KeyboardControler<Double,Angle> snakeControler){
        SnakeDouble newSnake = SnakeDouble.createSnakeDouble(plateau);
        HumanSlitherPlayer newPlayer = new HumanSlitherPlayer(newSnake, snakeControler);
        players.add(newPlayer);
        Color newColor = Color.DARKBLUE;
        colors.add(newColor);
        snakes.add(newSnake);
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
    public void move() {
        for (SnakeDouble snake : snakes) {
            try {
                snake.move();
            } catch (ExceptionCollision e ) {
                SnakeDouble.resetSnake(snake);
            }
        }
        notifyObservers();
    }


    @Override
    public double getRadius() {
        return snakes.get(0).getRadius();
    }

    @Override
    public HashMap<Coordinate<Double,Angle>, Commestible> getAllFood() {
        HashMap<Coordinate<Double,Angle>, Commestible> copie = new HashMap<Coordinate<Double,Angle>, Commestible>();
        for(Coordinate<Double,Angle> coord : plateau.getNourritures().keySet()){
            copie.put(coord, plateau.getNourritures().get(coord));
        }
        return copie;
    }

    @Override
    public BorderDouble getGameBorder() {
        return (BorderDouble) plateau.getBorder();
    }

   
    @Override
    public ArrayList<SnakeData<Double, Angle>> getAllSnake() {
        ArrayList<SnakeData<Double, Angle>> allSnake = new ArrayList<SnakeData<Double, Angle>>();
        for(int i = 0; i < snakes.size(); i++){
            allSnake.add(new SnakeData<Double, Angle>(snakes.get(i), colors.get(i)));
        }
        return allSnake;
    }

    @Override
    public void makePressed(KeyEvent ev) {
        for(HumanSlitherPlayer player : players){
            player.execute(ev);
        }
    }

    @Override
    public void makeReleased(KeyEvent ev) {
        for(HumanSlitherPlayer player : players){
            player.released(ev);
        }
    }

}
