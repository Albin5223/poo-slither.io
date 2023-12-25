package model.plateau;

import interfaces.Coordinate;
import interfaces.Orientation.Direction;
import model.coordinate.CoordinateInteger;
import exceptions.ExceptionCollision;
import exceptions.ExceptionCollisionWithSnake;
import exceptions.ExceptionCollisionWithWall;

public final class SnakeInteger extends Snake<Integer,Direction> {

    public static final int WIDTH_OF_SNAKE = 20;
    private static final Integer SNAKE_GAP_BETWEEN_TAIL = 1;
    private static final int SNAKE_BIRTH_LENGTH = 10;
    private static final int SNAKE_MAX_FOOD_CHARGING = 5;

    private static final int SNAKE_DEFAULT_SPEED = 10;
    private static final int SNAKE_BOOST_SPEED = SNAKE_DEFAULT_SPEED * 2;

    /** Do we want to add food behind a dead snake ? */
    private static final boolean DEATH_FOOD = false;

    /** Are we reappearing in the opposite side of the board when traversing the wall ? */
    private static final boolean TRAVERSABLE_WALL = true;

    public final class SnakePartInteger extends Snake<Integer,Direction>.SnakePart {

        private SnakePartInteger(Coordinate<Integer,Direction> center, Direction direction) {
            super(center, direction,0);
        }
    }

    public Direction getOrientation() {
        return head.getOrientation();
    }

    private SnakeInteger(CoordinateInteger location, PlateauInteger plateau, Direction startingDirection) throws ExceptionCollision {
        super(location,plateau,startingDirection,SNAKE_GAP_BETWEEN_TAIL, 0, SNAKE_BIRTH_LENGTH, SNAKE_MAX_FOOD_CHARGING, SNAKE_DEFAULT_SPEED, SNAKE_BOOST_SPEED);
        this.currentSpeed = SNAKE_DEFAULT_SPEED;
    }

    public static SnakeInteger createSnakeInteger(PlateauInteger plateau) {
        CoordinateInteger location = (CoordinateInteger) plateau.border.getRandomCoordinate();
        Direction dir = Direction.UP.getRandom();
        
        try{
            SnakeInteger snake = new SnakeInteger(location, plateau, dir);
            return snake;
        }
        catch(ExceptionCollision e){
            return createSnakeInteger(plateau);
        }
        
    }

    @Override
    public Direction turn(Turning turning, Direction initialDirection) {
        return initialDirection.changeDirectionWithTurn(turning);
    }

    @Override
    public void move() throws ExceptionCollision {

        this.plateau.removeSnake(this); // We remove the snake from the board

        // Create the new head : distance from the old head = GAP, angle = updated head's angle considering the current turning
        Direction newDirection = turn(currentTurning, head.getOrientation());
        SnakePartInteger newHead = new SnakePartInteger(head.getCenter().placeCoordinateFrom(newDirection,SNAKE_GAP_BETWEEN_TAIL), newDirection);

        // We check if the snake is traversing the wall
        if(TRAVERSABLE_WALL && !plateau.border.isInside(newHead.getCenter())){
            System.out.println("Snake is traversing the wall");
            newHead = new SnakePartInteger(plateau.border.getOpposite(newHead.getCenter()), newDirection);
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

        // We check if the snake is colliding with a snake, wall or itself
        if(plateau.isCollidingWithAll(this) || isCollidingWithMe()){
            if(DEATH_FOOD){
                plateau.addDeathFood(this);
            }
            throw new ExceptionCollisionWithSnake("Snake is colliding with another snake or itself");
        }

        int foodValue = plateau.isCollidingWithFood(this);
        if (foodValue != -1) { // We check if the snake is colliding with a food
            chargeFood(foodValue);
        }
        
        plateau.addSnake(this);   // We update the position of the snake on the board

        currentTurning = Turning.FORWARD;
    }

    public boolean isCollidingWithMe(){
        for(SnakePart part : tail){
            if(part.getCenter().equals(head.getCenter())){
                return true;
            }
        }
        return false;
    }
}