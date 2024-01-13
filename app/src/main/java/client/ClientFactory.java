package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import interfaces.GameBorder;
import interfaces.Observer;
import interfaces.Turnable;
import javafx.scene.input.KeyEvent;
import model.FoodData;
import model.SnakeData;
import model.coordinate.Coordinate;

public interface ClientFactory<Type extends Number & Comparable<Type>, O extends interfaces.Orientation<O>> {


    public void writeObject(ObjectOutputStream oos) throws IOException;
    public void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException;
    public GameBorder<Type,O> getBorder();
    public Turnable.Turning getTurning();
    public void setTurning(Turnable.Turning turning);
    public void setBoosting(boolean boosting);
    public void setBorder(GameBorder<Type,O> border);
    public Observer<Type,O> getPlayPage();
    public ArrayList<SnakeData<Type, O>> getAllSnake();
    public List<FoodData<Type, O>> getAllFood();
    public Coordinate<Type, O> getMainSnakeCenter();
    public void setKeyCode(KeyEvent ev);
    public void setReleasedKeyCode(KeyEvent ev);


    
}
