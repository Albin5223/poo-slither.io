package model.engine;

import java.util.ArrayList;

import interfaces.Orientation.Direction;
import model.plateau.PlateauInteger;
import model.plateau.Snake;
import server.Server;

public class EngineSnakeOnline extends EngineSnake{

    private Server server;


    protected EngineSnakeOnline(PlateauInteger plateau,Server server) {
        super(new ArrayList<>(), plateau);
        this.server = server;
    }


    public void addSnake(Snake<Integer,Direction> snake){
        SnakeMoverOnline<Integer,Direction> snakeMover = new SnakeMoverOnline<Integer,Direction>(snake,this, null,server);
        this.snakeMovers.add(snakeMover);

        snakeMover.start();
    }

    public static EngineSnakeOnline createEngineSnakeOnline(int width, int height,Server server){
        PlateauInteger plateau = PlateauInteger.createPlateauSnake(width, height);
        return new EngineSnakeOnline(plateau,server);
    }

    public void removeSnake(Snake<Integer,Direction> snake){
        for(SnakeMover<Integer,Direction> snakeMover : this.snakeMovers){
            if(snakeMover.getSnake() == snake){
                snakeMover.stop();
                this.snakeMovers.remove(snakeMover);
                break;
            }
        }
    }

    public void stop(){
        for(SnakeMover<Integer,Direction> snakeMover : this.snakeMovers){
            snakeMover.stop();
        }
    }
}