package interfaces;

public interface GameBorder<Type extends Number, O extends Orientation<O>> {
    
    public boolean isInside(Coordinate<Type,O> c);
    public Coordinate<Type,O> getOpposite(Coordinate<Type,O> c);
    public Coordinate<Type,O> getRandomCoordinate();

}
