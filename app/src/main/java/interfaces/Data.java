package interfaces;

import java.util.ArrayList;
import model.CoordinateDouble;

public interface Data {
    public ArrayList<CoordinateDouble> getAllPosition();
    public void move();
}
