package model.paquet;

import java.io.Serializable;

import interfaces.Orientation.Direction;
import model.skins.Skin;

public class  PaquetSnake implements Serializable {

    
    private String message;
    private FrameSnake frame;
    private Direction direction;
    private Skin skin;

    private PaquetSnake(String msg, FrameSnake frame, Direction direction,Skin skin){
        message = msg;
        this.frame = frame;
        this.direction = direction;
        this.skin = skin;
    }

    public static PaquetSnake createPaquetWithDirection(Direction direction){
        return new PaquetSnake("", null, direction,null);
    }

    public String getMessage() {
        return message;
    }

    public Direction getDirection() {
        return direction;
    }

    public FrameSnake getFrame() {
        return frame;
    }

    public Skin getSkin() {
        return skin;
    }

    public static PaquetSnake createPaquetWithFrame(FrameSnake frame){
        return new PaquetSnake("", frame, null,null);
    }

    public static PaquetSnake createPaquetWithMessage(String msg){
        return new PaquetSnake(msg, null, null,null);
    }

    public static PaquetSnake createPaquetWithSkin(Skin skin){
        return new PaquetSnake(null, null, null, skin);
    }




    
    
}
