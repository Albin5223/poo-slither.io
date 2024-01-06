package model.foods;

import java.io.Serializable;

import externData.OurColors;
import interfaces.Orientation;
import model.coordinate.Coordinate;
import model.foods.FoodFactory.FoodType;
import model.plateau.Snake;

public abstract class Food<Type extends Number & Comparable<Type>, O extends Orientation<O>> implements Serializable {

    protected final Coordinate<Type, O> coordinate;
    protected final double radius;
    protected final boolean respawn;
    protected final int probability;

    public Food(Coordinate<Type, O> coordinate, double radius, boolean respawn, int probability) {
        this.respawn = respawn;
        this.radius = radius;
        this.probability = probability;
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

    public int getProbability() {
        return probability;
    }

    public abstract FoodType getFoodType();
    public abstract OurColors getColor();
    public abstract void actOnSnake(Snake<Type,O> snake);
}
