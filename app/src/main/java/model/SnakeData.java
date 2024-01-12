package model;

import interfaces.Orientation;
import model.coordinate.Coordinate;
import model.plateau.Snake;
import model.skins.SkinFactory.SkinType;

import java.util.ArrayList;

public class SnakeData<Type extends Number & Comparable<Type>, O extends Orientation<O>> {

    private Coordinate<Type, O> head;
    private ArrayList<Coordinate<Type, O>> tail;
    private SkinType skin;
    private boolean shielded;
    private boolean poisoned;
    private double radius;
    private O orientation;

    public SnakeData(Snake<Type, O> snake) {
        this.head = snake.getHead().getCenter();
        this.tail = new ArrayList<Coordinate<Type, O>>();
        for (Snake<Type,O>.SnakePart snakePart : snake.getTail()) {
            this.tail.add(snakePart.getCenter());
        }
        this.skin = snake.getSkin();
        this.shielded = snake.isShielded();
        this.poisoned = snake.isPoisoned();
        this.radius = snake.getHitboxRadius();
        this.orientation = snake.getHead().getOrientation();
    }

    public SkinType getSkinType() {
        return skin;
    }

    public boolean isShielded() {
        return shielded;
    }

    public boolean isPoisoned() {
        return poisoned;
    }

    public Coordinate<Type, O> getHead() {
        return head;
    }

    public O getOrientation() {
        return orientation;
    }

    public double getRadius() {
        return radius;
    }

    public ArrayList<Coordinate<Type, O>> getTail() {
        return tail;
    }
    
    
}
