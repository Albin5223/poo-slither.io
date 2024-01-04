package GUI;

import client.Client;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import model.engine.EngineSlither;
import model.engine.EngineSnake;
import server.Server;

public class Window {

    private Server serverSnake = new Server();
    public Server getServerSnake() {return serverSnake;}
    private Thread serverSnakeThread = new Thread(serverSnake);
    public Thread getServerSnakeThread() {return serverSnakeThread;}
    public void replaceServerSnakeThread() {serverSnakeThread = new Thread(serverSnake);}

    private Server serverSlither = new Server();
    public Server getServerSlither() {return serverSlither;}
    private Thread serverSlitherThread = new Thread(serverSlither);
    public Thread getServerSlitherThread() {return serverSlitherThread;}
    public void replaceServerSlitherThread() {serverSlitherThread = new Thread(serverSlither);}

    private Client client = new Client();
    public Client getClient() {return client;}
    private Thread clientThread = new Thread(client);
    public Thread getClientThread() {return clientThread;}
    public void replaceClientThread() {clientThread = new Thread(client);}

    private EngineSlither offlineSlither;
    private EngineSnake offlineSnake;

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

    public EngineSlither getOfflineSlither() {return offlineSlither;}
    public EngineSnake getOfflineSnake() {return offlineSnake;}
    public void setOfflineSlither(EngineSlither offlineSlither) {this.offlineSlither = offlineSlither;}
    public void setOfflineSnake(EngineSnake offlineSnake) {this.offlineSnake = offlineSnake;}

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
