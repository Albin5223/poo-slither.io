package model.foods;

import configuration.ConfigurationFood;
import interfaces.Orientation;
import model.coordinate.Coordinate;
import model.foods.FoodFactory.FoodType;
import model.plateau.Snake;

public abstract class DeathFood<Type extends Number & Comparable<Type>, O extends Orientation<O>> extends Food<Type,O> {

    

    public DeathFood(Coordinate<Type, O> coordinate, double radius) {
        super(coordinate, radius, ConfigurationFood.DEATH_FOOD_RESPAWN, ConfigurationFood.DEATH_FOOD_PROBABILITY);
    }

    @Override
    public final void actOnSnake(Snake<Type,O> snake){
        snake.chargeFood(ConfigurationFood.DEATH_FOOD_VALUE);
    }

    @Override
    public final FoodType getFoodType() {
        return FoodType.DEATH_FOOD;
    }
    
}
