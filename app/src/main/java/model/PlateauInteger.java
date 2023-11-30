package model;

import interfaces.Coordinate;
import interfaces.Orientation.Direction;

import java.util.Random;

public final class PlateauInteger extends Plateau<Integer,Direction>{

    public PlateauInteger() {
        super();
    }

    public static PlateauInteger createPlateauSnake(){
        PlateauInteger plateau = new PlateauInteger();
        plateau.addAllFood();
        return plateau;
    }

    public void addOneFood(){
        int r = new Random().nextInt(2);
        int x = r == 1 ? new Random().nextInt(200) : -1*new Random().nextInt(200);
        r = new Random().nextInt(2);
        int y = r == 1 ? new Random().nextInt(200) : -1*new Random().nextInt(200);
        addFood(new CoordinateInteger(x,y),Commestible.getRandom());
    }

    @Override
    public void addAllFood() {
        for(int i = 0; i < 10; i++){
            addOneFood();
        }
    }

    @Override
    public int isCollidingWithFood(Snake<Integer, Direction> snake) {
        for(Coordinate<Integer, Direction> c : nourritures.keySet()){
            if(c.equals(snake.getHead().getCenter())){
                int value = nourritures.get(c).getValue();
                nourritures.remove(c);
                addOneFood();
                return value;
            }
        }
        return -1;
    }
    
}
