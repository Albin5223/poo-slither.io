package model.engine;

import java.util.ArrayList;

import configuration.ConfigurationFoodDouble;
import configuration.ConfigurationSnakeDouble;
import interfaces.Orientation.Angle;
import model.plateau.PlateauDouble;
import model.plateau.Snake;

public class EngineSlitherOnline extends EngineSlither{

    protected EngineSlitherOnline(PlateauDouble plateau) {
        super(new ArrayList<>(), plateau);
    }


    public void addSnake(Snake<Double,Angle> snake){
        SnakeMover<Double,Angle> snakeMover = new SnakeMover<Double,Angle>(snake,this, null);
        this.snakeMovers.add(snakeMover);

        snakeMover.start();
    }

    public static EngineSlitherOnline createEngineSlitherOnline(int rayon,ConfigurationFoodDouble foodConfig, ConfigurationSnakeDouble config){
        PlateauDouble plateau = PlateauDouble.createPlateauSlitherio(rayon, foodConfig, config);
        return new EngineSlitherOnline(plateau);
    }

    public void removeSnake(Snake<Double,Angle> snake){
        SnakeMover<Double,Angle> s = null;
        for(SnakeMover<Double,Angle> snakeMover : this.snakeMovers){
            if(snakeMover.getSnake() == snake){
                s = (SnakeMover<Double, Angle>) snakeMover;
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
        for(SnakeMover<Double,Angle> snakeMover : this.snakeMovers){
            snakeMover.stop();
        }
    }
}