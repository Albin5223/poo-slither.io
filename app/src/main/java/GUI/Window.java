package GUI;


import javax.swing.JFrame;

public class Window extends JFrame {

    AccueilPage accueilPage;
    public Window() {
        this.setTitle("Snake");
        this.setSize(800, 800);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        accueilPage = new AccueilPage(this);



        this.setContentPane(accueilPage);
        this.setVisible(true);
    }
    
}
