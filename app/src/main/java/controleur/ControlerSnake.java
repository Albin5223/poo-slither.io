package controleur;

import configuration.TouchControler;
import interfaces.Orientation.Direction;
import interfaces.Turnable.Turning;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.plateau.Snake;

public class ControlerSnake implements KeyboardControler<Integer,Direction>{

    private KeyCode UP;
    private KeyCode DOWN;
    private KeyCode LEFT;
    private KeyCode RIGHT;
    private KeyCode BOOST;

    public ControlerSnake(KeyCode UP, KeyCode DOWN, KeyCode LEFT, KeyCode RIGHT, KeyCode BOOST){
        this.UP = UP;
        this.DOWN = DOWN;
        this.LEFT = LEFT;
        this.RIGHT = RIGHT;
        this.BOOST = BOOST;
    }

    public ControlerSnake(TouchControler controler){
        this.UP = controler.getKeyCode(TouchControler.DirectionOfTouch.UP);
        this.DOWN = controler.getKeyCode(TouchControler.DirectionOfTouch.DOWN);
        this.LEFT = controler.getKeyCode(TouchControler.DirectionOfTouch.LEFT);
        this.RIGHT = controler.getKeyCode(TouchControler.DirectionOfTouch.RIGHT);
        this.BOOST = controler.getKeyCode(TouchControler.DirectionOfTouch.BOOST);
    }

    @Override
    public void keyPressed(KeyEvent ev, Snake<Integer, Direction> snake) {
        if (ev.getCode() == this.LEFT) {
            if (snake.getDirection() != Direction.RIGHT && snake.getDirection() != Direction.LEFT) {
                if (snake.getDirection() == Direction.UP) {
                    snake.setTurning(Turning.GO_LEFT);
                } else {
                    snake.setTurning(Turning.GO_RIGHT);
                }
            }
        } else if (ev.getCode() == this.RIGHT) {
            if (snake.getDirection() != Direction.RIGHT && snake.getDirection() != Direction.LEFT) {
                if (snake.getDirection() == Direction.UP) {
                    snake.setTurning(Turning.GO_RIGHT);
                } else {
                    snake.setTurning(Turning.GO_LEFT);
                }
            }
        } else if (ev.getCode() == this.UP) {
            if (snake.getDirection() != Direction.UP && snake.getDirection() != Direction.DOWN) {
                if (snake.getDirection() == Direction.LEFT) {
                    snake.setTurning(Turning.GO_RIGHT);
                } else {
                    snake.setTurning(Turning.GO_LEFT);
                }
            }
        } else if (ev.getCode() == this.DOWN) {
            if (snake.getDirection() != Direction.UP && snake.getDirection() != Direction.DOWN) {
                if (snake.getDirection() == Direction.LEFT) {
                    snake.setTurning(Turning.GO_LEFT);
                } else {
                    snake.setTurning(Turning.GO_RIGHT);
                }
            }
        }
        else if(ev.getCode() == this.BOOST){
            snake.setBoosting(true);
        }
    };

    @Override
    public void keyReleased(KeyEvent ev, Snake<Integer, Direction> snake) {
        if (ev.getCode() == this.LEFT || ev.getCode() == this.RIGHT) {
            snake.setTurning(Turning.FORWARD);
        } else if (ev.getCode() == this.BOOST) {
            snake.setBoosting(false);
        }
    }
    
}
