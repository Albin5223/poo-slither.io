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
        gameHandler.setOfflineSnake(offlineSnake);
    }

    public void stopServer(){
        gameHandler.getNetworkHandlerSnake().stopServer();
        gameHandler.getNetworkHandlerSlither().stopServer();
    }

    public void stopClient(){
        gameHandler.getNetworkHandlerSnake().stopClient();
        gameHandler.getNetworkHandlerSlither().stopClient();
    }


    public void startClient(boolean isSnake){
        if(isSnake){
            gameHandler.getNetworkHandlerSnake().startClient();
        }
        else{
            gameHandler.getNetworkHandlerSlither().startClient();
        }
    }

    public void setPseudoClient(String pseudo,boolean isSnake){
        if(isSnake){
            gameHandler.getNetworkHandlerSnake().setClientPseudo(pseudo);
        }
        else{
            gameHandler.getNetworkHandlerSlither().setClientPseudo(pseudo);
        }
    }

    public void setKeyCodeClient(KeyEvent ev, boolean isSnake){
        if(isSnake){
            gameHandler.getNetworkHandlerSnake().setKeyCode(ev);
        }
        else{
            gameHandler.getNetworkHandlerSlither().setKeyCode(ev);
        }
    }

    public PlayPageSnake getClientSnakePlayPage() {
        return gameHandler.getClientSnake().getPlayPage();
    }

    public void setClientSkin(Skin skin,boolean isSnake){
        if(isSnake){
            gameHandler.getNetworkHandlerSnake().setClientSkin(skin);
        }
        else{
            gameHandler.getNetworkHandlerSlither().setClientSkin(skin);
        }
    }

    public void setIpClient(String ip,boolean isSnake){
        if(isSnake){
            gameHandler.getNetworkHandlerSnake().setClientIp(ip);
        }
        else{
            gameHandler.getNetworkHandlerSlither().setClientIp(ip);
        }
    }

    public void setReleasedKeyCodeClient(KeyEvent ev, boolean isSnake){
        if(isSnake){
            gameHandler.getNetworkHandlerSnake().setReleasedKeyCode(ev);
            
        }
        else{
            gameHandler.getNetworkHandlerSlither().setReleasedKeyCode(ev);
        }
    }

    public void stopClient(boolean isSnake){
        if(isSnake){
            gameHandler.getNetworkHandlerSnake().stopClient();
        }
        else{
            gameHandler.getNetworkHandlerSlither().stopClient();;
        }   
    }

    public boolean isServerSnakeDone(){
        return gameHandler.getNetworkHandlerSnake().isServerDone();
    }

    public boolean isServerSlitherDone(){
        return gameHandler.getNetworkHandlerSlither().isServerDone();
    }

    public String getServerIp(boolean isSnake){
        if(isSnake){
            return gameHandler.getNetworkHandlerSnake().getServerIp();
        }
        else{
            return gameHandler.getNetworkHandlerSlither().getServerIp();
        }
    }

    public void stopServer(boolean isSnake){
        if(isSnake){
            gameHandler.getNetworkHandlerSnake().stopServer();
        }
        else{
            gameHandler.getNetworkHandlerSlither().stopServer();
        }
    }

    public void startServer(boolean isSnake){
        if(isSnake){
            gameHandler.getNetworkHandlerSnake().startServer();
        }
        else{
            gameHandler.getNetworkHandlerSlither().startServer();
        }
    }


    public PlayPageSlither getClientSlitherPlayPage() {
        return gameHandler.getClientSlither().getPlayPageSlither();
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
