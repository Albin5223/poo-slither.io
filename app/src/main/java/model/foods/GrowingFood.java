package model.foods;

import interfaces.Orientation;
import model.coordinate.Coordinate;
import model.plateau.Snake;

public class GrowingFood<Type extends Number & Comparable<Type>, O extends Orientation<O>> extends Food<Type,O> {

    private static final int value = 5;

    public GrowingFood(Coordinate<Type, O> coordinate) {
        super(true, coordinate);
    }

    @Override
    public void actOnSnake(Snake<Type, ?> snake) {
        snake.chargeFood(value);
    }
    
}
