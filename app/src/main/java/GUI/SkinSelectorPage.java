package GUI;

import GUI.customButton.ButtonNotClickeablePixelFont;
import GUI.customButton.ButtonPixelFont;
import externData.ImageBank;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.skins.Skin;
import model.skins.SkinFlag;

public class SkinSelectorPage extends Page {

    private final boolean isSnake;

    private static final int IMAGE_SIZE = 600;

    private int currentSkin = 0;
    private Skin[] allSkins = {SkinFlag.buildFrance(), SkinFlag.buildGermany(), SkinFlag.buildItaly()};
    private String[] allSkinNames = {"FRANCE", "GERMANY", "ITALY"};
    private Image[] allSlitherImages = {ImageBank.skin_slither_france, ImageBank.skin_slither_germany, ImageBank.skin_slither_italy};
    private Image[] allSnakeImages = {ImageBank.skin_snake_france, ImageBank.skin_snake_germany, ImageBank.skin_snake_italy};

    private ButtonNotClickeablePixelFont skinName;
    private ButtonPixelFont prevSkin;
    private ImageView skinImageView;
    private ButtonPixelFont nextSkin;
    private ButtonPixelFont play;

    private void goLeft(){
        currentSkin -= 1;
        if(currentSkin < 0){
            currentSkin = allSlitherImages.length - 1;
        }
        skinName.setButtonText(allSkinNames[currentSkin]);
        skinImageView.setImage(isSnake ? allSnakeImages[currentSkin] : allSlitherImages[currentSkin]);
    }

    private void goRight(){
        currentSkin += 1;
        if(currentSkin >= allSlitherImages.length){
            currentSkin = 0;
        }
        skinName.setButtonText(allSkinNames[currentSkin]);
        skinImageView.setImage(isSnake ? allSnakeImages[currentSkin] : allSlitherImages[currentSkin]);
    }

    public SkinSelectorPage(Window window, boolean isSnake) {
        super(window);
        this.isSnake = isSnake;
    }

    @Override
    public void createPage() {
        skinName = new ButtonNotClickeablePixelFont(allSkinNames[currentSkin], 100);
        prevSkin = new ButtonPixelFont("<", 70, false);
        nextSkin = new ButtonPixelFont(">", 70, false);
        play = new ButtonPixelFont("PLAY", 70, true);

        prevSkin.setOnAction(e -> {
            goLeft();
        });

        nextSkin.setOnAction(e -> {
            goRight();
        });

        play.setOnAction(e -> {
            window.getClient().setSkin(allSkins[currentSkin]);
            clear();
        });

        skinImageView = isSnake ? new ImageView(allSnakeImages[currentSkin]) : new ImageView(allSlitherImages[currentSkin]);
        skinImageView.setPreserveRatio(true);
        skinImageView.setFitWidth(IMAGE_SIZE);  // Set the maximum width of the ImageView
        skinImageView.setFitHeight(IMAGE_SIZE);  // Set the maximum height of the ImageView

        // Create a HBox and add the < button, ImageView, and > button to it
        HBox hbox = new HBox(prevSkin, skinImageView, nextSkin);
        hbox.setAlignment(Pos.CENTER);

        VBox layout = window.getLayout();
        layout.setSpacing(10);
        layout.setAlignment(Pos.CENTER);
        this.setBackground(ImageBank.wallpaper_dark_bridge);
        layout.getChildren().addAll(skinName, hbox, play);

        VBox.setMargin(hbox, new javafx.geometry.Insets(100, 0, 100, 0));  // Set the margin for the HBox
        HBox.setMargin(prevSkin, new javafx.geometry.Insets(0, 50, 0, 0));  // Set the margin for the < button
        HBox.setMargin(nextSkin, new javafx.geometry.Insets(0, 0, 0, 50));  // Set the margin for the > button
    }

    @Override
    public void sceneKeyConfiguration() {
        window.getScene().setOnKeyPressed( ev -> {
            if(ev.getCode() == KeyCode.ESCAPE){
                window.switchToMenuPage();
            }
            else if(ev.getCode() == KeyCode.ENTER){
                play.fire();
            }
            else if(ev.getCode() == KeyCode.LEFT){  // doesn't work with arrows...
                prevSkin.fire();
            }
            else if(ev.getCode() == KeyCode.RIGHT){ // doesn't work with arrows...
                nextSkin.fire();
            }
        });
    }
    
}
