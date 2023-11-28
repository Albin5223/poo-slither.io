package model;

import java.util.ArrayList;

import exceptions.ExceptionCollision;
import exceptions.ExecptionMoveInvalid;
import interfaces.Collisable;
import interfaces.Turnable;
import model.Snake.SnakePart.Direction;

public class Snake implements Turnable, Collisable<Snake> {

    /** The turning of the snake */
    private Turning currentTurning = Turning.FORWARD;


    public class SnakePart implements Cloneable {

        /** The coordinate center of the snake part */
        private CoordinateInteger center;

        public enum Direction {LEFT,UP,RIGHT,DOWN;
            private Direction changeDirectionWithTurn(Turning turning){
                switch (turning) {
                    case GO_LEFT : if (this.ordinal() == 0) return Direction.values()[Direction.values().length - 1];
                                   else return Direction.values()[this.ordinal() - 1];
                    case GO_RIGHT : return Direction.values()[(this.ordinal() + 1) % Direction.values().length];
                    default :return this;
                }       
            }

            public static Direction opposite(Direction direction) {
                switch (direction) {
                    case UP: return DOWN;   
                    case DOWN: return UP;
                    case LEFT: return RIGHT;
                    case RIGHT: return LEFT;
                    default:
                        throw new IllegalArgumentException("Unexpected value: " + direction);
                }
            }
        
        }
        /** The direction of the snake part */
        private Direction direction;


        /**
         * Private constructor for the {@link #clone} method
         * @param left the left coordinate of the snake part
         * @param center the center coordinate of the snake part
         * @param right the right coordinate of the snake part
         * @param direction the direction of the snake part
         */
        private SnakePart(CoordinateInteger center, Direction direction) {
            this.center = center.clone(); 
            this.direction = direction;
        }

        /**
         * @return the center of the snake part
         */
        public CoordinateInteger getCenter(){
            return center.clone();
        }

        /** The getter of the direction
         * @return the direction of the snake part
         */
        public Direction getDirection() {
            return direction;
        }

        /** The setter of the direction
         * @param d the new direction of the snake part
         */
        public void setDirection(Direction d) {
            this.direction = d;
        }

        @Override
        public SnakePart clone() {
            return new SnakePart(this.center, this.direction);
        }

        @Override
        public String toString() {
            return "SnakePart [center=" + center.toString() + ", direction=" + direction + "]";
        }
    }

    /** The head of the snake */
    private SnakePart head;
    /** The tail of the snake */
    private ArrayList<SnakePart> tail;
    /** The board where the snake is */
    private Plateau plateau;
    
    /**
     * The constructor of the snake
     * @param location the location of the snake
     * @param plateau the board where the snake is
     * @param startingDirection the starting angle of the snake
     */
    public Snake(CoordinateInteger location,Plateau plateau, Direction startingDirection) {
        this.head = new SnakePart(location.clone(), startingDirection);
        this.tail = new ArrayList<SnakePart>();

        Direction direction = head.getDirection();
        SnakePart tail1 = new SnakePart(head.getCenter().placeCoordinateFrom(Direction.opposite(direction)), direction);
        tail.add(tail1);

        this.plateau = plateau;

        plateau.addSnake(this);
    }

    public void resetSnake(CoordinateInteger location, Direction startingDirection) {

        this.head = new SnakePart(location.clone(), startingDirection);
        this.tail = new ArrayList<SnakePart>();

        Direction direction = head.getDirection();
        SnakePart tail1 = new SnakePart(head.getCenter().placeCoordinateFrom(Direction.opposite(direction)), direction);
        tail.add(tail1);

        plateau.update(this);
    }

    /**
     * @return a copy of {@link #head}
     */
    public SnakePart getHead() {
        return head;
    }

    /**
     * @return a copy of {@link #tail}
     */
    public SnakePart[] getTail() {
        return tail.stream().map(c -> c.clone()).toArray(SnakePart[]::new);
    }

    /**
     * @return a copy of {@link #head} and {@link #tail}
     */
    public SnakePart[] getAllSnakePart() {
        SnakePart[] tail = getTail();
        SnakePart[] allSnakePart = new SnakePart[tail.length + 1];
        allSnakePart[0] = head;
        for (int i = 0; i < tail.length; i++) {
            allSnakePart[i + 1] = tail[i];
        }
        return allSnakePart;
    }

    /**
     * Update the position of the snake on the board (the snake has moved)
     * @throws ExceptionCollision if the snake is colliding with another snake
     */
    public void move() throws ExceptionCollision {
        // Create the new head : distance from the old head = GAP, angle = updated head's angle considering the current turning
        Direction newDirection = turn(currentTurning, head.getDirection());
        SnakePart newHead = new SnakePart(head.getCenter().placeCoordinateFrom(newDirection), newDirection);

        this.tail.remove(tail.size() - 1);  // We remove the last element of the tail
        this.tail.add(0, head); // We add the old head to the tail
        this.head = newHead;    // We update the head

        if(plateau.isCollidingWithAll(this)){  // We check if the snake is colliding with another snake
            throw new ExceptionCollision("Snake is colliding with another snake");
        }
        
        plateau.update(this);   // We update the position of the snake on the board
    }

    /**
     * Turning the snake to the left or to the right, like in a trigonometric circle (0° is on the right, 90° is on the top, 180° is on the left, 270° is on the bottom)
     * @param turning the direction to turn
     */
    @Override
    public Direction turn(Turning turning, Direction initialDirection) {
        return initialDirection.changeDirectionWithTurn(turning);
    }

    /**
     * @return the angle of the snake
     */
    public Direction getDirection() {
        return head.getDirection();
    }

    public Turning getCurrentTurning() {
        return currentTurning;
    }

    @Override
    public boolean isColliding(Snake other) {
        SnakePart[] allParts = other.getAllSnakePart();
        for (SnakePart snakePart : allParts) {
            if (this.head.getCenter().equals(snakePart.getCenter())) {
                return true;
            }
        }
        return false;
    }

    public void grow() {
        SnakePart lastTail = tail.get(tail.size() - 1);
        Direction direction = lastTail.getDirection();
        SnakePart newTail = new SnakePart(lastTail.getCenter().placeCoordinateFrom(Direction.opposite(direction)), direction);
        tail.add(newTail);
    }

    public void grow(int nb) {
        for (int i = 0; i < nb; i++) {
            grow();
        }
    }

    @Override
    public String toString() {
        String res = "Snake [head=" + head.toString() + ", tail=";
        for (SnakePart s : tail) {
            res += s.toString() + " ";
        }
        res += "]";
        return res;
    }

    public void setTurning(Turning turning) {
        this.currentTurning = turning;
    }
}
