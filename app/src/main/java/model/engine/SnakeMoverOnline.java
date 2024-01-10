package model.engine;

import exceptions.ExceptionCollision;
import interfaces.Engine;
import interfaces.Orientation;
import model.plateau.Snake;
import model.player.Bot.BotPlayer;
import server.ServerGen;

public class SnakeMoverOnline<Type extends Number & Comparable<Type>, O extends Orientation<O>> extends SnakeMover<Type,O>{

    ServerGen server;

    public SnakeMoverOnline(Snake<Type, O> snake, Engine<Type, O> engine, BotPlayer bot,ServerGen server) {
        super(snake, engine, bot);
        this.server = server;
        
    }
    
    @Override
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
            server.sendInformationsToDrawToAll();
            engine.notifyObservers();
        } catch (ExceptionCollision e) {
            snake.reset();
        }
    }


    
}
