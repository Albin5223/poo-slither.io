package model.plateau;

import interfaces.Coordinate;
import interfaces.Orientation.Direction;
import model.coordinate.CoordinateInteger;

import java.util.Random;

import exceptions.ExceptionCollisionWithSnake;

public final class PlateauInteger extends Plateau<Integer,Direction>{

    private int xMin;
    private int xMax;

    private int yMin;
    private int yMax;

    private final static int NB_FOOD = 50;

    public PlateauInteger(int width, int height) {
        super(NB_FOOD);

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

    @Override
    public void addSnake(Snake<Integer, Direction> snake) throws ExceptionCollisionWithSnake {
        super.addSnake(snake);
    }

    @Override
    public CoordinateInteger getRandomCoordinate(){
        int x = new Random().nextInt(xMax - xMin) + xMin;
        int y = new Random().nextInt(yMax - yMin) + yMin;
        return new CoordinateInteger(x,y);
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
