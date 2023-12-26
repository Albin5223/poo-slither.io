package interfaces;

/** A class implements the {@code Collisable<T>} interface to indicate to the {@link #isColliding} method that it can be colliding with an object of type {@code T}.
 * @param <T> the type of the object that can be colliding with the object implementing this interface
 */
public interface Collisable<T> {
    
    public boolean isCollidingWith(T other);

}
