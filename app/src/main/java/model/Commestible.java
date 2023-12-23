package model;

import java.util.Random;

public enum Commestible {
    BIG_FOOD(10, 10,true),
    SMALL_FOOD(5, 5,true),
    DEATH_FOOD(3,3,false);

    private int value;
    private int range;
    private boolean respawn;

    private Commestible(int value, int range,boolean respawn) {
        this.value = value;
        this.range = range;
        this.respawn = respawn;
    }

    public int getValue() {
        return value;
    }
    public int getRange() {
        return range;
    }
    public boolean getRespawn() {
        return respawn;
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
