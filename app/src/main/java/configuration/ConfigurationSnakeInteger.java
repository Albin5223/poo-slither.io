package configuration;

import interfaces.ConfigurationSnake;

public class ConfigurationSnakeInteger implements ConfigurationSnake {

    /*Configuration for the snake */
    private boolean IS_ALIGN_WITH_SNAKE = true;

    private int SNAKE_BIRTH_LENGTH = 10;
    private int SNAKE_MAX_FOOD_CHARGING = 5;
    private int SNAKE_BIRTH_HITBOX_RADIUS = 10;
    private int SNAKE_MAX_RADIUS = SNAKE_BIRTH_HITBOX_RADIUS;
    private int SNAKE_GAP_BETWEEN_TAIL = SNAKE_BIRTH_HITBOX_RADIUS*2;

    private int SNAKE_DEFAULT_SPEED = 15;
    private int SNAKE_BOOST_SPEED = SNAKE_DEFAULT_SPEED * 2;

    /** Do we want to add food behind a dead snake ? */
    private boolean IS_DEATH_FOOD = true;
    private int DEATH_FOOD_PER_SEGMENT = 1;

    /** Are we reappearing in the opposite side of the board when traversing the wall ? */
    private boolean TRAVERSABLE_WALL = true;
    private boolean CAN_COLLIDING_WITH_HIMSELF = true;
    private boolean RADIUS_IS_GROWING = false;

    public ConfigurationSnakeInteger setAlignWithSnake(boolean b){IS_ALIGN_WITH_SNAKE = b;return this;}
    public ConfigurationSnakeInteger setBirthLength(int i){SNAKE_BIRTH_LENGTH = i;return this;}
    public ConfigurationSnakeInteger setMaxFoodCharging(int i){SNAKE_MAX_FOOD_CHARGING = i;return this;}
    public ConfigurationSnakeInteger setBirthHitboxRadius(int i){SNAKE_BIRTH_HITBOX_RADIUS = i;return this;}
    public ConfigurationSnakeInteger setGapBetweenTail(int i){SNAKE_GAP_BETWEEN_TAIL = i;return this;}
    public ConfigurationSnakeInteger setDefaultSpeed(int i){SNAKE_DEFAULT_SPEED = i;return this;}
    public ConfigurationSnakeInteger setBoostSpeed(int i){SNAKE_BOOST_SPEED = i;return this;}
    public ConfigurationSnakeInteger setDeathFood(boolean b){IS_DEATH_FOOD = b;return this;}
    public ConfigurationSnakeInteger setDeathFoodPerSegment(int i){DEATH_FOOD_PER_SEGMENT = i;return this;}
    public ConfigurationSnakeInteger setTraversableWall(boolean b){TRAVERSABLE_WALL = b;return this;}
    public ConfigurationSnakeInteger setCollidingWithHimself(boolean b){CAN_COLLIDING_WITH_HIMSELF = b;return this;}
    public ConfigurationSnakeInteger setRadiusGrowing(boolean b){RADIUS_IS_GROWING = b;return this;}
    public ConfigurationSnakeInteger setMaxRadius(int i){SNAKE_MAX_RADIUS = i;return this;}

    public boolean isAlignWithSnake(){return IS_ALIGN_WITH_SNAKE;}
    @Override public int getBirthLength(){return SNAKE_BIRTH_LENGTH;}
    @Override public int getMaxFoodCharging(){return SNAKE_MAX_FOOD_CHARGING;}
    @Override public int getBirthHitboxRadius(){return SNAKE_BIRTH_HITBOX_RADIUS;}
    @Override public int getGapBetweenTail(){return SNAKE_GAP_BETWEEN_TAIL;}
    @Override public int getDefaultSpeed(){return SNAKE_DEFAULT_SPEED;}
    @Override public int getBoostSpeed(){return SNAKE_BOOST_SPEED;}
    @Override public boolean isDeathFood(){return IS_DEATH_FOOD;}
    @Override public int getDeathFoodPerSegment(){return DEATH_FOOD_PER_SEGMENT;}
    @Override public boolean isTraversableWall(){return TRAVERSABLE_WALL;}
    @Override public boolean isCollidingWithHimself(){return CAN_COLLIDING_WITH_HIMSELF;}
    @Override public boolean isRadiusGrowing(){return RADIUS_IS_GROWING;}
    @Override public int getMaxRadius(){return SNAKE_MAX_RADIUS;}
}
