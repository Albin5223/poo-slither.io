package GUI.optionView;

import configuration.TouchControler;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class PlayerChoosePane extends VBox {


    

    private HBox botPlayerSpinnerBox;
    private HBox botPlayerSpinner;

    private HBox humanPlayerSpinnerBox;


    
    public PlayerChoosePane(boolean isSnake) {
        botPlayerSpinnerBox = new HBox();
        humanPlayerSpinnerBox = new HBox();
        Label titleBot = new Label ("Nombre de joueur robot : ");
        Label ViewNumber = new Label("0");
        botPlayerSpinner = new HBox();
        Label plus = new Label("+");
        Label minus = new Label("-");
        plus.setOnMouseClicked(event -> {
            SetOfConfiguration.incrementNumberOfBot();
            ViewNumber.setText(String.valueOf(SetOfConfiguration.getNumberOfBot()));
        });

        minus.setOnMouseClicked(event -> {
            SetOfConfiguration.decrementNumberOfBot();
            ViewNumber.setText(String.valueOf(SetOfConfiguration.getNumberOfBot()));
        });

        botPlayerSpinner.setSpacing(10);
        botPlayerSpinnerBox.setSpacing(50);
        botPlayerSpinner.getChildren().addAll(minus,ViewNumber,plus);
        botPlayerSpinnerBox.getChildren().addAll(titleBot,botPlayerSpinner);

        
        humanPlayerSpinnerBox.setSpacing(50);
        AddPlayerBox addPlayerBoxSnakePlayer1 = new AddPlayerBox(isSnake);
        AddPlayerBox addPlayerBoxSnakePlayer2 = new AddPlayerBox(isSnake);
        humanPlayerSpinnerBox.getChildren().addAll(addPlayerBoxSnakePlayer1,addPlayerBoxSnakePlayer2);
        
        
        setPadding(new Insets(10)); // Marge de 10 pixels à tous les côtés du Pane
        getChildren().addAll(botPlayerSpinnerBox);
        getChildren().addAll(humanPlayerSpinnerBox);
        setAlignment(javafx.geometry.Pos.CENTER);

    }
    
}
