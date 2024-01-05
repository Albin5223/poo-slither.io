package model.paquet;

import java.io.Serializable;

import interfaces.Turnable;
import interfaces.Orientation.Direction;
import model.plateau.SnakeInteger;
import model.skins.Skin;

public class PaquetSnake implements Serializable {



    private boolean quit = false;
    private String message;
    private SnakeInteger snake;
    private Direction direction;
    private Skin skin;
    private Turnable.Turning turning;

    private PaquetSnake(String msg, SnakeInteger snake, Direction direction,Skin skin){
        message = msg;
        this.direction = direction;
        this.skin = skin;
        this.snake = snake;
        turning = null;
    }


    public void affiche(){
        System.out.println("Message : " + message);
        System.out.println("Direction : " + direction);
        System.out.println("Snake : " + snake);
        System.out.println("Skin : " + skin);
    }

    private PaquetSnake(boolean quit){
        this.quit = quit;
    }

    public boolean isQuit() {
        return quit;
    }

    public static PaquetSnake createPaquetToQuit(){
        return new PaquetSnake(true);
    }

    private PaquetSnake(Turnable.Turning turning){
        this.turning = turning;
    }

    public Turnable.Turning getTurning() {
        return turning;
    }

    public static PaquetSnake createPaquetWithTurning(Turnable.Turning turning){
        return new PaquetSnake(turning);
    }

    public static PaquetSnake createPaquetWithDirection(Direction direction){
        return new PaquetSnake("", null,direction,null);
    }

    public String getMessage() {
        return message;
    }

    public Direction getDirection() {
        return direction;
    }

    public SnakeInteger getSnake() {
        return snake;
    }

    public Skin getSkin() {
        return skin;
    }

    public static PaquetSnake createPaquetWithSnake(SnakeInteger snake){
        return new PaquetSnake("", snake, null,null);
    }

    public static PaquetSnake createPaquetWithMessage(String msg){
        return new PaquetSnake(msg, null, null,null);
    }

    public static PaquetSnake createPaquetWithSkin(Skin skin){
        return new PaquetSnake(null, null, null, skin);
    }
    
    public static PaquetSnake createPaquetWithMessageAndSkin(String msg,Skin skin){
        return new PaquetSnake(msg, null, null, skin);
    }

    public static PaquetSnake createPaquetWithSnakeAndMessage(String msg, SnakeInteger snake){
        return new PaquetSnake(msg, snake, null, null);
    }




    
    
}
