package GUI.optionView;

import GUI.PlayPage.PlayPageSlither;
import GUI.PlayPage.PlayPageSnake;
import controleur.ControlerSlither;
import controleur.ControlerSnake;
import controleur.KeyboardControler;
import externData.ImageBank;
import interfaces.HumanPlayer;
import interfaces.Orientation.Angle;
import interfaces.Orientation.Direction;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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


    private Label title;
    private PlayerChoosePane playerChoosePane;

    private Button launchButton;
    public PageMainOptionOffline (Stage primaryStage,Scene scene,int width, int height,boolean isSnake) {
        title = new Label("Parametre de lancement");

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


        launchButton = new Button("Lancer la partie");
        
        setPadding(new Insets(20)); // Marge de 10 pixels à tous les côtés du Pane

        final boolean isSnakeFinal = isSnake;
        launchButton.setOnAction(e -> {
            if(isSnakeFinal){
                lanchSnake();
            }else{
                launchSlither();
            }
        });
        OptionConfigPane optionConfigPane = new OptionConfigPane();

        
        getChildren().addAll(title,playerChoosePane,optionConfigPane,launchButton);

        VBox.setMargin(playerChoosePane, new javafx.geometry.Insets(100, 0, 0, 0));
        setAlignment(javafx.geometry.Pos.CENTER);
    }



    public void launchSlither(){
        PlayPageSlither playPage = new PlayPageSlither(WITDH/2,HEIGHT/2);
        EngineSlither engine = EngineSlither.createGame(400);
        

        Scene gameScene = new Scene(playPage, WITDH, HEIGHT);

        KeyboardControler<Double,Angle> controler1 = new ControlerSlither(KeyCode.LEFT, KeyCode.RIGHT, KeyCode.UP);
        //KeyboardControler<Double,Angle> controler2 = new ControlerSlither(KeyCode.Q, KeyCode.D, KeyCode.Z);
        
        engine.addPlayer(controler1);
        //engine.addPlayer(controler2);

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

        engine.run();
    }

    public void lanchSnake(){
        PlayPageSnake playPage = new PlayPageSnake(WITDH/2,HEIGHT/2);
        EngineSnake engine = EngineSnake.createGame(WITDH,HEIGHT);
        
        Scene gameScene = new Scene(playPage, WITDH, HEIGHT);
        gameScene.setOnKeyTyped(null);
        
        KeyboardControler<Integer,Direction> controler1 = new ControlerSnake(KeyCode.UP, KeyCode.DOWN, KeyCode.LEFT, KeyCode.RIGHT, KeyCode.SPACE);
        //KeyboardControler<Integer,Direction> controler2 = new ControlerSnake(KeyCode.Z, KeyCode.S, KeyCode.Q, KeyCode.D, KeyCode.SPACE);
        
        engine.addPlayer(controler1);
        //engine.addPlayer(controler2);
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

        
        engine.run();
    }

    


    public void onSaveButtonClicked(){

    } 
}
