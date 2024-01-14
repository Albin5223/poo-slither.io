package configuration;

import interfaces.ConfigurationFood;

public class ConfigurationFoodInteger implements ConfigurationFood {

    private double AVERAGE_FOOD_RADIUS = 7;

    private double RATIO_OF_FOOD = 1.0/100;
    private int NB_FOOD = 100;
    private int MAX_FOOD_COEF = 2;

    /*Configuration for poison food */
    private int POISON_TIME = 5;
    private int POISON_POWER = 1;
    private double POISON_FOOD_RADIUS = 9;
    private boolean POISON_FOOD_RESPAWN = true;
    private int POISON_FOOD_PROBABILITY = 2;

    /*Configuration for big growing food */
    private int GROWING_BIG_FOOD_VALUE = 10;
    private double GROWING_BIG_FOOD_RADIUS = 9;
    private boolean GROWING_BIG_FOOD_RESPAWN = true;
    private int GROWING_BIG_FOOD_PROBABILITY = 50;

    /*Configuration for killer food */
    private int KILLER_FOOD_RADIUS = 9;
    private boolean KILLER_FOOD_RESPAWN = false;
    private int KILLER_FOOD_PROBABILITY = 2;


    /*Configuration for growing food */
    private int GROWING_FOOD_VALUE = 5;
    private double GROWING_FOOD_RADIUS = 5;
    private boolean GROWING_FOOD_RESPAWN = true;
    private int GROWING_FOOD_PROBABILITY = 80;

    /*Configuration for death food */
    private double DEATH_FOOD_RADIUS = 5;
    private int DEATH_FOOD_VALUE = 1;
    private boolean DEATH_FOOD_SPAWN_IN_CENTER = true;

    /*Configuration for shield food */
    private double SHIELD_FOOD_RADIUS = 9;
    private int SHIELD_VALUE = 5;
    private boolean SHIELD_FOOD_RESPAWN = true;
    private int SHIELD_FOOD_PROBABILITY = 2;

    public ConfigurationFoodInteger setRatioOfFood(double i){RATIO_OF_FOOD = i;return this;}
    public ConfigurationFoodInteger setNbFood(int i){NB_FOOD = i;return this;}
    public ConfigurationFoodInteger setMaxFoodCoef(int i){MAX_FOOD_COEF = i;return this;}
    public ConfigurationFoodInteger setPoisonTime(int i){POISON_TIME = i;return this;}
    public ConfigurationFoodInteger setPoisonPower(int i){POISON_POWER = i;return this;}
    public ConfigurationFoodInteger setPoisonFoodRadius(double d){POISON_FOOD_RADIUS = d;return this;}
    public ConfigurationFoodInteger setPoisonFoodRespawn(boolean b){POISON_FOOD_RESPAWN = b;return this;}
    public ConfigurationFoodInteger setPoisonFoodProbability(int i){POISON_FOOD_PROBABILITY = i;return this;}
    public ConfigurationFoodInteger setGrowingBigFoodValue(int i){GROWING_BIG_FOOD_VALUE = i;return this;}
    public ConfigurationFoodInteger setGrowingBigFoodRadius(double d){GROWING_BIG_FOOD_RADIUS = d;return this;}
    public ConfigurationFoodInteger setGrowingBigFoodRespawn(boolean b){GROWING_BIG_FOOD_RESPAWN = b;return this;}
    public ConfigurationFoodInteger setGrowingBigFoodProbability(int i){GROWING_BIG_FOOD_PROBABILITY = i;return this;}
    public ConfigurationFoodInteger setKillerFoodRadius(int i){KILLER_FOOD_RADIUS = i;return this;}
    public ConfigurationFoodInteger setKillerFoodRespawn(boolean b){KILLER_FOOD_RESPAWN = b;return this;}
    public ConfigurationFoodInteger setKillerFoodProbability(int i){KILLER_FOOD_PROBABILITY = i;return this;}
    public ConfigurationFoodInteger setGrowingFoodValue(int i){GROWING_FOOD_VALUE = i;return this;}
    public ConfigurationFoodInteger setGrowingFoodRadius(double d){GROWING_FOOD_RADIUS = d;return this;}
    public ConfigurationFoodInteger setGrowingFoodRespawn(boolean b){GROWING_FOOD_RESPAWN = b;return this;}
    public ConfigurationFoodInteger setGrowingFoodProbability(int i){GROWING_FOOD_PROBABILITY = i;return this;}
    public ConfigurationFoodInteger setDeathFoodRadius(double d){DEATH_FOOD_RADIUS = d;return this;}
    public ConfigurationFoodInteger setDeathFoodValue(int i){DEATH_FOOD_VALUE = i;return this;}
    public ConfigurationFoodInteger setDeathFoodSpawnInCenter(boolean b){DEATH_FOOD_SPAWN_IN_CENTER = b;return this;}
    public ConfigurationFoodInteger setShieldFoodRadius(double d){SHIELD_FOOD_RADIUS = d;return this;}
    public ConfigurationFoodInteger setShieldValue(int i){SHIELD_VALUE = i;return this;}
    public ConfigurationFoodInteger setShieldFoodRespawn(boolean b){SHIELD_FOOD_RESPAWN = b;return this;}
    public ConfigurationFoodInteger setShieldFoodProbability(int i){SHIELD_FOOD_PROBABILITY = i;return this;}
    public ConfigurationFoodInteger setAverageFoodRadius(double d){AVERAGE_FOOD_RADIUS = d;return this;}
    
