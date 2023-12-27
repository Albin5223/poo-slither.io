package model.engine;

import exceptions.ExceptionCollision;
import interfaces.Engine;
import interfaces.Orientation;
import javafx.animation.AnimationTimer;
import model.plateau.Snake;
import model.player.Bot.BotPlayer;

public class SnakeMover<Type extends Number & Comparable<Type>, O extends Orientation<O>> {

    private final Snake<Type,O> snake;
    private final Engine<Type,O> engine;
    private AnimationTimer timer;
    private double moverSpeed;
    BotPlayer bot;

    public Snake<Type,O> getSnake() {
        return snake;
    }

    private long lastUpdate = 0;

    public SnakeMover(Snake<Type,O> snake, Engine<Type,O> engine, BotPlayer bot) {
        this.snake = snake;
        this.engine = engine;
        this.bot = bot;
        this.moverSpeed = snake.getCurrentSpeed();
        
        this.timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (lastUpdate == 0 || now - lastUpdate >= 1_000_000_000 / moverSpeed) {
                    if(bot != null){
                        bot.nextTurning();
                    }
                    move();
                    lastUpdate = now;
                }
            }
        };
    }

    public void start() {
        timer.start();
    }

    public void move() {
        // check if speed has changed
        double newSpeed = snake.getCurrentSpeed();
        if (newSpeed != moverSpeed) {
            // speed has changed, adjust timer
            moverSpeed = newSpeed;
            lastUpdate = 0; // force move() to be called at next frame
        }

        // call snake's method to update position
        try {
            snake.move();
            engine.notifyObservers();
        } catch (ExceptionCollision e) {
            snake.reset();
        }
    }

    public void stop() {
        timer.stop();
    }

    public void resume() {
        timer.start();
    }
}