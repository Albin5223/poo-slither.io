package model.coordinate;

import interfaces.Orientation.Direction;

public class CoordinateInteger extends Coordinate<Integer,Direction> {

    public CoordinateInteger(Integer x, Integer y) {
        super(x, y);
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
