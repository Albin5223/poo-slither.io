package GUI;

import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.paint.Paint;

public class ButtonPixelFont extends Button {

    protected TextPixelFont textPixelFont;

    public ButtonPixelFont(String text, int size) {
        this.textPixelFont = new TextPixelFont(text, size);
        this.setGraphic(textPixelFont);

        // Set the style of the button
        this.setStyle("-fx-padding: 0; -fx-background-color: transparent;");

        // Change the cursor when it hovers over the button
        this.setOnMouseEntered(e -> this.setCursor(Cursor.HAND));  // Change to hand cursor
        this.setOnMouseExited(e -> this.setCursor(Cursor.DEFAULT));  // Change back to default cursor

        // Set the size of the button to be the same as the size of the text
        this.setPrefWidth(textPixelFont.getLayoutBounds().getWidth());
        this.setPrefHeight(textPixelFont.getLayoutBounds().getHeight());
    }

    public void setFill(Paint value) {
        textPixelFont.setFill(value);
    }

    public void setStroke(Paint value) {
        textPixelFont.setStroke(value);
    }

    public void setSize(int size) {
        this.textPixelFont = new TextPixelFont(textPixelFont.getText(), size);
        this.setGraphic(textPixelFont);
    }
    
}
