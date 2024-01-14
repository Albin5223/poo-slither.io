package model.foods;

import java.util.ArrayList;
import java.util.Random;

import externData.OurColors;
import interfaces.ConfigurationFood;
import interfaces.Orientation;
import model.coordinate.Coordinate;
import model.plateau.Snake;


public class FoodFactory<Type extends Number & Comparable<Type>, O extends Orientation<O>> {

    public static enum FoodType {
        GROWING_FOOD,
        GROWING_BIG_FOOD,
        KILLER_FOOD,
        POISON_FOOD,
        DEATH_FOOD,
        SHIELD_FOOD;
    }

    private final ConfigurationFood foodConfig;

    public FoodFactory(ConfigurationFood foodConfig) {this.foodConfig = foodConfig;}
    public ConfigurationFood getFoodConfig() {return foodConfig;}

    public class GrowingFood extends Food<Type, O> {

        private OurColors color = OurColors.getRandomColor();

        public GrowingFood(Coordinate<Type, O> coordinate) {
            super(coordinate, foodConfig.getGrowingFoodRadius(), foodConfig.getGrowingFoodRespawn(), foodConfig.getGrowingFoodProbability());
        }

        @Override
        public void actOnSnake(Snake<Type, O> snake) {
            snake.chargeFood(foodConfig.getGrowingFoodValue());
        }

        @Override
        public FoodType getFoodType() {
            return FoodType.GROWING_FOOD;
        }

        @Override
        public OurColors getColor() {
            return color;
        }
    }

    public class GrowingBigFood extends Food<Type, O> {

        private OurColors color = OurColors.getRandomColor();

        public GrowingBigFood(Coordinate<Type, O> coordinate) {
            super(coordinate, foodConfig.getGrowingBigFoodRadius(), foodConfig.getGrowingBigFoodRespawn(), foodConfig.getGrowingBigFoodProbability());
        }

        @Override
        public void actOnSnake(Snake<Type, O> snake) {
            snake.chargeFood(foodConfig.getGrowingBigFoodValue());
        }

        @Override
        public FoodType getFoodType() {
            return FoodType.GROWING_BIG_FOOD;
        }

        @Override
        public OurColors getColor() {
            return color;
        }
    }

    public class PoisonFood extends Food<Type,O> {
        
        public PoisonFood(Coordinate<Type, O> coordinate) {
            super(coordinate, foodConfig.getPoisonFoodRadius(), foodConfig.getPoisonFoodRespawn(), foodConfig.getPoisonFoodProbability());
        }

        @Override
        public void actOnSnake(Snake<Type, O> snake) {
            snake.setPoisoned(foodConfig.getPoisonTime(), foodConfig.getPoisonPower());
        }

        @Override
        public FoodType getFoodType() {
            return FoodType.POISON_FOOD;
        }

        @Override
        public OurColors getColor() {
            return OurColors.POISON;
        }
    }

    public class KillerFood extends Food<Type,O> {

        public KillerFood(Coordinate<Type, O> coordinate) {
            super(coordinate, foodConfig.getKillerFoodRadius(), foodConfig.getKillerFoodRespawn(), foodConfig.getKillerFoodProbability());
        }

        @Override
        public void actOnSnake(Snake<Type, O> snake) {
            snake.try_to_kill();
        }

        @Override
        public FoodType getFoodType() {
            return FoodType.KILLER_FOOD;
        }

        @Override
        public OurColors getColor() {
            return OurColors.SKULL;
        }
    }

    public class ShieldFood extends Food<Type,O> {

        public ShieldFood(Coordinate<Type, O> coordinate) {
            super(coordinate, foodConfig.getShieldFoodRadius(), foodConfig.getShieldFoodRespawn(), foodConfig.getShieldFoodProbability());
        }

        @Override
        public void actOnSnake(Snake<Type, O> snake) {
            snake.setShielded(foodConfig.getShieldTime());
        }

        @Override
        public FoodType getFoodType() {
            return FoodType.SHIELD_FOOD;
        }

        @Override
        public OurColors getColor() {
            return OurColors.SHIELD;
        }
    }

    public class DeathFood extends Food<Type,O> {

        private final OurColors color = OurColors.getRandomColor();
    
