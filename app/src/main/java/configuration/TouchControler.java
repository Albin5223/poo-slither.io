package configuration;

import java.util.HashMap;

import javafx.scene.input.KeyCode;

public class TouchControler {


    HashMap<DirectionOfTouch, KeyCode> touchControl = new HashMap<>();
    private static int number;
    private int id;

    private static KeyCode[][] defaultCode = 
    {
        {KeyCode.UP, KeyCode.DOWN, KeyCode.LEFT, KeyCode.RIGHT, KeyCode.SPACE},
        {KeyCode.Z, KeyCode.S, KeyCode.Q, KeyCode.D, KeyCode.SHIFT},
    };

    public static enum DirectionOfTouch{
        UP,
        DOWN,
        LEFT,
        RIGHT,
        BOOST;
    }

    public TouchControler(){
        this.id = number;
        number++;
        for(int i = 0; i < DirectionOfTouch.values().length; i++){
            touchControl.put(DirectionOfTouch.values()[i], defaultCode[id][i]);
        }
    }


    public void updateTouchControl(DirectionOfTouch direction, KeyCode keyCode){
        touchControl.remove(direction, touchControl.get(direction));
        touchControl.put(direction, keyCode);
    }

    public KeyCode getKeyCode(DirectionOfTouch direction){
        return touchControl.get(direction);
    }


    public static void resetNumber(){
        number = 0;
    }


    
}
