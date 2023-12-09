package GUI;

import model.SnakeData;

import interfaces.Coordinate;
import interfaces.Court;
import interfaces.Data;
import interfaces.Observer;
import interfaces.Orientation;
import javafx.animation.AnimationTimer;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import model.plateau.SnakeInteger;

public class PlayPageSnake extends Pane implements Observer{
    
    Window window;
    Court court;

    private int D_X;
    private int D_Y;

    AnimationTimer aTimer;
    private static final double UPDATE_INTERVAL = 0.05e9; // Interval en nanosecondes (0.05 seconde)


    public PlayPageSnake(Window window, int D_X, int D_Y) {
        this.window = window;
        this.D_X = D_X;
        this.D_Y = D_Y;
        
    }

    public void setCourt(Court court){
        this.court = court;
    }

    
    public void animate(){
        aTimer = new AnimationTimer() {

            private long lastUpdate = 0;

            @Override
            public void handle(long now) {
                if (now - lastUpdate >= UPDATE_INTERVAL) {
                    court.update();
                    lastUpdate = now;
                }
            }
    		
    	};
    	aTimer.start();
    }

    public void stopAnimate(){
        aTimer.stop();
    }


    @Override
    public void update(Data<? extends Number, ? extends Orientation> data) {
        this.getChildren().clear();

        for (Coordinate<? extends Number, ? extends Orientation> coord : data.getAllFood().keySet()) {
            int x = D_X +SnakeInteger.WIDTH_OF_SNAKE*coord.getX().intValue();
            int y = D_Y + SnakeInteger.WIDTH_OF_SNAKE*coord.getY().intValue();
            int radius = SnakeInteger.WIDTH_OF_SNAKE/2;
            Circle c = new Circle(x+radius,y+radius,radius);
            c.setFill(Paint.valueOf("#FA8072"));
            this.getChildren().add(c);
        }
        for(SnakeData<? extends Number, ? extends Orientation> snakeData : data.getAllSnake()){
            int x_head = D_X +SnakeInteger.WIDTH_OF_SNAKE*snakeData.getHead().getX().intValue();
            int y_head = D_Y + SnakeInteger.WIDTH_OF_SNAKE*snakeData.getHead().getY().intValue();
            int witdh = SnakeInteger.WIDTH_OF_SNAKE;

            Rectangle head = new Rectangle(x_head,y_head,witdh,witdh);
            head.setFill(snakeData.getColor());
            this.getChildren().add(head);

            for(Coordinate<? extends Number, ? extends Orientation> coord : snakeData.getTail()){
                int x = D_X +SnakeInteger.WIDTH_OF_SNAKE*coord.getX().intValue();
                int y = D_Y + SnakeInteger.WIDTH_OF_SNAKE*coord.getY().intValue();
                Rectangle r = new Rectangle(x,y,witdh,witdh);
                r.setFill(snakeData.getColor());
                this.getChildren().add(r);
            }
        }

        
    }}
