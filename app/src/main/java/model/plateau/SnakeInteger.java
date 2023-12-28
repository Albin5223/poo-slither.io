package model.plateau;

import java.util.ArrayList;

import interfaces.Orientation.Direction;
import model.coordinate.Coordinate;
import model.coordinate.CoordinateInteger;
import model.foods.Food;
import exceptions.ExceptionCollision;
import exceptions.ExceptionCollisionWithSnake;
import exceptions.ExceptionCollisionWithWall;
import configuration.ConfigurationSnakeInteger;

public final class SnakeInteger extends Snake<Integer,Direction> {

    
    public final class SnakePartInteger extends Snake<Integer,Direction>.SnakePart {

        private SnakePartInteger(Coordinate<Integer,Direction> center, Direction direction) {
            super(center, direction,ConfigurationSnakeInteger.SNAKE_BIRTH_HITBOX_RADIUS);
        }

        /**
         * We block this function for the SnakePartInteger because the hitbox radius of a snake part is constant
         * @param hitboxRadius the new hitbox radius
         */
        @Override
        public void setHitboxRadius(double hitboxRadius) {
            // We do nothing because the hitbox radius of a snake part is constant
        }
    }

    public Direction getOrientation() {
        return head.getOrientation();
    }

    private SnakeInteger(CoordinateInteger location, PlateauInteger plateau, Direction startingDirection) throws ExceptionCollision {
        super(location,plateau,startingDirection,ConfigurationSnakeInteger.SNAKE_GAP_BETWEEN_TAIL, ConfigurationSnakeInteger.SNAKE_BIRTH_HITBOX_RADIUS, ConfigurationSnakeInteger.SNAKE_BIRTH_LENGTH, ConfigurationSnakeInteger.SNAKE_MAX_FOOD_CHARGING, ConfigurationSnakeInteger.SNAKE_DEFAULT_SPEED, ConfigurationSnakeInteger.SNAKE_BOOST_SPEED, ConfigurationSnakeInteger.DEATH_FOOD_PER_SEGMENT);
        this.currentSpeed = ConfigurationSnakeInteger.SNAKE_DEFAULT_SPEED;
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
        SnakePartInteger newHead = new SnakePartInteger(head.getCenter().placeCoordinateFrom(newDirection,ConfigurationSnakeInteger.SNAKE_GAP_BETWEEN_TAIL), newDirection);

        // We check if the snake is traversing the wall
        if(ConfigurationSnakeInteger.TRAVERSABLE_WALL && !plateau.border.isInside(newHead.getCenter())){
            newHead = new SnakePartInteger(plateau.border.getOpposite(newHead.getCenter()), newDirection);
        }
        // We check if the snake is colliding with the wall
        else if(!plateau.border.isInside(newHead.getCenter())){
            if(ConfigurationSnakeInteger.DEATH_FOOD){
                plateau.addDeathFood(this);
            }
            throw new ExceptionCollisionWithWall("Snake is colliding with the wall");
        }

        this.tail.remove(tail.size() - 1);  // We remove the last element of the tail
        this.tail.add(0, head); // We add the old head to the tail
        this.head = newHead;    // We update the head

        // We check if the snake is colliding with a snake, wall or itself
        if(plateau.isCollidingWithAll(this) || isCollidingWithMe()){
            if(ConfigurationSnakeInteger.DEATH_FOOD){
                plateau.addDeathFood(this);
            }
            throw new ExceptionCollisionWithSnake("Snake is colliding with another snake or itself");
        }

        ArrayList<Food<Integer,Direction>> collidingFoods = plateau.isCollidingWithFoods(this);
        if(collidingFoods.size() != 0){ // We check if the snake is colliding with foods
            for(Food<Integer,Direction> food : collidingFoods){
                food.actOnSnake(this);
            }
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