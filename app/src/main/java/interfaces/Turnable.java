package interfaces;

public interface Turnable {

    enum Turning {
        LEFT, FORWARD, RIGHT;
    
        public static Turning randomDirection(){
            int random = (int) (Math.random() * Turning.values().length);
            return Turning.values()[random];
        }
    }

    public double turn(Turning turning, double initialAngle);
}