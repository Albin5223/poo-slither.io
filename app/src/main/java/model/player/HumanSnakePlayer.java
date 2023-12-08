package model.player;

import controleur.SnakeControler;
import interfaces.HumanPlayer;
import javafx.scene.input.KeyEvent;
import model.SnakeInteger;

public class HumanSnakePlayer implements HumanPlayer{

    private SnakeInteger snake;
    private SnakeControler controler;


    public HumanSnakePlayer(SnakeInteger snake,SnakeControler controler){
        this.snake = snake;
        this.controler = controler;
    }

    @Override
    public void execute(KeyEvent ev) {
        controler.handle(ev, snake);
    }
}
