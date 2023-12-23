package model.plateau;

import java.util.Random;

import interfaces.Coordinate;
import interfaces.GameBorder;
import interfaces.Orientation.Angle;
import model.coordinate.CoordinateDouble;

public final class PlateauDouble extends Plateau<Double,Angle>{

    public class BorderDouble implements GameBorder<Double,Angle> {

        /* The ratio of the random coordinate to be inside the map */
        private static final double RANDOM_RATIO = 0.9;

        private CoordinateDouble map_center;
        private double map_radius;

        public BorderDouble(CoordinateDouble map_center, double map_radius) {
            this.map_center = map_center.clone();
            this.map_radius = map_radius;
        }

        @Override
        public boolean isInside(Coordinate<Double, Angle> c) {
            double distance = c.distanceTo(map_center);
            return distance <= map_radius;
        }

        @Override
        public Coordinate<Double, Angle> getOpposite(Coordinate<Double, Angle> c) {
            double x = c.getX();
            double y = c.getY();
            double dx = x - map_center.getX();
            double dy = y - map_center.getY();
            double x2 = map_center.getX() - dx;
            double y2 = map_center.getY() - dy;
            return new CoordinateDouble(x2, y2);
        }

        @Override
        public Coordinate<Double, Angle> getRandomCoordinate() {
            double angle = new Random().nextDouble() * 2 * Math.PI;
            double radius = map_radius * Math.sqrt(new Random().nextDouble()) * RANDOM_RATIO;
            double x = map_center.getX() + radius * Math.cos(angle);
            double y = map_center.getY() + radius * Math.sin(angle);
            return new CoordinateDouble(x, y);
        }

        public CoordinateDouble getCenter() {
            return map_center;
        }

        public double getRadius() {
            return map_radius;
        }
    }

    private final static int NB_FOOD = 100;

    public PlateauDouble() {
        super(NB_FOOD);
        this.border = new BorderDouble(new CoordinateDouble(0,0), 400);
    }

    public static PlateauDouble createPlateauSlitherio(){
        PlateauDouble plateau = new PlateauDouble();
        plateau.addAllFood();
        return plateau;
    }

    @Override
    public int isCollidingWithFood(Snake<Double, Angle> snake) {
        for(Coordinate<Double, Angle> c : nourritures.keySet()){
            double distance = c.distanceTo(snake.getHead().getCenter());
            if(distance<=snake.getRadius()+nourritures.get(c).getRange()){
                int value = nourritures.get(c).getValue();
                if(!nourritures.get(c).getRespawn()){
                    removeFood(c);
                    return value;
                }
                removeFood(c);
                addOneFood();
                return value;
            }
        }
        return -1;
    }    
}
