package GUI;

import model.SnakeData;
import model.coordinate.Coordinate;
import model.engine.EngineSnake;
import model.foods.Food;

import java.util.ArrayList;

import externData.ImageBank;
import externData.OurColors;
import interfaces.Data;
import interfaces.Observer;
import interfaces.Orientation.Direction;
import javafx.scene.effect.Blend;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import model.plateau.PlateauInteger.BorderInteger;
import model.skins.Skin;
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

    private void shieldEffect(ImageView imageView){
        Glow glow = new Glow();
        glow.setLevel(10); // set the level of the glow effect
        imageView.setEffect(glow);
    }

    private void poisonEffect(ImageView imageView, double shadow_radius){
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setHue(-0.3); // change the hue of the image

        // Create a new DropShadow effect
        DropShadow dropShadow = new DropShadow();
        dropShadow.setColor(Color.GREEN); // set the color of the shadow
        dropShadow.setRadius(shadow_radius); // set the radius of the shadow

        // Combine the ColorAdjust and DropShadow effects
        Blend blend = new Blend();
        blend.setTopInput(colorAdjust);
        blend.setBottomInput(dropShadow);

        imageView.setEffect(blend);
    }

    @Override
    public void update(Data<Integer,Direction> data) {
        this.getChildren().clear();

        for (Coordinate<Integer,Direction> coord : data.getAllFood().keySet()) {
            Food<Integer,Direction> food = data.getAllFood().get(coord);
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
                Circle c = new Circle(D_X + coord.getX().doubleValue(), D_Y + coord.getY().doubleValue(), food.getRadius());
                c.setFill(Color.BLACK);
                this.getChildren().add(c);
            }
        }

        for(SnakeData<Integer,Direction> snakeData : data.getAllSnake()){
            int witdh = (int) (snakeData.getRadius()*2);

            Skin skin = snakeData.getSkin();
            ArrayList<OurColors> tail_pattern = skin.getTailPattern();
            int tail_pattern_size = tail_pattern.size();

            int i = 0;
            boolean shielded = snakeData.isShielded();
            boolean poisoned = snakeData.isPoisoned();
            for(Coordinate<Integer,Direction> coord : snakeData.getTail().reversed()){
                int x = D_X + coord.getX().intValue() - witdh/2;
                int y = D_Y + coord.getY().intValue() - witdh/2;

                // Drawing the tail
                Image image = ImageBank.getSquareImage(tail_pattern.get(i%tail_pattern_size));
                if(image != null){
                    ImageView imageView = new ImageView(image);
                    imageView.setX(x);
                    imageView.setY(y);
                    imageView.setFitHeight(witdh);
                    imageView.setFitWidth(witdh);
                    if(shielded){shieldEffect(imageView);}
                    if(poisoned){poisonEffect(imageView, snakeData.getRadius());}
                    this.getChildren().add(imageView);
                }
                else{
                    Rectangle black_back = new Rectangle(x,y,witdh,witdh);
                    black_back.setFill(Color.BLACK);
                    Rectangle r = new Rectangle(x+1,y+1,witdh-2,witdh-2);
                    r.setFill(tail_pattern.get(i%tail_pattern_size).toColorJavaFX());
                    this.getChildren().add(black_back);
                    this.getChildren().add(r);
                }
                i++;
            }

            int x_head = D_X + snakeData.getHead().getX().intValue() - witdh/2;
            int y_head = D_Y + snakeData.getHead().getY().intValue() - witdh/2;

            // Drawing the head
            OurColors head_color = snakeData.getSkin().getHeadColor();
            Image image = ImageBank.getSquareEyesImage(head_color);
            if(image != null){
                ImageView imageView = new ImageView(image);
                imageView.setX(x_head);
                imageView.setY(y_head);
                imageView.setFitHeight(witdh);
                imageView.setFitWidth(witdh);

                Direction orientation = snakeData.getOrientation();
                imageView.setRotate(orientation.getAngle());

                if(shielded){shieldEffect(imageView);}
                if(poisoned){poisonEffect(imageView, snakeData.getRadius());}

                this.getChildren().add(imageView);
            }
            else{
                Rectangle head = new Rectangle(x_head,y_head,witdh,witdh);
                head.setFill(Color.BLACK);
                this.getChildren().add(head);
            }
        }

        BorderInteger border = (BorderInteger) data.getGameBorder();
        Rectangle c = new Rectangle(D_X + border.getxMin(), D_Y + border.getyMin(), border.getxMax() - border.getxMin(), border.getyMax() - border.getyMin());
        c.setFill(Color.TRANSPARENT);
        c.setStroke(Color.BLACK);
        this.getChildren().add(c);
        
    }}
