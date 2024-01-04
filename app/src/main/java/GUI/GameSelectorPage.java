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

public abstract sealed class GameSelectorPage extends Page {

    private ButtonNotClickeablePixelFont title;

    protected ButtonPixelFont playButtonSlither;
    protected ButtonPixelFont playButtonSnake;
    protected ButtonPixelFont exitButton;

    public GameSelectorPage(Window window, String title) {
        super(window);
        this.title = new ButtonNotClickeablePixelFont(title.toUpperCase(),60);
    }

    public static GameSelectorPage createGameSelectorPage(Window window, boolean isOnline){
        if(isOnline){
            return new OnlineGameSelectorPage(window);
        }
        else{
            return new OfflineGameSelectorPage(window);
        }
    }

    protected abstract void setButtonsAction();

    @Override
    public void createPage() {
        title.setFill(Color.GRAY);
        playButtonSlither = new ButtonPixelFont("SLITHER.IO",40, true);
        playButtonSnake = new ButtonPixelFont("SNAKE",40, true);
        exitButton = new ButtonPixelFont("BACK",40, true);

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

        setButtonsAction();

        layout.getChildren().addAll(title, playButtonSlither, playButtonSnake, exitButton);

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

    public static final class OnlineGameSelectorPage extends GameSelectorPage {

        public OnlineGameSelectorPage(Window window) {
            super(window, "ONLINE");
        }

        @Override
        protected void setButtonsAction() {
            playButtonSlither.setOnAction(e -> {
                window.switchToHostOrJoinPage(false);
            });

            playButtonSnake.setOnAction(e -> {
                window.switchToHostOrJoinPage(true);
                
            });

            exitButton.setOnAction(e -> {
                window.switchToMenuPage();
            });
        }
    }

    public static final class OfflineGameSelectorPage extends GameSelectorPage {
        public OfflineGameSelectorPage(Window window) {
            super(window, "OFFLINE");
        }

        @Override
        protected void setButtonsAction() {
            playButtonSlither.setOnAction(e -> {
                window.switchToPageMainOptionOffline(false);
            });

            playButtonSnake.setOnAction(e -> {
                window.switchToPageMainOptionOffline(true);
                
            });

            exitButton.setOnAction(e -> {
                window.switchToMenuPage();
            });
        }
    }
    
}
