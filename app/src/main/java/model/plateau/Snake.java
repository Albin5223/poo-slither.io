package model.plateau;

import java.util.ArrayList;
import exceptions.ExceptionCollision;
import exceptions.ExceptionCollisionWithSnake;
import interfaces.Orientation;
import interfaces.Turnable;
import model.coordinate.Coordinate;
import model.foods.Food;
import model.skins.Skin;
import model.skins.SkinRandom;

public sealed abstract class Snake<Type extends Number & Comparable<Type>, O extends Orientation<O>> implements Turnable<O> permits SnakeInteger, SnakeDouble {

    /** The skin of the snake, by default it's random */
    protected Skin<Type,O> skin = SkinRandom.build();

    /** The turning of the snake */
    protected Turning currentTurning = Turning.FORWARD;

    /** The current speed of the snake */
    protected int currentSpeed = 0;
    protected volatile boolean isBoosting = false;

    private final Object lock = new Object();

    private final Type GAP_BETWEEN_TAIL;
    private final double BIRTH_HITBOX_RADIUS;
    private final int BIRTH_LENGTH;
    /** The amount of food that the snake needs to eat before growing */
    public final int MAX_FOOD_CHARGING;
    private final int DEFAULT_SPEED;
    private final int BOOST_SPEED;

    private int TIME_OF_POISON = 0;
    private int TIME_OF_SHIELD = 0;

    public final int DEATH_FOOD_PER_SEGMENT;
    
    /** The amount of food that the snake has eaten */
    private int foodCharging = 0;

    public sealed class SnakePart implements Cloneable permits SnakeInteger.SnakePartInteger, SnakeDouble.SnakePartDouble{

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

        public boolean isCollidingWith(SnakePart other) {
            return center.distanceTo(other.center) <= other.hitboxRadius;
        }

        public boolean isCollidingWith(Food<Type,O> other) {
            return center.distanceTo(other.getCenter()) <= this.hitboxRadius + other.getRadius();
        }
    }

    /** The head of the snake */
    protected SnakePart head;
    /** The tail of the snake */
    protected ArrayList<SnakePart> tail;
    /** The board where the snake is */
    protected Plateau<Type,O> plateau;

    protected Snake(Coordinate<Type,O> location, Plateau<Type,O> plateau, O startingDirection, Type gap_between_tail, double hitboxRadius, int nbTail, int maxFoodCharging, int defaultSpeed, int boostSpeed, int death_food_per_segment) throws ExceptionCollision {
        this.GAP_BETWEEN_TAIL = gap_between_tail;
        this.BIRTH_HITBOX_RADIUS = hitboxRadius;
        this.BIRTH_LENGTH = nbTail;
        this.MAX_FOOD_CHARGING = maxFoodCharging;
        this.DEFAULT_SPEED = defaultSpeed;
        this.BOOST_SPEED = boostSpeed;
        this.currentSpeed = defaultSpeed;
        this.DEATH_FOOD_PER_SEGMENT = death_food_per_segment;
        this.head = new SnakePart(location.clone(), startingDirection, BIRTH_HITBOX_RADIUS);
        this.tail = new ArrayList<SnakePart>();

        O direction = head.getOrientation();
        for (int i = 0; i < BIRTH_LENGTH; i++) {
            SnakePart tail1 = new SnakePart(head.getCenter().placeCoordinateFrom(direction.opposite(),GAP_BETWEEN_TAIL), direction, hitboxRadius);
            tail.add(tail1);
        }

        this.plateau = plateau;

        plateau.addSnake(this);
    }

    public void setSkin(Skin<Type,O> skin) {
        this.skin = skin;
    }

    public Skin<Type,O> getSkin() {
        return skin;
    }

    public double getRadius(){
        return head.getHitboxRadius();
    }

    public O getOrientation(){
        return head.getOrientation();
    }

    protected void resetSnake(Coordinate<Type,O> newLocation, O startingDirection, double hitboxRadius, int nbTail) throws ExceptionCollisionWithSnake{
        
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

        this.setBoosting(false);

        this.plateau.addSnake(this);
    }

    public void reset(){
        try {
            resetSnake(plateau.border.getRandomCoordinate(), head.getOrientation().getRandom(), BIRTH_HITBOX_RADIUS, BIRTH_LENGTH);
        } catch (ExceptionCollisionWithSnake e) {
            reset();
        }
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


    public void setPoisoned(int TIME) {
        if(isShielded()){
            TIME_OF_SHIELD = 0;
        }
        else{
            TIME_OF_POISON = TIME;
        }
    }

    public boolean underEffect(){
        return isPoisoned() || isShielded();
    }

    public boolean isPoisoned() {
        return TIME_OF_POISON > 0;
    }

    public boolean isShielded() {
        return TIME_OF_SHIELD > 0;
    }

    public void setShielded(int TIME) {
        if(isPoisoned()){
            TIME_OF_POISON = 0;
        }
        else{
            TIME_OF_SHIELD = TIME;
        }
    }


    public void applyEffect(){
        if(isPoisoned()){
            shrink();
            TIME_OF_POISON--;
        }
        if(isShielded()){
            TIME_OF_SHIELD--;
        }
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

    public boolean isCollidingWith(Snake<Type,O> other) {
        ArrayList<SnakePart> allParts = other.getAllSnakePart();
        for (SnakePart snakePart : allParts) {
            if(head.isCollidingWith(snakePart)){
                return true;
            }
        }
        return false;
    }

    public void shrink(){
        if(tail.size() > 3){
            tail.remove(tail.size() - 1);
        }
        else{
            TIME_OF_POISON = 0;
        }
    }

    public boolean isCollidingWith(Food<Type,O> other) {
        return head.isCollidingWith(other);
    }

    public void chargeFood(int value){
        foodCharging += value;
        int nbGrowth = foodCharging / MAX_FOOD_CHARGING;
        foodCharging = foodCharging % MAX_FOOD_CHARGING;
        grow(nbGrowth);
    }

    private void grow(){
        SnakePart lastTail = tail.get(tail.size() - 1);
        O direction = lastTail.getOrientation();
        SnakePart newTail = new SnakePart(lastTail.getCenter().placeCoordinateFrom(direction.opposite(),GAP_BETWEEN_TAIL), direction, lastTail.getHitboxRadius());
        tail.add(newTail);
    }

    private void grow(int nb) {
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

    public void setBoosting(boolean isBoosting) {
        synchronized(lock) {
            this.isBoosting = isBoosting;
            if(isBoosting){
                this.currentSpeed = BOOST_SPEED;
            }
            else{
                this.currentSpeed = DEFAULT_SPEED;
            }
        }
    }

    public boolean isBoosting() {
        return isBoosting;
    }

    public int getCurrentSpeed() {
        synchronized(lock) {
            return currentSpeed;
        }
    }

    public int getBoostSpeed() {
        return BOOST_SPEED;
    }
}
