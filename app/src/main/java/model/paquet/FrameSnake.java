package model.paquet;

import java.io.Serializable;
import java.util.ArrayList;

import interfaces.Orientation.Direction;
import model.coordinate.Coordinate;
import model.foods.Food;

public class FrameSnake implements Serializable {

    private int X_center;
    private int Y_center;
    private int widht;
    private int height;

    ArrayList<Food<Integer,Direction>> food;



    public FrameSnake (int X_center,int Y_centrer, int widht, int height){
        this.X_center = X_center;
        this.Y_center = Y_centrer;
        this.widht = widht;
        this.height = height;
        
    }

    public FrameSnake(Coordinate<Integer, Direction> center, int i, int j) {
        X_center = center.getX();
        Y_center = center.getY();
        widht = i;
        height = j;
    }
    
}
