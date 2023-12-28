package model.skins;

import java.util.ArrayList;

import externData.OurColors;
import interfaces.Orientation;
import javafx.scene.image.Image;
import model.plateau.Snake;

public abstract class Skin<Type extends Number & Comparable<Type>, O extends Orientation<O>> {

    protected OurColors head_color;
    protected ArrayList<OurColors> tail_pattern;

    protected final String subFolder;

    public Skin(OurColors head_color, ArrayList<OurColors> tail_pattern, String subFolder) {
        this.head_color = head_color;
        this.tail_pattern = tail_pattern;
        this.subFolder = subFolder;
    }

    public boolean applyToSnake(Snake<Type,O> snake) {
        String head_name = head_color.toString();
        Image head_image = new Image("file:src/main/resources/"+subFolder+"/"+head_name+"_eyes.png", snake.getHead().getHitboxRadius() * 2, snake.getHead().getHitboxRadius() * 2, false, false);
        if(head_image.isError()){return false;}
        //snake.getHead().setImage(head_image);

        ArrayList<Snake<Type,O>.SnakePart> tail = snake.getTail();
        for (int i = 0; i < tail.size(); i++) {
            Snake<Type,O>.SnakePart tail_part = tail.get(i);
            OurColors tail_color = tail_pattern.get(i % tail_pattern.size());
            Image tail_image = new Image("file:src/main/resources/"+subFolder+"/"+tail_color.toString()+".png", tail_part.getHitboxRadius() * 2, tail_part.getHitboxRadius() * 2, false, false);
            if(tail_image.isError()){return false;}
            //tail_part.setImage(tail_image);
        }
        return true;
    }
    
}
