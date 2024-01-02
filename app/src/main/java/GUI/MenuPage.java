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
        ButtonPixelFont playOnline = new ButtonPixelFont("ONLINE",50);
        ButtonPixelFont playOffline = new ButtonPixelFont("OFFLINE",50);
        Button exitButton = new ButtonPixelFont("EXIT",40);

        VBox layout = window.getLayout();
        layout.setSpacing(30);
        layout.setAlignment(Pos.CENTER);
        this.setBackground(ImageBank.homePageBackground);

        playOffline.setOnAction(e -> {
            window.switchToOfflinePage();
        });
        
        exitButton.setOnAction(e -> {
            window.getPrimaryStage().close();
        });

        layout.getChildren().add(title);
        layout.getChildren().add(playOnline);
        layout.getChildren().add(playOffline);
        layout.getChildren().add(exitButton);

        VBox.setMargin(playOnline, new Insets(60, 0, 0, 0));
    }

    @Override
    public void sceneKeyConfiguration() {}
    
}
