package GUI;

import GUI.customButton.ButtonNotClickeablePixelFont;
import GUI.customButton.ButtonPixelFont;
import GUI.optionView.SetOfConfiguration;
import configuration.TouchControler;
import externData.ImageBank;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.VBox;

public class MenuPage extends Page {

    public MenuPage(Window window) {
        super(window);
    }

    @Override
    public void createPage() {

        TouchControler.resetNumber();
        SetOfConfiguration.resetConfiguration();

        ButtonNotClickeablePixelFont title = new ButtonNotClickeablePixelFont("THE YAZICINHO'S GAME", 70);
        ButtonPixelFont playOnline = new ButtonPixelFont("ONLINE",50);
        ButtonPixelFont playOffline = new ButtonPixelFont("OFFLINE",50);
        Button exitButton = new ButtonPixelFont("EXIT",40);

        BackgroundSize backgroundSize = new BackgroundSize(Window.WITDH, Window.HEIGHT, false, false, false, true);
        BackgroundImage background = new BackgroundImage(
                ImageBank.homePageBackground,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                backgroundSize
        );


        VBox layout = window.getLayout();
        layout.setSpacing(30);
        layout.setAlignment(Pos.CENTER);
        layout.setBackground(new Background(background));

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
