package model.foods;

import model.plateau.Snake;

public abstract class Food<Type extends Number> {

    protected boolean respawn;

    public Food(boolean respawn) {
        this.respawn = respawn;
    }

    public boolean isRespawnable() {
        return respawn;
    }

    public abstract void actOnSnake(Snake<Type,?> snake);
}
