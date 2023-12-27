package GUI;

public enum OurColors {
    RED, BLUE, GREEN, YELLOW, PURPLE, ORANGE, PINK, BROWN;

    public static OurColors getRandomColor(){
        int random = (int) (Math.random() * OurColors.values().length);
        return OurColors.values()[random];
    }

    public static String toString(OurColors color){
        return color.toString().toLowerCase();
    }
    
}
