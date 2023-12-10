package model;

import interfaces.Coordinate;
import interfaces.Orientation;
import javafx.scene.paint.Color;
import model.plateau.Snake;
import java.util.ArrayList;

public class SnakeData<Type extends Number, O extends Orientation<O>> {

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
        ArrayList<Snake<Type,O>.SnakePart> tail = snake.getTail();
        ArrayList<Coordinate<Type, O>> tailCoordinate = new ArrayList<Coordinate<Type, O>>();   // TODO : peut-être faire un map
        for (Snake<Type,O>.SnakePart snakePart : tail) {
            tailCoordinate.add(snakePart.getCenter());
        }
        return tailCoordinate;
    }
    
    
}
