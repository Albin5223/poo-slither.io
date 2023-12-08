package model.plateau;

import java.util.Random;

import interfaces.Coordinate;
import interfaces.Orientation.Angle;
import model.Commestible;
import model.coordinate.CoordinateDouble;

public final class PlateauDouble extends Plateau<Double,Angle>{

    public PlateauDouble() {
        super();
    }

    public static PlateauDouble createPlateauSlitherio(){
        PlateauDouble plateau = new PlateauDouble();
        plateau.addAllFood();
        return plateau;
    }


    public void addOneFood(){
        int r = new Random().nextInt(2);
        double x = r == 1 ? new Random().nextInt(400) : -1*new Random().nextInt(400);
        r = new Random().nextInt(2);
        double y = r == 1 ? new Random().nextInt(400) : -1*new Random().nextInt(400);
        addFood(new CoordinateDouble(x,y),Commestible.getRandom());
    }

    @Override
    public void addAllFood() {
        for(int i = 0; i < 100; i++){
            addOneFood();
        }
    }

    @Override
    public int isCollidingWithFood(Snake<Double, Angle> snake) {
        for(Coordinate<Double, Angle> c : nourritures.keySet()){
            double distance = c.distanceTo(snake.getHead().getCenter());
            if(distance<=snake.getRadius()+nourritures.get(c).getRange()){
                int value = nourritures.get(c).getValue();
                if(nourritures.get(c).getRespawn()){
                    nourritures.remove(c);
                    return value;
                }
                nourritures.remove(c);
                addOneFood();
                return value;
            }
        }
        return -1;
    }

    
    
}
