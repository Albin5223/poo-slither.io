package model.paquet.snake;

import java.util.ArrayList;

import model.FoodData;
import model.SnakeData;

import java.io.Serializable;

public class PaquetSnakeStoC implements Serializable{
    
    private SnakeData<?, ?> snakeData;
    private ArrayList<SnakeData<?, ?>> allSnake;
    private ArrayList<FoodData<?, ?>> allFood;

    public SnakeData<?, ?> getSnakeData() {return snakeData;}
    public ArrayList<SnakeData<?, ?>> getAllSnake() {return allSnake;}
    public ArrayList<FoodData<?, ?>> getAllFood() {return allFood;}

    public PaquetSnakeStoC(SnakeData<?, ?> snakeData, ArrayList<SnakeData<?, ?>> snakesToDraw, ArrayList<FoodData<?, ?>> foodsToDraw) {
        this.snakeData = snakeData;
        this.allSnake = snakesToDraw;
        this.allFood = foodsToDraw;
    }
}
