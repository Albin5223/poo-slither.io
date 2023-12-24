package model.engine;

import exceptions.ExceptionCollision;
import interfaces.Engine;

public class EngineRunner {

    // TODO : en travaux

    private final Engine<?,?> engine;

    public EngineRunner(Engine<?,?> engine) {
        this.engine = engine;
    }

    public void move() throws ExceptionCollision {
        // call snake's method to update position
        //snake.move();
    }
}