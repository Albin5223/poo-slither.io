package model.plateau;

import interfaces.Orientation.Direction;
import model.coordinate.CoordinateInteger;
import exceptions.ExceptionCollision;

public final class SnakeInteger extends Snake<Integer,Direction> {

    private SnakeInteger(CoordinateInteger location, PlateauInteger plateau, Direction startingDirection) throws ExceptionCollision {
        super(location,plateau,startingDirection);
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