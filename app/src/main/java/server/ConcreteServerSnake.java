package server;

import java.util.ArrayList;

import configuration.ConfigurationFoodInteger;
import configuration.ConfigurationSnakeInteger;
import interfaces.GameBorder;
import interfaces.Orientation.Direction;
import model.FoodData;
import model.SnakeData;
import model.engine.EngineSnakeOnline;
import model.paquet.snake.PaquetSnakeStoC;
import model.plateau.PlateauInteger;
import model.plateau.Snake;
import model.plateau.SnakeInteger;
import model.player.Bot.BotSnakePlayer;

import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class ConcreteServerSnake implements ServerFactory<Integer,Direction> {

    public static final int port = 3000;
    public static final int NB_BOTS_CONSTANT = 10;

    private ConfigurationFoodInteger configFood = new ConfigurationFoodInteger().setRatioOfFood(0.5/100);
    private ConfigurationSnakeInteger configSnake = new ConfigurationSnakeInteger();

    private ArrayList<ServerMain<Integer,Direction>.ConnexionHandle> clients;
    private ServerSocket server;
    
    

    private ExecutorService pool;
    private EngineSnakeOnline engine;


    public ConcreteServerSnake(){
        clients = new ArrayList<ServerMain<Integer,Direction>.ConnexionHandle>();
        engine = EngineSnakeOnline.createEngineSnakeOnline(4000, 4000, configFood, configSnake);
    }

    @Override
    public boolean getOnlyOneTurn() {
        return true;
    }

    @Override
    public GameBorder<Integer,Direction> getBorder() {
        return engine.getPlateau().getBorder();
    }

    @Override
    public Snake<Integer,Direction> createSnake() {
        return SnakeInteger.createSnakeInteger((PlateauInteger) engine.getPlateau());
    }

    @Override
    public void addSnake(Snake<Integer,Direction> snake) {
        engine.addSnake(snake);
    }

    @Override
    public void removeClient(ServerMain<Integer,Direction>.ConnexionHandle client) {
        clients.remove(client);
    }

    @Override
    public void removeSnake(Snake<Integer, Direction> snake) {
        engine.removeSnake(snake);
    }

    @Override
    public void addBot() {
        engine.addBot();
    }

    @Override
    public BotSnakePlayer removeRandomBot() {
        return engine.removeRandomBot();
    }

    @Override
    public int sizeOfClient() {
        return clients.size();
    }

    @Override
    public String getIp() {
        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            return inetAddress.getHostAddress().toString();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return "ERROR : IP NOT FOUND";
    }

    @Override
    public void shutdown() {
        if(!Thread.currentThread().isInterrupted()){    // Garantie
            Thread.currentThread().interrupt();
        }
        try {
            ArrayList<ServerMain<Integer,Direction>.ConnexionHandle> clients_copy = new ArrayList<ServerMain<Integer,Direction>.ConnexionHandle>(this.clients);
            for(ServerMain<Integer,Direction>.ConnexionHandle client : clients_copy){
                client.close();
            }
            if(server != null && !server.isClosed()){
                server.close();
            }
            engine.stop();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        
        try {
            server = new ServerSocket(getPort());
            server.setPerformancePreferences(0, 1, 0);

            for(int i = 0; i < NB_BOTS_CONSTANT; i++){
                engine.addBot();
            }

            engine.run();
            pool = Executors.newCachedThreadPool();

        } catch (IOException e) {
            System.out.println("Server closed");
        }
    }

    @Override
    public void add(ServerMain<Integer,Direction>.ConnexionHandle handle) {
        clients.add(handle);
        pool.execute(handle);
    }

    @Override
    public ServerSocket getServerSocket() {
        return server;
    }

    @Override
    public void sendObject(ObjectOutputStream oos,Snake<Integer,Direction> snake,int window_width, int window_height) {
        try {
            SnakeData<Integer,Direction> snakeData = new SnakeData<Integer, Direction>(snake);
            ArrayList<SnakeData<Integer,Direction>> snakesToDraw = engine.getAllSnake();
            ArrayList<FoodData<Integer,Direction>> foodsToDraw = snake.getPlateau().getFoods().getRenderZone(snake.getHead().getCenter(), Math.max(window_height, window_width));
            
            PaquetSnakeStoC<Integer,Direction> paquet = new PaquetSnakeStoC<Integer, Direction>(snakeData, snakesToDraw, foodsToDraw);
            oos.writeObject(paquet);
        } catch (Exception e) {
            System.out.println("Something went wrong while sending the snake data");
        }

    }

    @Override
    public int getPort() {
        return port;
    }

    @Override
    public int getNbBotsMax() {
        return NB_BOTS_CONSTANT;
    }
    
}
