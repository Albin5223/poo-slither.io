package GUI.optionView;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class CommandMapingPane extends HBox{
    
    private Label title;
    private TextField selectTouch;
    
    
    public CommandMapingPane(String title){
        this.title = new Label(title);
        setSpacing(20);

        this.selectTouch = new TextField();
        this.selectTouch.setEditable(false);
        this.selectTouch.setPrefWidth(75);
        

        getChildren().addAll(this.title, this.selectTouch);
    }

    public TextField getSelectTouch(){
        return selectTouch;
    }

    public Label getTitle(){
        return title;
    }


}