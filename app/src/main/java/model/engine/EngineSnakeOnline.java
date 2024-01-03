package model.engine;

import java.util.ArrayList;

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

    public static EngineSnakeOnline createEngineSnakeOnline(int width, int height){
        PlateauInteger plateau = PlateauInteger.createPlateauSnake(width, height);
        return new EngineSnakeOnline(plateau);
    }

    public void removeSnake(SnakeMover<Integer,Direction> snakeMover){
        this.snakeMovers.remove(snakeMover);
        snakeMover.stop();
    }

    public void stop(){
        for(SnakeMover<Integer,Direction> snakeMover : this.snakeMovers){
            snakeMover.stop();
        }
    }
}