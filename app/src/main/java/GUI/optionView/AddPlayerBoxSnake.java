package GUI.optionView;

import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;

public class AddPlayerBoxSnake extends VBox {

    CheckBox present;
    boolean isPresent;

    private static int numero = 0;
    private int id;

    /*
     * 0 : up
     * 1 : down
     * 2 : left
     * 3 : right
     * 4 : boost
     */

    Label title;
    CommandMapingPane droite;
    CommandMapingPane gauche;
    CommandMapingPane haut;
    CommandMapingPane bas;
    CommandMapingPane boost;


    
    public AddPlayerBoxSnake(){
        id = numero;
        numero++;
        present = new CheckBox("Ajouter un joueur");
        present.setOnAction(e -> {
            isPresent = present.isSelected();
            if(isPresent){
                addPlayer();
            }else{
                removePlayer();
            }
        });
        title = new Label("Personnalisation des touches  : ");
        droite = new CommandMapingPane("Droite : ");
        gauche = new CommandMapingPane("Gauche : ");
        haut = new CommandMapingPane("Haut : ");
        bas = new CommandMapingPane("Bas : ");
        boost = new CommandMapingPane("Boost : ");

        KeyCode[] touches = new KeyCode[5];
        touches[0] = haut.getKey();
        touches[1] = bas.getKey();
        touches[2] = gauche.getKey();
        touches[3] = droite.getKey();
        touches[4] = boost.getKey();
        getChildren().addAll(present);
        removePlayer();
        getChildren().addAll(title,droite,gauche,haut,bas,boost);



    }

    private void removePlayer(){
        isPresent = false;
        title.setVisible(false);
        droite.setVisible(false);
        gauche.setVisible(false);
        haut.setVisible(false);
        bas.setVisible(false);
        boost.setVisible(false);   
    }

    private void addPlayer(){
        isPresent = false;
        title.setVisible(true);
        droite.setVisible(true);
        gauche.setVisible(true);
        haut.setVisible(true);
        bas.setVisible(true);
        boost.setVisible(true);
    }



    
}
