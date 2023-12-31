package GUI.optionView;


import javafx.scene.input.KeyCode;

public class SetOfConfiguration {
    
    private static int numberOfPlayer;
    private static int numberOfHumanPlayer;
    private static int numberOfBotPlayer;



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



    public static void decrementNumberOfHuman(){
        if(numberOfHumanPlayer > 0){
            numberOfHumanPlayer--;
        }
        updateNumberOfPlayer();
    }

    public static void incrementNumberOfBot(){
        if(numberOfPlayer < 6){
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
    
}
