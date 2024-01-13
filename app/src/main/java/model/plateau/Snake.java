package model.plateau;

import java.util.ArrayList;

import exceptions.ExceptionCollision;
import exceptions.ExceptionCollisionWithSnake;
import exceptions.ExceptionCollisionWithWall;
import interfaces.Orientation;
import interfaces.Turnable;
import model.coordinate.Coordinate;
import model.foods.Food;
import model.skins.Skin;
import java.io.Serializable;
import model.skins.SkinRandom;

public sealed abstract class Snake<Type extends Number & Comparable<Type>, O extends Orientation<O>> implements Turnable<O> permits SnakeInteger, SnakeDouble {

    /** The skin of the snake, by default it's random */
    protected Skin skin = SkinRandom.build();

    /** The turning of the snake */
    protected Turning currentTurning = Turning.FORWARD;

    /** The current speed of the snake */
    protected int currentSpeed = 0;
    protected int currentHitboxRadius;
    protected volatile boolean isBoosting = false;

    private final Object lock = new Object();

    public final int GAP_BETWEEN_TAIL;
    public final int BIRTH_HITBOX_RADIUS;
    public final int MAX_RADIUS;
    public final int BIRTH_LENGTH;
    /** The amount of food that the snake needs to eat before growing */
    public final int MAX_FOOD_CHARGING;
    public final int DEFAULT_SPEED;
    public final int BOOST_SPEED;

    private int TIME_OF_POISON = 0;
    private int POWER_OF_POISON = 0;
    private int TIME_OF_SHIELD = 0;

    public final int DEATH_FOOD_PER_SEGMENT;
    public final boolean IS_TRAVERSABLE_WALL;
    public final boolean IS_DROPING_FOOD_ON_DEATH;
    public final boolean CAN_COLLIDING_WITH_HIMSELF;
    public final boolean RADIUS_IS_GROWING;
    
    /** The amount of food that the snake has eaten */
    private int foodCharging = 0;

    private boolean isDead = false;

    public final class SnakePart implements Cloneable,Serializable {

        /** The coordinate center of the snake part */
        protected Coordinate<Type,O> center;

        /** The direction of the snake part */
        protected O orientation;

        protected SnakePart(Coordinate<Type,O> center, O direction) {
            this.center = center.clone(); 
            this.orientation = direction;
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

        @Override
        public String toString() {
            return "SnakePart [center=" + center.toString() + ", orientation=" + orientation +"]";
        }

        public SnakePart clone(){
            return new SnakePart(this.center, this.orientation);
        }
    }

    /** The head of the snake */
    protected SnakePart head;
    /** The tail of the snake */
    protected ArrayList<SnakePart> tail;
    /** The board where the snake is */
    protected Plateau<Type,O> plateau;

    protected Snake(Coordinate<Type,O> location, Plateau<Type,O> plateau, O startingDirection) throws ExceptionCollision {
        this.GAP_BETWEEN_TAIL = plateau.getSnakeConfig().getGapBetweenTail();
        this.BIRTH_HITBOX_RADIUS = plateau.getSnakeConfig().getBirthHitboxRadius();
        this.currentHitboxRadius = BIRTH_HITBOX_RADIUS;
        this.MAX_RADIUS = plateau.getSnakeConfig().getMaxRadius();
        this.BIRTH_LENGTH = plateau.getSnakeConfig().getBirthLength();
        this.MAX_FOOD_CHARGING = plateau.getSnakeConfig().getMaxFoodCharging();
        this.DEFAULT_SPEED = plateau.getSnakeConfig().getDefaultSpeed();
        this.BOOST_SPEED = plateau.getSnakeConfig().getBoostSpeed();
        this.currentSpeed = DEFAULT_SPEED;
        this.DEATH_FOOD_PER_SEGMENT = plateau.getSnakeConfig().getDeathFoodPerSegment();
        this.IS_TRAVERSABLE_WALL = plateau.getSnakeConfig().isTraversableWall();
        this.IS_DROPING_FOOD_ON_DEATH = plateau.getSnakeConfig().isDeathFood();
        this.CAN_COLLIDING_WITH_HIMSELF = plateau.getSnakeConfig().isCollidingWithHimself();
        this.RADIUS_IS_GROWING = plateau.getSnakeConfig().isRadiusGrowing();
        this.head = new SnakePart(location.clone(), startingDirection);
        this.tail = new ArrayList<SnakePart>();

        O direction = head.getOrientation();
        for (int i = 0; i < BIRTH_LENGTH; i++) {
            SnakePart tail1 = new SnakePart(head.getCenter().placeCoordinateFrom(direction.opposite(),GAP_BETWEEN_TAIL), direction);
            tail.add(tail1);
        }

        this.plateau = plateau;

        plateau.addSnake(this);
    }

