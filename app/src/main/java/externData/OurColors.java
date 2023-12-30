package externData;

import javafx.scene.paint.Color;

public enum OurColors {
    RED, BLUE, GREEN, YELLOW, PURPLE, ORANGE, PINK, BROWN, GRAY, WHITE;

    public static OurColors getRandomColor(){
        int random = (int) (Math.random() * OurColors.values().length);
        return OurColors.values()[random];
    }

    @Override
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
            case GRAY:
                return Color.GRAY;
            case WHITE:
                return Color.WHITE;
            default:
                return Color.BLACK;
        }
    }
    
}
