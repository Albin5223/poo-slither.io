package model.plateau;

import exceptions.ExceptionCollision;
import interfaces.Orientation.Angle;
import model.coordinate.CoordinateDouble;
import configuration.ConfigurationSnakeDouble;

/**
 * A snake in the Slither.io game.
 */
public final class SnakeDouble extends Snake<Double,Angle> {

    private SnakeDouble(CoordinateDouble location, PlateauDouble plateau, Angle startingDirection) throws ExceptionCollision {
        super(location,plateau,startingDirection);
    }

    public static SnakeDouble createSnakeDouble(PlateauDouble plateau) {
        CoordinateDouble location = (CoordinateDouble) plateau.border.getRandomCoordinate();
        Angle angle = Angle.getRandomAngle();
        
        try{
            SnakeDouble snake = new SnakeDouble(location, plateau, angle);
            return snake;
        }
        catch(ExceptionCollision e){
            return createSnakeDouble(plateau);
        }
        
    }

    @Override
    public Angle turn(Turning turning, Angle initialDirection) {
        return initialDirection.changeAngleWithTurn(turning, ((ConfigurationSnakeDouble) plateau.getSnakeConfig()).getTurningForce());
    } 
}
