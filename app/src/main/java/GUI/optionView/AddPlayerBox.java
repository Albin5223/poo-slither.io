package GUI.optionView;


import configuration.TouchControler;
import configuration.TouchControler.DirectionOfTouch;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class AddPlayerBox extends VBox {

    CheckBox present;
    boolean isPresent;

    private static int numero = 0;
    private int id;

    Label title;
    CommandMapingPane droite;
    CommandMapingPane gauche;
    CommandMapingPane haut;
    CommandMapingPane bas;
    CommandMapingPane boost;

    private boolean isSnake;
    private TouchControler touchControler;


    
    public AddPlayerBox(Boolean isSnake){
        id = numero;
        this.isSnake = isSnake;
        touchControler = new TouchControler();
        numero++;
        present = new CheckBox("Ajouter un joueur");
        present.setOnAction(e -> {
            isPresent = present.isSelected();
            if(isPresent){
                SetOfConfiguration.incrementNumberOfHuman();
                addPlayer();
            }else{
                SetOfConfiguration.decrementNumberOfHuman();
                removePlayer();
            }
        });
        title = new Label("Personnalisation des touches  : ");
        droite = new CommandMapingPane("Droite : ");
        droite.getSelectTouch().setOnKeyPressed(e -> {
            isPresent = present.isSelected();
            if(isPresent){
                touchControler.updateTouchControl(DirectionOfTouch.RIGHT, e.getCode());
                droite.getSelectTouch().setText(e.getCode().toString());
            }
        });
        gauche = new CommandMapingPane("Gauche : ");
        gauche.getSelectTouch().setOnKeyPressed(e -> {
            if(isPresent){
                touchControler.updateTouchControl(DirectionOfTouch.LEFT, e.getCode());
                gauche.getSelectTouch().setText(e.getCode().toString());
            }
        });
        if(isSnake){
            haut = new CommandMapingPane("Haut : ");
            haut.getSelectTouch().setOnKeyPressed(e -> {
                if(isPresent){
                    touchControler.updateTouchControl(DirectionOfTouch.UP, e.getCode());
                    haut.getSelectTouch().setText(e.getCode().toString());
                }
            });

            bas = new CommandMapingPane("Bas : ");
            bas.getSelectTouch().setOnKeyPressed(e -> {
                if(isPresent){
                    touchControler.updateTouchControl(DirectionOfTouch.DOWN, e.getCode());
                    bas.getSelectTouch().setText(e.getCode().toString());
                }
            });
        }
        
        boost = new CommandMapingPane("Boost : ");
        boost.getSelectTouch().setOnKeyPressed(e -> {
            if(isPresent){
                touchControler.updateTouchControl(DirectionOfTouch.BOOST, e.getCode());
                boost.getSelectTouch().setText(e.getCode().toString());
            }
        });

        getChildren().addAll(present);
        removePlayer();
        getChildren().addAll(title,droite,gauche,boost);
        if(isSnake){
            getChildren().addAll(haut,bas);
        }



    }

    public int getIdentify(){
        return id;
    }

    


    private void removePlayer(){
        isPresent = false;
        SetOfConfiguration.removeCommand(touchControler);
        title.setVisible(false);
        droite.setVisible(false);
        gauche.setVisible(false);
        boost.setVisible(false); 
        
        if(isSnake){
            haut.setVisible(false);
            bas.setVisible(false);
        }
    }

    private void addPlayer(){
        isPresent = false;
        SetOfConfiguration.addCommand(touchControler);
        title.setVisible(true);
        droite.setVisible(true);
        gauche.setVisible(true);
        boost.setVisible(true);
        if(isSnake){
            haut.setVisible(true);
            bas.setVisible(true);
        }
    }
    
}
