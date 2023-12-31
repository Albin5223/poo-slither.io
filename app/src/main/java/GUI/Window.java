package GUI;

import externData.ImageBank;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Window {

    int WITDH = 1200;
    int HEIGHT = 800;

    Stage primaryStage;
    Pane root;
    Label title;
    Scene scene;

    public Window(Stage primaryStage) {
        this.primaryStage = primaryStage;

        BackgroundSize backgroundSize = new BackgroundSize(WITDH, HEIGHT, false, false, false, true);
        BackgroundImage background = new BackgroundImage(
                ImageBank.homePageBackground,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                backgroundSize
        );

        VBox layout = new VBox(30);
        layout.setBackground(new Background(background));

        scene = new Scene(layout, WITDH, HEIGHT);

        title = new Label("The Yazicinho' Games");


        Button playOnline = new Button("Jouer en ligne");
        Button playOffline = new Button("Jouer hors ligne");

        playOffline.setOnAction(e -> {
            OfflinePage offlinePage = new OfflinePage(primaryStage, scene, WITDH, HEIGHT);
            Scene offlineScene = new Scene(offlinePage, WITDH, HEIGHT);
            primaryStage.setScene(offlineScene);
            primaryStage.show();

        });

        Button exitButton = new Button("Quitter");
        

        exitButton.setOnAction(e -> {
            primaryStage.close();
        });

        
        layout.getChildren().addAll(title,playOnline,playOffline, exitButton);
        layout.setAlignment(Pos.CENTER);

        VBox.setMargin(playOnline, new Insets(60, 0, 0, 0));
       
    }

    public Scene getScene() {
        return scene;
    }
    
}
