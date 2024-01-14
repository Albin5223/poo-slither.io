package model.player;

import GUI.Window;
import interfaces.HumanPlayer;
import interfaces.Orientation.Angle;
import interfaces.Turnable.Turning;
import javafx.scene.input.KeyEvent;
import model.plateau.Snake;

public class HumanMousePlayer implements HumanPlayer {


    private Thread mouseThread = new Thread(){
        public void run(){
            while(!Thread.currentThread().isInterrupted()){
                calculateAngle(isCenter);
                try {
                    Thread.sleep(1000/60);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }   
            }
        }
    };
    
    private volatile int lastX;
    private volatile boolean isCenter;
    private volatile int lastY;

    private Snake<Double,Angle> snake;
    public HumanMousePlayer(Snake<Double, Angle> snake){
        this.snake = snake;
    }

    @Override
    public void keyPressed(KeyEvent ev) {}

    @Override
    public void keyReleased(KeyEvent ev) {}

    public void startMouseThread(){
        mouseThread.start();
    }

    public void stopMouseThread(){
        mouseThread.interrupt();
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

    private void calculateAngle(boolean isCenter){
       
        int centerX = 0;
        int centerY = 0;

        int x = lastX;
        int y = lastY;

        if(isCenter){
            centerX = Window.WITDH/2;
            centerY = Window.HEIGHT/2;

            x += centerX;
            y += centerY;
        }
        else{
            centerX = snake.getHead().getCenter().getX().intValue(); 
            centerY = snake.getHead().getCenter().getY().intValue();
        }   


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
    public void mouseMoved(double x,double y,boolean isCenter) {
        this.isCenter = isCenter;
        lastX = (int)x;
        lastY = (int)y;
    }

    @Override
    public void mousePressed(boolean isCenter) {
        snake.setBoosting(true);
    }

    @Override
    public void mouseReleased(boolean isCenter) {
        snake.setBoosting(false);
    }

    public Snake<Double, Angle> getSnake() {
        return snake;
    }



    


    
}
