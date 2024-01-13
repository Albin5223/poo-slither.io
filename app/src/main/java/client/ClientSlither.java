package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import interfaces.GameBorder;
import interfaces.Observer;
import interfaces.Orientation.Angle;
import interfaces.Turnable.Turning;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.FoodData;
import model.SnakeData;
import model.coordinate.Coordinate;
import model.paquet.snake.PaquetSnakeCtoS;
import model.paquet.snake.PaquetSnakeStoC;
import model.plateau.PlateauDouble.BorderDouble;
import GUI.PlayPage.PlayPageSlither;

public class ClientSlither implements ClientFactory<Double, Angle> {


    private BorderDouble border;
    private SnakeData<Double, Angle> snakeData;
    private PlayPageSlither playPageSlither;
    

    private ArrayList<SnakeData<Double, Angle>> snakesToDraw;
    private ArrayList<FoodData<Double, Angle>> foodsToDraw;

    private Turning turning = Turning.FORWARD;
    private boolean boosting;

    private KeyCode LEFT =  KeyCode.LEFT;
    private KeyCode RIGHT = KeyCode.RIGHT;
    private KeyCode BOOST = KeyCode.SPACE;


    public ClientSlither(){
        this.playPageSlither = new PlayPageSlither();
    }

    public PlayPageSlither getPlayPageSlither() {
        return playPageSlither;
    }

    @Override
    public void writeObject(ObjectOutputStream oos) throws IOException {
        oos.writeObject(new PaquetSnakeCtoS(turning,boosting,false));
    }

    @Override
    @SuppressWarnings("unchecked")
    public void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException {
        PaquetSnakeStoC<Double,Angle> message = (PaquetSnakeStoC<Double,Angle>) ois.readObject();
        snakesToDraw = message.getAllSnake();
        foodsToDraw = message.getAllFood();
        snakeData = (SnakeData<Double,Angle>) message.getSnakeData();
    }

    @Override
    public GameBorder<Double, Angle> getBorder() {
        return border;
    }

    @Override
    public Turning getTurning() {
        return turning;
    }

    @Override
    public void setTurning(Turning turning) {
        this.turning = turning;
    }

    @Override
    public void setBoosting(boolean boosting) {
        this.boosting = boosting;
    }

    @Override
    public void setBorder(GameBorder<Double, Angle> border) {
        this.border = (BorderDouble) border;
    }

    @Override
    public Observer<Double, Angle> getPlayPage() {
        return playPageSlither;
    }

    @Override
    public ArrayList<SnakeData<Double, Angle>> getAllSnake() {
        return snakesToDraw;
    }

    @Override
    public List<FoodData<Double, Angle>> getAllFood() {
        return foodsToDraw;
    }

    @Override
    public Coordinate<Double, Angle> getMainSnakeCenter() {
        return snakeData.getHead();
    }

    @Override
    public void setKeyCode(KeyEvent ev) {
        if(ev.getCode() == this.LEFT){
            turning = Turning.GO_LEFT;
        }
        if(ev.getCode() == this.RIGHT){
            turning = Turning.GO_RIGHT;
        }
        if(ev.getCode() == this.BOOST){
            boosting = true;
        }
    }

    @Override
    public void setReleasedKeyCode(KeyEvent ev) {
        if(ev.getCode() == this.LEFT || ev.getCode() == this.RIGHT){
            turning = Turning.FORWARD;
        }
        if(ev.getCode() == this.BOOST){
            boosting = false;
        }
    }
    
}
