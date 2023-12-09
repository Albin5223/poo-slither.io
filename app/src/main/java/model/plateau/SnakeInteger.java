package model.plateau;

import interfaces.Coordinate;
import interfaces.Orientation.Direction;
import java.util.ArrayList;

import exceptions.ExceptionCollision;
import model.coordinate.CoordinateInteger;
import model.plateau.Snake.SnakePart;

public final class SnakeInteger extends Snake<Integer,Direction> {

    public static final int WIDTH_OF_SNAKE = 20;
    public static final int SIZE_OF_SNAKE_BIRTH = 2;

    public final class SnakePartInteger extends SnakePart<Integer,Direction> {

        private SnakePartInteger(Coordinate<Integer,Direction> center, Direction direction) {
            super(center, direction,0);
        }

        @Override
        public SnakePartInteger clone() {
            return new SnakePartInteger(this.center, this.orientation);
        }

        @Override
        public Direction getOrientation() {
            return orientation;
        }

        @Override
        public CoordinateInteger getCenter() {
            return (CoordinateInteger) center;
        }
    }

    public Direction getOrientation() {
        return head.getOrientation();
    }

    private SnakeInteger(Coordinate<Integer,Direction> location, Plateau<Integer,Direction> plateau, Direction startingDirection) {
        super(1);
        this.head = new SnakePartInteger(location.clone(), startingDirection);
        this.tail = new ArrayList<SnakePart<Integer,Direction>>();

        Direction direction = head.getOrientation();
        SnakePartInteger tail1 = new SnakePartInteger(head.getCenter().placeCoordinateFrom(direction.opposite(),gap_between_tail), direction);
        tail.add(tail1);

        this.plateau = plateau;

        plateau.addSnake(this);
    }

    public static SnakeInteger creatSnakeInteger(Plateau<Integer,Direction> plateau) {
        
        Coordinate<Integer,Direction> location = ((PlateauInteger)plateau).getRandomCoordinate();
        Direction dir = Direction.getRandom();
        
        try{
            SnakeInteger snake = new SnakeInteger(location, plateau, dir);
            return snake;
        }
        catch(Exception e){
            return creatSnakeInteger(plateau);
        }
        
    }

    @Override
    protected void resetSnake(Coordinate<Integer,Direction> newLocation, Direction startingDirection, int nbTail) throws ExceptionCollision {
        this.head = new SnakePartInteger(newLocation.clone(), startingDirection);
        this.tail = new ArrayList<SnakePart<Integer,Direction>>();

        Direction direction = head.getOrientation();
        for (int i = 0; i < nbTail; i++) {
            SnakePartInteger tail1 = new SnakePartInteger(head.getCenter().placeCoordinateFrom(direction.opposite(),gap_between_tail), direction);
            tail.add(tail1);
        }

        if(plateau.isCollidingWithAll(this)){
            throw new ExceptionCollision("Snake is colliding with another snake");
        }

        plateau.update(this);
    }

    public static void resetSnake(SnakeInteger snake){
        try {
            snake.resetSnake(((PlateauInteger)snake.plateau).getRandomCoordinate(), Direction.getRandom(), SIZE_OF_SNAKE_BIRTH);
        } catch (ExceptionCollision e) {
            resetSnake(snake);
        }
    }

    @Override
    public Direction turn(Turning turning, Direction initialDirection) {
        return initialDirection.changeDirectionWithTurn(turning);
    }

    @Override
    public SnakePartInteger[] getTail() {
        return tail.toArray(new SnakePartInteger[tail.size()]);
    }

    @Override
    public SnakePartInteger[] getAllSnakePart() {
        SnakePartInteger[] allSnakePart = new SnakePartInteger[tail.size() + 1];
        allSnakePart[0] = (SnakePartInteger) head;
        System.arraycopy(getTail(), 0, allSnakePart, 1, tail.size());
        return allSnakePart;
    }

    @Override
    public void move() throws ExceptionCollision {
        // Create the new head : distance from the old head = GAP, angle = updated head's angle considering the current turning
        Direction newDirection = turn(currentTurning, head.getOrientation());
        SnakePartInteger newHead = new SnakePartInteger(head.getCenter().placeCoordinateFrom(newDirection,gap_between_tail), newDirection);

        this.tail.remove(tail.size() - 1);  // We remove the last element of the tail
        this.tail.add(0, head); // We add the old head to the tail
        this.head = newHead;    // We update the head

        if(plateau.isCollidingWithAll(this)){  // We check if the snake is colliding with another snake
            throw new ExceptionCollision("Snake is colliding with another snake");
        }

        if(((PlateauInteger )plateau).isCollidingWithWall(this) || isCollidingWithMe()){ // We check if the snake is colliding with a wall
            System.out.println("Snake is colliding with a wall");
            throw new ExceptionCollision("Snake is colliding with a wall");
        }

        if (plateau.isCollidingWithFood(this) != -1) { // We check if the snake is colliding with a food
            grow();
        }
        
        plateau.update(this);   // We update the position of the snake on the board

        if(currentTurning != Turning.FORWARD){
            currentTurning = Turning.FORWARD;
        }
    }

    @Override
    public void grow() {
        SnakePartInteger lastTail = (SnakePartInteger) tail.get(tail.size() - 1);
        Direction direction = lastTail.getOrientation();
        SnakePartInteger newTail = new SnakePartInteger(lastTail.getCenter().placeCoordinateFrom(direction.opposite(),gap_between_tail), direction);
        tail.add(newTail);
    }

    public boolean isCollidingWithMe(){
        for(SnakePart<Integer, Direction> part : tail){
            if(part.getCenter().equals(head.getCenter())){
                return true;
            }
        }
        return false;
    }
}