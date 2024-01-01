package GUI;

import java.util.ArrayList;

import GUI.optionView.PageMainOptionOffline;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class OfflinePage extends VBox {


    Stage primaryStage;
    VBox homeLayout;
    Scene scene;

    ArrayList<Node> listNode = new ArrayList<>();
    

    public OfflinePage(Stage primaryStage,VBox homeLayout, Scene scene) {
        this.primaryStage = primaryStage;

        ButtonPixelFont title = new ButtonPixelFont("OFFLINE",60);

        ButtonPixelFont playButtonSlither = new ButtonPixelFont("SLITHER.IO",40);
        ButtonPixelFont playButtonSnake = new ButtonPixelFont("SNAKE",40);
        ButtonPixelFont exitButton = new ButtonPixelFont("EXIT",40);

        Border border = new Border(new javafx.scene.layout.BorderStroke(
                javafx.scene.paint.Color.BLACK,
                BorderStrokeStyle.NONE, // Type de la bordure, ici aucun
                new CornerRadii(10), // Marge arrondie du rectangle
                new BorderWidths(20) // Épaisseur de la bordure
        ));

        // Application de la bordure au VBox
        setBorder(border);
        setAlignment(Pos.CENTER);
        setSpacing(50);


        playButtonSlither.setOnAction(e -> {
            PageMainOptionOffline pageMainOption = new PageMainOptionOffline(primaryStage,homeLayout,scene,false);
            homeLayout.getChildren().removeAll(listNode);
            homeLayout.getChildren().addAll(pageMainOption);
        });

        playButtonSnake.setOnAction(e -> {
            PageMainOptionOffline pageMainOption = new PageMainOptionOffline(primaryStage,homeLayout,scene,true);
            homeLayout.getChildren().removeAll(listNode);
            homeLayout.getChildren().addAll(pageMainOption);
            
        });

        exitButton.setOnAction(e -> {
            primaryStage.close();
        });


        listNode.add(title);
        listNode.add(playButtonSlither);
        listNode.add(playButtonSnake);
        listNode.add(exitButton);

        homeLayout.getChildren().addAll(listNode);
        VBox.setMargin(playButtonSlither, new javafx.geometry.Insets(50, 0, 0, 0));
    }
    
}
