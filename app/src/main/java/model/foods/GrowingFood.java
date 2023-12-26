package model.foods;

import model.plateau.Snake;

public class GrowingFood<Type extends Number> extends Food<Type> {

    private static final int value = 5;

    public GrowingFood() {
        super(true);
    }

    @Override
    public void actOnSnake(Snake<Type, ?> snake) {
        snake.chargeFood(value);
    }
    
}
