package GUI;


import interfaces.Court;
import interfaces.Data;
import interfaces.Observer;
import javafx.animation.AnimationTimer;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import model.CoordinateDouble;

public class PlayPage extends Pane implements Observer{
    
    Window window;
    Court court;

    private int D_X;
    private int D_Y;

    AnimationTimer aTimer;

    public PlayPage(Window window, int D_X, int D_Y) {
        this.window = window;
        this.D_X = D_X;
        this.D_Y = D_Y;
        
    }

    public void setCourt(Court court){
        this.court = court;
    }

    
    public void animate(){
        aTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                court.update();
            }
    		
    	};
    	aTimer.start();
    }

    public void stopAnimate(){
        aTimer.stop();
    }


    @Override
    public void update(Data data) {
        this.getChildren().clear();
        for(CoordinateDouble coord : data.getAllPosition()){
            Circle c = new Circle(D_X+coord.getX(), D_Y+coord.getY(), data.getRadius());
            this.getChildren().add(c);
        }
    }}