package GUI;

import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;

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
        window.resetLayout();        
    }

    public void setBackground(Image image){
        window.getLayout().setBackground(new Background(new BackgroundImage(
                image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(Window.WITDH, Window.HEIGHT, false, false, false, true)
        )));
    }

    protected void show(){
        clear();
        createPage();
        sceneKeyConfiguration();
    }
}

