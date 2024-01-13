package interfaces;

import javafx.scene.input.KeyEvent;

public interface HumanPlayer {
    public void keyPressed(KeyEvent ev);
    public void keyReleased(KeyEvent ev);

    public void mouseMoved(double x, double y);
    public void mousePressed(double x, double y);
    public void mouseReleased(double x, double y);
}
