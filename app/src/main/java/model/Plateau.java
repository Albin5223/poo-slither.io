package model;

import java.util.HashMap;
import java.util.Map;

public class Plateau {

    private HashMap<Coordinate, Snake> plateau;
    private HashMap<Coordinate, Commestible> nourritures;


    public Plateau() {
        this.plateau = new HashMap<Coordinate, Snake>();
        this.nourritures = new HashMap<Coordinate, Commestible>();
    }

    /**
     * Add a snake to the board
     * @param snake the snake to add
     * @throws IllegalArgumentException if the snake is colliding with another snake already on the board
     */
    public void addSnake(Snake snake) throws IllegalArgumentException {
        if(isCollidingWithAll(snake)){throw new IllegalArgumentException("Snake added is colliding with another snake");}
        plateau.put(snake.getHead().getCenter(), snake);
    }

    public void addFood(Coordinate c) throws IllegalArgumentException{
        if (plateau.containsKey(c)) {
            throw new IllegalArgumentException("Snake already exists");
        }
        nourritures.put(c, Commestible.FOOD);
    }

    /**
     * Update the position of the snake on the board (the snake has moved)
     * @param snake the snake that has moved
     */
    public void update(Snake snake) {
        Coordinate keyToRemove = null;  // We need to remove the old coordinate of the snake from the map
        for (Map.Entry<Coordinate, Snake> entry : plateau.entrySet()) { // We search for the old coordinate
            if (entry.getValue().equals(snake)) {   // We found the old coordinate
                keyToRemove = entry.getKey();   // We save the old coordinate
                break;
            }
        }

        if(keyToRemove == null){    // We didn't find the old coordinate
            throw new IllegalArgumentException("Snake not found");
        }

        plateau.remove(keyToRemove);    // We remove the old coordinate    
        plateau.put(snake.getHead().getCenter(), snake);    // We add the new coordinate
    }

    /**
     * Check if the snake is colliding with another snake already on the board
     * @param snake the snake to check
     * @return  true if the snake is colliding with another snake already on the board, false otherwise
     */
    public boolean isCollidingWithAll(Snake snake){
        for (Snake s : plateau.values()) {
            if(snake != s && snake.isColliding(s)){ // We check if the snake is colliding with another snake already on the board (except itself)
                return true;
            }
        }
        return false;
    }
}
