package GUI;

import GUI.customButton.ButtonNotClickeablePixelFont;
import GUI.customButton.ButtonPixelFont;
import externData.ImageBank;
import javafx.geometry.Pos;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class OfflinePage extends Page {    

    public OfflinePage(Window window) {
        super(window);
    }

    @Override
    public void createPage() {
        ButtonNotClickeablePixelFont title = new ButtonNotClickeablePixelFont("OFFLINE",60);
        title.setFill(Color.GRAY);
        ButtonPixelFont playButtonSlither = new ButtonPixelFont("SLITHER.IO",40);
        ButtonPixelFont playButtonSnake = new ButtonPixelFont("SNAKE",40);
        ButtonPixelFont exitButton = new ButtonPixelFont("BACK",40);

        Border border = new Border(new javafx.scene.layout.BorderStroke(
                javafx.scene.paint.Color.BLACK,
                BorderStrokeStyle.NONE, // Type de la bordure, ici aucun
                new CornerRadii(10), // Marge arrondie du rectangle
                new BorderWidths(20) // Épaisseur de la bordure
        ));

        VBox layout = window.getLayout();
        layout.setSpacing(50);
        layout.setBorder(border);
        layout.setAlignment(Pos.CENTER);
        this.setBackground(ImageBank.homePageBackground);


        playButtonSlither.setOnAction(e -> {
            window.switchToPageMainOptionOffline(false);
        });

        playButtonSnake.setOnAction(e -> {
            window.switchToPageMainOptionOffline(true);
            
        });

        exitButton.setOnAction(e -> {
            window.switchToMenuPage();
        });

        layout.getChildren().add(title);
        layout.getChildren().add(playButtonSlither);
        layout.getChildren().add(playButtonSnake);
        layout.getChildren().add(exitButton);

        VBox.setMargin(playButtonSlither, new javafx.geometry.Insets(50, 0, 0, 0));
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
