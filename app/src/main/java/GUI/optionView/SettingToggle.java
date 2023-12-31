package GUI.optionView;

import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;

public class SettingToggle extends HBox{



    private Label title;
    private ToggleButton toggleButton;

    public SettingToggle(String title){
        this.title = new Label(title);

        toggleButton = new ToggleButton("OFF");
        toggleButton.setStyle("-fx-base: #ff0000;");
        toggleButton.setOnAction(event -> {
            if (toggleButton.isSelected()) {
                toggleButton.setText("ON");
                toggleButton.setStyle("-fx-base: #00ff00;");
                
            
            } else {
                toggleButton.setText("OFF");
                toggleButton.setStyle("-fx-base: #ff0000;");
            }   
        });


        
        setSpacing(30);
        getChildren().addAll(this.title,this.toggleButton);
        setAlignment(javafx.geometry.Pos.CENTER);
    }

    public boolean isActivated(){
        return toggleButton.isSelected();
    }

    
}