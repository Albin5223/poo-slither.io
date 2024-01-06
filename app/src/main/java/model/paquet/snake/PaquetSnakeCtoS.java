package model.paquet.snake;

import java.io.Serializable;

import interfaces.Turnable;

public class PaquetSnakeCtoS implements Serializable {
    
    private Turnable.Turning turning;
    private boolean boost = false;
    private boolean quit = false;

    public Turnable.Turning getTurning() {return turning;}
    public boolean isBoost() {return boost;}
    public boolean isQuit() {return quit;}

    public PaquetSnakeCtoS(Turnable.Turning turning, boolean boost, boolean quit) {
        this.turning = turning;
        this.boost = boost;
        this.quit = quit;
    }
}
