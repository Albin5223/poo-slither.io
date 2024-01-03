package model.coordinate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import interfaces.Orientation;
import model.foods.Food;
import model.plateau.Snake;

public class Grid<Type extends Number & Comparable<Type>, O extends Orientation<O>> implements Serializable{
    private static final int gridSize = 200;
    private final HashMap<Coordinate<Type, O>, List<Food<Type, O>>> grid = new HashMap<>();
    private final List<Food<Type, O>> allFood = new ArrayList<>();
    private int size = 0;

    public boolean insert(Food<Type, O> food) {
        Coordinate<Type, O> gridCoordinate = getGridCoordinate(food.getCenter());
        List<Food<Type, O>> foods = grid.computeIfAbsent(gridCoordinate, k -> new ArrayList<>());
        
        if (!foods.contains(food)) {
            foods.add(food);
            allFood.add(food);
            size+=1;
            return true;
        }
        
        return false;
    }

    public List<Food<Type, O>> getNearbyFoods(Snake<Type,O> snake) {
        List<Food<Type, O>> nearbyFoods = new ArrayList<>();
        int gridRadius = (int) Math.ceil(snake.getHitboxRadius() / gridSize);
    
        int centerX = (int) (snake.getHead().getCenter().getX().doubleValue() / gridSize);
        int centerY = (int) (snake.getHead().getCenter().getY().doubleValue() / gridSize);
    
        for (int x = centerX - gridRadius; x <= centerX + gridRadius; x++) {
            for (int y = centerY - gridRadius; y <= centerY + gridRadius; y++) {
                Coordinate<Type, O> gridCoordinate = snake.getHead().getCenter().clone(x, y);
                nearbyFoods.addAll(grid.getOrDefault(gridCoordinate, Collections.emptyList()));
            }
        }
    
        return nearbyFoods;
    }

    public List<Food<Type, O>> getRenderZone(Coordinate<Type, O> coordinate, double radius) {
        List<Food<Type, O>> nearbyFoods = new ArrayList<>();
        int gridRadius = (int) Math.ceil(radius / gridSize);
    
        int centerX = (int) (coordinate.getX().doubleValue() / gridSize);
        int centerY = (int) (coordinate.getY().doubleValue() / gridSize);
    
        for (int x = centerX - gridRadius; x <= centerX + gridRadius; x++) {
            for (int y = centerY - gridRadius; y <= centerY + gridRadius; y++) {
                Coordinate<Type, O> gridCoordinate = coordinate.clone(x, y);
                List<Food<Type, O>> foodsInGrid = grid.getOrDefault(gridCoordinate, Collections.emptyList());
                for (Food<Type, O> food : foodsInGrid) {
                    double distance = coordinate.distanceTo(food.getCenter());
                    if (distance <= radius) {
                        nearbyFoods.add(food);
                    }
                }
            }
        }
    
        return nearbyFoods;
    }

    private Coordinate<Type, O> getGridCoordinate(Coordinate<Type, O> coordinate) {
        int x = (int) (coordinate.getX().doubleValue() / gridSize);
        int y = (int) (coordinate.getY().doubleValue() / gridSize);
        return coordinate.clone(x, y);
    }

    public boolean remove(Food<Type,O> food) {
        Coordinate<Type, O> gridCoordinate = getGridCoordinate(food.getCenter());
        List<Food<Type, O>> foods = grid.get(gridCoordinate);
        
        if (foods != null && foods.remove(food)) {
            allFood.remove(food);
            size--;
            // If there are no more foods in this grid square, remove the square from the grid
            if (foods.isEmpty()) {
                grid.remove(gridCoordinate);
            }
            return true;
        }
        return false;
    }

    public int size() {
        return size;
    }
}
