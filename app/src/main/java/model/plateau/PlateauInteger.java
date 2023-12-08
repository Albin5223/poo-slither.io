package model.plateau;

import interfaces.Coordinate;
import interfaces.Orientation.Direction;
import model.Commestible;
import model.coordinate.CoordinateInteger;

import java.util.Random;

public final class PlateauInteger extends Plateau<Integer,Direction>{

    private int xMin;
    private int xMax;

    private int yMin;
    private int yMax;
    public PlateauInteger(int width, int height) {
        super();

        xMin = -1*width/(2*SnakeInteger.WIDTH_OF_SNAKE); 
        xMax = width/(2*SnakeInteger.WIDTH_OF_SNAKE);

        yMin = -1*height/(2*SnakeInteger.WIDTH_OF_SNAKE);
        yMax = height/(2*SnakeInteger.WIDTH_OF_SNAKE);
    }

    public static PlateauInteger createPlateauSnake(int width, int height){
        PlateauInteger plateau = new PlateauInteger(width,height);
        plateau.addAllFood();
        return plateau;
    }

    public void addOneFood(){
        int r = new Random().nextInt(2);
        int x = r == 1 ? new Random().nextInt(xMax) : -1*new Random().nextInt(xMax);
        r = new Random().nextInt(2);
        int y = r == 1 ? new Random().nextInt(yMax) : -1*new Random().nextInt(yMax);
        try {
            addFood(new CoordinateInteger(x,y),Commestible.getRandom());
        } catch (IllegalArgumentException e) {
            //Si la nourriture est présente alors on ne fait rien
        }
    }

    @Override
    public void addAllFood() {
        for(int i = 0; i < 50; i++){
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

    public boolean isCollidingWithWall(Snake<Integer, Direction> snake){
        Coordinate<Integer, Direction> c = snake.getHead().getCenter();
        return c.getX() < xMin || c.getX() > xMax || c.getY() < yMin || c.getY() > yMax;
    }
    
}
