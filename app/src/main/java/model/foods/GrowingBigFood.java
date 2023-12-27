package model.foods;

import interfaces.Orientation;
import model.coordinate.Coordinate;
import model.plateau.Snake;

public class GrowingBigFood<Type extends Number & Comparable<Type>, O extends Orientation<O>> extends Food<Type,O>{

    private static final int value = 10;

    public GrowingBigFood(Coordinate<Type, O> coordinate) {
        super(true, coordinate);
    }

    @Override
    public void actOnSnake(Snake<Type, ?> snake) {
        snake.chargeFood(value);
    }
    
}
