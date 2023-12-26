package model.foods;

import model.coordinate.Coordinate;

public class FoodHolder<Type extends Number> {
    
    private Food<Type> food;
    private Coordinate<Type,?> coordinate;
    private double radius;

    public FoodHolder(Food<Type> food, Coordinate<Type,?> coordinate, double radius) {
        this.food = food;
        this.coordinate = coordinate;
        this.radius = radius;
    }
    
    public Food<Type> getFood() {
        return food;
    }

    public Coordinate<Type,?> getLocation() {
        return coordinate;
    }

    public double getRadius() {
        return radius;
    }

}
