package model;

import java.util.Objects;

import interfaces.Coordinate;
import model.Snake.SnakePart.Direction;

public class CoordinateInteger implements Coordinate<Integer>{

    protected int x;
    protected int y;

    public CoordinateInteger(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Integer getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.x, this.y);
    }

    @Override
    public String toString() {
        return "(" + this.x + "," + this.y + ")";
    }

    @Override
    public double distanceTo(Coordinate<Integer> other) {
        return Math.sqrt(Math.pow(this.x - other.getX(), 2) + Math.pow(this.y - other.getY(), 2));
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof CoordinateInteger) {
            CoordinateInteger other = (CoordinateInteger) obj;
            return this.x == other.getX() && this.y == other.y;
        }
        return false;
    }
        
    @Override
    public boolean isContainedIn(Coordinate<Integer> upLeft, Coordinate<Integer> downRight) {
        return this.x >= upLeft.getX() && this.x <= downRight.getX() && this.y >= upLeft.getY() && this.y <= downRight.getY();
    } 

    @Override
    public CoordinateInteger clone() {
        return new CoordinateInteger(this.x, this.y);
    }

    @Override
    public CoordinateInteger placeCoordinateFrom(Direction direction) {
       switch (direction) {
        case UP:return new CoordinateInteger(this.x, this.y - 1);
        case DOWN:return new CoordinateInteger(this.x, this.y + 1);
        case LEFT:return new CoordinateInteger(this.x - 1, this.y);
        case RIGHT:return new CoordinateInteger(this.x + 1, this.y);
        default:
            throw new IllegalArgumentException("The direction " + direction + " is not a valid direction");
       }
    }
    
}
