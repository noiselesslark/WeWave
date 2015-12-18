package se.chalmers.halfwaytospirit.waveapp;

/**
 * Created by Elise on 07/12/2015.
 */
public class Point {
    private long x;
    private long y;

    /**
     * Constructor.
     * @param x - the x-coordinate of the point.
     * @param y - the y-coordinate of the point.
     */
    public Point(long x, long y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Gets x.
     * @return the x-coordinate of the point.
     */
    public long getX() {
        return x;
    }

    /**
     * Gets y.
     * @return the y-coordinate of the point.
     */
    public long getY() {
        return y;
    }
}
