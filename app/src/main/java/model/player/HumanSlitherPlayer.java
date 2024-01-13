package model.player;

import controleur.KeyboardControler;
import interfaces.HumanPlayer;
import interfaces.Orientation.Angle;
import javafx.scene.input.KeyEvent;
import model.plateau.Snake;

public class HumanSlitherPlayer implements HumanPlayer{
    
    private Snake<Double,Angle> snake;
    private KeyboardControler<Double,Angle> controler;

    public HumanSlitherPlayer(Snake<Double, Angle> snake,KeyboardControler<Double,Angle> controler){
        this.snake = snake;
        this.controler = controler;
    }

    public Snake<Double,Angle> getSnake(){
        return snake;
    }

    @Override
    public void keyPressed(KeyEvent ev) {
        controler.keyPressed(ev, snake);
    }


    @Override
    public void keyReleased(KeyEvent ev) {
        controler.keyReleased(ev, snake);
    }

    @Override
    public void mouseMoved(double x, double y) {
    }

    @Override
    public void mousePressed(double x, double y) {
    }

    @Override
    public void mouseReleased(double x, double y) {
    }


    
    
}
