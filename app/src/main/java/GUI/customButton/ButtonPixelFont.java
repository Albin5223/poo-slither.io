package GUI.customButton;

import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class ButtonPixelFont extends Button {

    protected TextPixelFont textPixelFont;

    public ButtonPixelFont(String text, int size, boolean border) {
        this.textPixelFont = new TextPixelFont(text, size);
        this.setGraphic(textPixelFont);

        // Set the style of the button
        if(border){
            this.setStyle("-fx-padding: 3 13 2 15; -fx-border-color: black; -fx-border-style: solid; -fx-border-width: 3;-fx-focus-color: transparent; -fx-faint-focus-color: transparent; -fx-background-insets: 0;");
        }
        else{
            this.setStyle("-fx-padding: 0; -fx-background-color: transparent;");
        }

        // Add drop shadow effect
        DropShadow ds = new DropShadow();
        ds.setOffsetY(size * TextPixelFont.SHADOW_OFFSET_RATIO);
        ds.setOffsetX(size * TextPixelFont.SHADOW_OFFSET_RATIO);
        ds.setColor(Color.color(0, 0, 0, 1));  // Set the opacity to 100%
        ds.setRadius(0);  // Reduce the radius to make the shadow less blurry
        this.setEffect(ds);

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

    public void setButtonText(String text) {
        this.textPixelFont.setText(text);
    }
    
}
