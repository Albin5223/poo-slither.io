package model.plateau;

import interfaces.GameBorder;
import interfaces.Orientation.Direction;
import model.coordinate.Coordinate;
import model.coordinate.CoordinateInteger;

import java.util.Random;

public final class PlateauInteger extends Plateau<Integer,Direction>{

    public class BorderInteger implements GameBorder<Integer,Direction> {

        private static final boolean IS_ALIGN_WITH_SNAKE = true;

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

        public int getxMin() {
            return xMin;
        }
        public int getxMax() {
            return xMax;
        }
        public int getyMin() {
            return yMin;
        }
        public int getyMax() {
            return yMax;
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

        private Coordinate<Integer, Direction> getRandomCoordinateAlignWithSnake(){
            int stepSize = SnakeInteger.SNAKE_GAP_BETWEEN_TAIL;

            int xRange = (xMax - xMin) / stepSize;
            int yRange = (yMax - yMin) / stepSize;

            int x = (new Random().nextInt(xRange) * stepSize) + xMin;
            int y = (new Random().nextInt(yRange) * stepSize) + yMin;
            return new CoordinateInteger(x,y);
        }

        private Coordinate<Integer, Direction> getRandomCoordinateNotAlignWithSnake(){
            int x = (new Random().nextInt(xMax - xMin) + xMin);
            int y = (new Random().nextInt(yMax - yMin) + yMin);
            return new CoordinateInteger(x,y);
        }

        @Override
        public Coordinate<Integer, Direction> getRandomCoordinate() {
            if(IS_ALIGN_WITH_SNAKE){
                return getRandomCoordinateAlignWithSnake();
            }
            else{
                return getRandomCoordinateNotAlignWithSnake();
            }
        }
    }

    private final static int NB_FOOD = 50;

    public PlateauInteger(int width, int height) {
        super(NB_FOOD);
        this.border = new BorderInteger(
            -4*width/(SnakeInteger.SNAKE_BIRTH_HITBOX_RADIUS),
            4*width/(SnakeInteger.SNAKE_BIRTH_HITBOX_RADIUS), 
            -4*height/(SnakeInteger.SNAKE_BIRTH_HITBOX_RADIUS), 
            4*height/(SnakeInteger.SNAKE_BIRTH_HITBOX_RADIUS)
        );
    }

    public static PlateauInteger createPlateauSnake(int width, int height){
        PlateauInteger plateau = new PlateauInteger(width,height);
        plateau.addAllFood();
        return plateau;
    }
}
