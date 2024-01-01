package model.coordinate;

import interfaces.Orientation.Angle;

public class CoordinateDouble extends Coordinate<Double,Angle> {

    public CoordinateDouble(Double x, Double y) {
        super(x, y);
    }

    @Override
    public CoordinateDouble clone() {
        return new CoordinateDouble(this.x, this.y);
    }

    @Override
    public CoordinateDouble placeCoordinateFrom(Angle direction, double distance) {
        double radian = Math.toRadians(direction.getAngle());
        double newX = this.x + Math.cos(radian) * distance;
        double newY = this.y + Math.sin(radian) * distance;
        return new CoordinateDouble(newX, newY);
    }

    @Override
    public Coordinate<Double, Angle> clone(double x, double y) {
        return new CoordinateDouble(x, y);
    }
}
