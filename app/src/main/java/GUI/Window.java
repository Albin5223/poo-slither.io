package GUI;

import GUI.PlayPage.PlayPageSlither;
import GUI.PlayPage.PlayPageSnake;
import configuration.ConfigurationFoodDouble;
import configuration.ConfigurationFoodInteger;
import configuration.ConfigurationSnakeDouble;
import configuration.ConfigurationSnakeInteger;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import model.engine.EngineSlither;
import model.engine.EngineSnake;
import model.skins.Skin;

public class Window {


    public static final Rectangle2D screen = Screen.getPrimary().getBounds();
    public static final int WITDH = (int) screen.getWidth();
    public static final int HEIGHT = (int) screen.getHeight();

    private Stage primaryStage;
    private Scene scene;
    private VBox layout;

    public Stage getPrimaryStage() {return primaryStage;}
    public Scene getScene() {return scene;}
    public VBox getLayout() {return layout;}
    public void resetLayout() {
        layout = new VBox();
        scene.setRoot(layout);
    }

    GameHandler gameHandler = new GameHandler();

    public EngineSlither getOfflineSlither() {
        return gameHandler.getOfflineSlither();
    }
    public EngineSnake getOfflineSnake() {
        return gameHandler.getOfflineSnake();
    }

    public void setOfflineSlither(EngineSlither offlineSlither) {
        gameHandler.setOfflineSlither(offlineSlither);
    }
    public void setOfflineSnake(EngineSnake offlineSnake) {
        gameHandler.setOfflineSlither(offlineSnake);
    }

    public void stopServer(){
        gameHandler.stopServerSnake();
        gameHandler.stopServerSlither();
    }

    public void stopClient(){
        gameHandler.stopClientSnake();
        gameHandler.stopClientSlither();
    }


    public void startClient(boolean isSnake){
        if(isSnake){
            gameHandler.startClientSnake();
        }
        else{
            gameHandler.startClientSlither();
        }
    }

    public void setPseudoClient(String pseudo,boolean isSnake){
        if(isSnake){
            gameHandler.setClientSnakePseudo(pseudo);
        }
        else{
            gameHandler.setClientSlitherPseudo(pseudo);
        }
    }

    public void setKeyCodeClient(KeyEvent ev, boolean isSnake){
        if(isSnake){
            gameHandler.setKeyCodeClientSnake(ev);
        }
        else{
            gameHandler.setKeyCodeClientSlither(ev);
        }
    }

    public PlayPageSnake getClientSnakePlayPage() {
        return gameHandler.getClientSnakePlayPage();
    }

    public void setClientSkin(Skin skin,boolean isSnake){
        if(isSnake){
            gameHandler.setClientSnakeSkin(skin);
        }
        else{
            gameHandler.setClientSlitherSkin(skin);
        }
    }

    public void setIpClient(String ip,boolean isSnake){
        if(isSnake){
            gameHandler.setClientSnakeIp(ip);
        }
        else{
            gameHandler.setClientSlitherIp(ip);
        }
    }

    public void setReleasedKeyCodeClient(KeyEvent ev, boolean isSnake){
        if(isSnake){
            gameHandler.setReleasedKeyCodeClientSnake(ev);
        }
        else{
            gameHandler.setReleasedKeyCodeClientSlither(ev);
        }
    }

    public void stopClient(boolean isSnake){
        if(isSnake){
            gameHandler.stopClientSnake();
        }
        else{
            gameHandler.stopClientSlither();
        }   
    }

    public boolean isServerSnakeDone(){
        return gameHandler.isServerSnakeDone();
    }

    public boolean isServerSlitherDone(){
        return gameHandler.isServerSlitherDone();
    }

    public String getServerIp(boolean isSnake){
        if(isSnake){
            return gameHandler.getServerSnakeIp();
        }
        else{
            return gameHandler.getServerSlitherIp();
        }
    }

    public void stopServer(boolean isSnake){
        if(isSnake){
            gameHandler.stopServerSnake();
        }
        else{
            gameHandler.stopServerSlither();
        }
    }

    public void startServer(boolean isSnake){
        if(isSnake){
            gameHandler.startServerSnake();
        }
        else{
            gameHandler.startServerSlither();
        }
    }

    public PlayPageSlither getClientSlitherPlayPage() {
        return gameHandler.getClientSlitherPlayPage();
    }

    public ConfigurationFoodDouble getConfigFoodDouble() {
        return gameHandler.getConfigFoodSlither();
    }

    public ConfigurationSnakeDouble getConfigSnakeSlither() {
        return gameHandler.getConfigSnakeSlither();
    }

    public ConfigurationFoodInteger getConfigFoodSnake(){
        return gameHandler.getConfigFoodSnake();
    }

    public ConfigurationSnakeInteger getConfigSnakeSnake(){
        return gameHandler.getConfigSnakeSnake();
    }


    public Window(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.layout = new VBox();
        this.scene = new Scene(layout, WITDH, HEIGHT);
        // setScene is already called in App.java

        switchToMenuPage();
    }

    public void switchToMenuPage(){
        new MenuPage(this).show();
    }

    public void switchToGameSelector(boolean isOnline){
        GameSelectorPage.createGameSelectorPage(this, isOnline).show();
    }

    public void switchToPageMainOptionOffline(boolean isSnake){
        new PageMainOptionOffline(this,isSnake).show();
    }

    public void switchToPseudoSelectorPage(boolean isSnake){
        new PseudoSelectorPage(this, isSnake).show();
    }

    public void switchToSkinSelectorPage(boolean isSnake){
        new SkinSelectorPage(this, isSnake).show();
    }

    public void switchToHostOrJoinPage(boolean isSnake){
        new HostOrJoinPage(this, isSnake).show();
    }

    public void switchToServerHostPage(boolean isSnake){
        new ServerHostPage(this, isSnake).show();
    }

    public void switchToServerJoinPage(boolean isSnake){
        new ServerJoinPage(this, isSnake).show();
    }
    
}
