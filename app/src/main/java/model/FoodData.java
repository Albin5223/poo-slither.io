package model;

import externData.OurColors;
import interfaces.Orientation;
import model.coordinate.Coordinate;
import model.foods.Food;

public class FoodData<Type extends Number & Comparable<Type>, O extends Orientation<O>> {

    private Coordinate<Type, O> center;
    private double radius;
    private OurColors apparence;

    public FoodData(Food<Type, O> food) {
        this.center = food.getCenter();
        this.radius = food.getRadius();
        this.apparence = food.getColor();
    }

    public Coordinate<Type, O> getCenter() {
        return center;
    }

    public double getRadius() {
        return radius;
    }

    public OurColors getFoodApparence() {
        return apparence;
    }
    
}
