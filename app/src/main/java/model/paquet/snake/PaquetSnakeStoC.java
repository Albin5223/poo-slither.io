package model.paquet.snake;

import java.util.ArrayList;

import interfaces.Orientation;
import model.FoodData;
import model.SnakeData;

import java.io.Serializable;

public class PaquetSnakeStoC<Type extends Number & Comparable<Type>, O extends Orientation<O>> implements Serializable{
    
    private SnakeData<Type, O> snakeData;
    private ArrayList<SnakeData<Type, O>> allSnake;
    private ArrayList<FoodData<Type, O>> allFood;

    public SnakeData<Type, O> getSnakeData() {return snakeData;}
    public ArrayList<SnakeData<Type, O>> getAllSnake() {return allSnake;}
    public ArrayList<FoodData<Type,O>> getAllFood() {return allFood;}

    public PaquetSnakeStoC(SnakeData<Type, O> snakeData, ArrayList<SnakeData<Type, O>> snakesToDraw, ArrayList<FoodData<Type, O>> foodsToDraw) {
        this.snakeData = snakeData;
        this.allSnake = snakesToDraw;
        this.allFood = foodsToDraw;
    }
}
