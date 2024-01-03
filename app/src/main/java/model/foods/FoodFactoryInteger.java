package model.foods;

import java.util.ArrayList;

import configuration.ConfigurationFoodInteger;
import externData.ImageBank;
import externData.OurColors;
import externData.OurSpecials;
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
                case SHIELD_FOOD:
                    return new ShieldFoodInteger(coordinate);
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
            super(coordinate, ConfigurationFoodInteger.GROWING_FOOD_RADIUS, ConfigurationFoodInteger.GROWING_FOOD_RESPAWN, ConfigurationFoodInteger.GROWING_FOOD_PROBABILITY);
        }

        @Override
        public void actOnSnake(Snake<Integer, Direction> snake) {
            snake.chargeFood(ConfigurationFoodInteger.GROWING_FOOD_VALUE);
        }

        @Override
        public FoodType getFoodType() {
            return FoodType.GROWING_FOOD;
        }

        @Override
        public Image getImage() {
            OurColors color = OurColors.getRandomColor();
            return ImageBank.getCircleImage(color);
        }
    }

    public static class ShieldFoodInteger extends Food<Integer,Direction> {

        public ShieldFoodInteger(Coordinate<Integer, Direction> coordinate) {
            super(coordinate, ConfigurationFoodInteger.SHIELD_FOOD_RADIUS, ConfigurationFoodInteger.SHIELD_FOOD_RESPAWN, ConfigurationFoodInteger.SHIELD_FOOD_PROBABILITY);
        }

        @Override
        public void actOnSnake(Snake<Integer, Direction> snake) {
            snake.setShielded(ConfigurationFoodInteger.SHIELD_VALUE);
        }

        @Override
        public FoodType getFoodType() {
            return FoodType.SHIELD_FOOD;
        }

        @Override
        public Image getImage() {
            return ImageBank.getSpecialImage(OurSpecials.SHIELD);
        }
    }

    public static class GrowingBigFoodInteger extends Food<Integer,Direction> {

        public GrowingBigFoodInteger(Coordinate<Integer, Direction> coordinate) {
            super(coordinate,ConfigurationFoodInteger.GROWING_BIG_FOOD_RADIUS, ConfigurationFoodInteger.GROWING_BIG_FOOD_RESPAWN, ConfigurationFoodInteger.GROWING_BIG_FOOD_PROBABILITY);
        }

        @Override
        public void actOnSnake(Snake<Integer, Direction> snake) {
            snake.chargeFood(ConfigurationFoodInteger.GROWING_BIG_FOOD_VALUE);
        }

        @Override
        public FoodType getFoodType() {
            return FoodType.GROWING_BIG_FOOD;
        }

        @Override
        public Image getImage() {
            OurColors color = OurColors.getRandomColor();
            return ImageBank.getCircleImage(color);
        }
    }

    public static class KillerFoodInteger extends Food<Integer,Direction> {

        

        public KillerFoodInteger(Coordinate<Integer, Direction> coordinate) {
            super(coordinate, ConfigurationFoodInteger.KILLER_FOOD_RADIUS, ConfigurationFoodInteger.KILLER_FOOD_RESPAWN, ConfigurationFoodInteger.KILLER_FOOD_PROBABILITY);
        }

        @Override
        public void actOnSnake(Snake<Integer, Direction> snake) {
            snake.try_to_kill();
        }

        @Override
        public FoodType getFoodType() {
            return FoodType.KILLER_FOOD;
        }

        @Override
        public Image getImage() {
            return ImageBank.getSpecialImage(OurSpecials.SKULL);
        }
    }


    public static class PoisonFoodInteger extends Food<Integer,Direction> {

        public PoisonFoodInteger(Coordinate<Integer, Direction> coordinate) {
            super(coordinate, ConfigurationFoodInteger.POISON_FOOD_RADIUS, ConfigurationFoodInteger.POISON_FOOD_RESPAWN, ConfigurationFoodInteger.POISON_FOOD_PROBABILITY);
        }

        @Override
        public void actOnSnake(Snake<Integer, Direction> snake) {
            snake.setPoisoned(ConfigurationFoodInteger.POISON_TIME, ConfigurationFoodInteger.POISON_POWER);
        }

        @Override
        public FoodType getFoodType() {
            return FoodType.POISON_FOOD;
        }

        @Override
        public Image getImage() {
            return ImageBank.getSpecialImage(OurSpecials.POISON);
        }
    }

    public static class DeathFoodInteger extends DeathFood<Integer,Direction> {

        public DeathFoodInteger(Coordinate<Integer, Direction> coordinate) {
            super(coordinate, ConfigurationFoodInteger.DEATH_FOOD_RADIUS, ConfigurationFoodInteger.DEATH_FOOD_VALUE);
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
