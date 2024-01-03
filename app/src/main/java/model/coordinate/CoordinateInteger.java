package model.coordinate;

import interfaces.Orientation.Direction;

public class CoordinateInteger extends Coordinate<Integer,Direction>  {

    public CoordinateInteger(Integer x, Integer y) {
        super(x, y);
    }

    @Override
    public CoordinateInteger clone() {
        return new CoordinateInteger(this.x, this.y);
    }

    @Override
    public CoordinateInteger placeCoordinateFrom(Direction direction, double distance) {
       switch (direction) {
        case UP:return new CoordinateInteger(this.x, (int) (this.y - distance));
        case DOWN:return new CoordinateInteger(this.x, (int) (this.y + distance));
        case LEFT:return new CoordinateInteger((int) (this.x - distance), this.y);
        case RIGHT:return new CoordinateInteger((int) (this.x + distance), this.y);
        default:
            throw new IllegalArgumentException("The direction " + direction + " is not a valid direction");
       }
    }

    @Override
    public Coordinate<Integer, Direction> clone(double x, double y) {
        return new CoordinateInteger((int)x, (int)y);
    }
    
}
