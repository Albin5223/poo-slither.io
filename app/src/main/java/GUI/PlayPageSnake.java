package GUI;

import model.SnakeData;
import model.engine.EngineSnake;
import interfaces.Coordinate;
import interfaces.Data;
import interfaces.Observer;
import interfaces.Orientation.Direction;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import model.plateau.SnakeInteger;
import javafx.scene.paint.Color;

public class PlayPageSnake extends Pane implements Observer<Integer, Direction>{
    
    Window window;
    EngineSnake engine;

    private int D_X;
    private int D_Y;

    public PlayPageSnake(Window window, int D_X, int D_Y) {
        this.window = window;
        this.D_X = D_X;
        this.D_Y = D_Y;
        
    }

    public void setEngine(EngineSnake engine){
        this.engine = engine;
    }

    @Override
    public void update(Data<Integer,Direction> data) {
        this.getChildren().clear();

        for (Coordinate<Integer,Direction> coord : data.getAllFood().keySet()) {
            int x = D_X +SnakeInteger.WIDTH_OF_SNAKE*coord.getX().intValue();
            int y = D_Y + SnakeInteger.WIDTH_OF_SNAKE*coord.getY().intValue();
            int radius = SnakeInteger.WIDTH_OF_SNAKE/2;
            Circle c = new Circle(x+radius,y+radius,radius);
            c.setFill(Paint.valueOf("#FA8072"));
            this.getChildren().add(c);
        }
        for(SnakeData<Integer,Direction> snakeData : data.getAllSnake()){
            int x_head = D_X +SnakeInteger.WIDTH_OF_SNAKE*snakeData.getHead().getX().intValue();
            int y_head = D_Y + SnakeInteger.WIDTH_OF_SNAKE*snakeData.getHead().getY().intValue();
            int witdh = SnakeInteger.WIDTH_OF_SNAKE;

            Rectangle head = new Rectangle(x_head,y_head,witdh,witdh);
            head.setFill(Color.BLACK);
            this.getChildren().add(head);

            for(Coordinate<Integer,Direction> coord : snakeData.getTail()){
                int x = D_X +SnakeInteger.WIDTH_OF_SNAKE*coord.getX().intValue();
                int y = D_Y + SnakeInteger.WIDTH_OF_SNAKE*coord.getY().intValue();
                Rectangle black_back = new Rectangle(x,y,witdh,witdh);
                black_back.setFill(Color.BLACK);
                Rectangle r = new Rectangle(x+1,y+1,witdh-2,witdh-2);
                r.setFill(snakeData.getColor());
                this.getChildren().add(black_back);
                this.getChildren().add(r);
            }
        }

        
    }}
