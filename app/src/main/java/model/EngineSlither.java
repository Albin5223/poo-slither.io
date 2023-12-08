package model;

import java.util.ArrayList;
import java.util.HashMap;


import exceptions.ExceptionCollision;
import exceptions.ExecptionAddSnake;
import interfaces.Coordinate;
import interfaces.Engine;
import interfaces.Observer;
import interfaces.Orientation.Angle;
import model.SnakeDouble.SnakePartDouble;


public class EngineSlither implements Engine<Double,Angle>{
    
    private Plateau<Double,Angle> plateau;
    SnakeDouble[] snakes;
    int nbSnake = 0;
    ArrayList<Observer> observers;

    private EngineSlither(int n,SnakeDouble[] snakes, Plateau<Double,Angle> plateau){
        this.snakes = snakes;
        this.plateau = plateau;
        this.observers = new ArrayList<Observer>();
    }

    public static EngineSlither createSnake(int n){
        SnakeDouble[] s = new SnakeDouble[n];
        PlateauDouble p = PlateauDouble.createPlateauSlitherio();
        return new EngineSlither(n,s,p);
    }

    public void addPlayerWithCoord(CoordinateDouble coord) throws ExecptionAddSnake{
        if(nbSnake >= snakes.length){
            throw new ExecptionAddSnake("Action impossible");
        }
        SnakeDouble newSnake = new SnakeDouble(coord, plateau, new Angle(90));
        snakes[nbSnake] = newSnake;
        notifyObservers();
        nbSnake++;
    }


    @Override
    public CoordinateDouble[] getAllPosition() {
        ArrayList<SnakePartDouble[]> snakeParts = new ArrayList<SnakePartDouble[]>();
        for(SnakeDouble snake : snakes){
            snakeParts.add(snake.getAllSnakePart());
        }
        ArrayList<CoordinateDouble> allPosition = new ArrayList<CoordinateDouble>();

        for(SnakePartDouble[] snakePart : snakeParts){
            for(SnakePartDouble part : snakePart){
                allPosition.add(part.getCenter());
            }
        }


        CoordinateDouble[] allPositionArray = new CoordinateDouble[allPosition.size()];
        for (int i = 0; i < allPosition.size(); i++) {
            allPositionArray[i] = allPosition.get(i);
        }
        return allPositionArray;

    }


    @Override
    public void addObserver(Observer o) {
        observers.add(o);
    }


    @Override
    public void removeObserver(Observer o) {
        observers.remove(o);
    }


    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(this);
        }
    }


    @Override
    public void move() {
        for (SnakeDouble snake : snakes) {
            try {
                snake.move();
            } catch (ExceptionCollision e ) {
                SnakePartDouble[] all = snake.getAllSnakePart();
                for(SnakePartDouble c : all){
                    plateau.addFood(c.getCenter(), Commestible.DEATH_FOOD);
                }
                snake.resetSnake(new CoordinateDouble(0, 0), new Angle(90),30);
            }
        }
        notifyObservers();
    }

    @Override
    public SnakeDouble[] getSnakes() {
        return snakes;
    }

    public int getNbSnake() {
        return nbSnake;
    }

    @Override
    public double getRadius() {
        return snakes[0].getRadius();
    }

    @Override
    public HashMap<Coordinate<Double,Angle>, Commestible> getAllFood() {
        HashMap<Coordinate<Double,Angle>, Commestible> copie = new HashMap<Coordinate<Double,Angle>, Commestible>();
        for(Coordinate<Double,Angle> coord : plateau.getNourritures().keySet()){
            copie.put(coord, plateau.getNourritures().get(coord));
        }
        return copie;
    }

}
