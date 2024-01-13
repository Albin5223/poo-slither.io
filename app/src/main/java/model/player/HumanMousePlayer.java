package model.player;


import GUI.Window;
import interfaces.Orientation.Angle;
import interfaces.Turnable.Turning;
import javafx.scene.input.KeyEvent;
import model.plateau.Snake;

public class HumanMousePlayer extends HumanSlitherPlayer {

    private Snake<Double,Angle> snake;
    public HumanMousePlayer(Snake<Double, Angle> snake){
        super(snake,null);
        this.snake = snake;
    }

    @Override
    public void keyPressed(KeyEvent ev) {
        
    }

    @Override
    public void keyReleased(KeyEvent ev) {
    }


    private double normalizeAngle(double angle) {
        while (angle > 180) {
            angle -= 360;
        }
        while (angle < -180) {
            angle += 360;
        }
        return angle;
    }

    private void calculateAngle(double x, double y){
        double centerX = Window.WITDH / 2;
        double centerY = Window.HEIGHT / 2;


        //double distance = Math.sqrt(Math.pow(xSnake-x,2)+Math.pow(ySnake-y,2));
        double angle = Math.atan2(y - centerY, x - centerX);
        double angleInDegrees = Math.toDegrees(angle);

        double snakeAngle = snake.getOrientation().getAngle();

        
        double angleDifference = normalizeAngle(angleInDegrees - snakeAngle);

        if (angleDifference < -2) {
            snake.setTurning(Turning.GO_LEFT);
        } else if (angleDifference > 2) {
            snake.setTurning(Turning.GO_RIGHT);
        } else {
            snake.setTurning(Turning.FORWARD);
        }
    }

    @Override
    public void mouseMoved(double x,double y) {
        calculateAngle(x, y);
    }

    @Override
    public void mousePressed(double x, double y) {
        snake.setBoosting(true);
        calculateAngle(x, y);
    }

    @Override
    public void mouseReleased(double x, double y) {
        snake.setBoosting(false);
        calculateAngle(x, y);
    }



    


    
}
