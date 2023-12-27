package model.plateau;

import java.util.ArrayList;

import exceptions.ExceptionCollision;
import exceptions.ExceptionCollisionWithWall;
import interfaces.Orientation.Angle;
import model.coordinate.Coordinate;
import model.coordinate.CoordinateDouble;
import model.foods.Food;

public final class SnakeDouble extends Snake<Double,Angle> {

    /*
     * The turning force of the snake is the angle that the snake will turn when the player press the left or right key
     */
    private static final Angle SLITHER_TURNING_FORCE = new Angle(4);
    private static final double SLITHER_GAP_BETWEEN_TAIL = 4;
    private static final int SLITHER_BIRTH_LENGTH = 50;
    private static final int SLITHER_MAX_FOOD_CHARGING = 10;
    private static final double SLITHER_BIRTH_HITBOX_RADIUS = 10;

    private static final int SLITHER_DEFAULT_SPEED = 100;
    private static final int SLITHER_BOOST_SPEED = SLITHER_DEFAULT_SPEED * 2;

    /** Do we want to add food behind a dead snake ? */
    private static final boolean IS_DEATH_FOOD = true;
    private static final int DEATH_FOOD_PER_SEGMENT = 1;

    /** Are we reappearing in the opposite side of the board when traversing the wall ? */
    private static final boolean IS_TRAVERSABLE_WALL = true;

    public final class SnakePartDouble extends Snake<Double,Angle>.SnakePart {
            
        private SnakePartDouble(Coordinate<Double,Angle> center, Angle direction) {
            super(center, direction, SLITHER_BIRTH_HITBOX_RADIUS);
        }
    }

    private SnakeDouble(CoordinateDouble location, PlateauDouble plateau, Angle startingDirection) throws ExceptionCollision {
        super(location,plateau,startingDirection,SLITHER_GAP_BETWEEN_TAIL, SLITHER_BIRTH_HITBOX_RADIUS, SLITHER_BIRTH_LENGTH, SLITHER_MAX_FOOD_CHARGING, SLITHER_DEFAULT_SPEED, SLITHER_BOOST_SPEED, DEATH_FOOD_PER_SEGMENT);
        this.currentSpeed = SLITHER_DEFAULT_SPEED;
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
        return initialDirection.changeAngleWithTurn(turning, SLITHER_TURNING_FORCE);
    }

    @Override
    public void move() throws ExceptionCollision {

        this.plateau.removeSnake(this); // We remove the snake from the board

        // Create the new head : distance from the old head = GAP, angle = updated head's angle considering the current turning
        Angle newDirection = turn(currentTurning, head.getOrientation());
        SnakePartDouble newHead = new SnakePartDouble(head.getCenter().placeCoordinateFrom(newDirection,SLITHER_GAP_BETWEEN_TAIL), newDirection);

        // We check if the snake is traversing the wall
        if(IS_TRAVERSABLE_WALL && !plateau.border.isInside(newHead.getCenter())){
            newHead = new SnakePartDouble(plateau.border.getOpposite(newHead.getCenter()), newDirection);
        }
        // We check if the snake is colliding with the wall
        else if(!plateau.border.isInside(newHead.getCenter())){
            if(IS_DEATH_FOOD){
                plateau.addDeathFood(this);
            }
            throw new ExceptionCollisionWithWall("Snake is colliding with the wall");
        }

        this.tail.remove(tail.size() - 1);  // We remove the last element of the tail
        this.tail.add(0, head); // We add the old head to the tail
        this.head = newHead;    // We update the head

        if(plateau.isCollidingWithAll(this)){  // We check if the snake is colliding with another snake
            if(IS_DEATH_FOOD){
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
