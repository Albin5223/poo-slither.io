package model.foods;

import java.util.ArrayList;
import java.util.Random;

import GUI.OurColors;
import configuration.ConfigurationFood;
import interfaces.Orientation.Angle;
import javafx.scene.image.Image;
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

        public GrowingFoodDouble(Coordinate<Double, Angle> coordinate) {
            super(coordinate, ConfigurationFood.GROWING_FOOD_RADIUS, ConfigurationFood.GROWING_FOOD_RESPAWN, ConfigurationFood.GROWING_FOOD_PROBABILITY);
            OurColors color = OurColors.getRandomColor();
            String name = OurColors.toString(color);
            image = new Image("file:src/main/resources/foods/"+name+".png", radius * 2, radius * 2, false, false);
        }

        @Override
        public void actOnSnake(Snake<Double, Angle> snake) {
            snake.chargeFood(ConfigurationFood.GROWING_FOOD_VALUE);
        }

        @Override
        public FoodType getFoodType() {
            return FoodType.GROWING_FOOD;
        }
    }

    public static class GrowingBigFoodDouble extends Food<Double,Angle> {

        public GrowingBigFoodDouble(Coordinate<Double, Angle> coordinate) {
            super(coordinate,ConfigurationFood.GROWING_BIG_FOOD_RADIUS, ConfigurationFood.GROWING_BIG_FOOD_RESPAWN, ConfigurationFood.GROWING_BIG_FOOD_PROBABILITY);
            OurColors color = OurColors.getRandomColor();
            String name = OurColors.toString(color);
            image = new Image("file:src/main/resources/foods/"+name+".png", radius * 2, radius * 2, false, false);
        }

        @Override
        public void actOnSnake(Snake<Double, Angle> snake) {
            snake.chargeFood(ConfigurationFood.GROWING_BIG_FOOD_VALUE);
        }

        @Override
        public FoodType getFoodType() {
            return FoodType.GROWING_BIG_FOOD;
        }
    }

    public static class PoisonFoodDouble extends Food<Double,Angle> {

        
        public PoisonFoodDouble(Coordinate<Double, Angle> coordinate) {
            super(coordinate, ConfigurationFood.POISON_FOOD_RADIUS, ConfigurationFood.POISON_FOOD_RESPAWN, ConfigurationFood.POISON_FOOD_PROBABILITY);
            String name = "poison";
            image = new Image("file:src/main/resources/foods/"+name+".png", radius * 2, radius * 2, false, false);
        
        }

        @Override
        public void actOnSnake(Snake<Double, Angle> snake) {
            snake.setPoisoned(ConfigurationFood.POISON_VALUE);
        }

        @Override
        public FoodType getFoodType() {
            return FoodType.POISON_FOOD;
        }
    }

    public static class KillerFoodDouble extends Food<Double,Angle> {

        public KillerFoodDouble(Coordinate<Double, Angle> coordinate) {
            super(coordinate, ConfigurationFood.KILLER_FOOD_RADIUS, ConfigurationFood.KILLER_FOOD_RESPAWN, ConfigurationFood.KILLER_FOOD_PROBABILITY);
            String name = "skull";
            image = new Image("file:src/main/resources/foods/"+name+".png", radius * 2, radius * 2, false, false);
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

        public DeathFoodDouble(Coordinate<Double, Angle> coordinate) {
            super(coordinate, ConfigurationFood.DEATH_FOOD_RADIUS);
            OurColors color = OurColors.getRandomColor();
            String name = OurColors.toString(color);
            image = new Image("file:src/main/resources/foods/"+name+".png", radius * 2, radius * 2, false, false);
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
