package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import GUI.PlayPage.PlayPageSnake;
import interfaces.GameBorder;
import interfaces.Turnable;
import interfaces.Orientation.Direction;
import interfaces.Turnable.Turning;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.FoodData;
import model.SnakeData;
import model.coordinate.Coordinate;
import model.paquet.snake.PaquetSnakeCtoS;
import model.paquet.snake.PaquetSnakeStoC;
import model.plateau.PlateauInteger.BorderInteger;

public class ClientSnake implements ClientFactory<Integer,Direction> {

    private BorderInteger border;
    private SnakeData<Integer, Direction> snakeData;
    private PlayPageSnake playPageSnake;
    

    private ArrayList<SnakeData<Integer, Direction>> snakesToDraw;
    private ArrayList<FoodData<Integer, Direction>> foodsToDraw;

    private Turning turning = Turning.FORWARD;
    private boolean boosting;

    private KeyCode UP = KeyCode.UP;
    private KeyCode DOWN = KeyCode.DOWN;
    private KeyCode LEFT =  KeyCode.LEFT;
    private KeyCode RIGHT = KeyCode.RIGHT;
    private KeyCode BOOST = KeyCode.SPACE;

    public ClientSnake(){
        this.playPageSnake = new PlayPageSnake();
    }


    @Override
    public void setBorder(GameBorder<Integer,Direction> border) {
        this.border = (BorderInteger) border;
    }

    @Override
    public GameBorder<Integer,Direction> getBorder() {
        return border;
    }

    @Override
    public Turnable.Turning getTurning(){
        return turning; 
    }

    @Override
    public void setTurning(Turnable.Turning turning){
        this.turning = turning;
    }
    @Override
    public void setBoosting(boolean boosting){
        this.boosting = boosting;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException{
        
        PaquetSnakeStoC<Integer,Direction> message = (PaquetSnakeStoC<Integer,Direction>) ois.readObject();
        snakesToDraw = message.getAllSnake();
        foodsToDraw = message.getAllFood();
        snakeData = (SnakeData<Integer,Direction>) message.getSnakeData();
    }


    @Override
    public void writeObject(ObjectOutputStream oos) throws IOException{
       
        oos.writeObject(new PaquetSnakeCtoS(turning, boosting, false));
        
    }
   

    @Override
    public void setKeyCode(KeyEvent ev){
        if (ev.getCode() == this.LEFT) {
            if (snakeData.getOrientation() != Direction.RIGHT && snakeData.getOrientation() != Direction.LEFT) {
                if (snakeData.getOrientation() == Direction.UP) {
                    turning = Turning.GO_LEFT;
                } else {
                    turning = Turning.GO_RIGHT;
                }
            }
        } else if (ev.getCode() == this.RIGHT) {
            if (snakeData.getOrientation() != Direction.RIGHT && snakeData.getOrientation() != Direction.LEFT) {
                if (snakeData.getOrientation() == Direction.UP) {
                    turning = Turning.GO_RIGHT;
                } else {
                    turning = Turning.GO_LEFT;
                }
            }
        } else if (ev.getCode() == this.UP) {
            if (snakeData.getOrientation() != Direction.UP && snakeData.getOrientation() != Direction.DOWN) {
                if (snakeData.getOrientation() == Direction.LEFT) {
                    turning = Turning.GO_RIGHT;
                } else {
                    turning = Turning.GO_LEFT;
                }
            }
        } else if (ev.getCode() == this.DOWN) {
            if (snakeData.getOrientation() != Direction.UP && snakeData.getOrientation() != Direction.DOWN) {
                if (snakeData.getOrientation() == Direction.LEFT) {
                    turning = Turning.GO_LEFT;
                } else {
                    turning = Turning.GO_RIGHT;
                }
            }
        }
        else if(ev.getCode() == this.BOOST){
            boosting = true;
        }
    }

    @Override
    public void setReleasedKeyCode(KeyEvent ev){
        if (ev.getCode() == this.LEFT || ev.getCode() == this.RIGHT) {
            turning = Turning.FORWARD;
        } else if (ev.getCode() == this.BOOST) {
            boosting = false;
        }
    }

    @Override
    public PlayPageSnake getPlayPage() {
        return playPageSnake;
    }

    @Override
    public ArrayList<SnakeData<Integer,Direction>> getAllSnake(){
        return snakesToDraw;
    }

    @Override
    public List<FoodData<Integer,Direction>> getAllFood(Coordinate<Integer, Direction> coordinate, double radius){
        return foodsToDraw;
    }

    public Coordinate<Integer, Direction> getMainSnakeCenter(){
        return snakeData.getHead();
    }
    
}
