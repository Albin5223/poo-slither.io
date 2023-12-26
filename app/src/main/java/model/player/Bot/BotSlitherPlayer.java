package model.player.Bot;

import java.util.Random;

import interfaces.Turnable.Turning;
import model.plateau.PlateauDouble;
import model.plateau.SnakeDouble;

public class BotSlitherPlayer implements BotPlayer {

    SnakeDouble snake;
    PlateauDouble plateau;

    public BotSlitherPlayer(SnakeDouble newSnake, PlateauDouble plateau) {
        snake = newSnake;
        this.plateau = plateau;
    }

    @Override
    public void nextTurning() {
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
