package GUI.optionView;


import java.util.ArrayList;


import GUI.ButtonPixelFont;
import GUI.PlayPage.PlayPageSlither;
import GUI.PlayPage.PlayPageSnake;
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
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.engine.EngineSlither;
import model.engine.EngineSnake;

public class PageMainOptionOffline extends VBox{

    private Stage primaryStage;
    private VBox homeLayout;
    private Scene scene;
    private boolean isSnake;


    private ButtonPixelFont title;
    private PlayerChoosePane playerChoosePane;
    OptionConfigPane optionConfigPane;

    PlayPageSlither playPageSlither;
    PlayPageSnake playPageSnake;
    


    ArrayList<Node> listNode = new ArrayList<>();


    private ButtonPixelFont launchButton;
    public PageMainOptionOffline (Stage primaryStage,VBox homeLayout,Scene scene,boolean isSnake) {
        title = new ButtonPixelFont("SETTING OF LAUNCH",40);
        this.scene = scene;
        this.isSnake = isSnake;
        this.primaryStage = primaryStage;


        this.homeLayout = homeLayout;
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

        listNode.add(title);
        listNode.add(playerChoosePane);
        listNode.add(optionConfigPane);
        listNode.add(launchButton);

        
        homeLayout.getChildren().addAll(listNode);

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
        playPageSlither = new PlayPageSlither((int) scene.getWidth(),(int)scene.getHeight());

        EngineSlither engine = EngineSlither.createGame(Math.min((int) scene.getWidth(),(int)scene.getHeight())/2);
        
        for(int i = 0;i<SetOfConfiguration.getNumberOfHuman();i++){
            KeyboardControler<Double,Angle> controler = new ControlerSlither(SetOfConfiguration.commandMapingPanes.get(i));
            engine.addPlayer(controler);
            
        }

        for(int i = 0;i<SetOfConfiguration.getNumberOfBot();i++){
            engine.addBot();
            engine.addBot();
            engine.addBot();
        }
        
        scene.setOnKeyPressed( ev -> {
            if(ev.getCode() == KeyCode.P){
                engine.stop();

                homeLayout.getChildren().removeAll(playPageSlither);
                homeLayout.getChildren().addAll(listNode);

            }
            for (HumanPlayer p : engine.getPlayers()) {
                p.keyPressed(ev);
            }
        });

        scene.setOnKeyReleased( ev -> {
            for (HumanPlayer p : engine.getPlayers()) {
                p.keyReleased(ev);
            }
        });
        
        engine.addObserver(playPageSlither);
        engine.notifyObservers();

        homeLayout.getChildren().removeAll(listNode);
        homeLayout.getChildren().add(playPageSlither);
        VBox.setMargin(playPageSlither, new javafx.geometry.Insets(-100, 0, 0, 0));
        
        
        TouchControler.resetNumber();
        SetOfConfiguration.resetConfiguration();
        engine.run();
    }

    public void lanchSnake(){
        playPageSnake = new PlayPageSnake((int) scene.getWidth()/2,(int)scene.getHeight()/2);
        EngineSnake engine = EngineSnake.createGame((int) scene.getWidth(),(int)scene.getHeight());
        
        for(int i = 0;i<SetOfConfiguration.getNumberOfHuman();i++){
            KeyboardControler<Integer,Direction> controler = new ControlerSnake (SetOfConfiguration.commandMapingPanes.get(i));
            engine.addPlayer(controler);
            
        }
       
        for(int i = 0;i<SetOfConfiguration.getNumberOfBot();i++){
            engine.addBot();
        }

        scene.setOnKeyPressed( ev -> {
            if(ev.getCode() == KeyCode.P){
                
                homeLayout.getChildren().removeAll(playPageSnake);
                homeLayout.getChildren().addAll(listNode);
                engine.stop();
            }
            for (HumanPlayer p : engine.getPlayers()) {
                p.keyPressed(ev);
            }
        });

        scene.setOnKeyReleased( ev -> {
            for (HumanPlayer p : engine.getPlayers()) {
                p.keyReleased(ev);
            }
        });
        

        engine.addObserver(playPageSnake);
        engine.notifyObservers();  playPageSnake.requestFocus();

        homeLayout.getChildren().removeAll(listNode);
        homeLayout.getChildren().add(playPageSnake);
        VBox.setMargin(playPageSnake, new javafx.geometry.Insets(-100, 0, 0, 0));

        TouchControler.resetNumber();
        SetOfConfiguration.resetConfiguration();
        engine.run();
    }
}
