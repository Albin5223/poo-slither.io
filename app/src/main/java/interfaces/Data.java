package interfaces;

import java.util.HashMap;
import java.util.ArrayList;
import model.SnakeData;
import model.coordinate.Coordinate;
import model.foods.Food;

public interface Data<Type extends Number & Comparable<Type>, O extends Orientation<O>> {
    public ArrayList<SnakeData<Type, O>> getAllSnake();
    public HashMap<Coordinate<Type, O>, Food<Type,O>> getAllFood();
    public GameBorder<Type,O> getGameBorder();
}
