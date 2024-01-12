package model.engine;

import java.util.ArrayList;

import configuration.ConfigurationFoodDouble;
import configuration.ConfigurationSnakeDouble;
import interfaces.Orientation.Angle;
import model.plateau.PlateauDouble;
import model.plateau.Snake;
import server.ServerFactory;

public class EngineSlitherOnline extends EngineSlither{

    private ServerFactory<Double,Angle> server;


    protected EngineSlitherOnline(PlateauDouble plateau,ServerFactory<Double,Angle> server) {
        super(new ArrayList<>(), plateau);
        this.server = server;
    }


    public void addSnake(Snake<Double,Angle> snake){
        SnakeMoverOnline<Double,Angle> snakeMover = new SnakeMoverOnline<Double,Angle>(snake,this, null,server);
        this.snakeMovers.add(snakeMover);

        snakeMover.start();
    }

    public static EngineSlitherOnline createEngineSnakeOnline(int rayon,ConfigurationFoodDouble foodConfig, ConfigurationSnakeDouble config,ServerFactory<Double,Angle> server){
        PlateauDouble plateau = PlateauDouble.createPlateauSlitherio(rayon, foodConfig, config);
        return new EngineSlitherOnline(plateau,server);
    }

    public void removeSnake(Snake<Double,Angle> snake){
        SnakeMoverOnline<Double,Angle> s = null;
        for(SnakeMover<Double,Angle> snakeMover : this.snakeMovers){
            if(snakeMover.getSnake() == snake){
                s = (SnakeMoverOnline<Double, Angle>) snakeMover;
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
        for(SnakeMover<Double,Angle> snakeMover : this.snakeMovers){
            snakeMover.stop();
        }
    }
}