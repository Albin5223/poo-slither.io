package interfaces;

import java.util.List;
import java.util.ArrayList;
import model.SnakeData;
import model.coordinate.Coordinate;
import model.foods.Food;
import model.plateau.Snake;

public interface Data<Type extends Number & Comparable<Type>, O extends Orientation<O>> {
    public ArrayList<SnakeData<Type, O>> getAllSnake();
    public List<Food<Type,O>> getAllFood(Coordinate<Type,O> coordinate, double radius);
    public GameBorder<Type,O> getGameBorder();
    public Snake<Type, O> getMainSnake();
}
