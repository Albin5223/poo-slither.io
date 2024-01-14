package interfaces;

import javafx.scene.input.KeyEvent;

public interface HumanPlayer {
    public void keyPressed(KeyEvent ev);
    public void keyReleased(KeyEvent ev);

    public void mouseMoved(double x, double y,boolean isCenter);
    public void mousePressed(boolean isCenter);
    public void mouseReleased(boolean isCenter);
    public default void mouseDragged(double x, double y,boolean isCenter){
        mouseMoved(x,y,isCenter);
        mousePressed(isCenter);
    }
}
