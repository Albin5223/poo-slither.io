package GUI;

import javafx.scene.Cursor;

public class ButtonNotClickeablePixelFont extends ButtonPixelFont{

    public ButtonNotClickeablePixelFont(String text, int size) {
        super(text, size);
        this.setOnMouseEntered(e -> this.setCursor(Cursor.DEFAULT));  // Change to DEFAULT cursor
    }
    
}
