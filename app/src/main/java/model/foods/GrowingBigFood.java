package model.foods;

import model.plateau.Snake;

public class GrowingBigFood<Type extends Number> extends Food<Type>{

    private static final int value = 10;

    public GrowingBigFood() {
        super(true);
    }

    @Override
    public void actOnSnake(Snake<Type, ?> snake) {
        snake.chargeFood(value);
    }
    
}
