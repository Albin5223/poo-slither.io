package model.plateau;

import interfaces.GameBorder;
import interfaces.Orientation.Direction;
import model.coordinate.Coordinate;
import model.coordinate.CoordinateInteger;
import model.foods.FoodFactoryInteger;

import java.io.Serializable;
import java.util.Random;

import configuration.ConfigurationSnakeInteger;

public final class PlateauInteger extends Plateau<Integer,Direction>{

    public static class BorderInteger implements GameBorder<Integer,Direction>,Serializable {

        public final int xMin;
        public final int xMax;

        public final int yMin;
        public final int yMax;

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
            int stepSize = ConfigurationSnakeInteger.SNAKE_GAP_BETWEEN_TAIL;

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
            if(ConfigurationSnakeInteger.IS_ALIGN_WITH_SNAKE){
                return getRandomCoordinateAlignWithSnake();
            }
            else{
                return getRandomCoordinateNotAlignWithSnake();
            }
        }

        /**
         * @return the center of the map
         */
        public CoordinateInteger getCenter(){
            return new CoordinateInteger((xMax + xMin)/2, (yMax + yMin)/2);
        }

        /**
         * @return the minimum radius to display the whole map on the screen (considering the center of the map is the center of the screen)
         */
        public int getMinRadius(){
            int halfWidth = (xMax - xMin) / 2;
            int halfHeight = (yMax - yMin) / 2;
            return (int) Math.sqrt(halfHeight*halfHeight + halfWidth*halfWidth);
        }
    }

    private final static int NB_FOOD = 50;

    public PlateauInteger(int nbFood, FoodFactoryInteger foodFactory, BorderInteger border) {
        super(nbFood, foodFactory, border);
    }

    public static PlateauInteger createPlateauSnake(int width, int height){
        BorderInteger border = new BorderInteger(
            -width/2, 
            width/2, 
            -height/2,
            height/2
        );

        PlateauInteger plateau = new PlateauInteger(NB_FOOD, FoodFactoryInteger.build(), border);
        return plateau;
    }
}
