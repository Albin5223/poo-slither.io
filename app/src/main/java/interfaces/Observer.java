package interfaces;

public interface Observer {
    public void update(Data<? extends Number,? extends Orientation<?>> data);
}
