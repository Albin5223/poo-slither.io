package model.plateau;

import java.util.HashMap;

import java.util.ArrayList;

import exceptions.ExceptionCollision;
import exceptions.ExceptionCollisionWithFood;
import exceptions.ExceptionCollisionWithSnake;
import exceptions.ExceptionTooManyFoods;
import interfaces.GameBorder;
import interfaces.Orientation;
import javafx.animation.AnimationTimer;
import model.coordinate.Coordinate;
import model.coordinate.CoordinateTree;
import model.foods.DeathFood;
import model.foods.Food;
import model.foods.FoodFactory;

public abstract sealed class Plateau<Type extends Number & Comparable<Type>, O extends Orientation<O>> permits PlateauDouble, PlateauInteger {

    protected HashMap<Coordinate<Type,O>, Snake<Type,O>> plateau = new HashMap<Coordinate<Type,O>, Snake<Type,O>>();
    protected CoordinateTree<Type, O, Food<Type,O>> foodTree = new CoordinateTree<Type, O, Food<Type,O>>();
    
    protected final FoodFactory<Type,O> foodFactory;
    protected final GameBorder<Type,O> border;

    protected final int MAX_FOOD_COEF = 3;
    protected final int NB_FOOD;

    /**
     * The lock used to synchronize the access to the board.
     * This lock is used to avoid concurrent access to the board.
     * Usefull for having snakes with different speed.
     */
    private Object lock = new Object();

    private AnimationTimer animation;
    private long lastUpdate = 0;


    protected Plateau(int nbFood, FoodFactory<Type,O> foodFactory, GameBorder<Type,O> border) {
        this.NB_FOOD = nbFood;
        this.foodFactory = foodFactory;
        this.border = border;
        animation = new AnimationTimer() {
            @Override
            public void handle(long now) {
                
                if((now-lastUpdate) >= 1_000_000_000){
                    System.out.println("Update");
                    for(Snake<Type,O> snake : plateau.values()){
                        if(snake.underEffect()){
                            snake.applyEffect();
                        }
                    }
                    lastUpdate = now;
                }
                
            }
        };
        this.addAllFood();
    }

    public void startAnimation(){
        animation.start();
    }

    public void stopAnimation(){
        animation.stop();
    }

    public CoordinateTree<Type, O, Food<Type,O>> getFoodTree() {
        return foodTree;
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

    protected void addFood(Coordinate<Type,O> c,Food<Type,O> food) throws ExceptionCollisionWithFood,ExceptionTooManyFoods {
        synchronized(lock) {
            if(foodTree.contains(c)){
                throw new ExceptionCollisionWithFood("Food added in another food");
            }
            foodTree.add(c,food);
        }
    }

    protected void addDeathFood(Snake<Type,O> snake) {
        synchronized(lock) {
            ArrayList<DeathFood<Type,O>> deathFoods = foodFactory.getDeathFoods(snake);
            if(foodTree.size() > MAX_FOOD_COEF*NB_FOOD){return;}
            for(DeathFood<Type,O> food : deathFoods){
                try {
                    addFood(food.getCenter(), food);
                } catch ( ExceptionCollisionWithFood | ExceptionTooManyFoods e ) {
                    // If the food is already present then we do nothing
                }
            }
        }
    }

    public void removeFood(Coordinate<Type,O> c) throws IllegalArgumentException {
        synchronized(lock) {
            if(!foodTree.remove(c)){
                throw new IllegalArgumentException("Food not found");
            }
        }
    }

    public void addOneFood(){
        try {
            Coordinate<Type,O> c = border.getRandomCoordinate();
            addFood(c, foodFactory.getRandomFood(c));
        } catch (ExceptionCollision | ExceptionTooManyFoods e) {
            // If the food is already present then we do nothing
        }
    }

    private void addAllFood() {
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
            if(snake != s && snake.isCollidingWith(s)){ // We check if the snake is colliding with another snake already on the board (except itself)
                System.out.println("Collision with " + s.head.getCenter().toString()+ " at " + snake.getHead().getCenter().toString());
                return true;
            }
        }
        return false;
    }

    public ArrayList<Food<Type,O>> isCollidingWithFoods(Snake<Type,O> snake){

        ArrayList<Food<Type,O>> collidingFoods = new ArrayList<Food<Type,O>>();
        int foodsToAdd = 0;

        while (true) {
            Coordinate<Type,O> closestCoordinate = foodTree.getClosest(snake.getHead().getCenter());
            if (closestCoordinate == null) {
                break;
            }

            Food<Type,O> closestFood = foodTree.get(closestCoordinate);

            double distance = closestFood.getCenter().distanceTo(snake.getHead().getCenter());
            if (distance <= snake.getRadius() + closestFood.getRadius()) {
                removeFood(closestCoordinate);
                if (closestFood.isRespawnable()) {
                    foodsToAdd += 1;
                }
                collidingFoods.add(closestFood);
            } else {
                break;
            }
        }

        // We add the new foods at the end to avoid the fact that the snake can eat the food it just added
        for(int i = 0; i < foodsToAdd; i++){
            addOneFood();
        }

        return collidingFoods;
    }
}
