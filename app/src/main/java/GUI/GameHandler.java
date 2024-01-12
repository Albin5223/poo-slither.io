package GUI;


import GUI.PlayPage.PlayPageSnake;
import client.ClientFactory;
import client.ClientMain;
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



    public GameHandler(){
        serverSnake = new ConcreteServerSnake();
        clientSnake = new ClientSnake();
        networkHandlerSnake = new NetworkHandler<>(serverSnake,clientSnake);
        //networkHandlerSlither = new NetworkHandler<>(null,null);
        
    }
    

    class NetworkHandler<Type extends Number & Comparable<Type>,O extends Orientation<O>>{
        protected ServerMain<Type,O> serverMain;
        protected ClientMain<Type,O> clientMain;
        protected Thread serverThread;
        private Thread clientThread;
        

        

        private Task<Void>  clientTask;

        public NetworkHandler(ServerFactory<Type,O> serverFactory,ClientFactory<Type,O> clientFactory){
            
            this.serverMain = new ServerMain<>(serverFactory);
            this.clientMain = new ClientMain<>(clientFactory);
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

    public void setKeyCodeClientSnake(KeyEvent ev){
        networkHandlerSnake.setKeyCode(ev);
    } 


    public boolean isServerSnakeDone(){
        return networkHandlerSnake.isServerDone();
    }

    public void startServerSnake(){
        networkHandlerSnake.startServer();
    }

    public void startClientSnake(){
        networkHandlerSnake.startClient();
    }

    public void stopClientSnake(){
        networkHandlerSnake.stopClient();
    }

    public void stopServerSnake(){
        networkHandlerSnake.stopServer();
    }


    public String getServerSnakeIp(){
        return networkHandlerSnake.getServerIp();
    }

    public void setClientSnakePseudo(String pseudo){
        networkHandlerSnake.setClientPseudo(pseudo);
    }

    public void setClientSnakeIp(String ip){
        networkHandlerSnake.setClientIp(ip);
    }

    public void setClientSnakeclientSnakeSkin(Skin skin){
        networkHandlerSnake.setClientSkin(skin);
    }

    public PlayPageSnake getClientSnakePlayPage() {
        return clientSnake.getPlayPage();
    }
    
    public void setClientSnakeSkin(Skin skin){
        networkHandlerSnake.setClientSkin(skin);
    }

    public void setOfflineSlither(EngineSlither offlineSlither) {
        this.offlineSlither = offlineSlither;
    }

    public void setOfflineSlither(EngineSnake offlineSnake) {
        this.offlineSnake = offlineSnake;
    }

    public EngineSlither getOfflineSlither() {
        return offlineSlither;
    }

    public EngineSnake getOfflineSnake() {
        return offlineSnake;
    }

    public void setReleasedKeyCodeClientSnake(KeyEvent ev){
        networkHandlerSnake.setReleasedKeyCode(ev);
    }






    public boolean isServerSlitherDone(){
        return networkHandlerSlither.isServerDone();
    }

    public String getServerSlitherIp(){
        return networkHandlerSlither.getServerIp();
    }

    public void stopClientSlither(){
        networkHandlerSlither.stopClient();
    }

    public void stopServerSlither(){
        networkHandlerSlither.stopServer();
    }

    public void startServerSlither(){
        networkHandlerSlither.startServer();
    }

    public void setClientSlitherPseudo(String pseudo){
        networkHandlerSlither.setClientPseudo(pseudo);
    }
}
