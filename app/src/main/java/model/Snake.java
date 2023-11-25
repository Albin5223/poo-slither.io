package model;

import java.util.ArrayList;

import exceptions.ExecptionMoveInvalid;
import interfaces.Angle;
import interfaces.Collisable;
import interfaces.Turnable;

public class Snake implements Turnable, Collisable<Snake> {

    /** The angle increment when turning */
    public final static double ANGLE_INCREMENT = 5;
    /** The maximum angle of the snake */
    public final static double ANGLE_MAX = 360;
    /** The minimum angle of the snake */
    public final static double ANGLE_MIN = 0;

    /** The minimum width of the snake */
    public final static double WIDTH_MIN = 1;
    /** The maximum width of the snake */
    public final static double WIDTH_MAX = 10;
    /** The width of the snake */
    private double currentWIDTH = WIDTH_MIN;

    /** The gap between two snake parts */
    public final static double GAP = 0.5;

    /** The turning of the snake */
    private Turning currentTurning = Turning.FORWARD;

    public class SnakePart implements Cloneable {

        /** The coordinate left of the snake part */
        private Coordinate left;
        /** The coordinate center of the snake part */
        private Coordinate center;
        /** The coordinate right of the snake part */
        private Coordinate right;
        /** The angle of the snake part */
        private double angle;

        /** The constructor of the snake part
         * @param center the center of the snake part
         * @param angle the angle of the snake part
         */
        private SnakePart(Coordinate center, double angle){
            this.angle = angle;

            this.left = center.placeCoordinateFrom(currentWIDTH / 2, Angle.getPerpendicularAngleLeft(angle));
            this.right = center.placeCoordinateFrom(currentWIDTH / 2, Angle.getPerpendicularAngleRight(angle));
            this.center = center.clone();
        }

        /**
         * Private constructor for the {@link #clone} method
         * @param left the left coordinate of the snake part
         * @param center the center coordinate of the snake part
         * @param right the right coordinate of the snake part
         * @param angle the angle of the snake part
         */
        private SnakePart(Coordinate left, Coordinate center, Coordinate right, double angle) {
            this.left = left.clone();
            this.center = center.clone();
            this.right = right.clone();
            this.angle = angle;
        }

        /**
         * @return a copy of {@link #left}
         */
        public Coordinate getLeft() {
            return left.clone();
        }

        /**
         * @return the center of the snake part
         */
        public Coordinate getCenter(){
            return center.clone();
        }

        /**
         * @return a copy of {@link #right}
         */
        public Coordinate getRight() {
            return right.clone();
        }

        /** The getter of the angle
         * @return the angle of the snake part
         */
        public double getAngle() {
            return angle;
        }

        /** The setter of the angle
         * @param d the new angle of the snake part
         */
        public void setAngle(double d) {
            this.angle = d;
        }

        /**
         * Get the width of the snake part
         * @return the width of the snake part
         */
        public double getWidth(){
            return left.distanceTo(right);
        }

        /**
         * Update the width of the snake part, based on the {@link #currentWIDTH}, the {@link #center}, and the {@link #angle}
         */
        public void updateWidth(){
            double newDistance = currentWIDTH / 2;
            this.left = center.placeCoordinateFrom(newDistance, Angle.getPerpendicularAngleLeft(angle));
            this.right = center.placeCoordinateFrom(newDistance, Angle.getPerpendicularAngleRight(angle));
        }

        @Override
        public SnakePart clone() {
            return new SnakePart(this.left, this.center, this.right, this.angle);
        }

        @Override
        public String toString() {
            return "SnakePart [left=" + left.toString() + ", center=" + center.toString() + ", right=" + right.toString() + ", angle=" + angle + "]";
        }
    }

    /** The head of the snake */
    private SnakePart head;
    /** The tail of the snake */
    private ArrayList<SnakePart> tail;
    /** The board where the snake is */
    private Plateau plateau;
    
    /**
     * The constructor of the snake
     * @param location the location of the snake
     * @param plateau the board where the snake is
     * @param startingAngle the starting angle of the snake
     */
    public Snake(Coordinate location,Plateau plateau, double startingAngle) {
        this.head = new SnakePart(location.clone(), startingAngle);
        this.tail = new ArrayList<SnakePart>();

        double angle = head.getAngle();
        SnakePart tail1 = new SnakePart(head.getCenter().placeCoordinateFrom(GAP, Angle.getOppositeAngle(angle)), angle);
        tail.add(tail1);

        this.plateau = plateau;

        plateau.addSnake(this);
    }

