package model.paquet.snake;

import java.io.Serializable;

import model.skins.Skin;

public class PaquetSnakeFirstCtoS implements Serializable {

    private String pseudo;
    private Skin skin;
    private int window_width;
    private int window_height;

    public String getMessage() {return pseudo;}
    public Skin getSkin() {return skin;}
    public int getWindow_width() {return window_width;}
    public int getWindow_height() {return window_height;}

    public PaquetSnakeFirstCtoS(String pseudo, Skin skin, int window_width, int window_height) {
        this.pseudo = pseudo;
        this.skin = skin;
        this.window_width = window_width;
        this.window_height = window_height;
    }
    
}
