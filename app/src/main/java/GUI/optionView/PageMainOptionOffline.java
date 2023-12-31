package GUI.optionView;


import GUI.ButtonPixelFont;
import GUI.PlayPage.PlayPageSlither;
import GUI.PlayPage.PlayPageSnake;
import configuration.ConfigurationSnakeDouble;
import configuration.ConfigurationSnakeInteger;
import configuration.TouchControler;
import controleur.ControlerSlither;
import controleur.ControlerSnake;
import controleur.KeyboardControler;
import externData.ImageBank;
import interfaces.HumanPlayer;
import interfaces.Orientation.Angle;
import interfaces.Orientation.Direction;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.engine.EngineSlither;
import model.engine.EngineSnake;

public class PageMainOptionOffline extends VBox{

    private int WITDH;
    private int HEIGHT;

    private Stage primaryStage;
    private Scene homeScene;

    private boolean isSnake;


    private ButtonPixelFont title;
    private PlayerChoosePane playerChoosePane;
    OptionConfigPane optionConfigPane;

    private ButtonPixelFont launchButton;
    public PageMainOptionOffline (Stage primaryStage,Scene scene,int width, int height,boolean isSnake) {
        title = new ButtonPixelFont("SETTING OF LAUNCH",40);

        this.isSnake = isSnake;
        this.primaryStage = primaryStage;

        BackgroundSize backgroundSize = new BackgroundSize(WITDH, HEIGHT, false, false, false, true);
        BackgroundImage background = new BackgroundImage(
                ImageBank.homePageBackground,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                backgroundSize
        );

        this.setBackground(new Background(background));

        this.homeScene = scene;
        this.WITDH = width;
        this.HEIGHT = height;
        playerChoosePane = new PlayerChoosePane(isSnake);


        launchButton = new ButtonPixelFont("LAUNCH GAME",30);
        
        setPadding(new Insets(20)); // Marge de 10 pixels à tous les côtés du Pane

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

        
        getChildren().addAll(title,playerChoosePane,optionConfigPane,launchButton);

        VBox.setMargin(playerChoosePane, new javafx.geometry.Insets(100, 0, 0, 0));
        VBox.setMargin(launchButton, new javafx.geometry.Insets(100, 0, 0, 0));
        setAlignment(javafx.geometry.Pos.CENTER);
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
        PlayPageSlither playPage = new PlayPageSlither(WITDH/2,HEIGHT/2);
        EngineSlither engine = EngineSlither.createGame(400);
        

        Scene gameScene = new Scene(playPage, WITDH, HEIGHT);

        
        for(int i = 0;i<SetOfConfiguration.getNumberOfHuman();i++){
            KeyboardControler<Double,Angle> controler = new ControlerSlither(SetOfConfiguration.commandMapingPanes.get(i));
            engine.addPlayer(controler);
            
        }

        for(int i = 0;i<SetOfConfiguration.getNumberOfBot();i++){
            engine.addBot();
            engine.addBot();
            engine.addBot();
        }
        
        gameScene.setOnKeyPressed( ev -> {
            if(ev.getCode() == KeyCode.ESCAPE){
                primaryStage.setScene(homeScene);
                primaryStage.show();
                engine.stop();
            }
            for (HumanPlayer p : engine.getPlayers()) {
                p.keyPressed(ev);
            }
        });

        gameScene.setOnKeyReleased( ev -> {
            for (HumanPlayer p : engine.getPlayers()) {
                p.keyReleased(ev);
            }
        });
        
        engine.addObserver(playPage);
        engine.notifyObservers();

        primaryStage.setScene(gameScene);
        primaryStage.show();

        playPage.setFocusTraversable(true);
        playPage.requestFocus();
        
        TouchControler.resetNumber();
        SetOfConfiguration.resetConfiguration();
        engine.run();
    }

    public void lanchSnake(){
        PlayPageSnake playPage = new PlayPageSnake(WITDH/2,HEIGHT/2);
        EngineSnake engine = EngineSnake.createGame(WITDH,HEIGHT);
        
        Scene gameScene = new Scene(playPage, WITDH, HEIGHT);
        gameScene.setOnKeyTyped(null);
        
        for(int i = 0;i<SetOfConfiguration.getNumberOfHuman();i++){
            KeyboardControler<Integer,Direction> controler = new ControlerSnake (SetOfConfiguration.commandMapingPanes.get(i));
            engine.addPlayer(controler);
            
        }
       
        for(int i = 0;i<SetOfConfiguration.getNumberOfBot();i++){
            engine.addBot();
        }

        gameScene.setOnKeyPressed( ev -> {
            if(ev.getCode() == KeyCode.ESCAPE){
                primaryStage.setScene(homeScene);
                primaryStage.show();
                engine.stop();
            }
            for (HumanPlayer p : engine.getPlayers()) {
                p.keyPressed(ev);
            }
        });

        gameScene.setOnKeyReleased( ev -> {
            for (HumanPlayer p : engine.getPlayers()) {
                p.keyReleased(ev);
            }
        });
        

        engine.addObserver(playPage);
        engine.notifyObservers();

        primaryStage.setScene(gameScene);
        primaryStage.show();

        playPage.setFocusTraversable(true);
        playPage.requestFocus();

        TouchControler.resetNumber();
        SetOfConfiguration.resetConfiguration();
        engine.run();
    }

    


    public void onSaveButtonClicked(){

    } 
}
