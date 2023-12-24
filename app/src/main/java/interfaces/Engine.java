package interfaces;

import javafx.scene.input.KeyEvent;

public interface Engine<Type extends Number, O extends Orientation<O>> extends Observable<Type,O>, Data<Type,O>, Court {

    public default void update() {
        move();
    }

    public void makePressed(KeyEvent ev, HumanPlayer player);

    public void makeReleased(KeyEvent ev, HumanPlayer player);


}
