package GUI;

import controleur.KeyboardControler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.engine.EngineSlither;
import model.engine.EngineSnake;
import interfaces.Orientation.Angle;
import interfaces.Orientation.Direction;
import interfaces.Turnable.Turning;

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
            EngineSlither engine = (EngineSlither) EngineSlither.createSnake(2);
            

            Scene gameScene = new Scene(playPage, WITDH, HEIGHT);
            gameScene.setOnKeyPressed(ev ->{
                engine.makePressed(ev);
            });

            gameScene.setOnKeyReleased(ev ->{
                engine.makeReleased(ev);
            });

            KeyboardControler<Double,Angle> controler1 = (ev,snake) ->{
                switch(ev.getCode()){
                        case LEFT:
                            snake.setTurning(Turning.GO_LEFT);
                            break;
                        case RIGHT:
                            snake.setTurning(Turning.GO_RIGHT);
                            break;
                        default:break;
                    }
            };

            KeyboardControler<Double,Angle> controler2 = (ev,snake) ->{
                switch(ev.getCode()){
                        case A:
                            snake.setTurning(Turning.GO_LEFT);
                            break;
                        case E:
                            snake.setTurning(Turning.GO_RIGHT);
                            break;
                        default:break;
                    }
            };
            
            engine.addPlayer(controler1);
            engine.addPlayer(controler2);
            
            playPage.setCourt(engine);
            engine.addObserver(playPage);
            engine.notifyObservers();

            primaryStage.setScene(gameScene);
            primaryStage.show();

            playPage.setFocusTraversable(true);
            playPage.requestFocus();

            playPage.animate();
        });




        playButtonSnake.setOnAction(e -> {
            PlayPageSnake playPage = new PlayPageSnake(this,WITDH/2,HEIGHT/2);
            EngineSnake engine = EngineSnake.createSnake(WITDH,HEIGHT);
            
            Scene gameScene = new Scene(playPage, WITDH, HEIGHT);
            gameScene.setOnKeyTyped(null);
            gameScene.setOnKeyPressed(ev ->{
                engine.makePressed(ev);
            });
            
            KeyboardControler<Integer,Direction> controler = (ev,snake)-> {
                switch(ev.getCode()){
                        case LEFT:
                            if(snake.getDirection() != Direction.RIGHT && snake.getDirection() != Direction.LEFT){
                                if(snake.getDirection() == Direction.UP){
                                    snake.setTurning(Turning.GO_LEFT);
                                }
                                else{
                                    snake.setTurning(Turning.GO_RIGHT);
                                }
                            }
                            break;
                        case RIGHT:
                            if(snake.getDirection() != Direction.RIGHT && snake.getDirection() != Direction.LEFT){
                                if(snake.getDirection() == Direction.UP){
                                    snake.setTurning(Turning.GO_RIGHT);
                                }
                                else{
                                    snake.setTurning(Turning.GO_LEFT);
                                }
                            }
                            break;
                        case UP : if (snake.getDirection() != Direction.UP && snake.getDirection() != Direction.DOWN){
                                    if(snake.getDirection() == Direction.LEFT){
                                        snake.setTurning(Turning.GO_RIGHT);
                                    }
                                    else{
                                        snake.setTurning(Turning.GO_LEFT);
                                    }
                                }
                                break;
                        case DOWN:
                                if (snake.getDirection() != Direction.UP && snake.getDirection() != Direction.DOWN){
                                    if(snake.getDirection() == Direction.LEFT){
                                        snake.setTurning(Turning.GO_LEFT);
                                    }
                                    else{
                                        snake.setTurning(Turning.GO_RIGHT);
                                    }
                                }
                                break;
                        default:break;
                    }
            };
            KeyboardControler<Integer,Direction> controler1 = (ev,snake)-> {
                switch(ev.getCode()){
                        case Q:
                            if(snake.getDirection() != Direction.RIGHT && snake.getDirection() != Direction.LEFT){
                                if(snake.getDirection() == Direction.UP){
                                    snake.setTurning(Turning.GO_LEFT);
                                }
                                else{
                                    snake.setTurning(Turning.GO_RIGHT);
                                }
                            }
                            break;
                        case D:
                            if(snake.getDirection() != Direction.RIGHT && snake.getDirection() != Direction.LEFT){
                                if(snake.getDirection() == Direction.UP){
                                    snake.setTurning(Turning.GO_RIGHT);
                                }
                                else{
                                    snake.setTurning(Turning.GO_LEFT);
                                }
                            }
                            break;
                        case Z : if (snake.getDirection() != Direction.UP && snake.getDirection() != Direction.DOWN){
                                    if(snake.getDirection() == Direction.LEFT){
                                        snake.setTurning(Turning.GO_RIGHT);
                                    }
                                    else{
                                        snake.setTurning(Turning.GO_LEFT);
                                    }
                                }
                                break;
                        case S:
                                if (snake.getDirection() != Direction.UP && snake.getDirection() != Direction.DOWN){
                                    if(snake.getDirection() == Direction.LEFT){
                                        snake.setTurning(Turning.GO_LEFT);
                                    }
                                    else{
                                        snake.setTurning(Turning.GO_RIGHT);
                                    }
                                }
                                break;
                        default:break;
                    }
            };
            
            engine.addPlayer(controler);
            engine.addPlayer(controler1);
            

            playPage.setCourt(engine);
            engine.addObserver(playPage);
            engine.notifyObservers();

            primaryStage.setScene(gameScene);
            primaryStage.show();

            playPage.setFocusTraversable(true);
            playPage.requestFocus();

            
            playPage.animate();
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
