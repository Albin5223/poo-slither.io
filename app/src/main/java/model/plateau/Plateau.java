package model.plateau;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

import exceptions.ExceptionCollision;
import exceptions.ExceptionCollisionWithFood;
import exceptions.ExceptionCollisionWithSnake;
import interfaces.ConfigurationSnake;
import interfaces.GameBorder;
import interfaces.Orientation;
import model.coordinate.Coordinate;
import model.coordinate.Grid;
import model.foods.Food;
import model.foods.FoodFactory;
import interfaces.ConfigurationFood;

/**
 * The board of the game.
 * It contains all the snakes and the foods.
 * It is also responsible for the collisions between the snakes and the foods.
 * @param Type the type of the coordinate, used to specify the position of snakes and food on the board
 * @param O the type of the orientation, used to specify how the snakes are turning
 */
public abstract sealed class Plateau<Type extends Number & Comparable<Type>, O extends Orientation<O>> permits PlateauDouble, PlateauInteger {

    /** The board of the game where the snakes are */
    protected HashMap<Coordinate<Type,O>, Snake<Type,O>> snakeBoard = new HashMap<Coordinate<Type,O>, Snake<Type,O>>();
    /** The board of the game where the foods are */
    protected Grid<Type,O> foodGrid = new Grid<Type,O>();
    
    /** The factory used to create the foods */
    protected final FoodFactory<Type,O> foodFactory;
    /** The border of the game */
    protected final GameBorder<Type,O> border;
    /** The configuration of the snakes */
    protected final ConfigurationSnake snakeConfig;
    /**
     * @return the configuration of the snakes
     */
    public ConfigurationSnake getSnakeConfig() {return snakeConfig;}

    /**
     * The lock used to synchronize the access to the board.
     * <p>
     * Used to avoid concurrent access to the board and useful for having snakes with different speed.
     */
    private Object lock = new Object();

    /**
     * Constructs a new game board with the provided food factory, snake configuration, and game border.
     * <p>
     * @apiNote All foods are added to the board upon creation.
     *
     * @param foodFactory the factory used to generate food items on the board
     * @param snakeConfig the configuration settings for the snakes
     * @param border the boundaries of the game
     *
     * @see FoodFactory
     * @see ConfigurationSnake
     * @see GameBorder
     */
    protected Plateau(FoodFactory<Type,O> foodFactory, ConfigurationSnake snakeConfig, GameBorder<Type,O> border) {
        this.foodFactory = foodFactory;
        this.border = border;
        this.snakeConfig = snakeConfig;
        
        this.addAllFood();
    }

    /**
     * @return the board of the game where the snakes are
     */
    public HashMap<Coordinate<Type,O>, Snake<Type,O>> getHashMap() {
        return snakeBoard;
    }

    /**
     * @return the board of the game where the foods are
     */
    public Grid<Type,O> getFoods() {
        return foodGrid;
    }

    /**
     * @return the border of the game
     */
    public GameBorder<Type,O> getBorder() {
        return border;
    }

    /**
     * This method is called at each step of a snake, to see if the snake is still on the board.
     * @param snake the snake to check
     * @return true if the snake is still on the board, false otherwise
     */
    protected boolean isSnakeOnBoard(Snake<Type,O> snake){
        synchronized(lock) {
            for (Snake<Type,O> s : snakeBoard.values()) {
                if(snake == s){ // We check if the snake is on the board
                    return true;
                }
            }
            return false;
        }
    }

