package model.plateau;

import interfaces.Orientation.Direction;
import model.coordinate.CoordinateInteger;
import exceptions.ExceptionCollision;
import configuration.ConfigurationSnakeInteger;

public final class SnakeInteger extends Snake<Integer,Direction> {

    private SnakeInteger(CoordinateInteger location, PlateauInteger plateau, Direction startingDirection) throws ExceptionCollision {
        super(location,plateau,startingDirection,ConfigurationSnakeInteger.SNAKE_GAP_BETWEEN_TAIL, ConfigurationSnakeInteger.SNAKE_BIRTH_HITBOX_RADIUS, ConfigurationSnakeInteger.SNAKE_BIRTH_LENGTH, ConfigurationSnakeInteger.SNAKE_MAX_FOOD_CHARGING, ConfigurationSnakeInteger.SNAKE_DEFAULT_SPEED, ConfigurationSnakeInteger.SNAKE_BOOST_SPEED, ConfigurationSnakeInteger.DEATH_FOOD_PER_SEGMENT, ConfigurationSnakeInteger.TRAVERSABLE_WALL, ConfigurationSnakeInteger.IS_DEATH_FOOD, ConfigurationSnakeInteger.CAN_COLLIDING_WITH_HIMSELF, ConfigurationSnakeInteger.RADIUS_IS_GROWING);
    }

    public static SnakeInteger createSnakeInteger(PlateauInteger plateau) {
        CoordinateInteger location = (CoordinateInteger) plateau.border.getRandomCoordinate();
        Direction dir = Direction.getRandomDirection();
        
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
        super.move();
        this.currentTurning = Turning.FORWARD;  // We reset the turning to FORWARD after each move to avoid the snake to turn twice in a row
    }
}