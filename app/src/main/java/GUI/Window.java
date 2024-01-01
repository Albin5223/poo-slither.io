package GUI;


import externData.ImageBank;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;

import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Window {

    
    public static Rectangle2D screen = Screen.getPrimary().getBounds();
    int WITDH = (int) screen.getWidth();
    int HEIGHT = (int) screen.getHeight();

    Stage primaryStage;
    Pane root;
    Button title;
    Scene scene;

    ArrayList<Node> listNode = new ArrayList<>();

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

        title = new ButtonPixelFont("THE YAZICINHO'S GAME", 70);


        ButtonPixelFont playOnline = new ButtonPixelFont("ONLINE",50);
        ButtonPixelFont playOffline = new ButtonPixelFont("OFFLINE",50);

        playOffline.setOnAction(e -> {
            OfflinePage offlinePage = new OfflinePage(primaryStage, layout,scene);
            
            layout.getChildren().removeAll(listNode);

            layout.getChildren().addAll(offlinePage);

        });

        Button exitButton = new ButtonPixelFont("EXIT",40);
        

        exitButton.setOnAction(e -> {
            primaryStage.close();
        });

        
        
        listNode.add(title);
        listNode.add(playOnline);
        listNode.add(playOffline);
        listNode.add(exitButton);
        layout.getChildren().addAll(listNode);
        layout.setAlignment(Pos.CENTER);

        VBox.setMargin(playOnline, new Insets(60, 0, 0, 0));
       
    }

    public Scene getScene() {
        return scene;
    }
    
}
