package model;

import interfaces.Coordinate;
import interfaces.Orientation;
import javafx.scene.paint.Color;
import model.plateau.Snake;
import java.util.ArrayList;

import model.plateau.Snake.SnakePart;

public class SnakeData<Type extends Number, O extends Orientation> {

    private Snake<Type, O> snake;
    private Color color;

    public SnakeData(Snake<Type, O> snake, Color color) {
        this.snake = snake;
        this.color = color;
    }

    public Coordinate<Type, O> getHead() {
        return snake.getHead().getCenter();
    }

    public Color getColor() {
        return color;
    }

    public ArrayList<Coordinate<Type, O>> getTail() {
        SnakePart<Type, O>[] tail = snake.getTail();
        ArrayList<Coordinate<Type, O>> tailCoordinate = new ArrayList<Coordinate<Type, O>>();
        for (SnakePart<Type, O> snakePart : tail) {
            tailCoordinate.add(snakePart.getCenter());
        }
        return tailCoordinate;
    }
    
    
}
