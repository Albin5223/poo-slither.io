package model.paquet;

import java.io.Serializable;

import interfaces.Orientation.Direction;
import model.coordinate.Coordinate;
import model.plateau.PlateauInteger;
import model.plateau.PlateauInteger.BorderInteger;

public class FrameSnake implements Serializable {

    private Coordinate<Integer,Direction> center;

    private BorderInteger border;
    private PlateauInteger plateau;

    public FrameSnake(Coordinate<Integer,Direction> center,PlateauInteger plateau) {
        this.center = center;
        this.plateau = plateau;
    }

    public BorderInteger getBorder() {
        return border;
    }

    public PlateauInteger getPlateau() {
        return plateau;
    }

    public Coordinate<Integer, Direction> getCenter() {
        return center;
    }
    
}
