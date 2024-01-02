package model.plateau;

import exceptions.ExceptionCollision;
import interfaces.Orientation.Angle;
import model.coordinate.CoordinateDouble;
import configuration.ConfigurationSnakeDouble;

public final class SnakeDouble extends Snake<Double,Angle> {

    private SnakeDouble(CoordinateDouble location, PlateauDouble plateau, Angle startingDirection) throws ExceptionCollision {
        super(location,plateau,startingDirection,ConfigurationSnakeDouble.SLITHER_GAP_BETWEEN_TAIL, ConfigurationSnakeDouble.SLITHER_BIRTH_HITBOX_RADIUS, ConfigurationSnakeDouble.SLITHER_BIRTH_LENGTH, ConfigurationSnakeDouble.SLITHER_MAX_FOOD_CHARGING, ConfigurationSnakeDouble.SLITHER_DEFAULT_SPEED, ConfigurationSnakeDouble.SLITHER_BOOST_SPEED, ConfigurationSnakeDouble.DEATH_FOOD_PER_SEGMENT, ConfigurationSnakeDouble.IS_TRAVERSABLE_WALL, ConfigurationSnakeDouble.IS_DEATH_FOOD, ConfigurationSnakeDouble.CAN_COLLIDING_WITH_HIMSELF, ConfigurationSnakeDouble.RADIUS_IS_GROWING);
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
        return initialDirection.changeAngleWithTurn(turning, ConfigurationSnakeDouble.SLITHER_TURNING_FORCE);
    } 
}
