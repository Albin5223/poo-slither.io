package GUI;

import model.SnakeData;
import model.coordinate.Coordinate;
import model.engine.EngineSnake;
import model.foods.FoodHolder;
import interfaces.Data;
import interfaces.Observer;
import interfaces.Orientation.Direction;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import model.plateau.PlateauInteger.BorderInteger;
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
            FoodHolder<Integer> commestible = data.getAllFood().get(coord);
            Circle c = new Circle(D_X + coord.getX().doubleValue(), D_Y + coord.getY().doubleValue(), commestible.getRadius());
            c.setFill(Paint.valueOf("#FA8072"));
            this.getChildren().add(c);
        }

        for(SnakeData<Integer,Direction> snakeData : data.getAllSnake()){
            int witdh = (int) (snakeData.getRadius()*2);

            int x_head = D_X + snakeData.getHead().getX().intValue() - witdh/2;
            int y_head = D_Y + snakeData.getHead().getY().intValue() - witdh/2;

            for(Coordinate<Integer,Direction> coord : snakeData.getTail()){
                int x = D_X + coord.getX().intValue() - witdh/2;
                int y = D_Y + coord.getY().intValue() - witdh/2;

                Rectangle black_back = new Rectangle(x,y,witdh,witdh);
                black_back.setFill(Color.BLACK);

                Rectangle r = new Rectangle(x+1,y+1,witdh-2,witdh-2);
                r.setFill(snakeData.getColor());

                this.getChildren().add(black_back);
                this.getChildren().add(r);
            }

            Rectangle head = new Rectangle(x_head,y_head,witdh,witdh);
            head.setFill(Color.BLACK);
            this.getChildren().add(head);
        }

        BorderInteger border = (BorderInteger) data.getGameBorder();
        Rectangle c = new Rectangle(D_X + border.getxMin(), D_Y + border.getyMin(), border.getxMax() - border.getxMin(), border.getyMax() - border.getyMin());
        c.setFill(Color.TRANSPARENT);
        c.setStroke(Color.BLACK);
        this.getChildren().add(c);
        
    }}
