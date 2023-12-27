package model.foods;

import java.util.ArrayList;
import java.util.Random;

import interfaces.Orientation.Angle;
import model.coordinate.Coordinate;
import model.coordinate.CoordinateDouble;
import model.plateau.Snake;

public class FoodFactoryDouble extends FoodFactory<Double,Angle> {

    public FoodBuilderDouble foodBuilderDouble = new FoodBuilderDouble();

    public static class FoodBuilderDouble extends FoodBuilder<Double,Angle> {

        @Override
        public Food<Double, Angle> createFood(FoodType foodType, Coordinate<Double, Angle> coordinate) {
            switch (foodType) {
                case GROWING_FOOD:
                    return new GrowingFoodDouble(coordinate);
                case GROWING_BIG_FOOD:
                    return new GrowingBigFoodDouble(coordinate);
                case KILLER_FOOD:
                    return new KillerFoodDouble(coordinate);
                case DEATH_FOOD:
                    return new DeathFoodDouble(coordinate);
                case POISON_FOOD:
                    return new PoisonFoodDouble(coordinate);
                default:
                    throw new IllegalArgumentException("FoodType not recognized");
            }
        }
    }

    private FoodFactoryDouble(FoodBuilderDouble foodBuilder) {
        super(foodBuilder);
    }

    public static FoodFactoryDouble build() {
        return new FoodFactoryDouble(new FoodBuilderDouble());
    }

    public static class GrowingFoodDouble extends Food<Double,Angle> {

        private static final int value = 5;
        private static final double radius = 5;
        private static final boolean respawn = true;
        private static final int probability = 10;

        public GrowingFoodDouble(Coordinate<Double, Angle> coordinate) {
            super(coordinate, radius, respawn, probability);
        }

        @Override
        public void actOnSnake(Snake<Double, Angle> snake) {
            snake.chargeFood(value);
        }

        @Override
        public FoodType getFoodType() {
            return FoodType.GROWING_FOOD;
        }
    }

    public static class GrowingBigFoodDouble extends Food<Double,Angle> {

        private static final int value = 10;
        private static final double radius = 10;
        private static final boolean respawn = true;
        private static final int probability = 10;

        public GrowingBigFoodDouble(Coordinate<Double, Angle> coordinate) {
            super(coordinate, radius, respawn, probability);
        }

        @Override
        public void actOnSnake(Snake<Double, Angle> snake) {
            snake.chargeFood(value);
        }

        @Override
        public FoodType getFoodType() {
            return FoodType.GROWING_BIG_FOOD;
        }
    }

    public static class PoisonFoodDouble extends Food<Double,Angle> {

        private static final double radius = 5;
        private static final boolean respawn = true;
        private static final int probability = 10;

        public PoisonFoodDouble(Coordinate<Double, Angle> coordinate) {
            super(coordinate, radius, respawn, probability);
        }

        @Override
        public void actOnSnake(Snake<Double, Angle> snake) {
            snake.shrink();
        }

        @Override
        public FoodType getFoodType() {
            return FoodType.POISON_FOOD;
        }
    }

    public static class KillerFoodDouble extends Food<Double,Angle> {

        private static final double radius = 10;
        private static final boolean respawn = false;
        private static final int probability = 2;

        public KillerFoodDouble(Coordinate<Double, Angle> coordinate) {
            super(coordinate, radius, respawn, probability);
        }

        @Override
        public void actOnSnake(Snake<Double, Angle> snake) {
            snake.reset();
        }

        @Override
        public FoodType getFoodType() {
            return FoodType.KILLER_FOOD;
        }
    }

    public static class DeathFoodDouble extends DeathFood<Double,Angle> {

        private static final double radius = 5;

        public DeathFoodDouble(Coordinate<Double, Angle> coordinate) {
            super(coordinate, radius);
        }
    }
    
    @Override
    public ArrayList<DeathFood<Double, Angle>> getDeathFoods(Snake<Double, Angle> snake) {
        ArrayList<DeathFood<Double, Angle>> deathFoods = new ArrayList<>();
        for (Snake<Double, Angle>.SnakePart s : snake.getAllSnakePart()) {
            Coordinate<Double, Angle> center = s.getCenter();
            double radius = s.getHitboxRadius();

            for(int i = 0; i < snake.DEATH_FOOD_PER_SEGMENT; i++){
                Random random = new Random();

                double randomAngle = 2 * Math.PI * random.nextDouble();
                double randomRadius = radius * Math.sqrt(random.nextDouble());

                double x = center.getX() + randomRadius * Math.cos(randomAngle);
                double y = center.getY() + randomRadius * Math.sin(randomAngle);

                CoordinateDouble c = new CoordinateDouble(x, y);
                deathFoods.add(new DeathFoodDouble(c));
            }
        }
        return deathFoods;
    }    
}
