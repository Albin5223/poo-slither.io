package GUI;


import interfaces.Coordinate;
import interfaces.Data;
import interfaces.Observer;
import interfaces.Orientation.Angle;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import model.SnakeData;
import model.engine.EngineSlither;
import model.foods.FoodHolder;
import model.plateau.PlateauDouble.BorderDouble; 

public class PlayPageSlither extends Pane implements Observer<Double, Angle>{
    
    Window window;
    EngineSlither engine;

    private int D_X;
    private int D_Y;

    public PlayPageSlither(Window window, int D_X, int D_Y) {
        this.window = window;
        this.D_X = D_X;
        this.D_Y = D_Y;
        
    }

    public void setEngine(EngineSlither court){
        this.engine = court;
    }

    @Override
    public void update(Data<Double,Angle> data) {
        this.getChildren().clear();

        for (Coordinate<Double,Angle> coord : data.getAllFood().keySet()) {
            FoodHolder<Double> commestible = data.getAllFood().get(coord);
            Circle c = new Circle(D_X + coord.getX().doubleValue(), D_Y + coord.getY().doubleValue(), commestible.getRadius());
            c.setFill(Paint.valueOf("#FA8072"));
            this.getChildren().add(c);
        }

        for(SnakeData<Double,Angle> snakeData : data.getAllSnake()){
            double x_head = D_X + snakeData.getHead().getX().intValue();
            double y_head = D_Y + snakeData.getHead().getY().intValue();

            for(Coordinate<Double,Angle> coord : snakeData.getTail()){
                double x = D_X + coord.getX().doubleValue();
                double y = D_Y +  coord.getY().doubleValue();

                Circle r = new Circle(x,y,snakeData.getRadius());
                r.setFill(snakeData.getColor());

                this.getChildren().add(r);
            }

            Circle head = new Circle(x_head,y_head,snakeData.getRadius());
            head.setFill(Color.BLACK);
            this.getChildren().add(head);
        }

        BorderDouble border = (BorderDouble) data.getGameBorder();
        Circle c = new Circle(D_X + border.getCenter().getX().doubleValue(), D_Y + border.getCenter().getY().doubleValue(), border.getRadius());
        c.setFill(Color.TRANSPARENT);
        c.setStroke(Color.BLACK);
        this.getChildren().add(c);
    }}