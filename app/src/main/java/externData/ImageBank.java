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

    private static Image blue_circle;
    private static Image brown_circle;
    private static Image green_circle;
    private static Image orange_circle;
    private static Image pink_circle;
    private static Image purple_circle;
    private static Image red_circle;
    private static Image yellow_circle;

    private static Pair[] circles = {new Pair(blue_circle, "blue"), new Pair(brown_circle, "brown"), new Pair(green_circle, "green"), new Pair(orange_circle, "orange"), new Pair(pink_circle, "pink"), new Pair(purple_circle, "purple"), new Pair(red_circle, "red"), new Pair(yellow_circle, "yellow")};

    private static Image blue_circle_eyes;
    private static Image brown_circle_eyes;
    private static Image green_circle_eyes;
    private static Image orange_circle_eyes;
    private static Image pink_circle_eyes;
    private static Image purple_circle_eyes;
    private static Image red_circle_eyes;
    private static Image yellow_circle_eyes;

    private static Pair[] circles_eyes = {new Pair(blue_circle_eyes, "blue_eyes"), new Pair(brown_circle_eyes, "brown_eyes"), new Pair(green_circle_eyes, "green_eyes"), new Pair(orange_circle_eyes, "orange_eyes"), new Pair(pink_circle_eyes, "pink_eyes"), new Pair(purple_circle_eyes, "purple_eyes"), new Pair(red_circle_eyes, "red_eyes"), new Pair(yellow_circle_eyes, "yellow_eyes")};

    private static Image poison;
    private static Image shield;
    private static Image skull;

    private static Pair[] specials = {new Pair(poison, "poison"), new Pair(shield, "shield"), new Pair(skull, "skull")};

    private static Image blue_square;
    private static Image brown_square;
    private static Image green_square;
    private static Image orange_square;
    private static Image pink_square;
    private static Image purple_square;
    private static Image red_square;
    private static Image yellow_square;

    private static Pair[] squares = {new Pair(blue_square, "blue"), new Pair(brown_square, "brown"), new Pair(green_square, "green"), new Pair(orange_square, "orange"), new Pair(pink_square, "pink"), new Pair(purple_square, "purple"), new Pair(red_square, "red"), new Pair(yellow_square, "yellow")};

    private static Image blue_square_eyes;
    private static Image brown_square_eyes;
    private static Image green_square_eyes;
    private static Image orange_square_eyes;
    private static Image pink_square_eyes;
    private static Image purple_square_eyes;
    private static Image red_square_eyes;
    private static Image yellow_square_eyes;
    
    private static Pair[] squares_eyes = {new Pair(blue_square_eyes, "blue_eyes"), new Pair(brown_square_eyes, "brown_eyes"), new Pair(green_square_eyes, "green_eyes"), new Pair(orange_square_eyes, "orange_eyes"), new Pair(pink_square_eyes, "pink_eyes"), new Pair(purple_square_eyes, "purple_eyes"), new Pair(red_square_eyes, "red_eyes"), new Pair(yellow_square_eyes, "yellow_eyes")};

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

    public static Image getSpecialImage(String special) {
        for (int i = 0; i < specials.length; i++) {
            if (specials[i].name.equals(special)) {
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

        for (int i = 0; i < circles.length; i++) {
            circles[i].image = new Image(getClass().getResourceAsStream("/circles/" + circles[i].name + ".png"));
        }

        for (int i = 0; i < circles_eyes.length; i++) {
            circles_eyes[i].image = new Image(getClass().getResourceAsStream("/circles/" + circles_eyes[i].name + ".png"));
        }

        for (int i = 0; i < specials.length; i++) {
            specials[i].image = new Image(getClass().getResourceAsStream("/specials/" + specials[i].name + ".png"));
        }

        for (int i = 0; i < squares.length; i++) {
            squares[i].image = new Image(getClass().getResourceAsStream("/squares/" + squares[i].name + ".png"));
        }

        for (int i = 0; i < squares_eyes.length; i++) {
            squares_eyes[i].image = new Image(getClass().getResourceAsStream("/squares/" + squares_eyes[i].name + ".png"));
        }
    }
    
}
