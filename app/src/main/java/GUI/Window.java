package GUI;


import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import model.engine.EngineSlither;
import model.engine.EngineSnake;

public class Window {

    private EngineSlither offlineSlither;
    private EngineSnake offlineSnake;

    public static final Rectangle2D screen = Screen.getPrimary().getBounds();
    public static final int WITDH = (int) screen.getWidth();
    public static final int HEIGHT = (int) screen.getHeight();

    private Stage primaryStage;
    private Scene scene;
    private VBox layout;

    private MenuPage menuPage;
    private OfflinePage offlinePage;
    private PageMainOptionOffline pageMainOptionOffline;

    public Stage getPrimaryStage() {return primaryStage;}
    public Scene getScene() {return scene;}
    public VBox getLayout() {return layout;}
    public void resetLayout() {layout = new VBox();}

    public EngineSlither getOfflineSlither() {return offlineSlither;}
    public EngineSnake getOfflineSnake() {return offlineSnake;}
    public void setOfflineSlither(EngineSlither offlineSlither) {this.offlineSlither = offlineSlither;}
    public void setOfflineSnake(EngineSnake offlineSnake) {this.offlineSnake = offlineSnake;}

    public Window(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.layout = new VBox();
        this.scene = new Scene(layout, WITDH, HEIGHT);
        // setScene is already called in App.java


        menuPage = new MenuPage(this);
        offlinePage = new OfflinePage(this);

        menuPage.show();
    }

    public void switchToMenuPage(){
        menuPage.show();
    }

    public void switchToOfflinePage(){
        offlinePage.show();
    }

    public void switchToPageMainOptionOffline(boolean isSnake){
        pageMainOptionOffline = new PageMainOptionOffline(this,isSnake);
        pageMainOptionOffline.show();
    }
    
}
