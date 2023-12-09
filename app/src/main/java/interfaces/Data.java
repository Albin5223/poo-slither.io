package interfaces;

import java.util.HashMap;
import java.util.ArrayList;
import model.Commestible;
import model.SnakeData;

public interface Data<Type extends Number, O extends Orientation> {
    public ArrayList<SnakeData<Type, O>> getAllSnake();
    public void move(); 
    public double getRadius();
    public HashMap<Coordinate<Type, O>, Commestible> getAllFood();
}
