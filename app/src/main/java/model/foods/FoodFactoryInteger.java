package model.foods;

import java.util.ArrayList;

import GUI.OurColors;
import configuration.ConfigurationFood;
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

       

        public GrowingFoodInteger(Coordinate<Integer, Direction> coordinate) {
            super(coordinate, ConfigurationFood.GROWING_FOOD_RADIUS, ConfigurationFood.GROWING_FOOD_RESPAWN, ConfigurationFood.GROWING_FOOD_PROBABILITY);
            OurColors color = OurColors.getRandomColor();
            String name = OurColors.toString(color);
            image = new Image("file:src/main/resources/foods/"+name+".png", radius * 2, radius * 2, false, false);
        }

        @Override
        public void actOnSnake(Snake<Integer, Direction> snake) {
            snake.chargeFood(ConfigurationFood.GROWING_FOOD_VALUE);
        }

        @Override
        public FoodType getFoodType() {
            return FoodType.GROWING_FOOD;
        }
    }

    public static class GrowingBigFoodInteger extends Food<Integer,Direction> {

        

        public GrowingBigFoodInteger(Coordinate<Integer, Direction> coordinate) {
            super(coordinate,ConfigurationFood.GROWING_BIG_FOOD_RADIUS, ConfigurationFood.GROWING_BIG_FOOD_RESPAWN, ConfigurationFood.GROWING_BIG_FOOD_PROBABILITY);
            OurColors color = OurColors.getRandomColor();
            String name = OurColors.toString(color);
            image = new Image("file:src/main/resources/foods/"+name+".png", radius * 2, radius * 2, false, false);
        }

        @Override
        public void actOnSnake(Snake<Integer, Direction> snake) {
            snake.chargeFood(ConfigurationFood.GROWING_BIG_FOOD_VALUE);
        }

        @Override
        public FoodType getFoodType() {
            return FoodType.GROWING_BIG_FOOD;
        }
    }

    public static class KillerFoodInteger extends Food<Integer,Direction> {

        

        public KillerFoodInteger(Coordinate<Integer, Direction> coordinate) {
            super(coordinate, ConfigurationFood.KILLER_FOOD_RADIUS, ConfigurationFood.KILLER_FOOD_RESPAWN, ConfigurationFood.KILLER_FOOD_PROBABILITY);
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
            super(coordinate, ConfigurationFood.POISON_FOOD_RADIUS, ConfigurationFood.POISON_FOOD_RESPAWN, ConfigurationFood.POISON_FOOD_PROBABILITY);
            String name = "poison";
            image = new Image("file:src/main/resources/foods/"+name+".png", radius * 2, radius * 2, false, false);
        }

        @Override
        public void actOnSnake(Snake<Integer, Direction> snake) {
            snake.setPoisoned(ConfigurationFood.POISON_VALUE);
        }

        @Override
        public FoodType getFoodType() {
            return FoodType.POISON_FOOD;
        }
    }

    public static class DeathFoodInteger extends DeathFood<Integer,Direction> {

        public DeathFoodInteger(Coordinate<Integer, Direction> coordinate) {
            super(coordinate, ConfigurationFood.DEATH_FOOD_RADIUS);
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
