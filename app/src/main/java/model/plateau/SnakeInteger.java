package model.plateau;

import interfaces.Coordinate;
import interfaces.Orientation.Direction;
import exceptions.ExceptionCollision;

public final class SnakeInteger extends Snake<Integer,Direction> {

    public static final int WIDTH_OF_SNAKE = 20;
    private static final Integer GAP_BETWEEN_TAIL = 2;
    private static final int SIZE_OF_SNAKE_BIRTH = 10;
    private static final int MAX_FOOD_CHARGING = 5;

    public final class SnakePartInteger extends Snake<Integer,Direction>.SnakePart {

        private SnakePartInteger(Coordinate<Integer,Direction> center, Direction direction) {
            super(center, direction,0);
        }
    }

    public Direction getOrientation() {
        return head.getOrientation();
    }

    private SnakeInteger(Coordinate<Integer,Direction> location, Plateau<Integer,Direction> plateau, Direction startingDirection) {
        super(location,plateau,startingDirection,GAP_BETWEEN_TAIL, 0, SIZE_OF_SNAKE_BIRTH, MAX_FOOD_CHARGING);
    }

    public static SnakeInteger creatSnakeInteger(Plateau<Integer,Direction> plateau) {
        Coordinate<Integer,Direction> location = plateau.getRandomCoordinate();
        Direction dir = Direction.getRandom();
        
        try{
            SnakeInteger snake = new SnakeInteger(location, plateau, dir);
            return snake;
        }
        catch(Exception e){
            return creatSnakeInteger(plateau);
        }
        
    }

    protected void resetSnake(Coordinate<Integer,Direction> newLocation, Direction startingDirection, int nbTail) throws ExceptionCollision {
        super.resetSnake(newLocation, startingDirection, 0, nbTail);
    }

    public static void resetSnake(SnakeInteger snake){
        try {
            snake.resetSnake(snake.plateau.getRandomCoordinate(), Direction.getRandom(), SIZE_OF_SNAKE_BIRTH);
        } catch (ExceptionCollision e) {
            resetSnake(snake);
        }
    }

    @Override
    public Direction turn(Turning turning, Direction initialDirection) {
        return initialDirection.changeDirectionWithTurn(turning);
    }

    @Override
    public void move() throws ExceptionCollision {
        // Create the new head : distance from the old head = GAP, angle = updated head's angle considering the current turning
        Direction newDirection = turn(currentTurning, head.getOrientation());
        SnakePartInteger newHead = new SnakePartInteger(head.getCenter().placeCoordinateFrom(newDirection,GAP_BETWEEN_TAIL), newDirection);

        this.tail.remove(tail.size() - 1);  // We remove the last element of the tail
        this.tail.add(0, head); // We add the old head to the tail
        this.head = newHead;    // We update the head

        if(plateau.isCollidingWithAll(this)){  // We check if the snake is colliding with another snake
            throw new ExceptionCollision("Snake is colliding with another snake");
        }

        if(((PlateauInteger)plateau).isCollidingWithWall(this) || isCollidingWithMe()){ // We check if the snake is colliding with a wall
            System.out.println("Snake is colliding with a wall");
            throw new ExceptionCollision("Snake is colliding with a wall");
        }

        int foodValue = plateau.isCollidingWithFood(this);
        if (foodValue != -1) { // We check if the snake is colliding with a food
            chargeFood(foodValue);
        }
        
        plateau.update(this);   // We update the position of the snake on the board

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