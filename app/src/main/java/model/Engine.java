package model;

import java.util.ArrayList;

import exceptions.ExceptionCollision;
import exceptions.ExecptionAddSnake;
import exceptions.ExecptionMoveInvalid;
import interfaces.Data;
import interfaces.Observable;
import interfaces.Observer;
import model.Snake.SnakePart;
import model.Snake.SnakePart.Direction;


public class Engine implements Observable, Data{
    
    private Plateau plateau;
    Snake[] snakes;
    Player[] players;
    int nbSnake = 0;
    ArrayList<Observer> observers;

    public Engine(int n){
        snakes = new Snake[n];
        players = new Player[n];
        this.plateau = new Plateau();
        this.observers = new ArrayList<Observer>();
    }


    public void addPlayer(char left, char right) throws ExecptionAddSnake {
        addPlayerWithCoord(new CoordinateInteger(0, 0), left, right);
    }

    public void addPlayerWithCoord(CoordinateInteger coord,char left, char right) throws ExecptionAddSnake{
        if(nbSnake >= snakes.length){
            throw new ExecptionAddSnake("Action impossible");
        }
        Snake newSnake = new Snake(coord, plateau, Direction.UP);
        Player newPlayer = new Player(left, right);
        players[nbSnake] = newPlayer;
        snakes[nbSnake] = newSnake;
        notifyObservers();
        nbSnake++;
    }


    @Override
    public ArrayList<CoordinateInteger> getAllPosition() {
        ArrayList<SnakePart[]> snakeParts = new ArrayList<SnakePart[]>();
        for(Snake snake : snakes){
            snakeParts.add(snake.getAllSnakePart());
        }
        ArrayList<CoordinateInteger> allPosition = new ArrayList<CoordinateInteger>();

        for(SnakePart[] snakePart : snakeParts){
            for(SnakePart part : snakePart){
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
        for (Snake snake : snakes) {
            try {
                snake.move();
            } catch (ExceptionCollision e ) {
                snake.resetSnake(new CoordinateInteger(0, 0), Direction.UP);
            }
        }
        notifyObservers();
    }

    public Snake[] getSnakes() {
        return snakes;
    }

    public Player[] getPlayers() {
        return players;
    }

    public int getNbSnake() {
        return nbSnake;
    }

}
