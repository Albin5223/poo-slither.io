package model.foods;

import java.util.ArrayList;

public class AllFood<Type extends Number> {

    private final ArrayList<Food<Type>> foods = new ArrayList<>();

    public AllFood() {
        foods.add(new GrowingFood<Type>());
        foods.add(new GrowingBigFood<Type>());
        //foods.add(new KillerFood<Type>());
    }

    public ArrayList<Food<Type>> getAllFoods() {
        return new ArrayList<>(foods);
    }

    public ArrayList<Food<Type>> getRespawnableFoods() {
        ArrayList<Food<Type>> respawnableFoods = new ArrayList<>();
        for (Food<Type> food : foods) {
            if (food.isRespawnable()) {
                respawnableFoods.add(food);
            }
        }
        return respawnableFoods;
    }

    public Food<Type> getRandomFood() {
        ArrayList<Food<Type>> respawnableFoods = getRespawnableFoods();
        int randomIndex = (int) (Math.random() * respawnableFoods.size());
        return respawnableFoods.get(randomIndex);
    }

    public Food<Type> getRandomRespawnableFood() {
        ArrayList<Food<Type>> respawnableFoods = getRespawnableFoods();
        int randomIndex = (int) (Math.random() * respawnableFoods.size());
        return respawnableFoods.get(randomIndex);
    }
    
}
