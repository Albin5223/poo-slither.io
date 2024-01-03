package interfaces;

import javafx.scene.input.KeyEvent;
import model.plateau.Plateau;

public interface Engine<Type extends Number & Comparable<Type>, O extends Orientation<O>> extends Observable<Type,O>, Data<Type,O> {

    public void run();

    public void stop();

    public void makePressed(KeyEvent ev, HumanPlayer player);

    public void makeReleased(KeyEvent ev, HumanPlayer player);

    public Plateau<Type,O> getPlateau();


}
