package GUI.optionView;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;

public class CommandMapingPane extends HBox{
    
    private Label title;
    private TextField selectTouch;
    private KeyCode key;
    
    public CommandMapingPane(String title){
        this.title = new Label(title);
        setSpacing(20);

        this.selectTouch = new TextField();
        this.selectTouch.setEditable(false);
        this.selectTouch.setPrefWidth(50);
        selectTouch.onKeyTypedProperty().set(e -> {
            key = e.getCode();
            selectTouch.setText(key.getName());
        });

        getChildren().addAll(this.title, this.selectTouch);
    }


    public KeyCode getKey(){
        return key;
    }
}