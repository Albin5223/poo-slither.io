package interfaces;

public interface ConfigurationSnake {
    public int getGapBetweenTail();
    public int getBirthLength();
    public int getMaxFoodCharging();
    public int getBirthHitboxRadius();
    public int getDefaultSpeed();
    public int getBoostSpeed();
    public boolean isDeathFood();
    public int getDeathFoodSegmentModulo();
    public int getBoostFoodSegmentModulo();
    public boolean isTraversableWall();
    public boolean isCollidingWithHimself();
    public boolean isRadiusGrowing();
    public int getMaxRadius();
    public int getInvincibilityTime();
}
