package configuration;

import interfaces.Orientation.Angle;

public class ConfigurationSnakeDouble {
    /* The ratio of the random coordinate to be inside the map */
    public static final double RANDOM_RATIO = 0.9;
    /*
     * The turning force of the snake is the angle that the snake will turn when the player press the left or right key
     */
    public static final Angle SLITHER_TURNING_FORCE = new Angle(4);
    public static final double SLITHER_GAP_BETWEEN_TAIL = 4;
    public static final int SLITHER_BIRTH_LENGTH = 50;
    public static final int SLITHER_MAX_FOOD_CHARGING = 10;
    public static final double SLITHER_BIRTH_HITBOX_RADIUS = 10;

    public static final int SLITHER_DEFAULT_SPEED = 75;
    public static final int SLITHER_BOOST_SPEED = SLITHER_DEFAULT_SPEED * 3;

    /** Do we want to add food behind a dead snake ? */
    public static final boolean IS_DEATH_FOOD = true;
    public static final int DEATH_FOOD_PER_SEGMENT = 1;

    /** Are we reappearing in the opposite side of the board when traversing the wall ? */
    public static final boolean IS_TRAVERSABLE_WALL = true;

    public static final boolean CAN_COLLIDING_WITH_HIMSELF = false;
}
