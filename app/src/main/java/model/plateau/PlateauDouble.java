package model.plateau;

import java.util.Random;

import interfaces.GameBorder;
import interfaces.Orientation.Angle;
import model.coordinate.Coordinate;
import model.coordinate.CoordinateDouble;
import model.foods.FoodFactory;
import configuration.ConfigurationFoodDouble;
import configuration.ConfigurationSnakeDouble;

public final class PlateauDouble extends Plateau<Double,Angle>{

    /**
     * The border of the map in the Slither.io game.
     * <p>
     * The map is a circle with a center and a radius.
     */
    public static class BorderDouble implements GameBorder<Double,Angle> {

        public final CoordinateDouble map_center;
        public final double map_radius;

        public BorderDouble(CoordinateDouble map_center, double map_radius) {
            this.map_center = map_center.clone();
            this.map_radius = map_radius;
        }

        /**
         * @param c the coordinate to check
         * @return true if the coordinate is inside the map, false otherwise
         */
        @Override
        public boolean isInside(Coordinate<Double, Angle> c) {
            double distance = c.distanceTo(map_center);
            return distance <= map_radius;
        }

        /**
         * @return the area of the map
         */
        @Override
        public double getArea() {
            return Math.PI * map_radius * map_radius;
        }

        /**
         * @param c the coordinate to get the opposite of
         * @return the opposite coordinate of the given coordinate
         */
        @Override
        public Coordinate<Double, Angle> getOpposite(Coordinate<Double, Angle> c) {
            double x = c.getX();
            double y = c.getY();
            double dx = x - map_center.getX();
            double dy = y - map_center.getY();
            double x2 = map_center.getX() - dx;
            double y2 = map_center.getY() - dy;
            return new CoordinateDouble(x2, y2);
        }

        /**
         * @return a random coordinate inside the map
         */
        @Override
        public Coordinate<Double, Angle> getRandomCoordinate() {
            double angle = new Random().nextDouble() * 2 * Math.PI;
            double radius = map_radius * Math.sqrt(new Random().nextDouble()) * 0.95;
            double x = map_center.getX() + radius * Math.cos(angle);
            double y = map_center.getY() + radius * Math.sin(angle);
            return new CoordinateDouble(x, y);
        }

        public CoordinateDouble getCenter() {
            return map_center;
        }

        public double getRadius() {
            return map_radius;
        }
    }

    /**
     * Create a new board with the given food factory, snake configuration and border.
     * @param foodFactory the factory to create food
     * @param config the configuration of the snake
     * @param border the border of the map
     */
    private PlateauDouble(FoodFactory<Double,Angle> foodFactory, ConfigurationSnakeDouble config, BorderDouble border) {
        super(foodFactory, config, border);
    }

    /**
     * Create a new board with the given map radius, food configuration and snake configuration
     * @param radius the radius of the map
     * @param foodConfig the configuration of the food
     * @param snakeConfig the configuration of the snake
     * @return
     */
    public static PlateauDouble createPlateauSlitherio(int radius, ConfigurationFoodDouble foodConfig, ConfigurationSnakeDouble snakeConfig){
        BorderDouble border = new BorderDouble(new CoordinateDouble(0.0,0.0), radius);
        
        foodConfig.setNbFood((int) (border.getArea() * foodConfig.getRatioOfFood() / foodConfig.getAverageFoodArea()));
        PlateauDouble plateau = new PlateauDouble(new FoodFactory<Double,Angle>(foodConfig), snakeConfig , border);
        return plateau;
    }
}
