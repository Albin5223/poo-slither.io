package model.plateau;

import java.util.ArrayList;
import exceptions.ExceptionCollision;
import exceptions.ExceptionCollisionWithFood;
import exceptions.ExceptionCollisionWithSnake;
import interfaces.Collisable;
import interfaces.Coordinate;
import interfaces.Orientation;
import interfaces.Turnable;


public sealed abstract class Snake<Type extends Number, O extends Orientation<O>> implements Turnable<O>, Collisable<Snake<Type,O>> permits SnakeInteger, SnakeDouble {

    /** The turning of the snake */
    protected Turning currentTurning = Turning.FORWARD;

    private final Type GAP_BETWEEN_TAIL;

    /** The amount of food that the snake needs to eat before growing */
    private final int MAX_FOOD_CHARGING;
    
    /** The amount of food that the snake has eaten */
    private int foodCharging = 0;

    public sealed class SnakePart implements Collisable<SnakePart>, Cloneable permits SnakeInteger.SnakePartInteger, SnakeDouble.SnakePartDouble{

        /** The coordinate center of the snake part */
        protected Coordinate<Type,O> center;

        /** The hitbox radius of the snake part */
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
        public Coordinate<Type,O> getCenter(){
            return center;
        }

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

        public SnakePart clone(){
            return new SnakePart(this.center, this.orientation, this.hitboxRadius);
        }

        @Override
        public boolean isColliding(SnakePart other) {
            return center.distanceTo(other.center) <= this.hitboxRadius + other.hitboxRadius;
        }
    }

    /** The head of the snake */
    protected SnakePart head;
    /** The tail of the snake */
    protected ArrayList<SnakePart> tail;
    /** The board where the snake is */
    protected Plateau<Type,O> plateau;

    protected Snake(Coordinate<Type,O> location, Plateau<Type,O> plateau, O startingDirection, Type gap_between_tail, double hitboxRadius, int nbTail, int maxFoodCharging) throws ExceptionCollision {
        this.GAP_BETWEEN_TAIL = gap_between_tail;
        this.MAX_FOOD_CHARGING = maxFoodCharging;
        this.head = new SnakePart(location.clone(), startingDirection, hitboxRadius);
        this.tail = new ArrayList<SnakePart>();

        O direction = head.getOrientation();
        for (int i = 0; i < nbTail; i++) {
            SnakePart tail1 = new SnakePart(head.getCenter().placeCoordinateFrom(direction.opposite(),GAP_BETWEEN_TAIL), direction, hitboxRadius);
            tail.add(tail1);
        }

        this.plateau = plateau;

        plateau.addSnake(this);
    }

    public double getRadius(){
        return head.getHitboxRadius();
    }

    public O getOrientation(){
        return head.getOrientation();
    }

    protected void resetSnake(Coordinate<Type,O> newLocation, O startingDirection, double hitboxRadius, int nbTail) throws ExceptionCollisionWithSnake, ExceptionCollisionWithFood {
        
        this.plateau.removeSnake(this); // We remove the snake from the board

        this.head = new SnakePart(newLocation.clone(), startingDirection, hitboxRadius);
        this.tail = new ArrayList<SnakePart>();

        O direction = head.getOrientation();
        for (int i = 0; i < nbTail; i++) {
            SnakePart tail1 = new SnakePart(head.getCenter().placeCoordinateFrom(direction.opposite(),GAP_BETWEEN_TAIL), direction, hitboxRadius);
            tail.add(tail1);
        }

        if(plateau.isCollidingWithAll(this)){
            throw new ExceptionCollisionWithSnake("Snake is colliding with another snake");
        }

        this.plateau.addSnake(this);
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
    public ArrayList<SnakePart> getTail() {
        return new ArrayList<>(tail);
    }

    /**
     * @return a copy of {@link #head} and {@link #tail}
     */
    public ArrayList<SnakePart> getAllSnakePart() {
        ArrayList<SnakePart> allSnakePart = new ArrayList<>();
        allSnakePart.add(head);
        allSnakePart.addAll(tail);
        return allSnakePart;
    }

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
        ArrayList<SnakePart> allParts = other.getAllSnakePart();
        for (SnakePart snakePart : allParts) {
            if(head.isColliding(snakePart)){
                return true;
            }
        }
        return false;
    }

    protected void chargeFood(int value){
        foodCharging += value;
        int nbGrowth = foodCharging / MAX_FOOD_CHARGING;
        foodCharging = foodCharging % MAX_FOOD_CHARGING;
        grow(nbGrowth);
    }

    protected void grow(){
        SnakePart lastTail = tail.get(tail.size() - 1);
        O direction = lastTail.getOrientation();
        SnakePart newTail = new SnakePart(lastTail.getCenter().placeCoordinateFrom(direction.opposite(),GAP_BETWEEN_TAIL), direction, lastTail.getHitboxRadius());
        tail.add(newTail);
    }

    protected void grow(int nb) {
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
