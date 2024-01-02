package GUI.customButton;

import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class TextPixelFont extends Text {

    public static final double STROKE_RATIO = 0.045;
    public static final double SHADOW_OFFSET_RATIO = 0.05;

    public TextPixelFont(String text, int size) {
        super(text);
        this.setStroke(Color.BLACK);
        this.setStrokeWidth(size*STROKE_RATIO);
        this.setFill(Color.WHITE);
        Font customFont = Font.loadFont(getClass().getResource("/PixelFontYSMAJ.ttf").toExternalForm(), size);
        this.setFont(customFont);

        // Add drop shadow effect
        DropShadow ds = new DropShadow();
        ds.setOffsetY(size * SHADOW_OFFSET_RATIO);
        ds.setOffsetX(size * SHADOW_OFFSET_RATIO);
        ds.setColor(Color.color(0, 0, 0, 1));  // Set the opacity to 100%
        ds.setRadius(0);  // Reduce the radius to make the shadow less blurry
        this.setEffect(ds);
    }
    
}
