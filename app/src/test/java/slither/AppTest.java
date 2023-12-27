/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package slither;

import org.junit.jupiter.api.Test;

import model.coordinate.CoordinateInteger;
import model.plateau.PlateauInteger;
import model.plateau.SnakeInteger;

import static org.junit.jupiter.api.Assertions.*;


class AppTest {
    App app = new App();

    @Test
    void testCompareTo(){
        CoordinateInteger c1 = new CoordinateInteger(1,1);
        CoordinateInteger c2 = new CoordinateInteger(1,1);
        assertEquals(0, c1.compareTo(c2));
    }

    @Test
    void testSnakeGrow(){
        PlateauInteger plateau = PlateauInteger.createPlateauSnake(100,100);
        SnakeInteger snake = SnakeInteger.createSnakeInteger(plateau);
        int n = snake.getTail().size();
        snake.chargeFood(snake.MAX_FOOD_CHARGING);
        assertEquals(n+1, snake.getTail().size());
    }

    @Test
    void testSnakeShrink(){
        PlateauInteger plateau = PlateauInteger.createPlateauSnake(100,100);
        SnakeInteger snake = SnakeInteger.createSnakeInteger(plateau);
        int n = snake.getTail().size();
        snake.chargeFood(snake.MAX_FOOD_CHARGING);
        snake.shrink();
        assertEquals(n, snake.getTail().size());
    }

    
    @Test void testApp() {
        assertTrue(true);
    }
}
