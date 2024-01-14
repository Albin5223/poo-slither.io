package GUI.optionView;


import java.util.ArrayList;

import configuration.TouchControler;
import javafx.scene.input.KeyCode;

public class SetOfConfiguration {

    public static final int NB_BOTS_MAX = 10;
    
    private static int numberOfPlayer;
    private static int numberOfHumanPlayer;
    private static int numberOfBotPlayer;

    private static int indexOfPlayerMouse = -1;

    public static void setIndexOfPlayerMouse(int indexOfPlayerMouse) {
        SetOfConfiguration.indexOfPlayerMouse = indexOfPlayerMouse;
    }

    public static int getIndexOfPlayerMouse() {
        return indexOfPlayerMouse;
    }

    public static ArrayList<TouchControler> commandMapingPanes = new ArrayList<>();


    private static KeyCode[] keyCodesPlayer1 = new KeyCode[5];
    private static KeyCode[] keyCodesPlayer2 = new KeyCode[5];

    public static void initKeyCodePlayer(int playerNumber, KeyCode[] keyCodes){
        if(playerNumber == 1){
            for(int i = 0; i < keyCodes.length; i++){
                keyCodesPlayer1[i] = keyCodes[i];
            }
        }else if(playerNumber == 2){
            for(int i = 0; i < keyCodes.length; i++){
                keyCodesPlayer2[i] = keyCodes[i];
            }
        }
    }

    public static void incrementNumberOfHuman(){
        if(numberOfHumanPlayer <2){
            numberOfHumanPlayer++;
        } 
    }


    public static void resetConfiguration(){
        numberOfHumanPlayer = 0;
        numberOfBotPlayer = 0;
        numberOfPlayer = 0;
        commandMapingPanes.clear();
        indexOfPlayerMouse = -1;
    }



    public static void decrementNumberOfHuman(){
        if(numberOfHumanPlayer > 0){
            numberOfHumanPlayer--;
        }
        updateNumberOfPlayer();
    }

    public static void incrementNumberOfBot(){
        if(numberOfPlayer < NB_BOTS_MAX){
            numberOfBotPlayer++;
        }
        updateNumberOfPlayer();
    }

    public static void decrementNumberOfBot(){
        if(numberOfBotPlayer > 0){
            numberOfBotPlayer--;
        }
        updateNumberOfPlayer();
    }

    public static int getNumberOfBot() {
        return numberOfBotPlayer;
    }

    public static int getNumberOfHuman() {
        return numberOfHumanPlayer;
    }

    private static void updateNumberOfPlayer(){
        numberOfPlayer = numberOfHumanPlayer + numberOfBotPlayer;
    }


    public static void addCommand(TouchControler command){
        commandMapingPanes.add(command);
    }

    public static void removeCommand(TouchControler command){
        commandMapingPanes.remove(command);
    }

}
