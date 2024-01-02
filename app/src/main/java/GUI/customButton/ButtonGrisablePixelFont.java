package GUI.customButton;

public class ButtonGrisablePixelFont extends ButtonPixelFont {

    private boolean grise;

    public ButtonGrisablePixelFont(String text, int size, boolean grisedAtStart) {
        super(text, size);
        this.grise = grisedAtStart;
        if(grise){
            this.textPixelFont.setOpacity(0.5);
        }
        else{
            this.textPixelFont.setOpacity(1);
        }
    }

    public boolean switchGrise() {
        grise = !grise;
        if (grise) {
            this.textPixelFont.setOpacity(0.5);
        } else {
            this.textPixelFont.setOpacity(1);
        }
        return grise;
    }

    public boolean isGrise() {
        return grise;
    }
}
