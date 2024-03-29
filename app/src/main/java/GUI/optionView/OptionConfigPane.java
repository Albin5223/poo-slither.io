package GUI.optionView;

import javafx.scene.layout.VBox;

public class OptionConfigPane extends VBox {


    SettingToggle toggleReachableWall;
    SettingToggle toggleCollitionWithMe;
    SettingToggle toggleGreedyDeath;
    SettingToggle toggleGrowingSnake;

    private int textSize = 25;
    
    public OptionConfigPane() {
        setSpacing(10);
        toggleReachableWall = new SettingToggle("REACHABLE WALL :", textSize);
        toggleCollitionWithMe = new SettingToggle("COLLITION WITH ME :", textSize);
        toggleGreedyDeath = new SettingToggle("GENEROUS DEATH :", textSize);
        toggleGrowingSnake = new SettingToggle("GROWING SNAKES :", textSize);
        this.getChildren().addAll(toggleReachableWall,toggleCollitionWithMe,toggleGreedyDeath,toggleGrowingSnake);
    }

    public boolean isReachableWallActivated(){
        return toggleReachableWall.isActivated();
    }

    public boolean isCollitionWithMeActivated(){
        return toggleCollitionWithMe.isActivated();
    }

    public boolean greedyDeathActivated(){
        return toggleGreedyDeath.isActivated();
    }

    public boolean growingSnakeActivated(){
        return toggleGrowingSnake.isActivated();
    }


}
