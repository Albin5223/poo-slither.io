package interfaces;

import model.Player;
import model.Snake;

public interface Engine<Type extends Number, O extends Orientation> extends Observable, Data<Type,O>, Court {

    public default void update() {
        move();
    }
    public Player[] getPlayers();
    public Snake<Type,O>[] getSnakes();
}
