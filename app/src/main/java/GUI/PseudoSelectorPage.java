package GUI;

import GUI.customButton.ButtonNotClickeablePixelFont;
import GUI.customButton.ButtonPixelFont;
import externData.ImageBank;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class PseudoSelectorPage extends Page {

    private static int PSEUDO_MIN_LENGTH = 1;
    private static int PSEUDO_MAX_LENGTH = 15;

    private ButtonNotClickeablePixelFont instructionLabel;
    private TextField pseudoField;
    private ButtonPixelFont submitButton;

    public PseudoSelectorPage(Window window, boolean isSnake) {
        super(window);
    }

    @Override
    public void createPage() {
        instructionLabel = new ButtonNotClickeablePixelFont("ENTER YOUR PSEUDO :",100);

        pseudoField = new TextField();
        pseudoField.setAlignment(Pos.CENTER);
        Font customFont = Font.loadFont(getClass().getResource("/PixelFontYSMAJ.ttf").toExternalForm(), 100);
        pseudoField.setFont(customFont);
        pseudoField.setStyle("-fx-padding: 0;");  // Set the padding of the TextField

        // Add an event filter to the TextField : if the user presses ESCAPE, switch back to the menu page
        pseudoField.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                window.switchToMenuPage();
                event.consume();  // Consume the event so it doesn't propagate further
            }
        });
        
        // Add a text formatter to transform input to uppercase
        pseudoField.setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getText().toUpperCase();
            if (newText.matches("[A-Z0-9-'_]*")) {  // Only accept uppercase letters, numbers, -, ', and _
                change.setText(newText);
            } else {
                change.setText("");
            }
            return change;
        }));
        
        submitButton = new ButtonPixelFont("SUBMIT", 60, true);
        submitButton.setOnAction(e ->{
            window.getClient().pseudo = pseudoField.getText();
            if(window.getClient().pseudo.length() >= PSEUDO_MIN_LENGTH && window.getClient().pseudo.length() <= PSEUDO_MAX_LENGTH){
                window.switchToMenuPage();
            }
        });
        
        VBox layout = window.getLayout();
        layout.setSpacing(10);
        layout.setAlignment(Pos.CENTER);
        this.setBackground(ImageBank.wallpaper_dark_bridge);
        layout.getChildren().addAll(instructionLabel, pseudoField, submitButton);  // Add the TextField directly to the layout
        
        VBox.setMargin(pseudoField, new javafx.geometry.Insets(20, 50, 20, 50));  // Set the margin for the TextField
    }

    @Override
    public void sceneKeyConfiguration() {
        window.getScene().setOnKeyPressed( ev -> {
            if(ev.getCode() == KeyCode.ESCAPE){
                window.switchToMenuPage();
            }
        });
    }
    
}
