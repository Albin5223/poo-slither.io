package externData;

import javafx.scene.paint.Color;

public enum OurColors {
    RED, BLUE, GREEN, YELLOW, PURPLE, ORANGE, PINK, BROWN;

    public static OurColors getRandomColor(){
        int random = (int) (Math.random() * OurColors.values().length);
        return OurColors.values()[random];
    }

    public String toString(){
        return this.name().toLowerCase();
    }

    public Color toColorJavaFX(){
        switch(this){
            case RED:
                return Color.RED;
            case BLUE:
                return Color.BLUE;
            case GREEN:
                return Color.GREEN;
            case YELLOW:
                return Color.YELLOW;
            case PURPLE:
                return Color.PURPLE;
            case ORANGE:
                return Color.ORANGE;
            case PINK:
                return Color.PINK;
            case BROWN:
                return Color.BROWN;
            default:
                return Color.BLACK;
        }
    }
    
}
