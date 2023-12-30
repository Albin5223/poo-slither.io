package model.foods;

import java.util.ArrayList;
import java.util.Random;

import configuration.ConfigurationFoodDouble;
import externData.ImageBank;
import externData.OurColors;
import externData.OurSpecials;
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
                case SHIELD_FOOD:
                    return new ShieldFoodDouble(coordinate);
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
            super(coordinate, ConfigurationFoodDouble.GROWING_FOOD_RADIUS, ConfigurationFoodDouble.GROWING_FOOD_RESPAWN, ConfigurationFoodDouble.GROWING_FOOD_PROBABILITY);
            OurColors color = OurColors.getRandomColor();
            image = ImageBank.getCircleImage(color);
        }

        @Override
        public void actOnSnake(Snake<Double, Angle> snake) {
            snake.chargeFood(ConfigurationFoodDouble.GROWING_FOOD_VALUE);
        }

        @Override
        public FoodType getFoodType() {
            return FoodType.GROWING_FOOD;
        }
    }

    public static class GrowingBigFoodDouble extends Food<Double,Angle> {

        public GrowingBigFoodDouble(Coordinate<Double, Angle> coordinate) {
            super(coordinate,ConfigurationFoodDouble.GROWING_BIG_FOOD_RADIUS, ConfigurationFoodDouble.GROWING_BIG_FOOD_RESPAWN, ConfigurationFoodDouble.GROWING_BIG_FOOD_PROBABILITY);
            OurColors color = OurColors.getRandomColor();
            image = ImageBank.getCircleImage(color);
        }

        @Override
        public void actOnSnake(Snake<Double, Angle> snake) {
            snake.chargeFood(ConfigurationFoodDouble.GROWING_BIG_FOOD_VALUE);
        }

        @Override
        public FoodType getFoodType() {
            return FoodType.GROWING_BIG_FOOD;
        }
    }

    public static class PoisonFoodDouble extends Food<Double,Angle> {

        
        public PoisonFoodDouble(Coordinate<Double, Angle> coordinate) {
            super(coordinate, ConfigurationFoodDouble.POISON_FOOD_RADIUS, ConfigurationFoodDouble.POISON_FOOD_RESPAWN, ConfigurationFoodDouble.POISON_FOOD_PROBABILITY);
            image = ImageBank.getSpecialImage(OurSpecials.POISON);
        
        }

        @Override
        public void actOnSnake(Snake<Double, Angle> snake) {
            snake.setPoisoned(ConfigurationFoodDouble.POISON_TIME, ConfigurationFoodDouble.POISON_POWER);
        }

        @Override
        public FoodType getFoodType() {
            return FoodType.POISON_FOOD;
        }
    }

    public static class KillerFoodDouble extends Food<Double,Angle> {

        public KillerFoodDouble(Coordinate<Double, Angle> coordinate) {
            super(coordinate, ConfigurationFoodDouble.KILLER_FOOD_RADIUS, ConfigurationFoodDouble.KILLER_FOOD_RESPAWN, ConfigurationFoodDouble.KILLER_FOOD_PROBABILITY);
            image = ImageBank.getSpecialImage(OurSpecials.SKULL);
        }

        @Override
        public void actOnSnake(Snake<Double, Angle> snake) {
            snake.try_to_kill();
        }

        @Override
        public FoodType getFoodType() {
            return FoodType.KILLER_FOOD;
        }
    }

    public static class DeathFoodDouble extends DeathFood<Double,Angle> {

        public DeathFoodDouble(Coordinate<Double, Angle> coordinate) {
            super(coordinate, ConfigurationFoodDouble.DEATH_FOOD_RADIUS, ConfigurationFoodDouble.DEATH_FOOD_VALUE);
        }
    }
    
    @Override
    public ArrayList<DeathFood<Double, Angle>> getDeathFoods(Snake<Double, Angle> snake) {
        ArrayList<DeathFood<Double, Angle>> deathFoods = new ArrayList<>();
        double radius = snake.getHitboxRadius();
        
        for (Snake<Double, Angle>.SnakePart s : snake.getAllSnakePart()) {
            Coordinate<Double, Angle> center = s.getCenter();

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


     public static class ShieldFoodDouble extends Food<Double,Angle> {

        public ShieldFoodDouble(Coordinate<Double, Angle> coordinate) {
            super(coordinate, ConfigurationFoodDouble.SHIELD_FOOD_RADIUS, ConfigurationFoodDouble.SHIELD_FOOD_RESPAWN, ConfigurationFoodDouble.SHIELD_FOOD_PROBABILITY);
            image = ImageBank.getSpecialImage(OurSpecials.SHIELD);
        }

        @Override
        public void actOnSnake(Snake<Double, Angle> snake) {
            snake.setShielded(ConfigurationFoodDouble.SHIELD_VALUE);
        }

        @Override
        public FoodType getFoodType() {
            return FoodType.SHIELD_FOOD;
        }
    }
}
