package GUI.PlayPage;


import java.util.ArrayList;
import java.util.List;

import GUI.Window;
import externData.ImageBank;
import externData.OurColors;
import interfaces.Data;
import interfaces.Observer;
import interfaces.Orientation.Angle;
import javafx.scene.effect.Blend;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import model.FoodData;
import model.SnakeData;
import model.coordinate.Coordinate;
import model.coordinate.CoordinateDouble;
import model.plateau.PlateauDouble.BorderDouble;
import model.skins.Skin;
import model.skins.SkinFactory; 

public class PlayPageSlither extends Pane implements Observer<Double, Angle>{

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
    public void update(Data<Double,Angle> data) {
        this.getChildren().clear();

        Coordinate<Double,Angle> location = data.getMainSnakeCenter();
        if(location == null){
            location = new CoordinateDouble(0.,0.);
        }

        int D_X = (int) (Window.WITDH/2 - location.getX().doubleValue());
        int D_Y = (int) (Window.HEIGHT/2 - location.getY().doubleValue());

        BorderDouble border = (BorderDouble) data.getGameBorder();

        /*
         * On affiche les foods seulement dans une certaine mesure :
         * On ne les affiche que si elles sont dans un cercle de centre border.getCenter() et de rayon border.getMinRadius()
         * Cela permet de ne pas afficher les foods qui sont trop loin de centre de l'écran
         * 
         * ATTENTION :
         * Quand on passera à la version ou il y a un snake principal à suivre, il faudra placer le centre sur ce snake
         */
        double renderRadius = Math.min(Window.HEIGHT, Window.WITDH);
        List<FoodData<Double,Angle>> allFood = data.getAllFood(location,renderRadius);   // Avoid recalculating it
        for (FoodData<Double,Angle> food : allFood) {
            Image image = ImageBank.getFoodImage(food.getFoodApparence());
            if(image != null){
                ImageView imageView = new ImageView(image);
                imageView.setX(D_X + food.getCenter().getX().doubleValue() - food.getRadius());
                imageView.setY(D_Y + food.getCenter().getY().doubleValue() - food.getRadius());
                imageView.setFitHeight(food.getRadius()*2);
                imageView.setFitWidth(food.getRadius()*2);
                this.getChildren().add(imageView);
            }
            else{
                Circle c = new Circle(D_X + food.getCenter().getX().doubleValue(), D_Y + food.getCenter().getY().doubleValue(), food.getRadius());
                c.setFill(Color.BLACK);
                this.getChildren().add(c);
            }
        }

        ArrayList<SnakeData<Double,Angle>> allSnakes = data.getAllSnake(); // Avoid recalculating it
        for(SnakeData<Double,Angle> snakeData : allSnakes){

            Skin skin = SkinFactory.build(snakeData.getSkinType());
            ArrayList<OurColors> tail_pattern = skin.getTailPattern();
            int tail_pattern_size = tail_pattern.size();

            int i = 0;
            boolean shielded = snakeData.isShielded();
            boolean poisoned = snakeData.isPoisoned();

            List<Coordinate<Double,Angle>> tail = snakeData.getTail().reversed();  // Avoid recalculating it
            for(Coordinate<Double,Angle> coord : tail){
                double x = D_X + coord.getX().doubleValue();
                double y = D_Y +  coord.getY().doubleValue();

                // Drawing the tail
                Image image = ImageBank.getCircleImage(tail_pattern.get(i%tail_pattern_size));
                if(image != null){
                    ImageView imageView = new ImageView(image);
                    imageView.setX(x - snakeData.getRadius());
                    imageView.setY(y - snakeData.getRadius());
                    imageView.setFitHeight(snakeData.getRadius()*2);
                    imageView.setFitWidth(snakeData.getRadius()*2);
                    if(shielded){shieldEffect(imageView);}
                    if(poisoned){poisonEffect(imageView, snakeData.getRadius());}
                    this.getChildren().add(imageView);
                }
                else{
                    Circle c = new Circle(x,y,snakeData.getRadius());
                    c.setFill(tail_pattern.get(i%tail_pattern_size).toColorJavaFX());
                    this.getChildren().add(c);
                }
                i++;
            }

            double x_head = D_X + snakeData.getHead().getX().intValue();
            double y_head = D_Y + snakeData.getHead().getY().intValue();

            // Drawing the head
            OurColors head_color = skin.getHeadColor();
            Image image = ImageBank.getCircleEyesImage(head_color);
            if(image != null){
                ImageView imageView = new ImageView(image);
                imageView.setX(x_head - snakeData.getRadius());
                imageView.setY(y_head - snakeData.getRadius());
                imageView.setFitHeight(snakeData.getRadius()*2);
                imageView.setFitWidth(snakeData.getRadius()*2);

                Angle orientation = snakeData.getOrientation();
                imageView.setRotate(orientation.getAngle());

                if(shielded){shieldEffect(imageView);}
                if(poisoned){poisonEffect(imageView, snakeData.getRadius());}

                this.getChildren().add(imageView);
            }
            else{
                Circle head = new Circle(x_head,y_head,snakeData.getRadius());
                head.setFill(Color.BLACK);
                this.getChildren().add(head);
            }
        }

        Circle c = new Circle(D_X + border.getCenter().getX().doubleValue(), D_Y + border.getCenter().getY().doubleValue(), border.getRadius());
        c.setFill(Color.TRANSPARENT);
        c.setStroke(Color.BLACK);
        this.getChildren().add(c);
    }}