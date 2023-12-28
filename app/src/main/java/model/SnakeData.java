package model;

import interfaces.Orientation;
import model.coordinate.Coordinate;
import model.plateau.Snake;
import model.skins.Skin;

import java.util.ArrayList;

public class SnakeData<Type extends Number & Comparable<Type>, O extends Orientation<O>> {

    private Snake<Type, O> snake;

    public SnakeData(Snake<Type, O> snake) {
        this.snake = snake;
    }

    public Skin<Type, O> getSkin() {
        return snake.getSkin();
    }

    public Coordinate<Type, O> getHead() {
        return snake.getHead().getCenter();
    }

    public O getOrientation() {
        return snake.getHead().getOrientation();
    }

    public double getRadius() {
        return snake.getHead().getHitboxRadius();
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
