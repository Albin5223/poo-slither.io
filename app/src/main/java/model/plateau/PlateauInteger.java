package model.plateau;

import interfaces.ConfigurationFood;
import interfaces.GameBorder;
import interfaces.Orientation.Direction;
import model.coordinate.Coordinate;
import model.coordinate.CoordinateInteger;
import model.foods.FoodFactory;

import java.util.Random;

import configuration.ConfigurationFoodInteger;
import configuration.ConfigurationSnakeInteger;

public final class PlateauInteger extends Plateau<Integer,Direction>{

    public static class BorderInteger implements GameBorder<Integer,Direction> {

        public final int xMin;
        public final int xMax;

        public final int yMin;
        public final int yMax;

        public final int snakeGap;
        public final int snakeSize;
        public final boolean isAlignWithSnake;

        public BorderInteger(int xMin, int xMax, int yMin, int yMax, int snakeGap, int snakeSize, boolean isAlignWithSnake) {
            this.xMin = xMin;
            this.xMax = xMax;

            this.yMin = yMin;
            this.yMax = yMax;

            this.snakeGap = snakeGap;
            this.snakeSize = snakeSize;
            this.isAlignWithSnake = isAlignWithSnake;
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
        public double getArea() {
            return (xMax - xMin) * (yMax - yMin);
        }

        @Override
        public boolean isInside(Coordinate<Integer, Direction> c) {
            return c.getX() >= xMin && c.getX() <= xMax && c.getY() >= yMin && c.getY() <= yMax;
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
            int xRange = (xMax - xMin) / snakeGap;
            int yRange = (yMax - yMin) / snakeGap;

            int x = (new Random().nextInt(xRange) * snakeGap) + xMin;
            int y = (new Random().nextInt(yRange) * snakeGap) + yMin;
            return new CoordinateInteger(x,y);
        }

        private Coordinate<Integer, Direction> getRandomCoordinateNotAlignWithSnake(){
            int x = (new Random().nextInt(xMax - xMin) + xMin);
            int y = (new Random().nextInt(yMax - yMin) + yMin);
            return new CoordinateInteger(x,y);
        }

        @Override
        public Coordinate<Integer, Direction> getRandomCoordinate() {
            if(isAlignWithSnake){
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

    public PlateauInteger(FoodFactory<Integer,Direction> foodFactory, ConfigurationSnakeInteger config, BorderInteger border) {
        super(foodFactory, config, border);
    }

    public static PlateauInteger createPlateauSnake(int width, int height, ConfigurationFoodInteger foodConfig, ConfigurationSnakeInteger snakeConfig){
        BorderInteger border = new BorderInteger(-width/2, width/2, -height/2,height/2, snakeConfig.getGapBetweenTail(), snakeConfig.getBirthHitboxRadius()*2, snakeConfig.isAlignWithSnake());
        foodConfig.setNbFood((int) (border.getArea() * ConfigurationFood.RATIO_OF_FOOD  / foodConfig.getAverageFoodArea()));
        PlateauInteger plateau = new PlateauInteger(new FoodFactory<Integer,Direction>(foodConfig), snakeConfig, border);

        return plateau;
    }
}
