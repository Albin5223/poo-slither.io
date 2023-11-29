package GUI;

import java.util.Timer;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controleur.KeyboardControler;
import court.Court;
import exceptions.ExecptionAddSnake;
import model.CoordinateDouble;
import model.Engine;

public class AccueilPage extends JPanel {

    
    JLabel title;
    JButton play;

    private static final Font TITLE_FONT = new Font("Arial", Font.BOLD, 50);
    private static final Font BUTTON_FONT = new Font("Arial", Font.BOLD, 30);
    

    public AccueilPage(Window window) {
        this.setLayout(new BorderLayout());
        setLayout(new GridBagLayout());

        title = new JLabel("Snake");
        title.setFont(TITLE_FONT);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 50, 0);  // Ajout de marge en bas
        add(title, gbc);


        play = new JButton("Play");
        play.setFont(BUTTON_FONT);

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(play, gbc);

        play.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Engine engine = Engine.createSnake(2);
                PlayPage playPage = new PlayPage(window);
                KeyboardControler keyboardControler = new KeyboardControler(engine);
                playPage.addKeyBoardListener(keyboardControler);
                engine.addObserver(playPage);
                engine.notifyObservers();

                try {
                    engine.addPlayerWithCoord(new CoordinateDouble(-50, -50),'a','e');
                    engine.getSnakes()[0].grow(100);
                    engine.addPlayerWithCoord(new CoordinateDouble(50, 50),'o','p');
                    engine.getSnakes()[1].grow(30);
                } catch (ExecptionAddSnake e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }

                window.setContentPane(playPage);
                window.revalidate();
                window.repaint();

                Court court = new Court(playPage, engine);
                Timer timer = new Timer(true);
                timer.schedule(court, 0, 100);
            }
        });
        
    }
    
}
