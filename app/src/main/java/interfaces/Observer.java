package interfaces;

public interface Observer<Type extends Number & Comparable<Type>, O extends Orientation<O>> {
    public void update(Data<Type,O> data);
}
