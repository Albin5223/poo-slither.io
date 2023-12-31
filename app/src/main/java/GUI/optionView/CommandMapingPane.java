package GUI.optionView;

import GUI.ButtonPixelFont;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class CommandMapingPane extends HBox{
    
    private ButtonPixelFont title;
    private TextField selectTouch;
    
    
    public CommandMapingPane(String title){
        this.title = new ButtonPixelFont(title,15);
        setSpacing(20);

        this.selectTouch = new TextField();
        this.selectTouch.setEditable(false);
        this.selectTouch.setPrefWidth(75);
        

        getChildren().addAll(this.title, this.selectTouch);
    }

    public TextField getSelectTouch(){
        return selectTouch;
    }

    public ButtonPixelFont getTitle(){
        return title;
    }


}