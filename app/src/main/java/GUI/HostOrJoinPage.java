package GUI;

import GUI.customButton.ButtonNotClickeablePixelFont;
import GUI.customButton.ButtonPixelFont;
import externData.ImageBank;
import javafx.geometry.Pos;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class HostOrJoinPage extends Page {

    private final boolean isSnake;

    private ButtonNotClickeablePixelFont title;

    private ButtonPixelFont host;
    private ButtonPixelFont join;

    private ButtonPixelFont back;

    public HostOrJoinPage(Window window, boolean isSnake) {
        super(window);
        this.isSnake = isSnake;
    }

    @Override
    public void createPage() {
        title = new ButtonNotClickeablePixelFont("SERVER'S CONFIGURATION", 70);
        host = new ButtonPixelFont("HOST", 70, true);
        join = new ButtonPixelFont("JOIN", 70, true);
        back = new ButtonPixelFont("BACK", 50, true);

        host.setOnAction(e -> {
            window.switchToServerHostPage(isSnake);
        });

        join.setOnAction(e -> {
            window.switchToServerJoinPage(isSnake);
        });

        back.setOnAction(e -> {
            window.switchToGameSelector(true);
        });

        VBox layout = window.getLayout();
        layout.setSpacing(10);
        layout.setAlignment(Pos.CENTER);
        this.setBackground(ImageBank.wallpaper_dark_bridge);

        HBox hbox = new HBox(host, join);
        hbox.setAlignment(Pos.CENTER);

        window.getLayout().getChildren().addAll(title,hbox,back);

        HBox.setMargin(join, new javafx.geometry.Insets(0, 0, 0, 50));
        HBox.setMargin(host, new javafx.geometry.Insets(0, 50, 0, 0));

        VBox.setMargin(hbox, new javafx.geometry.Insets(100, 0, 100, 0));
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
