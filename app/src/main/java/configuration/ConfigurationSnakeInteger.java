package configuration;

public class ConfigurationSnakeInteger{

    /*Configuration for the snake */
    public static final boolean IS_ALIGN_WITH_SNAKE = true;


    public static final int SNAKE_BIRTH_LENGTH = 10;
    public static final int SNAKE_MAX_FOOD_CHARGING = 5;
    public static final int SNAKE_BIRTH_HITBOX_RADIUS = 10;
    public static final Integer SNAKE_GAP_BETWEEN_TAIL = SNAKE_BIRTH_HITBOX_RADIUS*2;

    public static final int SNAKE_DEFAULT_SPEED = 10;
    public static final int SNAKE_BOOST_SPEED = SNAKE_DEFAULT_SPEED * 2;

    /** Do we want to add food behind a dead snake ? */
    public static final boolean DEATH_FOOD = true;
    public static final int DEATH_FOOD_PER_SEGMENT = 1;

    /** Are we reappearing in the opposite side of the board when traversing the wall ? */
    public static final boolean TRAVERSABLE_WALL = true;

}
