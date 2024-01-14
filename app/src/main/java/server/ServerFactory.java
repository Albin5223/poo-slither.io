package server;

import interfaces.GameBorder;
import interfaces.Orientation;
import model.plateau.Snake;
import model.player.Bot.BotPlayer;

import java.io.ObjectOutputStream;
import java.net.ServerSocket;

public interface ServerFactory <Type extends Number & Comparable<Type>, O extends Orientation<O>>{
    public GameBorder<Type,O> getBorder(); //engine.getPlateau().getBorder()

    public Snake<Type,O> createSnake(); //SnakeInteger.createSnakeInteger((PlateauInteger) engine.getPlateau());

    public void addSnake(Snake<Type,O> snake); // engine.addSnake(snake);

    public void removeClient(ServerMain<Type,O>.ConnexionHandle client);

    public void removeSnake(Snake<Type,O> snake); // engine.removeSnake(snake);

    public void addBot(); // engine.addBot();

    public BotPlayer removeRandomBot(); // engine.removeRandomBot();

    public int sizeOfClient();

    public String getIp();

    public int getPort();

    public void shutdown();

    public void run();

    public ServerSocket getServerSocket();

    public void add(ServerMain<Type,O>.ConnexionHandle handle);

    public void sendObject(ObjectOutputStream oos,Snake<Type,O> snakeFromMain,int window_width, int window_height);

    public boolean getOnlyOneTurn();

    public int getNbBotsMax();


}
