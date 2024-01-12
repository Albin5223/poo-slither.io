package model.paquet.snake;

import java.io.Serializable;

import interfaces.GameBorder;
import interfaces.Orientation;

public class PaquetSnakeFirstStoC<Type extends Number & Comparable<Type>, O extends Orientation<O>> implements Serializable {

    private GameBorder <Type,O> border;

    public GameBorder<Type,O> getBorder() {
        return border;
    }

    public PaquetSnakeFirstStoC(GameBorder<Type,O> border) {
        this.border = border;
    }
    
}
