package GUI.optionView;

import GUI.customButton.ButtonNotClickeablePixelFont;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class CommandMapingPane extends HBox{
    
    private ButtonNotClickeablePixelFont title;
    private TextField selectTouch;
    
    private int textSize = 25;
    
    public CommandMapingPane(String title){
        this.title = new ButtonNotClickeablePixelFont(title,textSize);
        setSpacing(20);

        this.selectTouch = new TextField();
        this.selectTouch.setEditable(false);
        this.selectTouch.setPrefWidth(75);
        

        getChildren().addAll(this.title, this.selectTouch);
    }

    public TextField getSelectTouch(){
        return selectTouch;
    }

    public ButtonNotClickeablePixelFont getTitle(){
        return title;
    }


}