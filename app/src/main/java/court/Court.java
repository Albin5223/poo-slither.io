package court;


import java.util.TimerTask;


import GUI.PlayPage;
import model.Engine;

public class Court extends TimerTask{

    PlayPage playPage;
    Engine engine;
    TimerTask timerTask;

    public Court(PlayPage play, Engine engine) {
        this.playPage = play;
        this.engine = engine;
    }

    @Override
    public void run() {
        engine.move();
    }    
}
