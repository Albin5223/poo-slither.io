package model;

import java.util.ArrayList;
import java.util.HashMap;

import exceptions.ExceptionCollision;
import exceptions.ExecptionAddSnake;
import interfaces.Coordinate;
import interfaces.Engine;
import interfaces.Observer;
import interfaces.Orientation.Direction;
import model.SnakeInteger.SnakePartInteger;

public class EngineSnake implements Engine<Integer,Direction> {

    private Plateau<Integer,Direction> plateau;
    SnakeInteger[] snakes;
    Player[] players;
    int nbSnake = 0;
    ArrayList<Observer> observers;

    private EngineSnake(int n,SnakeInteger[] snakes, Plateau<Integer,Direction> plateau){
        this.snakes = snakes;
        this.players = new Player[n];
        this.plateau = plateau;
        this.observers = new ArrayList<Observer>();
    }

    public static EngineSnake createSnake(int n,int width, int height){
        SnakeInteger[] s = new SnakeInteger[n];
        PlateauInteger p = PlateauInteger.createPlateauSnake(width, height);
        return new EngineSnake(n,s,p);
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
        for(SnakeInteger snake : snakes){
            try {
                snake.move();
            } catch (ExceptionCollision e) {
                snake.resetSnake(new CoordinateInteger(0, 0), Direction.RIGHT, SnakeInteger.SIZE_OF_SNAKE_BIRTH);
            }
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
    public Coordinate<Integer, Direction>[] getAllPosition() {
        ArrayList<SnakePartInteger[]> snakeParts = new ArrayList<SnakePartInteger[]>();
        for(SnakeInteger snake : snakes){
            snakeParts.add(snake.getAllSnakePart());
        }
        ArrayList<CoordinateInteger> allPosition = new ArrayList<CoordinateInteger>();

        for(SnakePartInteger[] snakePart : snakeParts){
            for(SnakePartInteger part : snakePart){
                allPosition.add(new CoordinateInteger(part.getCenter().getX(), part.getCenter().getY()));
            }
        }

        CoordinateInteger[] allPositionArray = new CoordinateInteger[allPosition.size()];
        for (int i = 0; i < allPosition.size(); i++) {
            allPositionArray[i] = allPosition.get(i);
        }
        return allPositionArray;
    }

    @Override
    public Player[] getPlayers() {
        return players;
    }

    @Override
    public SnakeInteger[] getSnakes() {
        return snakes;
    }

     public void addPlayerWithCoord(CoordinateInteger coord,char left, char right) throws ExecptionAddSnake{
        if(nbSnake >= snakes.length){
            throw new ExecptionAddSnake("Action impossible");
        }
        SnakeInteger newSnake = new SnakeInteger(coord, plateau, Direction.DOWN);
        Player newPlayer = new Player(left, right);
        players[nbSnake] = newPlayer;
        snakes[nbSnake] = newSnake;
        notifyObservers();
        nbSnake++;
    }
}
