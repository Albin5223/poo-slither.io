package model.plateau;

import java.io.Serializable;
import java.util.Random;

import interfaces.GameBorder;
import interfaces.Orientation.Angle;
import model.coordinate.Coordinate;
import model.coordinate.CoordinateDouble;
import model.foods.FoodFactoryDouble;
import configuration.ConfigurationSnakeDouble;

public final class PlateauDouble extends Plateau<Double,Angle>{

    public static class BorderDouble implements GameBorder<Double,Angle>, Serializable {

        public final CoordinateDouble map_center;
        public final double map_radius;

        public BorderDouble(CoordinateDouble map_center, double map_radius) {
            this.map_center = map_center.clone();
            this.map_radius = map_radius;
        }

        @Override
        public boolean isInside(Coordinate<Double, Angle> c) {
            double distance = c.distanceTo(map_center);
            return distance <= map_radius;
        }

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

        @Override
        public Coordinate<Double, Angle> getRandomCoordinate() {
            double angle = new Random().nextDouble() * 2 * Math.PI;
            double radius = map_radius * Math.sqrt(new Random().nextDouble()) * ConfigurationSnakeDouble.RANDOM_RATIO;
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

    private final static int NB_FOOD = 100;

    public PlateauDouble(int nbFood, FoodFactoryDouble foodFactory, BorderDouble border) {
        super(nbFood, foodFactory, border);
    }

    public static PlateauDouble createPlateauSlitherio(int radius){
        BorderDouble border = new BorderDouble(new CoordinateDouble(0.0,0.0), radius);
        PlateauDouble plateau = new PlateauDouble(NB_FOOD, FoodFactoryDouble.build(), border);
        return plateau;
    }
}
