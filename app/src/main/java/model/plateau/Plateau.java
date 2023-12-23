package model.plateau;

import java.util.HashMap;

import exceptions.ExceptionCollision;
import exceptions.ExceptionCollisionWithFood;
import exceptions.ExceptionCollisionWithSnake;
import interfaces.Coordinate;
import interfaces.GameBorder;
import interfaces.Orientation;
import model.Commestible;

public abstract sealed class Plateau<Type extends Number, O extends Orientation<O>> permits PlateauDouble, PlateauInteger {

    protected HashMap<Coordinate<Type,O>, Snake<Type,O>> plateau;
    protected HashMap<Coordinate<Type,O>, Commestible> nourritures;

    protected GameBorder<Type,O> border;

    private final int NB_FOOD;

    /**
     * The lock used to synchronize the access to the board.
     * This lock is used to avoid concurrent access to the board.
     * Usefull for having snakes with different speed.
     */
    private Object lock = new Object();


    protected Plateau(int nbFood) {
        this.NB_FOOD = nbFood;
        this.plateau = new HashMap<Coordinate<Type,O>, Snake<Type,O>>();
        this.nourritures = new HashMap<Coordinate<Type,O>, Commestible>();
    }

    public HashMap<Coordinate<Type, O>, Commestible> getNourritures() {
        return nourritures;
    }

    public GameBorder<Type,O> getBorder() {
        return border;
    }

    protected boolean isSnakeOnBoard(Snake<Type,O> snake){
        for (Snake<Type,O> s : plateau.values()) {
            if(snake == s){ // We check if the snake is on the board
                return true;
            }
        }
        return false;
    }


    /**
     * Add a snake to the board
     * @param snake the snake to add
     * @throws ExceptionCollisionWithSnake if the snake is colliding with another snake already on the board
     */
    protected void addSnake(Snake<Type,O> snake) throws ExceptionCollisionWithSnake {
        synchronized(lock) {
            if(isCollidingWithAll(snake)){
                throw new ExceptionCollisionWithSnake("Snake added is colliding with another snake");
            }
            plateau.put(snake.getHead().getCenter(), snake);
        }
    }

    /**
     * Remove a snake from the board
     * @param snake the snake to remove
     * @return true if the snake was on the board, false otherwise
     */
    protected boolean removeSnake(Snake<Type,O> snake) {
        synchronized(lock) {
            return plateau.remove(snake.getHead().getCenter()) != null;
        }
    }

    protected void addFood(Coordinate<Type,O> c,Commestible food) throws ExceptionCollisionWithFood {
        synchronized(lock) {
            if(nourritures.containsKey(c)){
                throw new ExceptionCollisionWithFood("Food added in another food");
            }
            nourritures.put(c,food);
        }
    }

    protected void addDeathFood(Snake<Type,O> snake) {
        synchronized(lock) {
            for(Snake<Type,O>.SnakePart c : snake.getTail()){   // We add a death food for each part of the snake (except the head)
                try {
                    addFood(c.getCenter(), Commestible.DEATH_FOOD);
                } catch (ExceptionCollisionWithFood e) {
                    System.out.println("Death food added in another food");
                }
            }
        }
    }

    public void removeFood(Coordinate<Type,O> c) throws IllegalArgumentException {
        synchronized(lock) {
            if(nourritures.remove(c) == null){
                throw new IllegalArgumentException("Food not found");
            }
        }
    }

    public void addOneFood(){
        try {
            addFood(border.getRandomCoordinate(),Commestible.getRandom());
        } catch (ExceptionCollision e) {
            //Si la nourriture est présente alors on ne fait rien
        }
    }

    protected void addAllFood() {
        for(int i = 0; i < NB_FOOD; i++){
            addOneFood();
        }
    }

    /**
     * Check if the snake is colliding with another snake already on the board
     * @param snake the snake to check
     * @return true if the snake is colliding with another snake already on the board, false otherwise
     */
    public boolean isCollidingWithAll(Snake<Type,O> snake){
        for (Snake<Type,O> s : plateau.values()) {
            if(snake != s && snake.isColliding(s)){ // We check if the snake is colliding with another snake already on the board (except itself)
                System.out.println("Collision with " + s.head.getCenter().toString()+ " at " + snake.getHead().getCenter().toString());
                return true;
            }
        }
        return false;
    }

    public abstract int isCollidingWithFood(Snake<Type, O> snake);
}
