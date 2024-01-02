package GUI.optionView;


import GUI.customButton.ButtonGrisablePixelFont;
import GUI.customButton.ButtonPixelFont;
import configuration.TouchControler;
import configuration.TouchControler.DirectionOfTouch;
import javafx.scene.layout.VBox;

public class AddPlayerBox extends VBox {

    ButtonGrisablePixelFont present;

    private static int numero = 0;
    private int id;

    ButtonPixelFont title;
    CommandMapingPane droite;
    CommandMapingPane gauche;
    CommandMapingPane haut;
    CommandMapingPane bas;
    CommandMapingPane boost;

    private boolean isSnake;
    private TouchControler touchControler;

    private int textSize = 25;

    
    public AddPlayerBox(Boolean isSnake){
        id = numero;
        this.isSnake = isSnake;
        this.setSpacing(5);
        touchControler = new TouchControler();
        numero++;
        present = new ButtonGrisablePixelFont("ADD PLAYER",textSize,true);
        
        present.setOnAction(e -> {
            
            if(!present.switchGrise()){
                SetOfConfiguration.incrementNumberOfHuman();
                addPlayer();
            }else{
                SetOfConfiguration.decrementNumberOfHuman();
                removePlayer();
            }
        });

        title = new ButtonPixelFont("CUSTOM COMMAND PLAYER ",15);
        droite = new CommandMapingPane("RIGHT : ");
        droite.getSelectTouch().setOnKeyPressed(e -> {
            
            touchControler.updateTouchControl(DirectionOfTouch.RIGHT, e.getCode());
            droite.getSelectTouch().setText(e.getCode().toString());
            
        });
        gauche = new CommandMapingPane("LEFT : ");
        gauche.getSelectTouch().setOnKeyPressed(e -> {
            
            touchControler.updateTouchControl(DirectionOfTouch.LEFT, e.getCode());
            gauche.getSelectTouch().setText(e.getCode().toString());
            
        });
        if(isSnake){
            haut = new CommandMapingPane("UP : ");
            haut.getSelectTouch().setOnKeyPressed(e -> {
                
                touchControler.updateTouchControl(DirectionOfTouch.UP, e.getCode());
                haut.getSelectTouch().setText(e.getCode().toString());
                
            });

            bas = new CommandMapingPane("DOWN : ");
            bas.getSelectTouch().setOnKeyPressed(e -> {
               
                touchControler.updateTouchControl(DirectionOfTouch.DOWN, e.getCode());
                bas.getSelectTouch().setText(e.getCode().toString());
            
            });
        }
        
        boost = new CommandMapingPane("BOOST : ");
        boost.getSelectTouch().setOnKeyPressed(e -> {
            
            touchControler.updateTouchControl(DirectionOfTouch.BOOST, e.getCode());
            boost.getSelectTouch().setText(e.getCode().toString());
            
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
