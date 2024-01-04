package GUI;

import GUI.customButton.ButtonNotClickeablePixelFont;
import GUI.customButton.ButtonPixelFont;
import externData.ImageBank;
import javafx.geometry.Pos;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;

public class ServerHostPage extends Page {

    private ButtonNotClickeablePixelFont titre;
    private ButtonPixelFont back;

    private ButtonPixelFont startServer;

    private ButtonNotClickeablePixelFont IP;
    private ButtonPixelFont stopServer;

    public ServerHostPage(Window window) {
        super(window);
    }

    @Override
    public void createPage() {

        titre = new ButtonNotClickeablePixelFont("SERVER HOSTING", 100);
        back = new ButtonPixelFont("BACK", 70, true);

        VBox layout = window.getLayout();
        layout.setSpacing(10);
        layout.setAlignment(Pos.CENTER);
        this.setBackground(ImageBank.wallpaper_dark_bridge);

        layout.getChildren().add(titre);

        if(window.getServer().isDone()){
            createPageDoneServer(layout);
        } else {
            createPageRunningServer(layout);
        }

        back.setOnAction(e -> {
            window.switchToHostOrJoinPage();
        });
        layout.getChildren().add(back);
    }

    private void createPageRunningServer(VBox layout) {

        IP = new ButtonNotClickeablePixelFont("IP : " + window.getServer().getIp(), 70);

        stopServer = new ButtonPixelFont("STOP SERVER", 70, true);
        stopServer.setOnAction(e -> {
            window.getServer().shutdown();
            window.replaceServerThread();
            window.switchToServerHostPage();
        });
        layout.getChildren().addAll(IP,stopServer);

        VBox.setMargin(IP, new javafx.geometry.Insets(100, 0, 0, 0));
        VBox.setMargin(stopServer, new javafx.geometry.Insets(0, 0, 100, 0));
    }

    private void createPageDoneServer(VBox layout) {
        startServer = new ButtonPixelFont("START SERVER", 70, true);
        startServer.setOnAction(e -> {
            window.getServerThread().start();
            window.switchToServerHostPage();
        });
        layout.getChildren().add(startServer);

        VBox.setMargin(startServer, new javafx.geometry.Insets(100, 0, 100, 0));
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
