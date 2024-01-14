package model.player.Bot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import interfaces.Orientation.Direction;
import interfaces.Turnable.Turning;
import model.coordinate.Coordinate;
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
        List<Turning> shuffledTurning = new ArrayList<Turning>(Arrays.asList(Turning.values()));
        Collections.shuffle(shuffledTurning, new Random());

        // Generate a random number between 0 and 100
        int randomNumber = new Random().nextInt(100);

        // If the random number is less than 70, move FORWARD to the front of the list
        if (randomNumber < 70 && shuffledTurning.remove(Turning.FORWARD)) {
            shuffledTurning.add(0, Turning.FORWARD);
        }

        for (Turning turn : shuffledTurning) {
            Direction snakeDir = snake.getDirection();
            Coordinate<Integer,Direction> nextCord = snake.getHead().getCenter().placeCoordinateFrom(snakeDir.changeDirectionWithTurn(turn), snake.getGapBetweenTail());
            snake.setTurning(turn);
            if(!plateau.willYouCollideWith(nextCord, snake.getHitboxRadius(), snake)){   // Check if the snake will collide with himself or not
                break;
            }
        }
    }

    public SnakeInteger getSnake() {
        return snake;
    }

}
