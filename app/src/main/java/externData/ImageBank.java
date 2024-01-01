package externData;

import javafx.scene.image.Image;

public class ImageBank {

    private static class Pair {
        private Image image;
        private String name;

        public Pair(Image image, String name) {
            this.image = image;
            this.name = name;
        }
    }

    private static OurColors SlitherColors[] = {OurColors.BLUE, OurColors.BROWN, OurColors.GREEN, OurColors.ORANGE, OurColors.PINK, OurColors.PURPLE, OurColors.RED, OurColors.YELLOW, OurColors.GRAY, OurColors.WHITE};
    private static OurColors SnakeColors[] = {OurColors.BLUE, OurColors.BROWN, OurColors.GREEN, OurColors.ORANGE, OurColors.PINK, OurColors.PURPLE, OurColors.RED, OurColors.YELLOW, OurColors.GRAY, OurColors.WHITE};

    private static Pair[] circles = new Pair[SlitherColors.length];
    private static Pair[] circles_eyes = new Pair[SlitherColors.length];

    private static Pair[] squares = new Pair[SnakeColors.length];
    private static Pair[] squares_eyes = new Pair[SnakeColors.length];

    private static Pair[] specials = new Pair[OurSpecials.values().length];

    public static Image homePageBackground;


    public static Image getCircleImage(OurColors color) {
        for (int i = 0; i < circles.length; i++) {
            if (circles[i].name.equals(color.toString())) {
                return circles[i].image;
            }
        }
        System.out.println("Image not found : " + color.toString());
        return null;
    }

    public static Image getCircleEyesImage(OurColors color) {
        for (int i = 0; i < circles_eyes.length; i++) {
            if (circles_eyes[i].name.equals(color.toString() + "_eyes")) {
                return circles_eyes[i].image;
            }
        }
        System.out.println("Image not found : " + color.toString()+ "_eyes");
        return null;
    }

    public static Image getSpecialImage(OurSpecials special) {
        for (int i = 0; i < specials.length; i++) {
            if (specials[i].name.equals(special.toString())) {
                return specials[i].image;
            }
        }
        System.out.println("Image not found : " + special.toString());
        return null;
    }

    public static Image getSquareImage(OurColors color) {
        for (int i = 0; i < squares.length; i++) {
            if (squares[i].name.equals(color.toString())) {
                return squares[i].image;
            }
        }
        System.out.println("Image not found : " + color.toString());
        return null;
    }

    public static Image getSquareEyesImage(OurColors color) {
        for (int i = 0; i < squares_eyes.length; i++) {
            if (squares_eyes[i].name.equals(color.toString()+ "_eyes")) {
                return squares_eyes[i].image;
            }
        }
        System.out.println("Image not found : " + color.toString()+ "_eyes");
        return null;
    }

    public void loadImages() throws NullPointerException {

        for(OurColors c : SlitherColors){
            circles[c.ordinal()] = new Pair(new Image(getClass().getResourceAsStream("/circles/" + c.toString() + ".png")), c.toString());
            circles_eyes[c.ordinal()] = new Pair(new Image(getClass().getResourceAsStream("/circles/" + c.toString() + "_eyes.png")), c.toString() + "_eyes");
        }

        for(OurColors c : SnakeColors){
            squares[c.ordinal()] = new Pair(new Image(getClass().getResourceAsStream("/squares/" + c.toString() + ".png")), c.toString());
            squares_eyes[c.ordinal()] = new Pair(new Image(getClass().getResourceAsStream("/squares/" + c.toString() + "_eyes.png")), c.toString() + "_eyes");
        }

        for(OurSpecials s : OurSpecials.values()){
            specials[s.ordinal()] = new Pair(new Image(getClass().getResourceAsStream("/specials/" + s.toString() + ".png")), s.toString());
        }

        homePageBackground = new Image(getClass().getResourceAsStream("/wallpaper/wallpaperHomePage.png"));

    }
    
}
