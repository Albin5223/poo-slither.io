package model.coordinate;

import java.util.Objects;
import interfaces.Orientation;

public abstract class Coordinate<Type extends Number, O extends Orientation<O>> implements Cloneable {
    
    protected Type x;
    protected Type y;

    public Type getX() {
        return x;
    }

    public Type getY() {
        return y;
    }

    public int hashCode() {
        return Objects.hash(this.x, this.y);
    }

    public String toString() {
        return "(" + this.x + "," + this.y + ")";
    }

    public double distanceTo(Coordinate<Type,O> other) {
        return Math.sqrt(Math.pow(this.x.doubleValue() - other.getX().doubleValue(), 2) + Math.pow(this.y.doubleValue() - other.getY().doubleValue(), 2));
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Coordinate<?,?> other = (Coordinate<?,?>) obj;
        return Objects.equals(x, other.getX()) && Objects.equals(y, other.getY());
    }

    public abstract Coordinate<Type,O> clone();
    public abstract Coordinate<Type,O> clone(double x, double y);
    public abstract Coordinate<Type,O> placeCoordinateFrom(O direction, double distance);
}