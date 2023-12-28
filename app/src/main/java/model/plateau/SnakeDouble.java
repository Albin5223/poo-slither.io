package model.plateau;

import java.util.ArrayList;

import exceptions.ExceptionCollision;
import exceptions.ExceptionCollisionWithWall;
import interfaces.Orientation.Angle;
import model.coordinate.Coordinate;
import model.coordinate.CoordinateDouble;
import model.foods.Food;
import configuration.ConfigurationSnakeDouble;

public final class SnakeDouble extends Snake<Double,Angle> {

    
    public final class SnakePartDouble extends Snake<Double,Angle>.SnakePart {
            
        private SnakePartDouble(Coordinate<Double,Angle> center, Angle direction) {
            super(center, direction, ConfigurationSnakeDouble.SLITHER_BIRTH_HITBOX_RADIUS);
        }
    }

    private SnakeDouble(CoordinateDouble location, PlateauDouble plateau, Angle startingDirection) throws ExceptionCollision {
        super(location,plateau,startingDirection,ConfigurationSnakeDouble.SLITHER_GAP_BETWEEN_TAIL, ConfigurationSnakeDouble.SLITHER_BIRTH_HITBOX_RADIUS, ConfigurationSnakeDouble.SLITHER_BIRTH_LENGTH, ConfigurationSnakeDouble.SLITHER_MAX_FOOD_CHARGING, ConfigurationSnakeDouble.SLITHER_DEFAULT_SPEED, ConfigurationSnakeDouble.SLITHER_BOOST_SPEED, ConfigurationSnakeDouble.DEATH_FOOD_PER_SEGMENT);
        this.currentSpeed = ConfigurationSnakeDouble.SLITHER_DEFAULT_SPEED;
    }

    public static SnakeDouble createSnakeDouble(PlateauDouble plateau) {
        CoordinateDouble location = (CoordinateDouble) plateau.border.getRandomCoordinate();
        Angle angle = new Angle(0).getRandom();
        
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

    @Override
    public void move() throws ExceptionCollision {

        this.plateau.removeSnake(this); // We remove the snake from the board

        // Create the new head : distance from the old head = GAP, angle = updated head's angle considering the current turning
        Angle newDirection = turn(currentTurning, head.getOrientation());
        SnakePartDouble newHead = new SnakePartDouble(head.getCenter().placeCoordinateFrom(newDirection,ConfigurationSnakeDouble.SLITHER_GAP_BETWEEN_TAIL), newDirection);

        // We check if the snake is traversing the wall
        if(ConfigurationSnakeDouble.IS_TRAVERSABLE_WALL && !plateau.border.isInside(newHead.getCenter())){
            newHead = new SnakePartDouble(plateau.border.getOpposite(newHead.getCenter()), newDirection);
        }
        // We check if the snake is colliding with the wall
        else if(!plateau.border.isInside(newHead.getCenter())){
            if(ConfigurationSnakeDouble.IS_DEATH_FOOD){
                plateau.addDeathFood(this);
            }
            throw new ExceptionCollisionWithWall("Snake is colliding with the wall");
        }

        this.tail.remove(tail.size() - 1);  // We remove the last element of the tail
        this.tail.add(0, head); // We add the old head to the tail
        this.head = newHead;    // We update the head

        if(plateau.isCollidingWithAll(this)){  // We check if the snake is colliding with another snake
            if(ConfigurationSnakeDouble.IS_DEATH_FOOD){
                plateau.addDeathFood(this); // We add a death food for each part of the snake (except the head)
            }
            throw new ExceptionCollision("Snake is colliding with another snake");
        }
        ArrayList<Food<Double,Angle>> collidingFoods = plateau.isCollidingWithFoods(this);
        if(collidingFoods.size() != 0){ // We check if the snake is colliding with foods
            for(Food<Double,Angle> food : collidingFoods){
                food.actOnSnake(this);
            }
        }
        
        plateau.addSnake(this);   // We update the position of the snake on the board
    }    
}
