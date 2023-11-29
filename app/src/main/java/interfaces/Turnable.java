package interfaces;

public interface Turnable<O extends Orientation> {

    enum Turning {
        GO_LEFT, FORWARD, GO_RIGHT;
    
        public static Turning randomDirection(){
            int random = (int) (Math.random() * Turning.values().length);
            return Turning.values()[random];
        }
    }

    public O turn(Turning turning, O initialDirection);
}