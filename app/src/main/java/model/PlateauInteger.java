package model;

import interfaces.Orientation.Direction;

public final class PlateauInteger extends Plateau<Integer,Direction>{

    public PlateauInteger() {
        super();
    }

    public static PlateauInteger createPlateauSnake(){
        PlateauInteger plateau = new PlateauInteger();
        plateau.addAllFood();
        return plateau;
    }

    @Override
    public void addAllFood() {
        for(int i = 0; i < 10; i++){
            addFood(new CoordinateInteger((int)Math.random()*20,(int)Math.random()*20),Commestible.getRandom());
        }
    }
    
}
