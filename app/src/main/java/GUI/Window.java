package GUI;

import controleur.SnakeControler;
import exceptions.ExecptionAddSnake;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.CoordinateDouble;
import model.EngineSlither;
import model.EngineSnake;
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
                switch(ev.getCode()){
                    case LEFT:
                        engine.getSnakes()[0].setTurning(Turning.GO_LEFT);
                        break;
                    case RIGHT:
                        engine.getSnakes()[0].setTurning(Turning.GO_RIGHT);
                        break;
                    case A:
                        engine.getSnakes()[1].setTurning(Turning.GO_LEFT);
                        break;
                    case E:
                        engine.getSnakes()[1].setTurning(Turning.GO_RIGHT);
                        break;
                    case SPACE:
                            playPage.stopAnimate(); break;
                    default: break;
                }
            });

            gameScene.setOnKeyReleased(ev ->{
                switch(ev.getCode()){
                    case LEFT:
                        engine.getSnakes()[0].setTurning(Turning.FORWARD);
                        break;
                    case RIGHT:
                        engine.getSnakes()[0].setTurning(Turning.FORWARD);
                        break;
                    case A:
                        engine.getSnakes()[1].setTurning(Turning.FORWARD);
                        break;
                    case E:
                        engine.getSnakes()[1].setTurning(Turning.FORWARD);
                        break;
                    case SPACE:
                            playPage.animate(); break;
                    default: break;
                }
            });

            
            //KeyboardControler keyboardControler = new KeyboardControler(engine);
            try {
                engine.addPlayerWithCoord(new CoordinateDouble(30, 30));
                engine.getSnakes()[0].grow(20);
                engine.addPlayerWithCoord(new CoordinateDouble(-30, -30));
                engine.getSnakes()[1].grow(55);
            } catch (ExecptionAddSnake e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }

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
            EngineSnake engine = EngineSnake.createSnake(1,WITDH,HEIGHT);
            

            Scene gameScene = new Scene(playPage, WITDH, HEIGHT);
            gameScene.setOnKeyTyped(null);
            gameScene.setOnKeyPressed(ev ->{
                engine.makeMouv(ev);
            });
            
            SnakeControler controler = (ev,snake)-> {
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
            try {
                engine.addPlayer(controler);
            } catch (ExecptionAddSnake e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }

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
