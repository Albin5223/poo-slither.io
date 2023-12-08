package controleur;

import interfaces.Orientation;
import javafx.scene.input.KeyEvent;
import model.plateau.Snake;


//Interface fonctionnelle
public interface KeyboardControler<Type extends Number,O extends Orientation>{
    public void handle(KeyEvent ev,Snake<Type,O> snake);
}
