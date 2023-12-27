package model.foods;

import interfaces.Orientation;
import model.coordinate.Coordinate;
import model.plateau.Snake;

public abstract class Food<Type extends Number & Comparable<Type>, O extends Orientation<O>> {

    protected Coordinate<Type, O> coordinate;
    protected double radius = 9;
    protected boolean respawn;

    public Food(boolean respawn, Coordinate<Type, O> coordinate) {
        this.respawn = respawn;
        this.coordinate = coordinate;
    }

    public Coordinate<Type,O> getCenter() {
        return coordinate;
    }

    public double getRadius() {
        return radius;
    }

    public boolean isRespawnable() {
        return respawn;
    }

    public abstract void actOnSnake(Snake<Type,?> snake);
}
