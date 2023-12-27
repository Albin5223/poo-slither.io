package interfaces;

public interface Observable<Type extends Number & Comparable<Type>, O extends Orientation<O>> {
    public void addObserver(Observer<Type,O> o);
    public void removeObserver(Observer<Type,O> o);
    public void notifyObservers();
}
