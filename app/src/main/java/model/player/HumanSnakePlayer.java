package model.player;

import controleur.KeyboardControler;
import interfaces.HumanPlayer;
import interfaces.Orientation.Direction;
import javafx.scene.input.KeyEvent;
import model.plateau.SnakeInteger;

public class HumanSnakePlayer implements HumanPlayer{

    private SnakeInteger snake;
    private KeyboardControler<Integer,Direction> controler;


    public HumanSnakePlayer(SnakeInteger snake,KeyboardControler<Integer,Direction> controler){
        this.snake = snake;
        this.controler = controler;
    }


    @Override
    public void keyPressed(KeyEvent ev) {
        controler.keyPressed(ev, snake);
    }


    @Override
    public void keyReleased(KeyEvent ev) {
        controler.keyReleased(ev, snake);
    }
}
