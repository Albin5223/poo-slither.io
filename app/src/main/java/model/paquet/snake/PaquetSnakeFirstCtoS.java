package model.paquet.snake;

import model.skins.SkinFactory.SkinType;

public class PaquetSnakeFirstCtoS {

    private String pseudo;
    private SkinType skin;
    private int window_width;
    private int window_height;

    public String getMessage() {return pseudo;}
    public SkinType getSkin() {return skin;}
    public int getWindow_width() {return window_width;}
    public int getWindow_height() {return window_height;}

    public PaquetSnakeFirstCtoS(String pseudo, SkinType skin, int window_width, int window_height) {
        this.pseudo = pseudo;
        this.skin = skin;
        this.window_width = window_width;
        this.window_height = window_height;
    }
    
}
