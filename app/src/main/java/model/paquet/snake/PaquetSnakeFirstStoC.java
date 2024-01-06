package model.paquet.snake;

import java.io.Serializable;

import model.plateau.PlateauInteger.BorderInteger;

public class PaquetSnakeFirstStoC implements Serializable {

    private BorderInteger border;

    public BorderInteger getBorder() {return border;}

    public PaquetSnakeFirstStoC(BorderInteger border) {
        this.border = border;
    }
    
}
