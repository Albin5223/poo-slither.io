package interfaces;

import model.Snake.SnakePart.Direction;

public interface Turnable {

    enum Turning {
        GO_LEFT, FORWARD, GO_RIGHT;
    
        public static Turning randomDirection(){
            int random = (int) (Math.random() * Turning.values().length);
            return Turning.values()[random];
        }
    }

    public Direction turn(Turning turning, Direction initialDirection);
}