package model.plateau;

import interfaces.GameBorder;
import interfaces.Coordinate;
import interfaces.Orientation.Direction;
import model.coordinate.CoordinateInteger;

import java.util.Random;

import exceptions.ExceptionCollisionWithSnake;

public final class PlateauInteger extends Plateau<Integer,Direction>{

    public class BorderInteger implements GameBorder<Integer,Direction> {

        private int xMin;
        private int xMax;

        private int yMin;
        private int yMax;

        public BorderInteger(int xMin, int xMax, int yMin, int yMax) {
            this.xMin = xMin;
            this.xMax = xMax;

            this.yMin = yMin;
            this.yMax = yMax;
        }

        @Override
        public boolean isInside(Coordinate<Integer, Direction> c) {
            return c.getX() > xMin && c.getX() < xMax && c.getY() > yMin && c.getY() < yMax;
        }

        @Override
        public Coordinate<Integer, Direction> getOpposite(Coordinate<Integer, Direction> c) {
            int x = c.getX();
            int y = c.getY();
            if(x < xMin){
                x = xMax;
            }
            else if(x > xMax){
                x = xMin;
            }
            if(y < yMin){
                y = yMax;
            }
            else if(y > yMax){
                y = yMin;
            }
            return new CoordinateInteger(x,y);
        }

        @Override
        public Coordinate<Integer, Direction> getRandomCoordinate() {
            int x = new Random().nextInt(xMax - xMin) + xMin;
            int y = new Random().nextInt(yMax - yMin) + yMin;
            return new CoordinateInteger(x,y);
        }
    }

    private final static int NB_FOOD = 50;

    public PlateauInteger(int width, int height) {
        super(NB_FOOD);
        this.border = new BorderInteger(
            -1*width/(2*SnakeInteger.WIDTH_OF_SNAKE),
            width/(2*SnakeInteger.WIDTH_OF_SNAKE), 
            -1*height/(2*SnakeInteger.WIDTH_OF_SNAKE), 
            height/(2*SnakeInteger.WIDTH_OF_SNAKE)
        );
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
