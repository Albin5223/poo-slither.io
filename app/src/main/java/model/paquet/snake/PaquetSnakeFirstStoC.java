package model.paquet.snake;

import java.io.Serializable;

import interfaces.GameBorder;

public class PaquetSnakeFirstStoC implements Serializable {

    private GameBorder <?,?> border;

    public GameBorder<?,?> getBorder() {
        return border;
    }

    public PaquetSnakeFirstStoC(GameBorder<?,?> border) {
        this.border = border;
    }
    
}
