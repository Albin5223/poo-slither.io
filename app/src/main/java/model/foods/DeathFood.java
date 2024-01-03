package model.foods;

import externData.ImageBank;
import externData.OurColors;
import interfaces.Orientation;
import javafx.scene.image.Image;
import model.coordinate.Coordinate;
import model.foods.FoodFactory.FoodType;
import model.plateau.Snake;

public abstract class DeathFood<Type extends Number & Comparable<Type>, O extends Orientation<O>> extends Food<Type,O> {

    private final int value;

    public DeathFood(Coordinate<Type, O> coordinate, double radius, int value) {
        super(coordinate, radius, false, 0);
        this.value = value;
    }

    @Override
    public final void actOnSnake(Snake<Type,O> snake){
        snake.chargeFood(value);
    }

    @Override
    public final FoodType getFoodType() {
        return FoodType.DEATH_FOOD;
    }

    @Override
    public Image getImage(){
        OurColors color = OurColors.getRandomColor();
        return ImageBank.getCircleImage(color);
    }
}