    public void setHitboxRadius(int hitboxRadius) {
        this.currentHitboxRadius = hitboxRadius;
    }

    public final double getHitboxRadius() {
        return currentHitboxRadius;
    }

    public final void setSkin(Skin skin) {
        this.skin = skin;
    }

    public final Skin getSkin() {
        return skin;
    }

    public Plateau<Type, O> getPlateau() {
        return plateau;
    }

    public final O getOrientation(){
        return head.getOrientation();
    }

    private void resetAttributes(){
        this.currentHitboxRadius = BIRTH_HITBOX_RADIUS;
        this.foodCharging = 0;
        this.setBoosting(false);
        this.setPoisoned(0,0);
        this.setShielded(0);
    }

    private final void resetSnake(Coordinate<Type,O> newLocation, O startingDirection, int nbTail) throws ExceptionCollisionWithSnake{
        
        this.plateau.removeSnake(this); // We remove the snake from the board

        if(IS_DROPING_FOOD_ON_DEATH && !isDead){
            plateau.addDeathFood(this); // We add a death food for each part of the snake (except the head)
        }

        this.isDead = true;
        resetAttributes();

        this.head = new SnakePart(newLocation.clone(), startingDirection);
        this.tail = new ArrayList<SnakePart>();

        O direction = head.getOrientation();
        for (int i = 0; i < nbTail; i++) {
            SnakePart tail1 = new SnakePart(head.getCenter().placeCoordinateFrom(direction.opposite(),GAP_BETWEEN_TAIL), direction);
            tail.add(tail1);
        }

        if(plateau.isCollidingWithAll(this)){
            throw new ExceptionCollisionWithSnake("Snake is colliding with another snake");
        }

        this.isDead = false;
        this.plateau.addSnake(this);
    }

    public final void reset(){
        try {
            resetSnake(plateau.border.getRandomCoordinate(), head.getOrientation().getRandom(), BIRTH_LENGTH);
        } catch (ExceptionCollisionWithSnake e) {
            reset();
        }
    }

    /**
     * @return a copy of {@link #head}
     */
    public final SnakePart getHead() {
        return head;
    }

    /**
     * @return a copy of {@link #tail}
     */
    public final ArrayList<SnakePart> getTail() {
        return new ArrayList<>(tail);
    }

    public final void setPoisoned(int TIME, int POWER) {
        if(isShielded()){
            TIME_OF_SHIELD = 0;
            POWER_OF_POISON = 0;
        }
        else{
            TIME_OF_POISON = TIME > 0 ? TIME : 0;
            POWER_OF_POISON = POWER > 0 ? POWER : 0;
        }
    }

    public final boolean underEffect(){
        return isPoisoned() || isShielded();
    }

    public final boolean isPoisoned() {
        return TIME_OF_POISON > 0;
    }

    public final boolean isShielded() {
        return TIME_OF_SHIELD > 0;
    }

    public final void setShielded(int TIME) {
        if(isPoisoned()){
            TIME_OF_POISON = 0;
        }
        else{
            TIME_OF_SHIELD = TIME > 0 ? TIME : 0;
        }
    }

    public final void try_to_kill(){
        if(!isShielded()){
            this.reset();
        }
        else{
            TIME_OF_SHIELD = 0;
        }
    }


    public final void applyEffect(){
        if(isPoisoned()){
            shrink(POWER_OF_POISON);
            TIME_OF_POISON -= 1;
        }
        if(isShielded()){
            TIME_OF_SHIELD -= 1;
        }
    }

    /**
     * @return a copy of {@link #head} and {@link #tail}
     */
    public final ArrayList<SnakePart> getAllSnakePart() {
        ArrayList<SnakePart> allSnakePart = new ArrayList<>();
        allSnakePart.add(head);
        allSnakePart.addAll(tail);
        return allSnakePart;
    }

