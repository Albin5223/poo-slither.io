package model.player.Bot;

import java.util.Random;

import interfaces.Turnable.Turning;
import model.plateau.PlateauInteger;
import model.plateau.SnakeInteger;

public class BotSnakePlayer implements BotPlayer {
    
    SnakeInteger snake;
    PlateauInteger plateau;
    
    public BotSnakePlayer(SnakeInteger newSnake, PlateauInteger plateau){
        snake = newSnake;
        this.plateau = plateau;
    }

    @Override
    public void nextTurning(){
        Random rand = new Random();
        int direction = rand.nextInt(3);
        switch(direction){
            case 0:
                snake.setTurning(Turning.GO_LEFT);;
                break;
            case 1:
                snake.setTurning(Turning.GO_RIGHT);;
                break;
            case 2:
                snake.setTurning(Turning.FORWARD);
                break;
        }
    }


}
