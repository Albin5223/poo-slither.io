package model;

import java.util.Random;

public enum Commestible {
    BIG_FOOD(5, 10),
    SMALL_FOOD(1, 5);

    private int value;
    private int range;

    private Commestible(int value, int range) {
        this.value = value;
        this.range = range;
    }

    public int getValue() {
        return value;
    }
    public int getRange() {
        return range;
    }

    public static Commestible getRandom() {
        int r = new Random().nextInt(10);
        if(r<7){
            return SMALL_FOOD;
        }
        else{
            return BIG_FOOD;
        }
    }


}
