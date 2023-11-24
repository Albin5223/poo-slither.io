package model;

public class Coordinate {
    private int x;
    private int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y =y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object other) {
        return this.x == ((Coordinate) other).x && this.y == ((Coordinate) other).y;
    }

    @Override
    public int hashCode() {
        return this.x * 100 + this.y;
    }

    public Coordinate CoordinateOf(Direction direction) {
        switch (direction) {
            case UP:
                return new Coordinate(this.x, this.y - 1);
            case DOWN:
                return new Coordinate(this.x, this.y + 1);
            case LEFT:
                return new Coordinate(this.x - 1, this.y);
            case RIGHT:
                return new Coordinate(this.x + 1, this.y);
            default:
                return null;
        }
    }
}
