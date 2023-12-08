package model.coordinate;

import java.util.Objects;

import interfaces.Coordinate;
import interfaces.Orientation.Angle;

public class CoordinateDouble implements Coordinate<Double,Angle> {

    protected double x;
    protected double y;

    public CoordinateDouble(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public Double getX() {
        return x;
    }

    @Override
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
    public double distanceTo(Coordinate<Double,Angle> other) {
        return Math.sqrt(Math.pow(this.x - other.getX(), 2) + Math.pow(this.y - other.getY(), 2));
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
    public CoordinateDouble clone() {
        return new CoordinateDouble(this.x, this.y);
    }

    @Override
    public CoordinateDouble placeCoordinateFrom(Angle direction, Double distance) {
        double radian = Math.toRadians(direction.getAngle());
        double newX = this.x + Math.cos(radian) * distance;
        double newY = this.y + Math.sin(radian) * distance;
        return new CoordinateDouble(newX, newY);
    }
}
