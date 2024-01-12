package model.engine;

import java.util.ArrayList;

import configuration.ConfigurationFoodInteger;
import configuration.ConfigurationSnakeInteger;
import interfaces.Orientation.Direction;
import model.plateau.PlateauInteger;
import model.plateau.Snake;

public class EngineSnakeOnline extends EngineSnake{

    protected EngineSnakeOnline(PlateauInteger plateau) {
        super(new ArrayList<>(), plateau);
    }


    public void addSnake(Snake<Integer,Direction> snake){
        SnakeMover<Integer,Direction> snakeMover = new SnakeMover<Integer,Direction>(snake,this, null);
        this.snakeMovers.add(snakeMover);

        snakeMover.start();
    }

    public static EngineSnakeOnline createEngineSnakeOnline(int width, int height,ConfigurationFoodInteger foodConfig, ConfigurationSnakeInteger config){
        PlateauInteger plateau = PlateauInteger.createPlateauSnake(width, height, foodConfig, config);
        return new EngineSnakeOnline(plateau);
    }

    public void removeSnake(Snake<Integer,Direction> snake){
        SnakeMover<Integer,Direction> s = null;
        for(SnakeMover<Integer,Direction> snakeMover : this.snakeMovers){
            if(snakeMover.getSnake() == snake){
                s = (SnakeMover<Integer, Direction>) snakeMover;
                break;
            }
        }
        if(s == null){
            System.out.println("SnakeMover not found while removing");
            return;
        }
        s.stop();
        this.snakeMovers.remove(s);
        this.plateau.removeSnake(snake);
    }

    public void stop(){
        for(SnakeMover<Integer,Direction> snakeMover : this.snakeMovers){
            snakeMover.stop();
        }
    }
}