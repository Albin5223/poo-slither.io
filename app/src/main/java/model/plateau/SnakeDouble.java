package model.plateau;

import exceptions.ExceptionCollision;
import exceptions.ExceptionCollisionWithWall;
import interfaces.Coordinate;
import interfaces.Orientation.Angle;
import model.coordinate.CoordinateDouble;

public final class SnakeDouble extends Snake<Double,Angle> {

    /*
     * The turning force of the snake is the angle that the snake will turn when the player press the left or right key
     */
    private static final Angle TURNING_FORCE = new Angle(2);
    private static final double GAP_BETWEEN_TAIL = 1;
    private static final int SIZE_OF_SNAKE_BIRTH = 50;
    private static final int MAX_FOOD_CHARGING = 10;

    /** Do we want to add food behind a dead snake ? */
    private static final boolean DEATH_FOOD = true;

    /** Are we reappearing in the opposite side of the board when traversing the wall ? */
    private static final boolean TRAVERSABLE_WALL = true;

    public final class SnakePartDouble extends Snake<Double,Angle>.SnakePart {

        public static final double HITBOX_RADIUS_BIRTH = 10;
            
        private SnakePartDouble(Coordinate<Double,Angle> center, Angle direction) {
            super(center, direction, HITBOX_RADIUS_BIRTH);
        }
    }

    private SnakeDouble(CoordinateDouble location, PlateauDouble plateau, Angle startingDirection) throws ExceptionCollision {
        super(location,plateau,startingDirection,GAP_BETWEEN_TAIL, SnakePartDouble.HITBOX_RADIUS_BIRTH, SIZE_OF_SNAKE_BIRTH, MAX_FOOD_CHARGING);
    }

    public static SnakeDouble createSnakeDouble(PlateauDouble plateau) {
        CoordinateDouble location = (CoordinateDouble) plateau.border.getRandomCoordinate();
        Angle angle = Angle.getRandom();
        
        try{
            SnakeDouble snake = new SnakeDouble(location, plateau, angle);
            return snake;
        }
        catch(ExceptionCollision e){
            return createSnakeDouble(plateau);
        }
        
    }

    protected void resetSnake(CoordinateDouble newLocation, Angle startingDirection, int nbTail) throws ExceptionCollision {
        super.resetSnake(newLocation, startingDirection, SnakePartDouble.HITBOX_RADIUS_BIRTH, nbTail);
    }

    public static void resetSnake(SnakeDouble snake){
        try {
            snake.resetSnake((CoordinateDouble)snake.plateau.border.getRandomCoordinate(), Angle.getRandom(), SIZE_OF_SNAKE_BIRTH);
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

        this.plateau.removeSnake(this); // We remove the snake from the board

        // Create the new head : distance from the old head = GAP, angle = updated head's angle considering the current turning
        Angle newDirection = turn(currentTurning, head.getOrientation());
        SnakePartDouble newHead = new SnakePartDouble(head.getCenter().placeCoordinateFrom(newDirection,GAP_BETWEEN_TAIL), newDirection);

        // We check if the snake is traversing the wall
        if(TRAVERSABLE_WALL && !plateau.border.isInside(newHead.getCenter())){
            System.out.println("Snake is traversing the wall");
            newHead = new SnakePartDouble(plateau.border.getOpposite(newHead.getCenter()), newDirection);
        }
        // We check if the snake is colliding with the wall
        else if(!plateau.border.isInside(newHead.getCenter())){
            if(DEATH_FOOD){
                plateau.addDeathFood(this);
            }
            throw new ExceptionCollisionWithWall("Snake is colliding with the wall");
        }

        this.tail.remove(tail.size() - 1);  // We remove the last element of the tail
        this.tail.add(0, head); // We add the old head to the tail
        this.head = newHead;    // We update the head

        if(plateau.isCollidingWithAll(this)){  // We check if the snake is colliding with another snake
            if(DEATH_FOOD){
                plateau.addDeathFood(this); // We add a death food for each part of the snake (except the head)
            }
            throw new ExceptionCollision("Snake is colliding with another snake");
        }
        int value = plateau.isCollidingWithFood(this);
        if(value != -1){ // We check if the snake is colliding with a food
            chargeFood(value);
        }
        
        plateau.addSnake(this);   // We update the position of the snake on the board
    }    
}
