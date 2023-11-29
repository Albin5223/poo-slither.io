package model;

import java.util.ArrayList;

import exceptions.ExceptionCollision;
import interfaces.Collisable;
import interfaces.Coordinate;
import interfaces.Orientation;
import interfaces.Turnable;


public sealed abstract class Snake<Type extends Number, O extends Orientation> implements Turnable<O>, Collisable<Snake<Type,O>> permits SnakeInteger, SnakeDouble {

    /** The turning of the snake */
    protected Turning currentTurning = Turning.FORWARD;

    protected Type gap_between_tail;

    public static abstract sealed class SnakePart<Type extends Number,O extends Orientation> implements Collisable<SnakePart<Type,O>>, Cloneable permits SnakeInteger.SnakePartInteger, SnakeDouble.SnakePartDouble{

        /** The coordinate center of the snake part */
        protected Coordinate<Type,O> center;

        protected double hitboxRadius;

        /** The direction of the snake part */
        protected O orientation;

        protected SnakePart(Coordinate<Type,O> center, O direction, double hitboxRadius) {
            this.center = center.clone(); 
            this.orientation = direction;
            this.hitboxRadius = hitboxRadius;
        }

        /**
         * @return the center of the snake part
         */
        public abstract Coordinate<Type,O> getCenter();

        public O getOrientation(){
            return orientation;
        }

        public void setHitboxRadius(double hitboxRadius) {
            this.hitboxRadius = hitboxRadius;
        }

        @Override
        public String toString() {
            return "SnakePart [center=" + center.toString() + ", orientation=" + orientation + ", hitboxRadius=" + hitboxRadius +"]";
        }

        public double getHitboxRadius() {
            return hitboxRadius;
        }

        @Override
        public abstract SnakePart<Type,O> clone();

        @Override
        public boolean isColliding(SnakePart<Type,O> other) {
            return center.distanceTo(other.center) <= this.hitboxRadius + other.hitboxRadius;
        }
    }

    /** The head of the snake */
    protected SnakePart<Type,O> head;
    /** The tail of the snake */
    protected ArrayList<SnakePart<Type,O>> tail;
    /** The board where the snake is */
    protected Plateau<Type,O> plateau;

    public Snake(Type gap_between_tail){
        this.gap_between_tail = gap_between_tail;
    }

    public double getRadius(){
        return head.getHitboxRadius();
    }

    public abstract void resetSnake(Coordinate<Type,O> newLocation, O startingDirection, int nbTail);

    /**
     * @return a copy of {@link #head}
     */
    public SnakePart<Type,O> getHead() {
        return head;
    }

    /**
     * @return a copy of {@link #tail}
     */
    public abstract SnakePart<Type,O>[] getTail();

    /**
     * @return a copy of {@link #head} and {@link #tail}
     */
    public abstract SnakePart<Type,O>[] getAllSnakePart();

    /**
     * Update the position of the snake on the board (the snake has moved)
     * @throws ExceptionCollision if the snake is colliding with another snake
     */
    public abstract void move() throws ExceptionCollision;

    /**
     * Turning the snake to the left or to the right, like in a trigonometric circle (0° is on the right, 90° is on the top, 180° is on the left, 270° is on the bottom)
     * @param turning the direction to turn
     */
    @Override
    public abstract O turn(Turning turning, O initialDirection);

    /**
     * @return the angle of the snake
     */
    public O getDirection() {
        return head.getOrientation();
    }

    public Turning getCurrentTurning() {
        return currentTurning;
    }

    @Override
    public boolean isColliding(Snake<Type,O> other) {
        SnakePart<Type,O>[] allParts = other.getAllSnakePart();
        for (SnakePart<Type,O> snakePart : allParts) {
            if(head.isColliding(snakePart)){
                return true;
            }
        }
        return false;
    }

    public abstract void grow();

    public void grow(int nb) {
        for (int i = 0; i < nb; i++) {
            grow();
        }
    }

    @Override
    public String toString() {
        String res = "Snake [head=" + head.toString() + ", tail=";
        for (SnakePart<Type,O> s : tail) {
            res += s.toString() + " ";
        }
        res += "]";
        return res;
    }

    public void setTurning(Turning turning) {
        this.currentTurning = turning;
    }
}