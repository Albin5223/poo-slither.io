package model.player;

import controleur.KeyboardControler;
import interfaces.HumanPlayer;
import interfaces.Orientation.Angle;
import interfaces.Turnable.Turning;
import javafx.scene.input.KeyEvent;
import model.plateau.Snake;

public class HumanSlitherPlayer implements HumanPlayer{
    
    private Snake<Double,Angle> snake;
    private KeyboardControler<Double,Angle> controler;

    public HumanSlitherPlayer(Snake<Double, Angle> snake,KeyboardControler<Double,Angle> controler){
        this.snake = snake;
        this.controler = controler;
    }


    @Override
    public void execute(KeyEvent ev) {
        controler.handle(ev, snake);
    }

    public void released(KeyEvent ev) {
        snake.setTurning(Turning.FORWARD);
    }
    
}
