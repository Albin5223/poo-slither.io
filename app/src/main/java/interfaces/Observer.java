package interfaces;

public interface Observer<Type extends Number, O extends Orientation<O>> {
    public void update(Data<Type,O> data);
}
