package server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import configuration.ConfigurationFoodDouble;
import configuration.ConfigurationSnakeDouble;
import interfaces.GameBorder;
import interfaces.Orientation.Angle;
import model.FoodData;
import model.SnakeData;
import model.engine.EngineSlitherOnline;
import model.paquet.snake.PaquetSnakeStoC;
import model.plateau.PlateauDouble;
import model.plateau.Snake;
import model.plateau.SnakeDouble;

public class ConcreteServerSlither implements ServerFactory<Double,Angle> {

    public static final int port = 4000;

    private ConfigurationFoodDouble configFood = new ConfigurationFoodDouble();
    private ConfigurationSnakeDouble configSnake = new ConfigurationSnakeDouble();

    private ArrayList<ServerMain<Double,Angle>.ConnexionHandle> clients;
    private ServerSocket server;
    
    

    private ExecutorService pool;
    private EngineSlitherOnline engine;


    public ConcreteServerSlither(){
        clients = new ArrayList<ServerMain<Double,Angle>.ConnexionHandle>();
        engine = EngineSlitherOnline.createEngineSlitherOnline(4000, configFood, configSnake);
    }


    @Override
    public GameBorder<Double, Angle> getBorder() {
        return engine.getPlateau().getBorder();
    }

    @Override
    public Snake<Double, Angle> createSnake() {
        return SnakeDouble.createSnakeDouble((PlateauDouble) engine.getPlateau());
    }

    @Override
    public void addSnake(Snake<Double, Angle> snake) {
        engine.addSnake(snake);
    }

    @Override
    public void removeClient(ServerMain<Double, Angle>.ConnexionHandle client) {
        clients.remove(client);
    }

    @Override
    public void removeSnake(Snake<Double, Angle> snake) {
        engine.removeSnake(snake);
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
            ArrayList<ServerMain<Double,Angle>.ConnexionHandle> clients_copy = new ArrayList<ServerMain<Double,Angle>.ConnexionHandle>(this.clients);
            for(ServerMain<Double,Angle>.ConnexionHandle client : clients_copy){
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
            engine.run();
            pool = Executors.newCachedThreadPool();

        } catch (IOException e) {
            System.out.println("Server closed");
        }
    }

    @Override
    public boolean getOnlyOneTurn() {
        return false;
    }

    @Override
    public ServerSocket getServerSocket() {
        return server;
    }

    @Override
    public void add(ServerMain<Double, Angle>.ConnexionHandle handle) {
        clients.add(handle);
        pool.execute(handle);
    }

    @Override
    public void sendObject(ObjectOutputStream oos, Snake<Double, Angle> snake, int window_width,
            int window_height) {
        
        SnakeData<Double,Angle> snakeData = new SnakeData<>(snake);
        ArrayList<SnakeData<Double,Angle>> snakesToDraw = engine.getAllSnake();
        ArrayList<FoodData<Double,Angle>> foodsToDraw = snake.getPlateau().getFoods().getRenderZone(snake.getHead().getCenter(), Math.max(window_height, window_width));
        
        PaquetSnakeStoC<Double,Angle> paquet = new PaquetSnakeStoC<>(snakeData, snakesToDraw, foodsToDraw);
        try {
            oos.writeObject(paquet);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    @Override
    public int getPort() {
        return port;
    }
    
}
