package model;

import java.util.Random;

import interfaces.Orientation.Angle;

public final class PlateauDouble extends Plateau<Double,Angle>{

    public PlateauDouble() {
        super();
    }

    public static PlateauDouble createPlateauSlitherio(){
        PlateauDouble plateau = new PlateauDouble();
        plateau.addAllFood();
        return plateau;
    }

    @Override
    public void addAllFood() {
        for(int i = 0; i < 10; i++){
            int r = new Random().nextInt(2);
            double x = r == 1 ? new Random().nextInt(200) : -1*new Random().nextInt(200);
            r = new Random().nextInt(2);
            double y = r == 1 ? new Random().nextInt(200) : -1*new Random().nextInt(200);
            addFood(new CoordinateDouble(x,y),Commestible.getRandom());
        }
    }
    
}
