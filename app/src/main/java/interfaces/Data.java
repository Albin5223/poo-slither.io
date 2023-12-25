package interfaces;

import java.util.HashMap;
import java.util.ArrayList;
import model.Commestible;
import model.SnakeData;

public interface Data<Type extends Number, O extends Orientation<O>> {
    public ArrayList<SnakeData<Type, O>> getAllSnake();
    public double getRadius();
    public HashMap<Coordinate<Type, O>, Commestible> getAllFood();
    public GameBorder<Type,O> getGameBorder();
}
