package model;

import java.util.Objects;

import interfaces.Coordinate;
import model.Snake.SnakePart.Direction;

public class CoordinateDouble implements Coordinate<Double> {

    protected double x;
    protected double y;

    public CoordinateDouble(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Double getX() {
        return x;
    }

    public Double getY() {
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
    public double distanceTo(Coordinate<Double> other) {
        return Math.sqrt(Math.pow(this.x - other.getX(), 2) + Math.pow(this.y - other.getY(), 2));
    }

    /** 
     * Place a coordinate from {@code this} coordinate with a distance and an angle
     * @param distance the distance from {@code this} and the new coordinate
     * @param angle the angle from {@code this} and the new coordinate
     * @return the new coordinate
    */
    public CoordinateDouble placeCoordinateFrom(double distance, double angle) {
        double radian = Math.toRadians(angle);
        double newX = this.x + Math.cos(radian) * distance;
        double newY = this.y + Math.sin(radian) * distance;
        return new CoordinateDouble(newX, newY);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof CoordinateDouble) {
            CoordinateDouble other = (CoordinateDouble) obj;
            return this.x == other.getX() && this.y == other.y;
        }
        return false;
    }
        
    @Override
    public boolean isContainedIn(Coordinate<Double> upLeft, Coordinate<Double> downRight) {
        return this.x >= upLeft.getX() && this.x <= downRight.getX() && this.y >= upLeft.getY() && this.y <= downRight.getY();
    } 

    @Override
    public CoordinateDouble clone() {
        return new CoordinateDouble(this.x, this.y);
    }

    @Override
    public Coordinate<Double> placeCoordinateFrom(Direction direction) {
        return null;
    }
}
