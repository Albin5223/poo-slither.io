package interfaces;

import java.util.List;
import java.util.ArrayList;

import model.FoodData;
import model.SnakeData;
import model.coordinate.Coordinate;

public interface Data<Type extends Number & Comparable<Type>, O extends Orientation<O>> {
    public ArrayList<SnakeData<Type, O>> getAllSnake();
    public List<FoodData<Type,O>> getAllFood(Coordinate<Type,O> coordinate, double radius);
    public GameBorder<Type,O> getGameBorder();
    public Coordinate<Type,O> getMainSnakeCenter();
}
