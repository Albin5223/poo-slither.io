package model.coordinate;

import java.util.Objects;

import interfaces.Orientation;

public abstract class Coordinate<Type extends Number, O extends Orientation<O>> implements Cloneable {
    
    protected Type x;
    protected Type y;

    public Coordinate(Type x, Type y) {
        this.x = x;
        this.y = y;
    }

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

    public double distanceTo(Coordinate<Type,?> other) {
        return Math.sqrt(Math.pow(this.x.doubleValue() - other.getX().doubleValue(), 2) + Math.pow(this.y.doubleValue() - other.getY().doubleValue(), 2));
    }

    public boolean equals(Object obj) {
        if (obj instanceof Coordinate<?,?>) {
            Coordinate<?,?> other = (Coordinate<?,?>) obj;
            return this.x.equals(other.getX()) && this.y.equals(other.getY());
        }
        return false;
    }

    public abstract Coordinate<Type,O> clone();
    public abstract Coordinate<Type,O> clone(double x, double y);
    public abstract Coordinate<Type,O> placeCoordinateFrom(O direction, double distance);
}