package se.chalmers.halfwaytospirit.waveapp;

/**
 * Created by Elise on 07/12/2015.
 */
public class Point {
    private int x;
    private int y;

    /**
     * Constructor.
     * @param x - the x-coordinate of the point.
     * @param y - the y-coordinate of the point.
     */
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Gets x.
     * @return the x-coordinate of the point.
     */
    public int getX() {
        return x;
    }

    /**
     * Gets y.
     * @return the y-coordinate of the point.
     */
    public int getY() {
        return y;
    }
}