    /**
     * @return the list of all the snakes on the board
     */
    public ArrayList<Snake<Type,O>> getSnakes() {
        return new ArrayList<Snake<Type,O>>(snakeBoard.values());
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
            snakeBoard.put(snake.getHead().getCenter(), snake);
        }
    }

    /**
     * Remove a snake from the board
     * @param snake the snake to remove
     * @return true if the snake was on the board, false otherwise
     */
    public boolean removeSnake(Snake<Type,O> snake) {
        synchronized(lock) {
            return snakeBoard.remove(snake.getHead().getCenter()) != null;
        }
    }

    /**
     * Add a food to the board
     * @param food the food to add
     * @throws ExceptionCollisionWithFood if the food is added in the exact same center as another food already on the board
     */
    protected void addFood(Food<Type,O> food) throws ExceptionCollisionWithFood {
        synchronized(lock) {
            if(!foodGrid.insert(food)){
                throw new ExceptionCollisionWithFood("Food added in another food");
            }
        }
    }

    /**
     * Add a snake's death food to the board
     * @apiNote This method is called when a snake dies
     * @apiNote If the number of food on the board is already too high, then no food is added
     * @param snake the snake to add the death food
     * @see ConfigurationFood#getMaxFoodCoef()
     */
    protected void addDeathFood(Snake<Type,O> snake) {
        synchronized(lock) {
            ArrayList<FoodFactory<Type,O>.DeathFood> deathFoods = foodFactory.getDeathFoods(snake);
            if(foodGrid.size() > foodFactory.getFoodConfig().getNbFood()*foodFactory.getFoodConfig().getMaxFoodCoef()){return;}
            for(FoodFactory<Type,O>.DeathFood food : deathFoods){
                try {
                    addFood(food);
                } catch ( ExceptionCollisionWithFood e ) {
                    // If the food is already present then we do nothing
                }
            }
        }
    }

    /**
     * Remove a food from the board
     * @param food the food to remove
     * @throws IllegalArgumentException if the food is not on the board
     */
    public void removeFood(Food<Type,O> food) throws IllegalArgumentException {
        synchronized(lock) {
            if(!foodGrid.remove(food)){
                throw new IllegalArgumentException("Food not found");
            }
        }
    }

    /**
     * Add a random food to the board at a random position
     * @apiNote This method is called when a food is eaten by a snake
     */
    public void addOneFood(){
        try {
            Coordinate<Type,O> c = border.getRandomCoordinate();
            addFood(foodFactory.getRandomFood(c));
        } catch (ExceptionCollision e) {
            // If the food is already present, then we retry
            addOneFood();
        }
    }

    /**
     * Add all the foods to the board
     * @apiNote This method is called when the board is created
     */
    private void addAllFood() {
        for(int i = 0; i < foodFactory.getFoodConfig().getNbFood(); i++){
            addOneFood();
        }
    }

    /**
     * Check if the snake is colliding with another snake already on the board (or maybe itself)
     * @param snake the snake to check
     * @return true if the snake is colliding with another snake already on the board, false otherwise
     * @apiNote This method is synchronized to avoid that a changement is occuring in the snake board while we are checking if it is colliding with another snake
     */
    public boolean isCollidingWithAll(Snake<Type,O> snake){
        synchronized(lock){
            for (Snake<Type,O> s : snakeBoard.values()) {
                if(snake.isCollidingWith(s)){ // We check if the snake is colliding with another snake already on the board
                    System.out.println("Collision with " + s.head.getCenter().toString()+ " at " + snake.getHead().getCenter().toString());
                    return true;
                }
            }
            return false;
        }
    }

    /**
     * Check if a coordinate is colliding with a snake already on the board
     * @param c the coordinate to check
     * @param radius the radius of the coordinate
     * @param snake the snake to check
     * @return true if the coordinate is colliding with the snake already on the board, false otherwise
     */
    public boolean willYouCollideWith(Coordinate<Type,O> c, double radius, Snake<Type,O> snake){
        for (Snake<Type,O>.SnakePart s : snake.getAllSnakePart()) {
            if(s.getCenter().distanceTo(c) < radius + snake.getHitboxRadius()){ // We check if the coordinate is colliding with the snake already on the board
                return true;
            }
        }
        return false;
    }

    /**
     * Check if the snake is colliding with a food already on the board
     * @param snake the snake to check
     * @return the list of all the foods colliding with the snake
     * 
     * @apiNote This method also removes the food from the board if it is colliding with the snake
     */
    public ArrayList<Food<Type,O>> isCollidingWithFoods(Snake<Type,O> snake){

        ArrayList<Food<Type,O>> collidingFoods = new ArrayList<Food<Type,O>>();
        int foodsToAdd = 0;

        List<Food<Type,O>> nearbyFoods = foodGrid.getNearbyFoods(snake);

        for (Food<Type,O> food : nearbyFoods) {
            if (snake.isCollidingWith(food)) {
                if(food.isRespawnable()){
                    foodsToAdd += 1;
                }
                collidingFoods.add(food);
            }
        }

        // We remove the food from the board AFTER the loop to avoid concurrent access
        for(Food<Type,O> food : collidingFoods){
            try {
                removeFood(food);
            } catch (IllegalArgumentException e) {
                // If the food is already removed then we do nothing
            }
        }

        // We add the new foods at the end to avoid the fact that the snake can eat the food it just added
        for(int i = 0; i < foodsToAdd; i++){
            addOneFood();
        }

        return collidingFoods;
    }
}
