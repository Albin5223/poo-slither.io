package model.plateau;

import java.util.HashMap;
import java.util.Map;

import interfaces.Coordinate;
import interfaces.Orientation;
import model.Commestible;

public abstract sealed class Plateau<Type extends Number, O extends Orientation<O>> permits PlateauDouble, PlateauInteger {

    protected HashMap<Coordinate<Type,O>, Snake<Type,O>> plateau;
    protected HashMap<Coordinate<Type,O>, Commestible> nourritures;


    protected Plateau() {
        this.plateau = new HashMap<Coordinate<Type,O>, Snake<Type,O>>();
        this.nourritures = new HashMap<Coordinate<Type,O>, Commestible>();
    }

    public HashMap<Coordinate<Type, O>, Commestible> getNourritures() {
        return nourritures;
    }


    /**
     * Add a snake to the board
     * @param snake the snake to add
     * @throws IllegalArgumentException if the snake is colliding with another snake already on the board
     */
    public void addSnake(Snake<Type,O> snake) throws IllegalArgumentException {
        if(isCollidingWithAll(snake)){throw new IllegalArgumentException("Snake added is colliding with another snake");}
        plateau.put(snake.getHead().getCenter(), snake);
    }

    public void addFood(Coordinate<Type,O> c,Commestible food) throws IllegalArgumentException{
        if(nourritures.containsKey(c)){throw new IllegalArgumentException("Food added in another food");}
        nourritures.put(c,food);
    }

    public abstract void addAllFood();
    public abstract int isCollidingWithFood(Snake<Type,O> snake);

    /**
     * Update the position of the snake on the board (the snake has moved)
     * @param snake the snake that has moved
     */
    public void update(Snake<Type,O> snake) {
        Coordinate<Type,O> keyToRemove = null;  // We need to remove the old coordinate of the snake from the map
        for (Map.Entry<Coordinate<Type,O>, Snake<Type,O>> entry : plateau.entrySet()) { // We search for the old coordinate
            if (entry.getValue().equals(snake)) {   // We found the old coordinate
                keyToRemove = entry.getKey();   // We save the old coordinate
                break;
            }
        }

        if(keyToRemove == null){    // We didn't find the old coordinate
            throw new IllegalArgumentException("Snake not found");
        }

        plateau.remove(keyToRemove);    // We remove the old coordinate    
        this.addSnake(snake);    // We add the new coordinate
    }

    /**
     * Check if the snake is colliding with another snake already on the board
     * @param snake the snake to check
     * @return  true if the snake is colliding with another snake already on the board, false otherwise
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

    public void removeSnake(Snake<Type,O> snake){
        if(plateau.remove(snake.getHead().getCenter()) == null){
            throw new IllegalArgumentException("Snake not found");
        }
    }


    public abstract Coordinate<Type,O> getRandomCoordinate();
}
