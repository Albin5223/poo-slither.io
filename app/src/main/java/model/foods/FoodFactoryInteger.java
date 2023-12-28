package model.foods;

import java.util.ArrayList;

import GUI.OurColors;
import configuration.Configuration;
import interfaces.Orientation.Direction;
import javafx.scene.image.Image;
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
                case POISON_FOOD:
                    return new PoisonFoodInteger(coordinate);
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
        private static final int probability = 0;

        public GrowingFoodInteger(Coordinate<Integer, Direction> coordinate) {
            super(coordinate, radius, respawn, probability);
            OurColors color = OurColors.getRandomColor();
            String name = OurColors.toString(color);
            image = new Image("file:src/main/resources/foods/"+name+".png", radius * 2, radius * 2, false, false);
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
            OurColors color = OurColors.getRandomColor();
            String name = OurColors.toString(color);
            image = new Image("file:src/main/resources/foods/"+name+".png", radius * 2, radius * 2, false, false);
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
        private static final int probability = 0;

        public KillerFoodInteger(Coordinate<Integer, Direction> coordinate) {
            super(coordinate, radius, respawn, probability);
            String name = "skull";
            image = new Image("file:src/main/resources/foods/"+name+".png", radius * 2, radius * 2, false, false);
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


    public static class PoisonFoodInteger extends Food<Integer,Direction> {

        public PoisonFoodInteger(Coordinate<Integer, Direction> coordinate) {
            super(coordinate, Configuration.POISON_FOOD_RADIUS, Configuration.POISON_FOOD_RESPAWN, Configuration.POISON_FOOD_PROBABILITY);
            String name = "poison";
            image = new Image("file:src/main/resources/foods/"+name+".png", radius * 2, radius * 2, false, false);
        }

        @Override
        public void actOnSnake(Snake<Integer, Direction> snake) {
            snake.setPoisoned(Configuration.POISON_VALUE);
        }

        @Override
        public FoodType getFoodType() {
            return FoodType.POISON_FOOD;
        }
    }

    public static class DeathFoodInteger extends DeathFood<Integer,Direction> {

        private static final double radius = 5;

        public DeathFoodInteger(Coordinate<Integer, Direction> coordinate) {
            super(coordinate, radius);
            OurColors color = OurColors.getRandomColor();
            String name = OurColors.toString(color);
            image = new Image("file:src/main/resources/foods/"+name+".png", radius * 2, radius * 2, false, false);
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
