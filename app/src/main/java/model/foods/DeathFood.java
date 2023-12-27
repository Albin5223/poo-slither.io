package model.foods;

import interfaces.Orientation;
import model.coordinate.Coordinate;
import model.foods.FoodFactory.FoodType;
import model.plateau.Snake;

public abstract class DeathFood<Type extends Number & Comparable<Type>, O extends Orientation<O>> extends Food<Type,O> {

    private static final int value = 1;

    public DeathFood(Coordinate<Type, O> coordinate, double radius) {
        super(coordinate, radius, false, 0);
    }

    @Override
    public final void actOnSnake(Snake<Type,O> snake){
        snake.chargeFood(value);
    }

    @Override
    public final FoodType getFoodType() {
        return FoodType.DEATH_FOOD;
    }
    
}
