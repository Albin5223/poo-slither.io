package model.paquet;

import java.io.Serializable;

import interfaces.Orientation.Direction;
import model.coordinate.CoordinateInteger;
import model.plateau.SnakeInteger;
import model.skins.Skin;

public class PaquetSnake implements Serializable {

    
    private String message;
    private SnakeInteger snake;
    private Direction direction;
    private Skin skin;

    private PaquetSnake(String msg, SnakeInteger snake, Direction direction,Skin skin){
        message = msg;
        this.direction = direction;
        this.skin = skin;
        this.snake = snake;

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
    
    public static PaquetSnake createPaquetWithSkinAndMessage(Skin skin,String msg){
        return new PaquetSnake(msg, null, null, skin);
    }

    public static PaquetSnake createPaquetWithSnakeAndMessage(String msg, SnakeInteger snake){
        return new PaquetSnake(msg, snake, null, null);
    }




    
    
}
