package model;

import java.util.Objects;

import interfaces.Coordinate;
import interfaces.Orientation.Direction;

public class CoordinateInteger implements Coordinate<Integer,Direction> {

    protected int x;
    protected int y;

    public CoordinateInteger(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public Integer getX() {
        return x;
    }

    @Override
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
    public double distanceTo(Coordinate<Integer,Direction> other) {
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
    public CoordinateInteger clone() {
        return new CoordinateInteger(this.x, this.y);
    }

    @Override
    public CoordinateInteger placeCoordinateFrom(Direction direction, Integer distance) {
       switch (direction) {
        case UP:return new CoordinateInteger(this.x, this.y - distance);
        case DOWN:return new CoordinateInteger(this.x, this.y + distance);
        case LEFT:return new CoordinateInteger(this.x - distance, this.y);
        case RIGHT:return new CoordinateInteger(this.x + distance, this.y);
        default:
            throw new IllegalArgumentException("The direction " + direction + " is not a valid direction");
       }
    }
    
}
