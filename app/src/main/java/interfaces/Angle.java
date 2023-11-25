package interfaces;

public interface Angle {
    
    public static double getPerpendicularAngleLeft(double angle) {
        if(angle < 0 || angle > 360){throw new IllegalArgumentException("Angle must be between 0 and 360");}
        return (angle + 90) % 360;
    }

    public static double getPerpendicularAngleRight(double angle) {
        if(angle < 0 || angle > 360){throw new IllegalArgumentException("Angle must be between 0 and 360");}
        return (angle - 90) % 360;
    }

    public static double getOppositeAngle(double angle) {
        if(angle < 0 || angle > 360){throw new IllegalArgumentException("Angle must be between 0 and 360");}
        return (angle + 180) % 360;
    }
}
