package model;


/**
 * This class represents the player.
 * 
 * @version 1.0
 * @since 1.0
 */
public final class Player {
    
    private final char keyEventGoLeft;
    private final char keyEventGoRight;

    /**
     * Constructs a new player.
     * 
     * @param keyEventGoLeft the key event to go left
     * @param keyEventGoRight the key event to go right
     */
    public Player(char keyEventGoLeft, char keyEventGoRight) {
        this.keyEventGoLeft = keyEventGoLeft;
        this.keyEventGoRight = keyEventGoRight;
    }

    public char getKeyEventGoLeft() {
        return keyEventGoLeft;
    }
    public char getKeyEventGoRight() {
        return keyEventGoRight;
    }
}
