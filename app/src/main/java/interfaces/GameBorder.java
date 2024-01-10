package interfaces;

import model.coordinate.Coordinate;
import java.io.Serializable;

public interface GameBorder<Type extends Number, O extends Orientation<O>> extends Serializable {
    
    public boolean isInside(Coordinate<Type,O> c);
    public Coordinate<Type,O> getOpposite(Coordinate<Type,O> c);
    public Coordinate<Type,O> getRandomCoordinate();

}
