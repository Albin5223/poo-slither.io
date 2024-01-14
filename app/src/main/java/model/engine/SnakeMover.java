package model.engine;

import exceptions.ExceptionCollision;
import interfaces.Engine;
import interfaces.Orientation;
import javafx.application.Platform;
import model.plateau.Snake;
import model.player.Bot.BotPlayer;

public class SnakeMover<Type extends Number & Comparable<Type>, O extends Orientation<O>> {

    protected final Snake<Type,O> snake;
    protected final Engine<Type,O> engine;
    private BotPlayer bot;

    private Runnable mover = new Runnable() {
        @Override
        public void run() {
            while(!Thread.currentThread().isInterrupted()){
                if(bot != null){
                    bot.nextTurning();
                }
                move();
                if(snake.isBoosting()){
                    snake.incrementeShrink(0.2);
                }
                try {
                    Thread.sleep(1000/snake.getCurrentSpeed());
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    };

    private Thread moverThread = new Thread(mover);

    public SnakeMover(Snake<Type,O> snake, Engine<Type,O> engine, BotPlayer bot) {
        this.snake = snake;
        this.engine = engine;
        this.bot = bot;
    }

    public Snake<Type,O> getSnake() {return snake;}

    public void start() {moverThread.start();}

    public void move() {
        // call snake's method to update position
        try {
            snake.move();
        } catch (ExceptionCollision e) {
            snake.reset();
        }
        Platform.runLater(() -> engine.notifyObservers());
    }

    public void stop() {
        moverThread.interrupt();
        moverThread = new Thread(mover);
    }
}