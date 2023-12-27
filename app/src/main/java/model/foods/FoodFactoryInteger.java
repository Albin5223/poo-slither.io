package model.foods;

import java.util.ArrayList;
import interfaces.Orientation.Direction;
import model.coordinate.Coordinate;
import model.plateau.Snake;

public class FoodFactoryInteger extends FoodFactory<Integer,Direction> {

    public FoodBuilderInteger foodBuilderDouble = new FoodBuilderInteger();

    public static class FoodBuilderInteger extends FoodBuilder<Integer,Direction> {

        @Override
        public Food<Integer, Direction> createFood(FoodType foodType, Coordinate<Integer, Direction> coordinate) {
            switch (foodType) {
                case GROWING_FOOD:
                    return new GrowingFoodInteger(coordinate);
                case GROWING_BIG_FOOD:
                    return new GrowingBigFoodInteger(coordinate);
                case KILLER_FOOD:
                    return new KillerFoodInteger(coordinate);
                case DEATH_FOOD:
                    return new DeathFoodInteger(coordinate);
                default:
                    throw new IllegalArgumentException("FoodType not recognized");
            }
        }
    }

    public FoodFactoryInteger(FoodBuilder<Integer, Direction> foodBuilder) {
        super(foodBuilder);
    }

    public static FoodFactoryInteger build() {
        return new FoodFactoryInteger(new FoodBuilderInteger());
    }

    public static class GrowingFoodInteger extends Food<Integer,Direction> {

        private static final int value = 5;
        private static final int radius = 5;
        private static final boolean respawn = true;
        private static final int probability = 10;

        public GrowingFoodInteger(Coordinate<Integer, Direction> coordinate) {
            super(coordinate, radius, respawn, probability);
        }

        @Override
        public void actOnSnake(Snake<Integer, Direction> snake) {
            snake.chargeFood(value);
        }

        @Override
        public FoodType getFoodType() {
            return FoodType.GROWING_FOOD;
        }
    }

    public static class GrowingBigFoodInteger extends Food<Integer,Direction> {

        private static final int value = 10;
        private static final int radius = 9;
        private static final boolean respawn = true;
        private static final int probability = 10;

        public GrowingBigFoodInteger(Coordinate<Integer, Direction> coordinate) {
            super(coordinate, radius, respawn, probability);
        }

        @Override
        public void actOnSnake(Snake<Integer, Direction> snake) {
            snake.chargeFood(value);
        }

        @Override
        public FoodType getFoodType() {
            return FoodType.GROWING_BIG_FOOD;
        }
    }

    public static class KillerFoodInteger extends Food<Integer,Direction> {

        private static final int radius = 10;
        private static final boolean respawn = false;
        private static final int probability = 1;

        public KillerFoodInteger(Coordinate<Integer, Direction> coordinate) {
            super(coordinate, radius, respawn, probability);
        }

        @Override
        public void actOnSnake(Snake<Integer, Direction> snake) {
            snake.reset();
        }

        @Override
        public FoodType getFoodType() {
            return FoodType.KILLER_FOOD;
        }
    }

    public static class DeathFoodInteger extends DeathFood<Integer,Direction> {

        private static final double radius = 5;

        public DeathFoodInteger(Coordinate<Integer, Direction> coordinate) {
            super(coordinate, radius);
        }
    }


    @Override
    public ArrayList<DeathFood<Integer, Direction>> getDeathFoods(Snake<Integer, Direction> snake) {
        ArrayList<DeathFood<Integer, Direction>> deathFoods = new ArrayList<>();
        for (Snake<Integer, Direction>.SnakePart s : snake.getAllSnakePart()) {
            Coordinate<Integer, Direction> center = s.getCenter();

            for(int i = 0; i < snake.DEATH_FOOD_PER_SEGMENT; i++){
                deathFoods.add(new DeathFoodInteger(center));
            }
        }
        return deathFoods;
    }

    
}
