package model.plateau;

import java.util.Random;

import exceptions.ExceptionCollision;
import interfaces.Coordinate;
import interfaces.Orientation.Angle;
import model.coordinate.CoordinateDouble;

public final class SnakeDouble extends Snake<Double,Angle> {

    /*
     * The turning force of the snake is the angle that the snake will turn when the player press the left or right key
     */
    private static final Angle TURNING_FORCE = new Angle(5);
    private static final double GAP_BETWEEN_TAIL = 0.4;
    private static final int SIZE_OF_SNAKE_BIRTH = 10;

    //private int size = 2;

    public final class SnakePartDouble extends SnakePart {

        public static final double HITBOX_RADIUS_BIRTH = 4;
            
        private SnakePartDouble(Coordinate<Double,Angle> center, Angle direction) {
            super(center, direction, HITBOX_RADIUS_BIRTH);
        }
    }

    private SnakeDouble(Coordinate<Double,Angle> location, Plateau<Double,Angle> plateau, Angle startingDirection) {
        super(location,plateau,startingDirection,GAP_BETWEEN_TAIL, SnakePartDouble.HITBOX_RADIUS_BIRTH);
    }

    public static SnakeDouble createSnakeDouble(Plateau<Double,Angle> plateau) {
        Coordinate<Double,Angle> location = new CoordinateDouble(0,0);
        Angle angle = Angle.getRandom();
        
        try{
            SnakeDouble snake = new SnakeDouble(location, plateau, angle);
            return snake;
        }
        catch(Exception e){
            return createSnakeDouble(plateau);
        }
        
    }

    protected void resetSnake(Coordinate<Double, Angle> newLocation, Angle startingDirection, int nbTail) throws ExceptionCollision {
        super.resetSnake(newLocation, startingDirection, SnakePartDouble.HITBOX_RADIUS_BIRTH, nbTail);
    }

    public static void resetSnake(SnakeDouble snake){
        try {
            Random random = new Random();
            double x = random.nextDouble(60);
            double y = random.nextDouble(60);
            snake.resetSnake(new CoordinateDouble(x, y), Angle.getRandom(), SIZE_OF_SNAKE_BIRTH);
        } catch (ExceptionCollision e) {
            resetSnake(snake);
        }
    }

    @Override
    public Angle turn(Turning turning, Angle initialDirection) {
        return initialDirection.changeAngleWithTurn(turning, TURNING_FORCE);
    }

    @Override
    public void move() throws ExceptionCollision {
        // Create the new head : distance from the old head = GAP, angle = updated head's angle considering the current turning
        Angle newDirection = turn(currentTurning, head.getOrientation());
        SnakePartDouble newHead = new SnakePartDouble(head.getCenter().placeCoordinateFrom(newDirection,GAP_BETWEEN_TAIL), newDirection);

        this.tail.remove(tail.size() - 1);  // We remove the last element of the tail
        this.tail.add(0, head); // We add the old head to the tail
        this.head = newHead;    // We update the head

        if(plateau.isCollidingWithAll(this)){  // We check if the snake is colliding with another snake
            throw new ExceptionCollision("Snake is colliding with another snake");
        }
        int value = plateau.isCollidingWithFood(this);
        if(value != -1){ // We check if the snake is colliding with a food
            grow(value);
        }
        
        plateau.update(this);   // We update the position of the snake on the board
    }    
}
