package model.paquet.snake;

import java.util.ArrayList;

import interfaces.Orientation.Direction;
import model.FoodData;
import model.SnakeData;

import java.io.Serializable;

public class PaquetSnakeStoC implements Serializable{
    
    private SnakeData<Integer, Direction> snakeData;
    private ArrayList<SnakeData<Integer, Direction>> allSnake;
    private ArrayList<FoodData<Integer, Direction>> allFood;

    public SnakeData<Integer, Direction> getSnakeData() {return snakeData;}
    public ArrayList<SnakeData<Integer, Direction>> getAllSnake() {return allSnake;}
    public ArrayList<FoodData<Integer, Direction>> getAllFood() {return allFood;}

    public PaquetSnakeStoC(SnakeData<Integer, Direction> snakeData, ArrayList<SnakeData<Integer, Direction>> allSnake, ArrayList<FoodData<Integer, Direction>> allFood) {
        this.snakeData = snakeData;
        this.allSnake = allSnake;
        this.allFood = allFood;
    }
}
