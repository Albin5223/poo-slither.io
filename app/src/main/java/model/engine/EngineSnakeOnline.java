package model.engine;

import java.util.ArrayList;

import configuration.ConfigurationFoodInteger;
import configuration.ConfigurationSnakeInteger;
import interfaces.Orientation.Direction;
import model.plateau.PlateauInteger;
import model.plateau.Snake;
import server.ServerGen;

public class EngineSnakeOnline extends EngineSnake{

    private ServerGen server;


    protected EngineSnakeOnline(PlateauInteger plateau,ServerGen server) {
        super(new ArrayList<>(), plateau);
        this.server = server;
    }


    public void addSnake(Snake<Integer,Direction> snake){
        SnakeMoverOnline<Integer,Direction> snakeMover = new SnakeMoverOnline<Integer,Direction>(snake,this, null,server);
        this.snakeMovers.add(snakeMover);

        snakeMover.start();
    }

    public static EngineSnakeOnline createEngineSnakeOnline(int width, int height,ConfigurationFoodInteger foodConfig, ConfigurationSnakeInteger config,ServerGen server){
        PlateauInteger plateau = PlateauInteger.createPlateauSnake(width, height, foodConfig, config);
        return new EngineSnakeOnline(plateau,server);
    }

    public void removeSnake(Snake<Integer,Direction> snake){
        SnakeMoverOnline<Integer,Direction> s = null;
        for(SnakeMover<Integer,Direction> snakeMover : this.snakeMovers){
            if(snakeMover.getSnake() == snake){
                s = (SnakeMoverOnline<Integer, Direction>) snakeMover;
                break;
            }
        }
        if(s == null){
            System.out.println("SnakeMoverOnline not found while removing");
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