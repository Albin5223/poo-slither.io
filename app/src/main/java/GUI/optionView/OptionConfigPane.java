package GUI.optionView;

import javafx.scene.layout.VBox;

public class OptionConfigPane extends VBox {


    SettingToggle toggleReachableWall;
    SettingToggle toggleCollitionWithMe;
    SettingToggle toggleGreedyDeath;
    public OptionConfigPane() {
    
        toggleReachableWall = new SettingToggle("REACHABLE WALL");

        toggleCollitionWithMe = new SettingToggle("COLLITION WITH ME");
        toggleGreedyDeath = new SettingToggle("GREEDY DEATH");
        this.getChildren().addAll(toggleReachableWall,toggleCollitionWithMe,toggleGreedyDeath);
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


}
