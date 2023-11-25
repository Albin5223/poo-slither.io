package model;

import java.util.Objects;

public class Coordinate implements Cloneable {

    private double x;
    private double y;

    public Coordinate(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Coordinate)) {
            return false;
        }
        return this.x == ((Coordinate) other).x && this.y == ((Coordinate) other).y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.x, this.y);
    }

    @Override
    public String toString() {
        return "(" + Math.round(this.x * 100.0) / 100.0 + "," + Math.round(this.y * 100.0) / 100.0 + ")";
    }

    public double distanceTo(Coordinate other) {
        return Math.sqrt(Math.pow(this.x - other.x, 2) + Math.pow(this.y - other.y, 2));
    }

    public Coordinate placeCoordinateFrom(double distance, double angle) {
        double radian = Math.toRadians(angle);
        double newX = this.x + Math.cos(radian) * distance;
        double newY = this.y + Math.sin(radian) * distance;
        return new Coordinate(newX, newY);
    }

    public boolean isContainedIn(Coordinate upLeft, Coordinate downRight) {
        return this.x >= upLeft.x && this.x <= downRight.x && this.y >= upLeft.y && this.y <= downRight.y;
    } 

    @Override
    public Coordinate clone() {
        return new Coordinate(this.x, this.y);
    }
}
