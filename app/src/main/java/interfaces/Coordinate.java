package interfaces;

public interface Coordinate<Type extends Number, O extends Orientation> extends Cloneable {
    
    public Type getX();
    public Type getY();
    public Coordinate<Type,O> clone();
    public double distanceTo(Coordinate<Type,O> other);
    public int hashCode();
    public String toString();
    public boolean equals(Object obj);
    public Coordinate<Type,O> placeCoordinateFrom(O direction, Type distance);
}