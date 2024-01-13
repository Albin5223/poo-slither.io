package interfaces;

public interface ConfigurationFood {

    public static double RATIO_OF_FOOD = 2.0/100;
    public int getNbFood();
    public int getMaxFoodCoef();

    public int getPoisonTime();
    public int getPoisonPower();
    public double getPoisonFoodRadius();
    public boolean getPoisonFoodRespawn();
    public int getPoisonFoodProbability();
    public int getGrowingBigFoodValue();
    public double getGrowingBigFoodRadius();
    public boolean getGrowingBigFoodRespawn();
    public int getGrowingBigFoodProbability();
    public int getKillerFoodRadius();
    public boolean getKillerFoodRespawn();
    public int getKillerFoodProbability();
    public int getGrowingFoodValue();
    public double getGrowingFoodRadius();
    public boolean getGrowingFoodRespawn();
    public int getGrowingFoodProbability();
    public int getDeathFoodValue();
    public double getDeathFoodRadius();
    public boolean getDeathFoodSpawnInCenter();
    public double getShieldFoodRadius();
    public int getShieldValue();
    public boolean getShieldFoodRespawn();
    public int getShieldFoodProbability();
    public double getAverageFoodRadius();

    public default double getAverageFoodArea(){
        return Math.PI * getAverageFoodRadius() * getAverageFoodRadius();
    }
    
}