    /**
     * Update the position of the snake on the board (the snake has moved)
     * @throws ExceptionCollision if the snake is colliding something
     */
    public void move() throws ExceptionCollision {

        this.plateau.removeSnake(this); // We remove the snake from the board

        // Create the new head : distance from the old head = GAP, angle = updated head's angle considering the current turning
        O newDirection = turn(currentTurning, head.getOrientation());
        SnakePart newHead = new SnakePart(head.getCenter().placeCoordinateFrom(newDirection,GAP_BETWEEN_TAIL), newDirection);

        // We check if the snake is traversing the wall
        if(IS_TRAVERSABLE_WALL && !plateau.border.isInside(newHead.getCenter())){
            newHead = new SnakePart(plateau.border.getOpposite(newHead.getCenter()), newDirection);
        }
        // We check if the snake is colliding with the wall or he's outside the board
        else if(!plateau.border.isInside(newHead.getCenter())){
            throw new ExceptionCollisionWithWall("Snake is colliding with the wall");
        }

        this.tail.remove(tail.size() - 1);  // We remove the last element of the tail
        this.tail.add(0, head); // We add the old head to the tail
        this.head = newHead;    // We update the head

        if(RADIUS_IS_GROWING && currentHitboxRadius < MAX_RADIUS){
            this.currentHitboxRadius = (int) (BIRTH_HITBOX_RADIUS + (tail.size() * (BIRTH_HITBOX_RADIUS * 0.005)));
            if (currentHitboxRadius > MAX_RADIUS) {currentHitboxRadius = MAX_RADIUS;}
        }

        plateau.addSnake(this);   // We update the position of the snake on the board

        if(plateau.isCollidingWithAll(this)){  // We check if the snake is colliding with another snake
            throw new ExceptionCollisionWithSnake("Snake is colliding with another snake");
        }
        ArrayList<Food<Type,O>> collidingFoods = plateau.isCollidingWithFoods(this);
        if(collidingFoods.size() != 0){ // We check if the snake is colliding with foods
            for(Food<Type,O> food : collidingFoods){
                food.actOnSnake(this);
            }
        }
    }   

    /**
     * Turning the snake to the left or to the right, like in a trigonometric circle (0° is on the right, 90° is on the top, 180° is on the left, 270° is on the bottom)
     * @param turning the direction to turn
     */
    @Override
    public abstract O turn(Turning turning, O initialDirection);

    /**
     * @return the angle of the snake
     */
    public final O getDirection() {
        return head.getOrientation();
    }

    public final Turning getCurrentTurning() {
        return currentTurning;
    }

    public final boolean isCollidingWith(Snake<Type,O> other) {
        ArrayList<SnakePart> parts;
        if(this == other){  // If the snake is colliding with itself, we check if the head is only colliding with his tail (excluding his head)
            if(!CAN_COLLIDING_WITH_HIMSELF){return false;}
            parts = getTail();
            ArrayList<SnakePart> safeTail = new ArrayList<SnakePart>();
            for (SnakePart snakePart : parts) {
                if(head.center.distanceTo(snakePart.center) <= currentHitboxRadius){
                    safeTail.add(snakePart);
                }
                else{
                    break;
                }
            }
            parts.removeAll(safeTail);
        }
        else{parts = other.getAllSnakePart();}  // If the snake is colliding with another snake, we check if the head is colliding with all the parts of the other snake

        for (SnakePart snakePart : parts) {
            if(head.center.distanceTo(snakePart.center) <= other.getHitboxRadius()){
                return true;
            }
        }
        return false;
    }

    public final boolean isCollidingWith(Food<Type,O> other) {
        return head.center.distanceTo(other.getCenter()) <= this.currentHitboxRadius + other.getRadius();
    }

    public final void shrink(int nb){
        int newSize = Math.max(tail.size() - nb, 3);
        while (tail.size() > newSize) {
            tail.remove(tail.size() - 1);
        }
        if(tail.size() <= 3){
            TIME_OF_POISON = 0;
        }
    }

    public final void chargeFood(int value){
        foodCharging += value;
        int nbGrowth = foodCharging / MAX_FOOD_CHARGING;
        foodCharging = foodCharging % MAX_FOOD_CHARGING;
        grow(nbGrowth);
    }

    private final void grow(){
        SnakePart lastTail = tail.get(tail.size() - 1);
        O direction = lastTail.getOrientation();
        SnakePart newTail = new SnakePart(lastTail.getCenter().placeCoordinateFrom(direction.opposite(),GAP_BETWEEN_TAIL), direction);
        tail.add(newTail);
    }

    private final void grow(int nb) {
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

    public final void setTurning(Turning turning) {
        this.currentTurning = turning;
    }

    public final void setBoosting(boolean isBoosting) {
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

    public final boolean isBoosting() {
        return isBoosting;
    }

    public final int getCurrentSpeed() {
        synchronized(lock) {
            return currentSpeed;
        }
    }

    public final int getBoostSpeed() {
        return BOOST_SPEED;
    }
}
