package GUI;

import GUI.customButton.ButtonNotClickeablePixelFont;
import GUI.customButton.ButtonPixelFont;
import externData.ImageBank;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class ServerJoinPage extends Page {

    private final boolean isSnake;

    private static final int IP_FIELD_MIN_LENGTH = 1;
    private static final int IP_FIELD_MAX_LENGTH = 3;

    private ButtonNotClickeablePixelFont titre;

    private TextField[] fields = {new TextField(), new TextField(), new TextField(), new TextField()};
    private ButtonNotClickeablePixelFont dot1;
    private ButtonNotClickeablePixelFont dot2;
    private ButtonNotClickeablePixelFont dot3;

    private ButtonPixelFont back;
    private ButtonPixelFont join;

    public ServerJoinPage(Window window, boolean isSnake) {
        super(window);
        this.isSnake = isSnake;
    }

    private TextField createField(int index){
        TextField field = new TextField();
        field.setAlignment(Pos.CENTER);
        Font customFont = Font.loadFont(getClass().getResource("/PixelFontYSMAJ.ttf").toExternalForm(), 100);
        field.setFont(customFont);
        field.setStyle("-fx-padding: 0;");  // Set the padding of the TextField
        field.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                window.switchToMenuPage();
                event.consume();  // Consume the event so it doesn't propagate further
            }
        });

        field.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getControlNewText().matches("[0-9]*") && change.getControlNewText().length() <= IP_FIELD_MAX_LENGTH) {  // Only accept numbers
                change.setText(change.getText());
            } 
            else{
                change.setText("");
            }
            return change;
        }));

        field.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() == IP_FIELD_MAX_LENGTH && index < fields.length - 1) {
                // Request focus for the next TextField
                fields[index + 1].requestFocus();
            }
        });

        return field;
    }

    @Override
    public void createPage() {
            
        titre = new ButtonNotClickeablePixelFont("SERVER JOINING", 100);
        dot1 = new ButtonNotClickeablePixelFont(".", 100);
        dot2 = new ButtonNotClickeablePixelFont(".", 100);
        dot3 = new ButtonNotClickeablePixelFont(".", 100);
        back = new ButtonPixelFont("BACK", 70, true);
        join = new ButtonPixelFont("JOIN", 70, true);

        for(int i = 0; i < fields.length; i++){
            fields[i] = createField(i);
        }

        VBox layout = window.getLayout();
        layout.setSpacing(10);
        layout.setAlignment(Pos.CENTER);
        this.setBackground(ImageBank.wallpaper_dark_bridge);

        HBox ipField = new HBox();
        ipField.setAlignment(Pos.CENTER);
        ipField.setSpacing(10);
        ipField.getChildren().addAll(fields[0], dot1, fields[1], dot2, fields[2], dot3, fields[3]);

        VBox.setMargin(ipField, new javafx.geometry.Insets(100, 50, 100, 50));

        HBox backOrJoin = new HBox();
        backOrJoin.setAlignment(Pos.CENTER);
        backOrJoin.setSpacing(10);
        backOrJoin.getChildren().addAll(back, join);

        window.getLayout().getChildren().addAll(titre,ipField,backOrJoin);

        back.setOnAction(e -> {
            window.switchToHostOrJoinPage(isSnake);
        });

        join.setOnAction(e -> {
            for(int i = 0; i < fields.length; i++){ // Check if the IP is valid
                if(fields[i].getText().length() < IP_FIELD_MIN_LENGTH || fields[i].getText().length() > IP_FIELD_MAX_LENGTH){
                    return;
                }
            }
            String ip = "";
            for(int i = 0; i < fields.length; i++){
                ip += fields[i].getText();
                if(i != fields.length - 1){
                    ip += ".";
                }
            }
            window.setIpClient(ip,isSnake);
            window.switchToPseudoSelectorPage(isSnake);
        });

        fields[0].requestFocus();
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
