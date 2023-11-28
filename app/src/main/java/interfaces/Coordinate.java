package interfaces;

import model.Snake.SnakePart.Direction;

public interface Coordinate<Type extends Number> extends Cloneable {
    
    public Type getX();
    public Type getY();
    public Coordinate<Type> clone();
    public double distanceTo(Coordinate<Type> other);
    public boolean isContainedIn(Coordinate<Type> upLeft, Coordinate<Type> downRight);
    public int hashCode();
    public String toString();
    public boolean equals(Object obj);
    public Coordinate<Type> placeCoordinateFrom(Direction direction);
}
