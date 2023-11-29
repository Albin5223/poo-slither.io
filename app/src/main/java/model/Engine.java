package model;

import java.util.ArrayList;
import java.util.HashMap;

import exceptions.ExceptionCollision;
import exceptions.ExecptionAddSnake;
import interfaces.Coordinate;
import interfaces.Court;
import interfaces.Data;
import interfaces.Observable;
import interfaces.Observer;
import interfaces.Orientation.Angle;
import model.SnakeDouble.SnakePartDouble;


public class Engine implements Observable, Data,Court{
    
    private Plateau<Double,Angle> plateau;
    SnakeDouble[] snakes;
    Player[] players;
    int nbSnake = 0;
    ArrayList<Observer> observers;

    private Engine(int n,SnakeDouble[] snakes, Plateau<Double,Angle> plateau){
        this.snakes = snakes;
        this.players = new Player[n];
        this.plateau = plateau;
        this.observers = new ArrayList<Observer>();
    }

    public static Engine createSnake(int n){
        SnakeDouble[] s = new SnakeDouble[n];
        PlateauDouble p = PlateauDouble.createPlateauSlitherio();
        return new Engine(n,s,p);
    }

    public void addPlayerWithCoord(CoordinateDouble coord,char left, char right) throws ExecptionAddSnake{
        if(nbSnake >= snakes.length){
            throw new ExecptionAddSnake("Action impossible");
        }
        SnakeDouble newSnake = new SnakeDouble(coord, plateau, new Angle(90));
        Player newPlayer = new Player(left, right);
        players[nbSnake] = newPlayer;
        snakes[nbSnake] = newSnake;
        notifyObservers();
        nbSnake++;
    }


    @Override
    public ArrayList<CoordinateDouble> getAllPosition() {
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
        return allPosition;

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
                snake.resetSnake(new CoordinateDouble(0, 0), new Angle(90),30);
            }
        }
        notifyObservers();
    }

    public SnakeDouble[] getSnakes() {
        return snakes;
    }

    public Player[] getPlayers() {
        return players;
    }

    public int getNbSnake() {
        return nbSnake;
    }

    @Override
    public void update() {
        move();
    }

    @Override
    public double getRadius() {
        return snakes[0].getRadius();
    }

    @Override
    public HashMap<Coordinate<Double,Angle>, Commestible> getAllFood() {
        HashMap<Coordinate<Double,Angle>, Commestible> copie = new HashMap<Coordinate<Double,Angle>, Commestible>();
        for(Coordinate<Double,Angle> coord : plateau.nourritures.keySet()){
            copie.put(coord, plateau.nourritures.get(coord));
        }
        return copie;
    }

}
