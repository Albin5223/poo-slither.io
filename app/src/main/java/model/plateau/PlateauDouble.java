package model.plateau;

import java.util.Random;

import interfaces.Coordinate;
import interfaces.Orientation.Angle;
import model.coordinate.CoordinateDouble;

public final class PlateauDouble extends Plateau<Double,Angle>{

    private final static int NB_FOOD = 100;

    public PlateauDouble() {
        super(NB_FOOD);
    }

    public static PlateauDouble createPlateauSlitherio(){
        PlateauDouble plateau = new PlateauDouble();
        plateau.addAllFood();
        return plateau;
    }

    @Override
    public CoordinateDouble getRandomCoordinate() {
        int bound = 300;
        int r = new Random().nextInt(2);
        double x = r == 1 ? new Random().nextInt(bound) : -1*new Random().nextInt(bound);
        r = new Random().nextInt(2);
        double y = r == 1 ? new Random().nextInt(bound) : -1*new Random().nextInt(bound);
        return new CoordinateDouble(x,y);
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
