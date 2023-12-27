package GUI;

import controleur.ControlerSlither;
import controleur.ControlerSnake;
import controleur.KeyboardControler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.engine.EngineSlither;
import model.engine.EngineSnake;
import interfaces.HumanPlayer;
import interfaces.Orientation.Angle;
import interfaces.Orientation.Direction;

public class Window {

    int WITDH = 1200;
    int HEIGHT = 800;

    Stage primaryStage;
    Pane root;
    Scene scene;

    public Window(Stage primaryStage) {
        this.primaryStage = primaryStage;
        Button playButtonSlither = new Button("Jouer Slither.io");
        Button playButtonSnake = new Button("Jouer Snake");
        Button optionsButton = new Button("Options");
        Button exitButton = new Button("Quitter");

        playButtonSlither.setOnAction(e -> {
            PlayPageSlither playPage = new PlayPageSlither(this,WITDH/2,HEIGHT/2);
            EngineSlither engine = EngineSlither.createGame(400);
            

            Scene gameScene = new Scene(playPage, WITDH, HEIGHT);

            KeyboardControler<Double,Angle> controler1 = new ControlerSlither(KeyCode.LEFT, KeyCode.RIGHT, KeyCode.SPACE);
            //KeyboardControler<Double,Angle> controler2 = new ControlerSlither(KeyCode.Q, KeyCode.D, KeyCode.Z);
            
            engine.addPlayer(controler1);
            //engine.addPlayer(controler2);
            engine.addBot();
            engine.addBot();
            engine.addBot();
            

            gameScene.setOnKeyPressed( ev -> {
                for (HumanPlayer p : engine.getPlayers()) {
                    p.keyPressed(ev);
                }
            });

            gameScene.setOnKeyReleased( ev -> {
                for (HumanPlayer p : engine.getPlayers()) {
                    p.keyReleased(ev);
                }
            });
            
            playPage.setEngine(engine);
            engine.addObserver(playPage);
            engine.notifyObservers();

            primaryStage.setScene(gameScene);
            primaryStage.show();

            playPage.setFocusTraversable(true);
            playPage.requestFocus();

            playPage.engine.run();
        });




        playButtonSnake.setOnAction(e -> {
            PlayPageSnake playPage = new PlayPageSnake(this,WITDH/2,HEIGHT/2);
            EngineSnake engine = EngineSnake.createGame(WITDH,HEIGHT);
            
            Scene gameScene = new Scene(playPage, WITDH, HEIGHT);
            gameScene.setOnKeyTyped(null);
            
            KeyboardControler<Integer,Direction> controler1 = new ControlerSnake(KeyCode.UP, KeyCode.DOWN, KeyCode.LEFT, KeyCode.RIGHT, KeyCode.ENTER);
            //KeyboardControler<Integer,Direction> controler2 = new ControlerSnake(KeyCode.Z, KeyCode.S, KeyCode.Q, KeyCode.D, KeyCode.SPACE);
            
            engine.addPlayer(controler1);
            //engine.addPlayer(controler2);
            engine.addBot();
            engine.addBot();
            engine.addBot();

            gameScene.setOnKeyPressed( ev -> {
                for (HumanPlayer p : engine.getPlayers()) {
                    p.keyPressed(ev);
                }
            });

            gameScene.setOnKeyReleased( ev -> {
                for (HumanPlayer p : engine.getPlayers()) {
                    p.keyReleased(ev);
                }
            });
            

            playPage.setEngine(engine);
            engine.addObserver(playPage);
            engine.notifyObservers();

            primaryStage.setScene(gameScene);
            primaryStage.show();

            playPage.setFocusTraversable(true);
            playPage.requestFocus();

            
            playPage.engine.run();
        });

        optionsButton.setOnAction(e -> {
            System.out.println("Afficher les options");
        });

        exitButton.setOnAction(e -> {
            primaryStage.close();
        });

        VBox layout = new VBox(20);
        layout.getChildren().addAll(playButtonSlither,playButtonSnake, optionsButton, exitButton);
        layout.setAlignment(Pos.CENTER);

        
        scene = new Scene(layout, WITDH, HEIGHT);
       
    }

    public Scene getScene() {
        return scene;
    }
    
}
