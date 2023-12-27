package model.foods;

import interfaces.Orientation;
import model.coordinate.Coordinate;
import model.plateau.Snake;

public class KillerFood<Type extends Number & Comparable<Type>, O extends Orientation<O>> extends Food<Type,O> {

    public KillerFood(Coordinate<Type, O> coordinate) {
        super(true, coordinate);
    }

    @Override
    public void actOnSnake(Snake<Type, ?> snake) {
        snake.reset();
    }
    
}
