package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import interfaces.Data;
import interfaces.Observer;
import model.CoordinateDouble;
import model.SnakeDouble;
import controleur.KeyboardControler;

public class PlayPage extends JPanel implements Observer{
    
    JPanel panelGame;
    Window window;

    public static final int SNAKE_WIDTH = 10;


    /*
     * X_D is the X of the center of the screen
     * Y_D is the Y of the center of the screen
     * 
     * X_D and Y_D are used to calculate the position of the snake for (0,0) is in the center of the page
     * 
     */
    public int X_D;
    private int Y_D; 

    public PlayPage(Window window) {
        this.setLayout(new BorderLayout());
        panelGame = new JPanel();
        this.window = window;

        X_D = window.getWidth()/2;
        Y_D = window.getHeight()/2;
    }

    public void addKeyBoardListener(KeyboardControler keyboardControler){
        window.requestFocus();
        window.addKeyListener(keyboardControler);

    }

    @Override
    public void update(Data data) {
        if(panelGame!=null){
            this.remove(panelGame);
        }

        panelGame = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                for (CoordinateDouble coordinate : data.getAllPosition()) {
                    g.setColor(Color.BLACK);
                    g.fillOval((int)(X_D+coordinate.getX()), (int)(Y_D+coordinate.getY()), (int)SnakeDouble.SnakePartDouble.hitboxRadius, (int)SnakeDouble.SnakePartDouble.hitboxRadius);
                    //g.fillRect(X_D+(coordinate.getX()*SNAKE_WIDTH),Y_D+(coordinate.getY()*SNAKE_WIDTH), SNAKE_WIDTH, SNAKE_WIDTH);
                }
            }
        };
        
        this.add(panelGame);
        window.revalidate();
        panelGame.repaint();
    }

    

    
}