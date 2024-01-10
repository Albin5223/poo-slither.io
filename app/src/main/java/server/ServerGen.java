package server;

import java.util.ArrayList;
import server.ServerSnake.ConnexionHandle;

public interface ServerGen extends Runnable {

    public final static int port = 3000;

    public void sendInformationsToDrawToAll();

    
    public String getIp();
    public void shutdown();
   

    public ArrayList<ConnexionHandle> getClients();




    
}
