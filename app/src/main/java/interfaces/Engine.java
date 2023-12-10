package interfaces;

import javafx.scene.input.KeyEvent;

public interface Engine<Type extends Number, O extends Orientation<O>> extends Observable, Data<Type,O>, Court {

    public default void update() {
        move();
    } 


    public void makePressed(KeyEvent ev);

    public void makeReleased(KeyEvent ev);


}
