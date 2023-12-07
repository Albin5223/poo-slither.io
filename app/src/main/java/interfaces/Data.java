package interfaces;

import java.util.HashMap;

import model.Commestible;

public interface Data<Type extends Number, O extends Orientation> {
    public Coordinate<Type, O>[] getAllPosition();
    public void move(); 
    public double getRadius();
    public HashMap<Coordinate<Type, O>, Commestible> getAllFood();
}
