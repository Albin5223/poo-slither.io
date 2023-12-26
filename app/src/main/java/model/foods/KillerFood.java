package model.foods;

import model.plateau.Snake;

public class KillerFood<Type extends Number> extends Food<Type> {

    public KillerFood() {
        super(true);
    }

    @Override
    public void actOnSnake(Snake<Type, ?> snake) {
        snake.reset();
    }
    
}
