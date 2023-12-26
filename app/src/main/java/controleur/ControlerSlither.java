package controleur;

import interfaces.Orientation.Angle;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.plateau.Snake;
import interfaces.Turnable.Turning;

public class ControlerSlither implements KeyboardControler<Double,Angle>{

    private KeyCode LEFT;
    private KeyCode RIGHT;
    private KeyCode BOOST;

    public ControlerSlither(KeyCode LEFT, KeyCode RIGHT, KeyCode BOOST){
        this.LEFT = LEFT;
        this.RIGHT = RIGHT;
        this.BOOST = BOOST;
    }

    @Override
    public void keyPressed(KeyEvent ev, Snake<Double, Angle> snake) {
        if(ev.getCode() == LEFT){
            snake.setTurning(Turning.GO_LEFT);
        }
        else if(ev.getCode() == RIGHT){
            snake.setTurning(Turning.GO_RIGHT);
        }
        else if(ev.getCode() == BOOST){
            System.out.println("BOOST");
            snake.setBoosting(true);
        }
    }

    @Override
    public void keyReleased(KeyEvent ev, Snake<Double, Angle> snake) {
        if(ev.getCode() == LEFT || ev.getCode() == RIGHT){
            snake.setTurning(Turning.FORWARD);
        }
        else if(ev.getCode() == BOOST){
            System.out.println("STOOOOOOP BOOST");
            snake.setBoosting(false);
        }
    }
    
}
