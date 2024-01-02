package GUI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;

public abstract class Page {

    protected Window window;
    
    public Page(Window window){
        this.window = window;
    }

    /** This method needs to create the page */
    public abstract void createPage();

    /** This method needs to set the keyListener for the scene if necessary */
    public abstract void sceneKeyConfiguration();

    /** This method clears the layout of the window */
    public void clear(){
        window.getLayout().getChildren().clear();
        window.getLayout().setSpacing(0);
        window.getLayout().setPadding(new Insets(0));
        window.getLayout().setAlignment(Pos.CENTER);
        window.getLayout().setBorder(null);
        
    }

    protected void show(){
        clear();
        createPage();
        sceneKeyConfiguration();
    }
}

