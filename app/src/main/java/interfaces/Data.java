package interfaces;

import java.util.ArrayList;
import model.CoordinateInteger;

public interface Data {
    public ArrayList<CoordinateInteger> getAllPosition();
    public void move();
}
