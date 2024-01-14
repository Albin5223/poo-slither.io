package model.engine;
import java.util.ArrayList;
import java.util.List;

import configuration.ConfigurationFoodDouble;
import configuration.ConfigurationSnakeDouble;
import controleur.KeyboardControler;
import interfaces.Engine;
import interfaces.HumanPlayer;
import interfaces.Observer;
import interfaces.Orientation.Angle;
import javafx.animation.AnimationTimer;
import javafx.scene.input.KeyEvent;
import model.FoodData;
import model.SnakeData;
import model.coordinate.Coordinate;
import model.plateau.Plateau;
import model.plateau.PlateauDouble;
import model.plateau.PlateauDouble.BorderDouble;
import model.plateau.Snake;
import model.plateau.SnakeDouble;
import model.player.HumanMousePlayer;
import model.player.HumanSlitherPlayer;
import model.player.Bot.BotSlitherPlayer;


public class EngineSlither implements Engine<Double,Angle>{
    
    protected PlateauDouble plateau;
    protected ArrayList<SnakeMover<Double,Angle>> snakeMovers;
    protected ArrayList<Observer<Double,Angle>> observers;
    protected ArrayList<HumanSlitherPlayer> players;
    protected ArrayList<BotSlitherPlayer> bots;


    protected HumanMousePlayer mousePlayer;
    protected AnimationTimer animationEffect;
    protected long lastUpdate = 0;

    protected EngineSlither(ArrayList<SnakeDouble> snakes, PlateauDouble plateau){
        this.snakeMovers = new ArrayList<SnakeMover<Double,Angle>>();
        for(SnakeDouble snake : snakes){
            this.snakeMovers.add(new SnakeMover<Double,Angle>(snake,this,null));
        }
        this.plateau = plateau;
        this.bots = new ArrayList<BotSlitherPlayer>();
        this.observers = new ArrayList<Observer<Double,Angle>>();
        this.players = new ArrayList<HumanSlitherPlayer>();

        animationEffect = new AnimationTimer() {
            @Override
            public void handle(long now) {
                
                if((now-lastUpdate) >= 1_000_000_000){
                    //System.out.println("Update");
                    List<Snake<Double,Angle>> snakes = new ArrayList<>(plateau.getHashMap().values());
                    for(Snake<Double,Angle> snake : snakes){
                        if(snake.underEffect()){
                            snake.applyEffect();
                        }
                    }
                    lastUpdate = now;
                }
                
            }
        };
    }

    public static EngineSlither createGame(int radius, ConfigurationFoodDouble foodConfig, ConfigurationSnakeDouble snakeConfig){
        ArrayList<SnakeDouble> s = new ArrayList<>();
        PlateauDouble p = PlateauDouble.createPlateauSlitherio(radius, foodConfig, snakeConfig);
        return new EngineSlither(s,p);
    }

    public void addBot(){
        SnakeDouble newSnake = SnakeDouble.createSnakeDouble(plateau);
        BotSlitherPlayer newBot = new BotSlitherPlayer(newSnake, plateau);
        bots.add(newBot);
        snakeMovers.add(new SnakeMover<Double,Angle>(newSnake,this,newBot)); 
    }

    public BotSlitherPlayer removeRandomBot(){
        if(bots.size() > 0){
            int index = (int) (Math.random() * bots.size());
            BotSlitherPlayer bot = bots.get(index);
            bots.remove(index);
            SnakeMover<Double,Angle> botMover = snakeMovers.remove(index);
            botMover.stop();
            plateau.removeSnake(bot.getSnake());
            return bot;
        }
        return null;
    }


    public void addPlayerMouse(){
        SnakeDouble newSnake = SnakeDouble.createSnakeDouble(plateau);
        mousePlayer = new HumanMousePlayer(newSnake);
        snakeMovers.add(new SnakeMover<Double,Angle>(newSnake,this,null));
        notifyObservers();
    }

    public HumanMousePlayer getMousePlayer() {
        return mousePlayer;
    }

    public void addPlayer(KeyboardControler<Double,Angle> snakeControler ){
        SnakeDouble newSnake = SnakeDouble.createSnakeDouble(plateau);
        HumanSlitherPlayer newPlayer = new HumanSlitherPlayer(newSnake, snakeControler);
        players.add(newPlayer);
        snakeMovers.add(new SnakeMover<Double,Angle>(newSnake,this,null));
        notifyObservers();
    }

    @Override
    public void addObserver(Observer<Double,Angle> o) {
        observers.add(o);
    }


    @Override
    public void removeObserver(Observer<Double,Angle> o) {
        observers.remove(o);
    }


    @Override
    public void notifyObservers() {
        for (Observer<Double,Angle> observer : observers) {
            observer.update(this);
        }
    }

    @Override
    public List<FoodData<Double,Angle>> getAllFood(Coordinate<Double,Angle> coordinate, double radius) {
        return plateau.getFoods().getRenderZone(coordinate, radius);
    }

    @Override
    public BorderDouble getGameBorder() {
        return (BorderDouble) plateau.getBorder();
    }

    public ArrayList<HumanSlitherPlayer> getPlayers() {
        return players;
    }

    @Override
    public Plateau<Double, Angle> getPlateau() {
        return plateau;
    }

   
    @Override
    public ArrayList<SnakeData<Double, Angle>> getAllSnake() {
        ArrayList<SnakeData<Double, Angle>> allSnake = new ArrayList<SnakeData<Double, Angle>>();
        for(int i = 0; i < snakeMovers.size(); i++){
            allSnake.add(new SnakeData<Double, Angle>(snakeMovers.get(i).getSnake()));
        }
        return allSnake;
    }

    @Override
    public void makePressed(KeyEvent ev, HumanPlayer player) {
        for(HumanPlayer p : players){
            if(p == player){
                p.keyPressed(ev);
                return;
            }
        }
    }

    @Override
    public void makeReleased(KeyEvent ev, HumanPlayer player) {
        for(HumanPlayer p : players){
            if(p == player){
                p.keyReleased(ev);
                return;
            }
        }
    }

    @Override
    public void run() {
        animationEffect.start();
        if(mousePlayer != null){
            mousePlayer.startMouseThread();
        }
        for(SnakeMover<Double,Angle> snakeMover : snakeMovers){
            snakeMover.start();
        }
    }

    @Override
    public void stop() {
        animationEffect.stop();
        if(mousePlayer != null){
            mousePlayer.stopMouseThread();
        }
        for(SnakeMover<Double,Angle> snakeMover : snakeMovers){
            snakeMover.stop();
        }
    }

    @Override
    public Coordinate<Double,Angle> getMainSnakeCenter() {
        if((players.size() == 1 && mousePlayer == null)){
            return players.get(0).getSnake().getHead().getCenter();
        }
        else if((players.size() == 0 && mousePlayer != null)){
            return mousePlayer.getSnake().getHead().getCenter();
        }
        else{
            return null;
        }
    }

}
