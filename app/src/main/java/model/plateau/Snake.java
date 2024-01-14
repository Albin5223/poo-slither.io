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

    private double NUMBER_OF_SHRINK = 0;

    /** The turning of the snake */
    protected Turning currentTurning = Turning.FORWARD;

    /** The current speed of the snake */
    protected int currentSpeed = 0;
    /** The current hitbox radius of the snake */
    protected int currentHitboxRadius;
    /** Is the snake currently boosting ? */
    protected volatile boolean isBoosting = false;

    /** The lock used to synchronize the acces to the boost value */
    private final Object lock = new Object();

    /** The gap between the head and the tail */
    public final int GAP_BETWEEN_TAIL;
    /** The radius of the snake when he's born */
    public final int BIRTH_HITBOX_RADIUS;
    /** The maximum radius of the snake */
    public final int MAX_RADIUS;
    /** The length of the snake when he's born */
    public final int BIRTH_LENGTH;
    /** The amount of food that the snake needs to eat before growing */
    public final int MAX_FOOD_CHARGING;
    /** The default speed of the snake */
    public final int DEFAULT_SPEED;
    /** The speed of the snake when he's boosting */
    public final int BOOST_SPEED;
    /** The max time of invincibility */
    public final int INVINCIBILITY_MAX_TIME;

    /** The time of poison */
    private int TIME_OF_POISON = 0;
    /**
     * The power of poison
     * @apiNote the power of poison is the number of segments that the snake will lose
     */
    private int POWER_OF_POISON = 0;
    /** The time of shield */
    private int TIME_OF_SHIELD = 0;

    private int TIME_OF_INVINCIBILITY = 0;

    /** The amount of death food that the snake will drop when he's dead */
    public final int DEATH_FOOD_PER_SEGMENT;
    /** Is the snake traversing the wall ? */
    public final boolean IS_TRAVERSABLE_WALL;
    /** Is the snake dropping food when he's dead ? */
    public final boolean IS_DROPING_FOOD_ON_DEATH;
    /** Can the snake colliding with himself ? */
    public final boolean CAN_COLLIDING_WITH_HIMSELF;
    /** Is the radius of the snake growing ? */
    public final boolean RADIUS_IS_GROWING;
    
    /** The amount of food that the snake has eaten */
    private int foodCharging = 0;

    /** Is the snake dead ? */
    private boolean isDead = false;

    /**
     * The class that will represent a part/segment of a snake
     */
    public final class SnakePart implements Cloneable,Serializable {

        /** The coordinate center of the snake part */
        protected Coordinate<Type,O> center;

        /** The direction of the snake part */
        protected O orientation;

        /**
         * Constructs a new snake part with the given arguments.
         * @param center the center of the snake part
         * @param direction the direction of the snake part
         */
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

        /**
         * @return the orientation of the snake part
         */
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


    /**
     * Constructs a new snake with the given arguments.
     * @param location the location of the snake
     * @param plateau the board where the snake has to be
     * @param startingDirection the starting direction of the snake
     * @throws ExceptionCollision if the snake is colliding something when he's born
     * 
     * @apiNote The snake will be added to the board automatically at the end of the construction if there is no collision
     */
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
        this.INVINCIBILITY_MAX_TIME = plateau.getSnakeConfig().getInvincibilityTime();
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

    /**
     * @return the current hitbox radius of the snake
     */
    public final double getHitboxRadius() {
        return currentHitboxRadius;
    }

    /**
     * @return the gap between each segment of the snake
     */
    public final int getGapBetweenTail() {
        return GAP_BETWEEN_TAIL;
    }

    /**
     * Set the skin of the snake
     * @param skin the skin to set
     */
    public final void setSkin(Skin skin) {
        this.skin = skin;
    }

    /** @return the skin of the snake */
    public final Skin getSkin() {
        return skin;
    }

    /** @return the board where the snake is */
    public Plateau<Type, O> getPlateau() {
        return plateau;
    }

    /** @return the orientation of the snake */
    public final O getOrientation(){
        return head.getOrientation();
    }

    /**
     * Private method to reset the attributes of the snake
     * @apiNote this method is called when the snake is dead in {@link #resetSnake(Coordinate, Orientation, int)}
     */
    private void resetAttributes(){
        this.currentHitboxRadius = BIRTH_HITBOX_RADIUS;
        this.foodCharging = 0;
        this.setBoosting(false);
        this.setPoisoned(0,0);
        this.setShielded(0);
        this.setInvincible(0);
    }

    /**
     * Reset the snake with the given arguments.
     * @param newLocation the new location of the snake
     * @param startingDirection the new starting direction of the snake
     * @param nbTail the new length of the snake
     * @throws ExceptionCollisionWithSnake if the snake is colliding with another snake when he's born
     */
    private final void resetSnake(Coordinate<Type,O> newLocation, O startingDirection, int nbTail) throws ExceptionCollisionWithSnake{
        
        this.plateau.removeSnake(this); // We remove the snake from the board

        if(IS_DROPING_FOOD_ON_DEATH && !isDead){    // We drop food if the snake is droping food and if he's not already dead
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

    /**
     * Reset a snake
     * @apiNote this method is called when the snake is dead
     * @apiNote this method will call himself until the snake is not colliding with another snake when he's born
     */
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

    /**
     * Make the snake poisoned
     * @param TIME the time of poison (the number of time that the snake will be poisoned)
     * @param POWER the power of poison (the number of segments that the snake will lose)
     */
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

    /**
     * @return true if the snake is under effect (poisoned or shielded), false otherwise
     */
    public final boolean underEffect(){
        return isPoisoned() || isShielded() || isInvincible();
    }

    /** @return true if the snake is poisoned, false otherwise */
    public final boolean isPoisoned() {
        return TIME_OF_POISON > 0;
    }

    /** @return true if the snake is shielded, false otherwise */
    public final boolean isShielded() {
        return TIME_OF_SHIELD > 0;
    }

    /**
     * Make the snake shielded
     * @param TIME the time of shield (the number of time that the snake will be shielded)
     */
    public final void setShielded(int TIME) {
        if(isPoisoned()){
            TIME_OF_POISON = 0;
        }
        else{
            TIME_OF_SHIELD = TIME > 0 ? TIME : 0;
        }
    }

    /**
     * Make the snake invincible
     * @param TIME the time of invincibility (the number of time that the snake will be invincible)
     */
    public final void setInvincible(int TIME){
        TIME_OF_INVINCIBILITY = TIME > 0 ? TIME : 0;
    }

    /** @return true if the snake is invincible, false otherwise */
    public final boolean isInvincible(){
        return TIME_OF_INVINCIBILITY > 0;
    }

    /**
     * Try to kill the snake
     * @apiNote if the snake is shielded, the shield will be removed
     */
    public final void try_to_kill(){
        if(!(isShielded() || isInvincible())){
            this.reset();
        }
        else{
            TIME_OF_SHIELD = 0;
        }
    }

    /**
     * Apply the effect of the snake (poisoned or shielded)
     */
    public final void applyEffect(){
        if(isPoisoned()){
            shrink(POWER_OF_POISON);
            TIME_OF_POISON -= 1;
        }
        if(isShielded()){
            TIME_OF_SHIELD -= 1;
        }
        if(isInvincible()){
            TIME_OF_INVINCIBILITY -= 1;
        }
    }

    /**
     * @return a copy of {@link #head} and {@link #tail} in an ArrayList
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
            if(!isShielded() && !isInvincible()){
                throw new ExceptionCollisionWithSnake("Snake is colliding with another snake");
            }
            else{
                setShielded(0);
                setInvincible(INVINCIBILITY_MAX_TIME);
            }
        }
        ArrayList<Food<Type,O>> collidingFoods = plateau.isCollidingWithFoods(this);
        if(collidingFoods.size() != 0){ // We check if the snake is colliding with foods
            for(Food<Type,O> food : collidingFoods){
                food.actOnSnake(this);  // We apply the effect of the food on the snake
            }
        }
    }   

    /**
     * Turning the snake to the left or to the right
     * @param turning the direction to turn
     * @param initialDirection the initial direction of the snake
     */
    @Override
    public abstract O turn(Turning turning, O initialDirection);

    /**
     * @return the orientation of the snake
     */
    public final O getDirection() {
        return head.getOrientation();
    }

    /**
     * @return the current turning of the snake (where he wants to turn)
     */
    public final Turning getCurrentTurning() {
        return currentTurning;
    }

    /**
     * Method to know if the snake is colliding with another snake
     * @param other the other snake
     * @return true if the snake is colliding with the other snake, false otherwise
     * @apiNote When checking if the snake is colliding with himself, the firsts parts of the tail are excluded (because the head is colliding with them)
     */
    public final boolean isCollidingWith(Snake<Type,O> other) {
        ArrayList<SnakePart> parts;
        if(this == other){  // If the snake is colliding with itself, we check if the head is only colliding with his tail (excluding his head)
            if(!CAN_COLLIDING_WITH_HIMSELF){return false;}
            parts = getTail();
            ArrayList<SnakePart> safeTail = new ArrayList<SnakePart>(); // We have to exclude all the parts in the tail that are ALREADY colliding with the head
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

    /**
     * Method to know if the snake is colliding with a food
     * @param other the food
     * @return true if the snake is colliding with the food, false otherwise
     */
    public final boolean isCollidingWith(Food<Type,O> other) {
        return head.center.distanceTo(other.getCenter()) <= this.currentHitboxRadius + other.getRadius();
    }

    /**
     * Method to shrink the snake
     * @param nb the number of segments that the snake will lose
     * @apiNote the snake cannot be more small than 3, so the {@link #TIME_OF_POISON} will be set to 0 if the snake is more small than 3
     */

    
    public final void incrementeShrink(double n){
        synchronized(lock) {
            NUMBER_OF_SHRINK +=n;
            if(NUMBER_OF_SHRINK > 1){
                shrink(1);
                NUMBER_OF_SHRINK -= 1;
            }
        }
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

    /**
     * Method to charge the food
     * @param value the amount of food to charge
     */
    public final void chargeFood(int value){
        foodCharging += value;
        int nbGrowth = foodCharging / MAX_FOOD_CHARGING;
        foodCharging = foodCharging % MAX_FOOD_CHARGING;
        grow(nbGrowth);
    }

    /**
     * Method to grow the snake (add a segment to the tail)
     */
    private final void grow(){
        SnakePart lastTail = tail.get(tail.size() - 1);
        O direction = lastTail.getOrientation();
        SnakePart newTail = new SnakePart(lastTail.getCenter().placeCoordinateFrom(direction.opposite(),GAP_BETWEEN_TAIL), direction);
        tail.add(newTail);
    }

    /**
     * Method to grow the snake (add a segment to the tail)
     * @param nb the number of segments to add
     */
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

    /**
     * Set the current turning of the snake
     * @param turning the new turning of the snake
     */
    public final void setTurning(Turning turning) {
        this.currentTurning = turning;
    }

    /**
     * Set the current boosting of the snake
     * @param isBoosting the new boosting of the snake
     */
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

    /**
     * @return true if the snake is boosting, false otherwise
     */
    public final boolean isBoosting() {
        return isBoosting;
    }

    /**
     * @return the current speed of the snake
     */
    public final int getCurrentSpeed() {
        synchronized(lock) {
            return currentSpeed;
        }
    }

    /**
     * @return the boost speed of the snake
     */
    public final int getBoostSpeed() {
        return BOOST_SPEED;
    }
}
