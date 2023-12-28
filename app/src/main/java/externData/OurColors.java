package externData;

public enum OurColors {
    RED, BLUE, GREEN, YELLOW, PURPLE, ORANGE, PINK, BROWN;

    public static OurColors getRandomColor(){
        int random = (int) (Math.random() * OurColors.values().length);
        return OurColors.values()[random];
    }

    public String toString(){
        return this.name().toLowerCase();
    }
    
}
