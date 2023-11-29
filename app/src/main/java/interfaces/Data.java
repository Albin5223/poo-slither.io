package interfaces;

import java.util.ArrayList;
import java.util.HashMap;

import interfaces.Orientation.Angle;
import model.Commestible;
import model.CoordinateDouble;

public interface Data {
    public ArrayList<CoordinateDouble> getAllPosition();
    public void move();
    public double getRadius();
    public HashMap<Coordinate<Double,Angle>, Commestible> getAllFood();
}