    /**
     * @return a copy of {@link #head}
     */
    public SnakePart getHead() {
        return head;
    }

    /**
     * @return a copy of {@link #tail}
     */
    public SnakePart[] getTail() {
        return tail.stream().map(c -> c.clone()).toArray(SnakePart[]::new);
    }

    /**
     * @return a copy of {@link #head} and {@link #tail}
     */
    public SnakePart[] getAllSnakePart() {
        SnakePart[] tail = getTail();
        SnakePart[] allSnakePart = new SnakePart[tail.length + 1];
        allSnakePart[0] = head;
        for (int i = 0; i < tail.length; i++) {
            allSnakePart[i + 1] = tail[i];
        }
        return allSnakePart;
    }

    /**
     * Update the width of the snake, based on the {@link #currentWIDTH}, the {@link #head}, and the {@link #tail}
     * This method should be called after the {@link #currentWIDTH} has been updated (= after the snake has eaten food)
     * @see SnakePart#updateWidth()
     */
    public void updateWidth(){
        head.updateWidth();
        for (SnakePart s : tail) {
            s.updateWidth();
        }
    }

    /**
     * Update the position of the snake on the board (the snake has moved)
     * @throws ExecptionMoveInvalid if the snake is colliding with another snake
     */
    public void move() throws ExecptionMoveInvalid {

        // Create the new head : distance from the old head = GAP, angle = updated head's angle considering the current turning
        double newAngle = turn(currentTurning, head.getAngle());
        SnakePart newHead = new SnakePart(head.getCenter().placeCoordinateFrom(GAP, newAngle), head.getAngle());

        this.tail.remove(tail.size() - 1);  // We remove the last element of the tail
        this.tail.add(0, head); // We add the old head to the tail
        this.head = newHead;    // We update the head

        if(plateau.isCollidingWithAll(this)){  // We check if the snake is colliding with another snake
            throw new ExecptionMoveInvalid("Snake is colliding with another snake");
        }
        
        plateau.update(this);   // We update the position of the snake on the board
    }

    /**
     * Turning the snake to the left or to the right, like in a trigonometric circle (0° is on the right, 90° is on the top, 180° is on the left, 270° is on the bottom)
     * @param turning the direction to turn
     */
    @Override
    public double turn(Turning turning, double initialAngle) {
        switch (turning) {
            case LEFT:
                return (initialAngle + ANGLE_INCREMENT) % ANGLE_MAX;
            case RIGHT:
                return (initialAngle - ANGLE_INCREMENT) % ANGLE_MAX;
            case FORWARD:
                return initialAngle;
            default:
                throw new IllegalArgumentException("Turning not recognized");
        }
    }

    /**
     * @return the angle of the snake
     */
    public double getAngle() {
        return head.getAngle();
    }

    public double getCurrentWIDTH() {
        return currentWIDTH;
    }

    /**
     * Checking if the snake is colliding with another snake, considering that a snake is colliding with another snake
     * if the distance between his head is less than the width of the other snake.
     * @param other the other snake that we want to check if it's colliding with our {@link #head}
     * @return {@code true} if the snake is colliding with the other snake, {@code false} otherwise
     */
    @Override
    public boolean isColliding(Snake other) {
        SnakePart[] allParts = other.getAllSnakePart();
        for (int i = 0; i < allParts.length - 1; i++) {
            SnakePart p1 = allParts[i];
            SnakePart p2 = allParts[i + 1];
            if(this.head.getLeft().isContainedIn(p1.getLeft(), p2.getRight())){ // if the left part of the head is contained in the other snake part, then the snake is colliding
                return true;
            }
            if(this.head.getCenter().isContainedIn(p1.getLeft(), p2.getRight())){   // if the center part of the head is contained in the other snake part, then the snake is colliding
                return true;
            }
            if(this.head.getRight().isContainedIn(p1.getLeft(), p2.getRight())){    // if the right part of the head is contained in the other snake part, then the snake is colliding
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        String res = "Snake [head=" + head.toString() + ", tail=";
        for (SnakePart s : tail) {
            res += s.toString() + " ";
        }
        res += "]";
        return res;
    }
}
