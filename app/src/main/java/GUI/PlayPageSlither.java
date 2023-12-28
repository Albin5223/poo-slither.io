package GUI;


import java.util.ArrayList;

import externData.ImageBank;
import externData.OurColors;
import interfaces.Data;
import interfaces.Observer;
import interfaces.Orientation.Angle;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import model.SnakeData;
import model.coordinate.Coordinate;
import model.engine.EngineSlither;
import model.foods.Food;
import model.plateau.PlateauDouble.BorderDouble;
import model.skins.Skin; 

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
            Food<Double,Angle> food = data.getAllFood().get(coord);
            Image image = food.getImage();
            if(image != null){
                ImageView imageView = new ImageView(image);
                imageView.setX(D_X + coord.getX().doubleValue() - food.getRadius());
                imageView.setY(D_Y + coord.getY().doubleValue() - food.getRadius());
                imageView.setFitHeight(food.getRadius()*2);
                imageView.setFitWidth(food.getRadius()*2);
                this.getChildren().add(imageView);
            }
            else{
                System.out.println("image null");
                Circle c = new Circle(D_X + coord.getX().doubleValue(), D_Y + coord.getY().doubleValue(), food.getRadius());
                c.setFill(Color.BLACK);
                this.getChildren().add(c);
            }
        }

        for(SnakeData<Double,Angle> snakeData : data.getAllSnake()){

            Skin<Double,Angle> skin = snakeData.getSkin();
            ArrayList<OurColors> tail_pattern = skin.getTailPattern();
            int tail_pattern_size = tail_pattern.size();

            int i = 0;

            for(Coordinate<Double,Angle> coord : snakeData.getTail().reversed()){
                double x = D_X + coord.getX().doubleValue();
                double y = D_Y +  coord.getY().doubleValue();

                Image image = ImageBank.getCircleImage(tail_pattern.get(i%tail_pattern_size));
                if(image != null){
                    ImageView imageView = new ImageView(image);
                    imageView.setX(x - snakeData.getRadius());
                    imageView.setY(y - snakeData.getRadius());
                    imageView.setFitHeight(snakeData.getRadius()*2);
                    imageView.setFitWidth(snakeData.getRadius()*2);
                    this.getChildren().add(imageView);
                }
                else{
                    Circle c = new Circle(x,y,snakeData.getRadius());
                    c.setFill(snakeData.getColor());
                    this.getChildren().add(c);
                }
            }

            double x_head = D_X + snakeData.getHead().getX().intValue();
            double y_head = D_Y + snakeData.getHead().getY().intValue();

            OurColors head_color = snakeData.getSkin().getHeadColor();
            Image image = ImageBank.getCircleEyesImage(head_color);
            if(image != null){
                ImageView imageView = new ImageView(image);
                imageView.setX(x_head - snakeData.getRadius());
                imageView.setY(y_head - snakeData.getRadius());
                imageView.setFitHeight(snakeData.getRadius()*2);
                imageView.setFitWidth(snakeData.getRadius()*2);

                Angle orientation = snakeData.getOrientation();
                imageView.setRotate(orientation.getAngle());

                this.getChildren().add(imageView);
            }
            else{
                Circle head = new Circle(x_head,y_head,snakeData.getRadius());
                head.setFill(Color.BLACK);
                this.getChildren().add(head);
            }
        }

        BorderDouble border = (BorderDouble) data.getGameBorder();
        Circle c = new Circle(D_X + border.getCenter().getX().doubleValue(), D_Y + border.getCenter().getY().doubleValue(), border.getRadius());
        c.setFill(Color.TRANSPARENT);
        c.setStroke(Color.BLACK);
        this.getChildren().add(c);
    }}