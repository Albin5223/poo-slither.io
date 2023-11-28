package controleur;


import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import interfaces.Turnable.Turning;
import model.Engine;

public class KeyboardControler implements KeyListener {


    Engine engine;
    public KeyboardControler(Engine engine) {
        this.engine = engine;
    }

    @Override
    public void keyTyped(KeyEvent e) { 
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        for(int i = 0; i < engine.getNbSnake(); i++){
            if(e.getKeyChar() == engine.getPlayers()[i].getKeyEventGoLeft()){
                engine.getSnakes()[i].setTurning(Turning.GO_LEFT);
            }
            if(e.getKeyChar() == engine.getPlayers()[i].getKeyEventGoRight()){
                engine.getSnakes()[i].setTurning(Turning.GO_RIGHT);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
       for(int i = 0; i < engine.getNbSnake(); i++){
            if(e.getKeyChar() == engine.getPlayers()[i].getKeyEventGoLeft()){
                engine.getSnakes()[i].setTurning(Turning.FORWARD);
            }
            if(e.getKeyChar() == engine.getPlayers()[i].getKeyEventGoRight()){
                engine.getSnakes()[i].setTurning(Turning.FORWARD);
            }
        }
    }
    
}
