package model.plateau;

import interfaces.GameBorder;
import interfaces.Orientation.Direction;
import model.coordinate.Coordinate;
import model.coordinate.CoordinateInteger;
import model.foods.FoodFactory;

import java.util.Random;

import configuration.ConfigurationFoodInteger;
import configuration.ConfigurationSnakeInteger;

public final class PlateauInteger extends Plateau<Integer,Direction>{

    /**
     * The border of the map in the Snake game.
     * <p>
     * The map is a rectangle with 4 points.
     */
    public static class BorderInteger implements GameBorder<Integer,Direction> {

        public final int xMin;
        public final int xMax;

        public final int yMin;
        public final int yMax;

        public final int snakeGap;
        public final int snakeSize;
        public final boolean isAlignWithSnake;

        /**
         * Constructs a new game border with the provided arguments.
         * @param xMin the minimum x value of the border
         * @param xMax the maximum x value of the border
         * @param yMin the minimum y value of the border
         * @param yMax the maximum y value of the border
         * @param snakeGap the snakes segments gap (to know where to put the center of the foods)
         * @param snakeSize the size of the snake (to know where to put the center of the foods)
         * @param isAlignWithSnake true if the foods should be aligned with the snake, false otherwise
         */
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

        /**
         * @return the area of the map
         */
        @Override
        public double getArea() {
            return (xMax - xMin) * (yMax - yMin);
        }

        /**
         * @param c the coordinate to check
         * @return true if the coordinate is inside the map, false otherwise
         */
        @Override
        public boolean isInside(Coordinate<Integer, Direction> c) {
            return c.getX() >= xMin && c.getX() <= xMax && c.getY() >= yMin && c.getY() <= yMax;
        }

        /**
         * @param c the coordinate to get the opposite of
         * @return the opposite coordinate of the given coordinate
         */
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

        /**
         * @return a random coordinate inside the map
         */
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

    private PlateauInteger(FoodFactory<Integer,Direction> foodFactory, ConfigurationSnakeInteger config, BorderInteger border) {
        super(foodFactory, config, border);
    }

    public static PlateauInteger createPlateauSnake(int width, int height, ConfigurationFoodInteger foodConfig, ConfigurationSnakeInteger snakeConfig){
        BorderInteger border = new BorderInteger(-width/2, width/2, -height/2,height/2, snakeConfig.getGapBetweenTail(), snakeConfig.getBirthHitboxRadius()*2, snakeConfig.isAlignWithSnake());
        foodConfig.setNbFood((int) (border.getArea() * foodConfig.getRatioOfFood() / foodConfig.getAverageFoodArea()));
        PlateauInteger plateau = new PlateauInteger(new FoodFactory<Integer,Direction>(foodConfig), snakeConfig, border);

        return plateau;
    }
}
