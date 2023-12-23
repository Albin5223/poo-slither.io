package model.plateau;

import java.util.Random;

import interfaces.Coordinate;
import interfaces.GameBorder;
import interfaces.Orientation.Angle;
import model.coordinate.CoordinateDouble;

public final class PlateauDouble extends Plateau<Double,Angle>{

    public class BorderDouble implements GameBorder<Double,Angle> {

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
            int bound = 300;
            int r = new Random().nextInt(2);
            double x = r == 1 ? new Random().nextInt(bound) : -1*new Random().nextInt(bound);
            r = new Random().nextInt(2);
            double y = r == 1 ? new Random().nextInt(bound) : -1*new Random().nextInt(bound);
            return new CoordinateDouble(x,y);
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
