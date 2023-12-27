package controleur;

import interfaces.Orientation;
import javafx.scene.input.KeyEvent;
import model.plateau.Snake;


//Interface fonctionnelle
public interface KeyboardControler<Type extends Number & Comparable<Type>,O extends Orientation<O>>{
    public void keyPressed(KeyEvent ev,Snake<Type,O> snake);
    public void keyReleased(KeyEvent ev,Snake<Type,O> snake);
}
