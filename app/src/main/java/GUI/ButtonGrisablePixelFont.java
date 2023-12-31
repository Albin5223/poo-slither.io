package GUI;

public class ButtonGrisablePixelFont extends ButtonPixelFont {

    private boolean grise;

    public ButtonGrisablePixelFont(String text, int size, boolean grisedAtStart) {
        super(text, size);
        this.grise = grisedAtStart;
        this.setOnAction(e -> {
            this.grise = !this.grise;
            if (grise) {
                this.textPixelFont.setOpacity(0.5);
            }
            else {
                this.textPixelFont.setOpacity(1);
            }
        });
    }
}