    @Override public double getRatioOfFood(){return RATIO_OF_FOOD;}
    @Override public int getNbFood(){return NB_FOOD;}
    @Override public int getMaxFoodCoef(){return MAX_FOOD_COEF;}
    @Override public int getPoisonTime(){return POISON_TIME;}
    @Override public int getPoisonPower(){return POISON_POWER;}
    @Override public double getPoisonFoodRadius(){return POISON_FOOD_RADIUS;}
    @Override public boolean getPoisonFoodRespawn(){return POISON_FOOD_RESPAWN;}
    @Override public int getPoisonFoodProbability(){return POISON_FOOD_PROBABILITY;}
    @Override public int getGrowingBigFoodValue(){return GROWING_BIG_FOOD_VALUE;}
    @Override public double getGrowingBigFoodRadius(){return GROWING_BIG_FOOD_RADIUS;}
    @Override public boolean getGrowingBigFoodRespawn(){return GROWING_BIG_FOOD_RESPAWN;}
    @Override public int getGrowingBigFoodProbability(){return GROWING_BIG_FOOD_PROBABILITY;}
    @Override public int getKillerFoodRadius(){return KILLER_FOOD_RADIUS;}
    @Override public boolean getKillerFoodRespawn(){return KILLER_FOOD_RESPAWN;}
    @Override public int getKillerFoodProbability(){return KILLER_FOOD_PROBABILITY;}
    @Override public int getGrowingFoodValue(){return GROWING_FOOD_VALUE;}
    @Override public double getGrowingFoodRadius(){return GROWING_FOOD_RADIUS;}
    @Override public boolean getGrowingFoodRespawn(){return GROWING_FOOD_RESPAWN;}
    @Override public int getGrowingFoodProbability(){return GROWING_FOOD_PROBABILITY;}
    @Override public int getDeathFoodValue(){return DEATH_FOOD_VALUE;}
    @Override public boolean getDeathFoodSpawnInCenter(){return DEATH_FOOD_SPAWN_IN_CENTER;}
    @Override public double getDeathFoodRadius(){return DEATH_FOOD_RADIUS;}
    @Override public double getShieldFoodRadius(){return SHIELD_FOOD_RADIUS;}
    @Override public int getShieldValue(){return SHIELD_VALUE;}
    @Override public boolean getShieldFoodRespawn(){return SHIELD_FOOD_RESPAWN;}
    @Override public int getShieldFoodProbability(){return SHIELD_FOOD_PROBABILITY;}
    @Override public double getAverageFoodRadius(){return AVERAGE_FOOD_RADIUS;}
}
