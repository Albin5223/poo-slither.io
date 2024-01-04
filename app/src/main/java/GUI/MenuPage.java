package GUI;

import GUI.customButton.ButtonNotClickeablePixelFont;
import GUI.customButton.ButtonPixelFont;
import GUI.optionView.SetOfConfiguration;
import configuration.TouchControler;
import externData.ImageBank;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class MenuPage extends Page {

    public MenuPage(Window window) {
        super(window);
    }

    @Override
    public void createPage() {

        TouchControler.resetNumber();
        SetOfConfiguration.resetConfiguration();

        ButtonNotClickeablePixelFont title = new ButtonNotClickeablePixelFont("THE YAZICINHO'S GAME", 70);
        title.setFill(Color.GRAY);
        ButtonPixelFont playOnline = new ButtonPixelFont("ONLINE",50, true);
        ButtonPixelFont playOffline = new ButtonPixelFont("OFFLINE",50, true);
        Button exitButton = new ButtonPixelFont("EXIT",40, true);

        VBox layout = window.getLayout();
        layout.setSpacing(30);
        layout.setAlignment(Pos.CENTER);
        this.setBackground(ImageBank.homePageBackground);

        playOnline.setOnAction(e -> {
            window.switchToGameSelector(true);
        });

        playOffline.setOnAction(e -> {
            window.switchToGameSelector(false);
        });
        
        exitButton.setOnAction(e -> {
            window.getServer().shutdown();
            // TODO : don't forget to shutdown the client
            //window.getClient().shutdown();
            window.getPrimaryStage().close();
        });

        layout.getChildren().addAll(title, playOnline, playOffline, exitButton);

        VBox.setMargin(playOnline, new Insets(60, 0, 0, 0));
    }

    @Override
    public void sceneKeyConfiguration() {}
    
}
