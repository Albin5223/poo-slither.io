package configuration;

import interfaces.ConfigurationSnake;
import interfaces.Orientation.Angle;

public class ConfigurationSnakeDouble implements ConfigurationSnake {
    /* The ratio of the random coordinate to be inside the map */
    private double RANDOM_RATIO = 0.9;
    /*
     * The turning force of the snake is the angle that the snake will turn when the player press the left or right key
     */
    private Angle SLITHER_TURNING_FORCE = new Angle(4);
    private int SLITHER_GAP_BETWEEN_TAIL = 4;
    private int SLITHER_BIRTH_LENGTH = 15;
    private int SLITHER_MAX_FOOD_CHARGING = 10;
    private int SLITHER_BIRTH_HITBOX_RADIUS = 10;
    private int SLITHER_MAX_RADIUS = SLITHER_BIRTH_HITBOX_RADIUS * 2;

    private int SLITHER_DEFAULT_SPEED = 75;
    private int SLITHER_BOOST_SPEED = SLITHER_DEFAULT_SPEED * 3;

    /** Do we want to add food behind a dead snake ? */
    private boolean IS_DEATH_FOOD = false;
    private int DEATH_FOOD_PER_SEGMENT = 1;

    /** Are we reappearing in the opposite side of the board when traversing the wall ? */
    private boolean IS_TRAVERSABLE_WALL = false;
    private boolean CAN_COLLIDING_WITH_HIMSELF = false;
    private boolean RADIUS_IS_GROWING = true;

    public ConfigurationSnakeDouble setRandomRatio(double d){RANDOM_RATIO = d;return this;}
    public ConfigurationSnakeDouble setTurningForce(Angle a){SLITHER_TURNING_FORCE = a;return this;}
    public ConfigurationSnakeDouble setGapBetweenTail(int i){SLITHER_GAP_BETWEEN_TAIL = i;return this;}
    public ConfigurationSnakeDouble setBirthLength(int i){SLITHER_BIRTH_LENGTH = i;return this;}
    public ConfigurationSnakeDouble setMaxFoodCharging(int i){SLITHER_MAX_FOOD_CHARGING = i;return this;}
    public ConfigurationSnakeDouble setBirthHitboxRadius(int i){SLITHER_BIRTH_HITBOX_RADIUS = i;return this;}
    public ConfigurationSnakeDouble setDefaultSpeed(int i){SLITHER_DEFAULT_SPEED = i;return this;}
    public ConfigurationSnakeDouble setBoostSpeed(int i){SLITHER_BOOST_SPEED = i;return this;}
    public ConfigurationSnakeDouble setDeathFood(boolean b){IS_DEATH_FOOD = b;return this;}
    public ConfigurationSnakeDouble setDeathFoodPerSegment(int i){DEATH_FOOD_PER_SEGMENT = i;return this;}
    public ConfigurationSnakeDouble setTraversableWall(boolean b){IS_TRAVERSABLE_WALL = b;return this;}
    public ConfigurationSnakeDouble setCollidingWithHimself(boolean b){CAN_COLLIDING_WITH_HIMSELF = b;return this;}
    public ConfigurationSnakeDouble setRadiusGrowing(boolean b){RADIUS_IS_GROWING = b;return this;}
    public ConfigurationSnakeDouble setMaxRadius(int i){SLITHER_MAX_RADIUS = i;return this;}

    public double getRandomRatio(){return RANDOM_RATIO;}
    public Angle getTurningForce(){return SLITHER_TURNING_FORCE;}
    @Override public int getGapBetweenTail(){return SLITHER_GAP_BETWEEN_TAIL;}
    @Override public int getBirthLength(){return SLITHER_BIRTH_LENGTH;}
    @Override public int getMaxFoodCharging(){return SLITHER_MAX_FOOD_CHARGING;}
    @Override public int getBirthHitboxRadius(){return SLITHER_BIRTH_HITBOX_RADIUS;}
    @Override public int getDefaultSpeed(){return SLITHER_DEFAULT_SPEED;}
    @Override public int getBoostSpeed(){return SLITHER_BOOST_SPEED;}
    @Override public boolean isDeathFood(){return IS_DEATH_FOOD;}
    @Override public int getDeathFoodPerSegment(){return DEATH_FOOD_PER_SEGMENT;}
    @Override public boolean isTraversableWall(){return IS_TRAVERSABLE_WALL;}
    @Override public boolean isCollidingWithHimself(){return CAN_COLLIDING_WITH_HIMSELF;}
    @Override public boolean isRadiusGrowing(){return RADIUS_IS_GROWING;}
    @Override public int getMaxRadius(){return SLITHER_MAX_RADIUS;}
}
