package GUI;

import javafx.scene.input.KeyCode;

public class ServerJoinPage extends Page {

    public ServerJoinPage(Window window) {
        super(window);
    }

    @Override
    public void createPage() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createPage'");
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
