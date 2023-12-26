package interfaces;

import java.util.HashMap;
import java.util.ArrayList;
import model.SnakeData;
import model.coordinate.Coordinate;
import model.foods.FoodHolder;

public interface Data<Type extends Number, O extends Orientation<O>> {
    public ArrayList<SnakeData<Type, O>> getAllSnake();
    public HashMap<Coordinate<Type, O>, FoodHolder<Type>> getAllFood();
    public GameBorder<Type,O> getGameBorder();
}
