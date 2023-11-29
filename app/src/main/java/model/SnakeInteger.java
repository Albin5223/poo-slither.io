package model;

import interfaces.Coordinate;
import interfaces.Orientation.Direction;

import java.util.ArrayList;
import exceptions.ExceptionCollision;

public final class SnakeInteger extends Snake<Integer,Direction> {

    public final class SnakePartInteger extends SnakePart<Integer,Direction> {

        private SnakePartInteger(Coordinate<Integer,Direction> center, Direction direction) {
            super(center, direction,0);
        }

        @Override
        public SnakePartInteger clone() {
            return new SnakePartInteger(this.center, this.orientation);
        }

        @Override
        public Direction getOrientation() {
            return orientation;
        }

        @Override
        public CoordinateInteger getCenter() {
            return (CoordinateInteger) center;
        }
    }

    public SnakeInteger(Coordinate<Integer,Direction> location, Plateau<Integer,Direction> plateau, Direction startingDirection) {
        super(1);
        this.head = new SnakePartInteger(location.clone(), startingDirection);
        this.tail = new ArrayList<SnakePart<Integer,Direction>>();

        Direction direction = head.getOrientation();
        SnakePartInteger tail1 = new SnakePartInteger(head.getCenter().placeCoordinateFrom(direction.opposite(),gap_between_tail), direction);
        tail.add(tail1);

        this.plateau = plateau;

        plateau.addSnake(this);
    }

    @Override
    public void resetSnake(Coordinate<Integer,Direction> newLocation, Direction startingDirection) {
        this.head = new SnakePartInteger(newLocation.clone(), startingDirection);
        this.tail = new ArrayList<SnakePart<Integer,Direction>>();

        Direction direction = head.getOrientation();
        SnakePartInteger tail1 = new SnakePartInteger(head.getCenter().placeCoordinateFrom(direction.opposite(),gap_between_tail), direction);
        tail.add(tail1);

        plateau.update(this);
    }

    @Override
    public Direction turn(Turning turning, Direction initialDirection) {
        return initialDirection.changeDirectionWithTurn(turning);
    }

    @Override
    public SnakePartInteger[] getTail() {
        return tail.toArray(new SnakePartInteger[tail.size()]);
    }

    @Override
    public SnakePartInteger[] getAllSnakePart() {
        SnakePartInteger[] allSnakePart = new SnakePartInteger[tail.size() + 1];
        allSnakePart[0] = (SnakePartInteger) head;
        System.arraycopy(getTail(), 0, allSnakePart, 1, tail.size());
        return allSnakePart;
    }

    @Override
    public void move() throws ExceptionCollision {
        // Create the new head : distance from the old head = GAP, angle = updated head's angle considering the current turning
        Direction newDirection = turn(currentTurning, head.getOrientation());
        SnakePartInteger newHead = new SnakePartInteger(head.getCenter().placeCoordinateFrom(newDirection,gap_between_tail), newDirection);

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
        SnakePartInteger lastTail = (SnakePartInteger) tail.get(tail.size() - 1);
        Direction direction = lastTail.getOrientation();
        SnakePartInteger newTail = new SnakePartInteger(lastTail.getCenter().placeCoordinateFrom(direction.opposite(),gap_between_tail), direction);
        tail.add(newTail);
    }
}