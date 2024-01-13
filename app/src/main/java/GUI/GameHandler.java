package GUI;

import client.ClientFactory;
import client.ClientMain;
import client.ClientSlither;
import client.ClientSnake;
import configuration.ConfigurationFoodDouble;
import configuration.ConfigurationFoodInteger;
import configuration.ConfigurationSnakeDouble;
import configuration.ConfigurationSnakeInteger;
import interfaces.Orientation;
import interfaces.Orientation.Angle;
import interfaces.Orientation.Direction;
import javafx.concurrent.Task;
import javafx.scene.input.KeyEvent;
import model.engine.EngineSlither;
import model.engine.EngineSnake;
import model.skins.Skin;
import server.ConcreteServerSlither;
import server.ConcreteServerSnake;
import server.ServerFactory;
import server.ServerMain;

public class GameHandler {

    private EngineSlither offlineSlither;
    public final ConfigurationFoodDouble configFoodSlither = new ConfigurationFoodDouble();
    public ConfigurationFoodDouble getConfigFoodSlither() {
        return configFoodSlither;
    }
    public final ConfigurationSnakeDouble configSnakeSlither = new ConfigurationSnakeDouble();
    public ConfigurationSnakeDouble getConfigSnakeSlither() {
        return configSnakeSlither;
    }

    private EngineSnake offlineSnake;
    public final ConfigurationFoodInteger configFoodSnake = new ConfigurationFoodInteger();
    public ConfigurationFoodInteger getConfigFoodSnake() {
        return configFoodSnake;
    }
    public final ConfigurationSnakeInteger configSnakeSnake = new ConfigurationSnakeInteger();
    public ConfigurationSnakeInteger getConfigSnakeSnake() {
        return configSnakeSnake;
    }

    private NetworkHandler<Double,Angle> networkHandlerSlither;
    private NetworkHandler<Integer,Direction> networkHandlerSnake;

    private ConcreteServerSnake serverSnake;
    private ClientSnake clientSnake;

    private ConcreteServerSlither serverSlither;
    private ClientSlither clientSlither;



    public GameHandler(){
        serverSnake = new ConcreteServerSnake();
        clientSnake = new ClientSnake();
        networkHandlerSnake = new NetworkHandler<>(serverSnake,clientSnake);

        serverSlither = new ConcreteServerSlither();
        clientSlither = new ClientSlither();
        networkHandlerSlither = new NetworkHandler<>(serverSlither,clientSlither);
        
    }
    

    class NetworkHandler<Type extends Number & Comparable<Type>,O extends Orientation<O>>{
        protected ServerMain<Type,O> serverMain;
        protected ClientMain<Type,O> clientMain;
        protected Thread serverThread;
        private Thread clientThread;
        

        

        private Task<Void>  clientTask;

        public NetworkHandler(ServerFactory<Type,O> serverFactory,ClientFactory<Type,O> clientFactory){
            
            this.serverMain = new ServerMain<>(serverFactory);
            this.clientMain = new ClientMain<>(clientFactory, serverFactory.getPort());
            this.serverThread = new Thread(serverMain);
            clientTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                // long-running task
                clientMain.run();
                return null;
            }
        };
            this.clientThread = new Thread(clientTask);

        }

        public void startServer(){
            serverThread.start();
        }

        public void startClient(){
            clientThread.start();
        }

        public void stopServer(){
            serverMain.shutdown();
            serverThread = new Thread(serverMain);
        }

        public void stopClient(){
            clientMain.shutdown();
            clientThread = new Thread(clientMain);
        }

        public boolean isServerDone(){
            return !serverThread.isAlive();
        }

        public String getServerIp(){
            return serverMain.getIp();
        }

        public void setClientIp(String ip){
            clientMain.setIp(ip);
        }

        public void setClientPseudo(String pseudo){
            clientMain.setPseudo(pseudo);
        }

        public void setClientSkin(Skin skin){
            clientMain.setSkin(skin);
        } 

        public void setKeyCode(KeyEvent ev){
            clientMain.setKeyCode(ev);
        }

        public void setReleasedKeyCode(KeyEvent ev){
            clientMain.setReleasedKeyCode(ev);
        }
    }

    public void setOfflineSnake(EngineSnake offlineSnake) {
        this.offlineSnake = offlineSnake;
    }

    public void setOfflineSlither(EngineSlither offlineSlither) {
        this.offlineSlither = offlineSlither;
    }

    public EngineSlither getOfflineSlither() {
        return offlineSlither;
    }

    public EngineSnake getOfflineSnake() {
        return offlineSnake;
    }


    public NetworkHandler<Double, Angle> getNetworkHandlerSlither() {
        return networkHandlerSlither;
    }

    public NetworkHandler<Integer, Direction> getNetworkHandlerSnake() {
        return networkHandlerSnake;
    }

    public ClientSlither getClientSlither() {
        return clientSlither;
    }

    public ClientSnake getClientSnake() {
        return clientSnake;
    }
}
