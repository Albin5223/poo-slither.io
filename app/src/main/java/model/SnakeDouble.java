package model;

import java.util.ArrayList;

import exceptions.ExceptionCollision;
import interfaces.Coordinate;
import interfaces.Orientation.Angle;

public final class SnakeDouble extends Snake<Double,Angle> {

    /*
     * The turning force of the snake is the angle that the snake will turn when the player press the left or right key
     */
    private static final Angle TURNING_FORCE = new Angle(5);

    public final class SnakePartDouble extends SnakePart<Double,Angle> {

        public static final double hitboxRadius = 10;
            
        private SnakePartDouble(Coordinate<Double,Angle> center, Angle direction) {
            super(center, direction, hitboxRadius);
        }

        @Override
        public SnakePartDouble clone() {
            return new SnakePartDouble(this.center, this.orientation);
        }

        @Override
        public Angle getOrientation() {
            return orientation;
        }

        @Override
        public CoordinateDouble getCenter() {
            return (CoordinateDouble) center;
        }
    }

    public SnakeDouble(Coordinate<Double,Angle> location, Plateau<Double,Angle> plateau, Angle startingDirection) {
        super(5.0);
        this.head = new SnakePartDouble(location.clone(), startingDirection);
        this.tail = new ArrayList<SnakePart<Double,Angle>>();

        Angle direction = head.getOrientation();
        SnakePartDouble tail1 = new SnakePartDouble(head.getCenter().placeCoordinateFrom(direction.opposite(),gap_between_tail), direction);
        tail.add(tail1);

        this.plateau = plateau;

        plateau.addSnake(this);
    }

    @Override
    public void resetSnake(Coordinate<Double, Angle> newLocation, Angle startingDirection, int nbTail) {
        this.head = new SnakePartDouble(newLocation.clone(), startingDirection);
        this.tail = new ArrayList<SnakePart<Double,Angle>>();

        Angle direction = head.getOrientation();
        for (int i = 0; i < nbTail; i++) {
        SnakePartDouble tail1 = new SnakePartDouble(head.getCenter().placeCoordinateFrom(direction.opposite(),gap_between_tail), direction);
        tail.add(tail1);
        }

        plateau.update(this);
    }

    @Override
    public Angle turn(Turning turning, Angle initialDirection) {
        return initialDirection.changeAngleWithTurn(turning, TURNING_FORCE);
    }

    @Override
    public SnakePartDouble[] getTail() {
        return tail.toArray(new SnakePartDouble[tail.size()]);
    }

    @Override
    public SnakePartDouble[] getAllSnakePart() {
        SnakePartDouble[] allSnakePart = new SnakePartDouble[tail.size() + 1];
        allSnakePart[0] = (SnakePartDouble) head;
        System.arraycopy(getTail(), 0, allSnakePart, 1, tail.size());
        return allSnakePart;
    }

    @Override
    public void move() throws ExceptionCollision {
        // Create the new head : distance from the old head = GAP, angle = updated head's angle considering the current turning
        Angle newDirection = turn(currentTurning, head.getOrientation());
        SnakePartDouble newHead = new SnakePartDouble(head.getCenter().placeCoordinateFrom(newDirection,gap_between_tail), newDirection);

        this.tail.remove(tail.size() - 1);  // We remove the last element of the tail
        this.tail.add(0, head); // We add the old head to the tail
        this.head = newHead;    // We update the head

        if(plateau.isCollidingWithAll(this)){  // We check if the snake is colliding with another snake
            throw new ExceptionCollision("Snake is colliding with another snake");
        }
        
        plateau.update(this);   // We update the position of the snake on the board
    }

    @Override
    public void grow() {
        SnakePartDouble lastTail = (SnakePartDouble) tail.get(tail.size() - 1);
        Angle direction = lastTail.getOrientation();
        SnakePartDouble newTail = new SnakePartDouble(lastTail.getCenter().placeCoordinateFrom(direction.opposite(),gap_between_tail), direction);
        tail.add(newTail);
    }
    
}
