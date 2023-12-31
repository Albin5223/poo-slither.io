package GUI.optionView;

import javafx.scene.layout.VBox;

public class OptionConfigPane extends VBox {


    public OptionConfigPane() {
    
        SettingToggle toggle = new SettingToggle("Mur franchissable");
        this.getChildren().add(toggle);
    }
}
