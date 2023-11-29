package interfaces;

import interfaces.Turnable.Turning;

public sealed interface Orientation {

    public Orientation opposite();
    
    public final class Angle implements Orientation {

        private double angle;

        public Angle(double angle) {
            this.angle = angle;
        }

        public double getAngle() {
            return this.angle;
        }
    
        @Override
        public Angle opposite() {
            if(this.angle < 0 || this.angle > 360){throw new IllegalArgumentException("Angle must be between 0 and 360");}
            return new Angle((this.angle + 180) % 360);
        }

        public Angle changeAngleWithTurn(Turning turning, Angle step) {
            switch (turning) {
                case GO_LEFT : return new Angle((this.angle - step.getAngle()) % 360);
                case GO_RIGHT : return new Angle((this.angle + step.getAngle()) % 360);
                default :return this;
            }       
        }
    }

    public static enum Direction implements Orientation {LEFT,UP,RIGHT,DOWN;
        public Direction changeDirectionWithTurn(Turning turning){
            switch (turning) {
                case GO_LEFT : if (this.ordinal() == 0) return Direction.values()[Direction.values().length - 1];
                                else return Direction.values()[this.ordinal() - 1];
                case GO_RIGHT : return Direction.values()[(this.ordinal() + 1) % Direction.values().length];
                default :return this;
            }       
        }

        @Override
        public Direction opposite() {
            switch (this) {
                case UP: return DOWN;   
                case DOWN: return UP;
                case LEFT: return RIGHT;
                case RIGHT: return LEFT;
                default:
                    throw new IllegalArgumentException("Unexpected value: " + this);
            }
        }    
    }

}