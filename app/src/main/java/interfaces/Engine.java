package interfaces;

import model.Snake;

public interface Engine<Type extends Number, O extends Orientation> extends Observable, Data<Type,O>, Court {

    public default void update() {
        move();
    }
    
    public Snake<Type,O>[] getSnakes();
}
