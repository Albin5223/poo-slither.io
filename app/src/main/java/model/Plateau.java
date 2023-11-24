package model;

import java.util.HashMap;

public class Plateau {
    private HashMap<Coordinate, Snake> plateau;
    private HashMap<Coordinate, Square> nourriures;


    public Plateau() {
        this.plateau = new HashMap<Coordinate, Snake>();
        this.nourriures = new HashMap<Coordinate, Square>();
    }

    public void addSnake(Snake snake)throws IllegalArgumentException{
        if (plateau.containsKey(snake.getHead())) {
            throw new IllegalArgumentException("Snake already exists");
        }
        plateau.put(snake.getHead(), snake);
        for (Coordinate c : snake.getTail()) {
            if(plateau.containsKey(c)) {
                throw new IllegalArgumentException("Snake already exists");
            }
            plateau.put(c, snake);
        }
    }

    public void addFood(Coordinate c) throws IllegalArgumentException{
        if (plateau.containsKey(c)) {
            throw new IllegalArgumentException("Snake already exists");
        }
        nourriures.put(c, Square.FOOD);
    }

    public boolean isFree(Coordinate c){
        return !plateau.containsKey(c);
    }

    public void update(Snake snake,Coordinate last) {
        if(last != null){
            plateau.remove(last);
        }
        plateau.put(snake.getHead(), snake);
    }

    public boolean isEatingFood(Coordinate c){
        return nourriures.containsKey(c);
    }

}