        public DeathFood(Coordinate<Type, O> coordinate) {
            super(coordinate, foodConfig.getDeathFoodRadius(), false, 0);
        }
    
        @Override
        public final void actOnSnake(Snake<Type,O> snake){
            snake.chargeFood(foodConfig.getDeathFoodValue());
        }
    
        @Override
        public final FoodType getFoodType() {
            return FoodType.DEATH_FOOD;
        }
    
        @Override
        public final OurColors getColor() {
            return color;
        }
    }

    public Food<Type,O> createFood(FoodType foodType, Coordinate<Type,O> coordinate){
        switch (foodType) {
            case GROWING_FOOD:
                return new GrowingFood(coordinate);
            case GROWING_BIG_FOOD:
                return new GrowingBigFood(coordinate);
            case KILLER_FOOD:
                return new KillerFood(coordinate);
            case DEATH_FOOD:
                return new DeathFood(coordinate);
            case POISON_FOOD:
                return new PoisonFood(coordinate);
            case SHIELD_FOOD:
                return new ShieldFood(coordinate);
            default:
                throw new IllegalArgumentException("FoodType not recognized");
        }
    }

    /**
     * Return a list of food that will be spawned when the snake dies
     * @param snake the snake that dies
     * @return a list of food that have to be spawned when the snake dies
     */
    public ArrayList<DeathFood> getDeathFoods(Snake<Type, O> snake) {
        ArrayList<DeathFood> deathFoods = new ArrayList<>();
        double radius = snake.getHitboxRadius();
        
        int i = 0;
        for (Snake<Type, O>.SnakePart s : snake.getAllSnakePart()) {
            Coordinate<Type, O> center = s.getCenter();

            if(i % snake.DEATH_FOOD_SEGMENT_MODULO != 0){
                i++;
                continue;
            }
            if(foodConfig.getDeathFoodSpawnInCenter()){
                deathFoods.add(new DeathFood(center));
            }
            else{
                Random random = new Random();

                double randomAngle = 2 * Math.PI * random.nextDouble();
                double randomRadius = radius * Math.sqrt(random.nextDouble());

                double x = center.getX().doubleValue() + randomRadius * Math.cos(randomAngle);
                double y = center.getY().doubleValue() + randomRadius * Math.sin(randomAngle);

                Coordinate<Type,O> c = center.clone(x, y);
                deathFoods.add(new DeathFood(c));
            }
            i++;
        }
        return deathFoods;
    }

    /**
     * Return the food that will be spawned behind the snake when he boosts
     * @param snake the snake that boosts
     * @return the food that will be spawned behind the snake when he boosts
     */
    public DeathFood getBoostFoods(Snake<Type, O> snake) {
        double radius = snake.getHitboxRadius();
        
        Coordinate<Type, O> center = snake.getTail().get(snake.getTail().size() - 1).getCenter();

        if(foodConfig.getDeathFoodSpawnInCenter()){
            return new DeathFood(center);
        }
        else{
            Random random = new Random();

            double randomAngle = 2 * Math.PI * random.nextDouble();
            double randomRadius = radius * Math.sqrt(random.nextDouble()) * 0.5;

            double x = center.getX().doubleValue() + randomRadius * Math.cos(randomAngle);
            double y = center.getY().doubleValue() + randomRadius * Math.sin(randomAngle);

            Coordinate<Type,O> c = center.clone(x, y);
            return new DeathFood(c);
        }
    }

    public Food<Type,O> getRandomFood(Coordinate<Type,O> coordinate){
        ArrayList<Food<Type,O>> allFoods = new ArrayList<Food<Type,O>>();
        for (FoodType foodType : FoodType.values()) {
            allFoods.add(this.createFood(foodType, coordinate));
        }

        int totalProbability = 0;
        for (Food<Type,O> food : allFoods) {
            totalProbability += food.getProbability();
        }

        Random rand = new Random();
        int random = rand.nextInt(totalProbability);
        int currentProbability = 0;
        for (Food<Type,O> food : allFoods) {
            currentProbability += food.getProbability();
            if (random < currentProbability) {
                return food;
            }
        }

        throw new RuntimeException("Food not found");
    }
    
}
