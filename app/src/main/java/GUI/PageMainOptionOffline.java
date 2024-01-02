package GUI;

import GUI.PlayPage.PlayPageSlither;
import GUI.PlayPage.PlayPageSnake;
import GUI.customButton.ButtonNotClickeablePixelFont;
import GUI.customButton.ButtonPixelFont;
import GUI.optionView.OptionConfigPane;
import GUI.optionView.PlayerChoosePane;
import GUI.optionView.SetOfConfiguration;
import configuration.ConfigurationSnakeDouble;
import configuration.ConfigurationSnakeInteger;
import configuration.TouchControler;
import controleur.ControlerSlither;
import controleur.ControlerSnake;
import controleur.KeyboardControler;
import interfaces.HumanPlayer;
import interfaces.Orientation.Angle;
import interfaces.Orientation.Direction;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import model.engine.EngineSlither;
import model.engine.EngineSnake;

public class PageMainOptionOffline extends Page {

    private boolean isSnake;

    OptionConfigPane optionConfigPane;

    public PageMainOptionOffline (Window window, boolean isSnake) {
        super(window);
        this.isSnake = isSnake;
    }

    public void valideConfig(){
        if(isSnake){
            if(optionConfigPane.isReachableWallActivated()){
                ConfigurationSnakeInteger.TRAVERSABLE_WALL = true;
            }
            if(optionConfigPane.isCollitionWithMeActivated()){
                ConfigurationSnakeInteger.CAN_COLLIDING_WITH_HIMSELF = true;
            }
            if(optionConfigPane.greedyDeathActivated()){
                ConfigurationSnakeInteger.IS_DEATH_FOOD = true;
            }
        }
        else{
            if(optionConfigPane.isReachableWallActivated()){
                ConfigurationSnakeDouble.IS_TRAVERSABLE_WALL = true;
            }
            if(optionConfigPane.isCollitionWithMeActivated()){
                ConfigurationSnakeDouble.CAN_COLLIDING_WITH_HIMSELF = true;
            }
            if(optionConfigPane.greedyDeathActivated()){
                ConfigurationSnakeDouble.IS_DEATH_FOOD = true;
            }
        }
    }



    public void launchSlither(){
        clear();
        Scene scene = window.getScene();

        PlayPageSlither playPageSlither = new PlayPageSlither((int) scene.getWidth(),(int)scene.getHeight());
        window.setOfflineSlither(EngineSlither.createGame(Math.min((int) scene.getWidth(),(int)scene.getHeight())/2));
        
        for(int i = 0;i<SetOfConfiguration.getNumberOfHuman();i++){
            KeyboardControler<Double,Angle> controler = new ControlerSlither(SetOfConfiguration.commandMapingPanes.get(i));
            window.getOfflineSlither().addPlayer(controler);
        }

        for(int i = 0;i<SetOfConfiguration.getNumberOfBot();i++){
            window.getOfflineSlither().addBot();
            window.getOfflineSlither().addBot();
            window.getOfflineSlither().addBot();
        }
        
        scene.setOnKeyPressed( ev -> {
            if(ev.getCode() == KeyCode.ESCAPE){
                window.getOfflineSlither().stop();
                window.switchToMenuPage();
            }
            for (HumanPlayer p : window.getOfflineSlither().getPlayers()) {
                p.keyPressed(ev);
            }
        });

        scene.setOnKeyReleased( ev -> {
            for (HumanPlayer p : window.getOfflineSlither().getPlayers()) {
                p.keyReleased(ev);
            }
        });
        
        window.getOfflineSlither().addObserver(playPageSlither);
        window.getOfflineSlither().notifyObservers();

        window.getLayout().getChildren().add(playPageSlither);
        //VBox.setMargin(playPageSlither, new javafx.geometry.Insets(-100, 0, 0, 0));
        
        
        TouchControler.resetNumber();
        SetOfConfiguration.resetConfiguration();
        window.getOfflineSlither().run();
    }

    public void lanchSnake(){
        clear();
        Scene scene = window.getScene();

        PlayPageSnake playPageSnake = new PlayPageSnake((int) scene.getWidth()/2,(int)scene.getHeight()/2);
        window.setOfflineSnake(EngineSnake.createGame((int) scene.getWidth(),(int)scene.getHeight()));
        
        for(int i = 0;i<SetOfConfiguration.getNumberOfHuman();i++){
            KeyboardControler<Integer,Direction> controler = new ControlerSnake (SetOfConfiguration.commandMapingPanes.get(i));
            window.getOfflineSnake().addPlayer(controler);
        }
        for(int i = 0;i<SetOfConfiguration.getNumberOfBot();i++){
            window.getOfflineSnake().addBot();
        }

        scene.setOnKeyPressed( ev -> {
            if(ev.getCode() == KeyCode.ESCAPE){
                window.getOfflineSnake().stop();
                window.switchToMenuPage();
            }
            for (HumanPlayer p : window.getOfflineSnake().getPlayers()) {
                p.keyPressed(ev);
            }
        });

        scene.setOnKeyReleased( ev -> {
            for (HumanPlayer p : window.getOfflineSnake().getPlayers()) {
                p.keyReleased(ev);
            }
        });
        

        window.getOfflineSnake().addObserver(playPageSnake);
        window.getOfflineSnake().notifyObservers();  playPageSnake.requestFocus();

        window.getLayout().getChildren().add(playPageSnake);
        //VBox.setMargin(playPageSnake, new javafx.geometry.Insets(-100, 0, 0, 0));

        TouchControler.resetNumber();
        SetOfConfiguration.resetConfiguration();
        window.getOfflineSnake().run();
    }

    @Override
    public void createPage() {
        ButtonNotClickeablePixelFont title = new ButtonNotClickeablePixelFont("SETTING OF LAUNCH",40);
        PlayerChoosePane playerChoosePane = new PlayerChoosePane(isSnake);
        ButtonPixelFont launchButton = new ButtonPixelFont("LAUNCH GAME",30);
        
        VBox layout = window.getLayout();
        layout.getChildren().clear();
        layout.setSpacing(50);
        layout.setPadding(new Insets(20)); // Marge de 10 pixels à tous les côtés du Pane
        layout.setAlignment(Pos.CENTER);

        final boolean isSnakeFinal = isSnake;
        launchButton.setOnAction(e -> {
            valideConfig();
            if(isSnakeFinal){
                lanchSnake();
            }else{
                launchSlither();
            }
        });
        optionConfigPane = new OptionConfigPane();

        layout.getChildren().add(title);
        layout.getChildren().add(playerChoosePane);
        layout.getChildren().add(optionConfigPane);
        layout.getChildren().add(launchButton);

        VBox.setMargin(playerChoosePane, new javafx.geometry.Insets(100, 0, 0, 0));
        VBox.setMargin(launchButton, new javafx.geometry.Insets(100, 0, 0, 0));
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
