package se.chalmers.halfwaytospirit.waveapp;

/**
 * This class defines a shape.
 */
public class ShapeDefinition {
    private long centerX;
    private long centerY;

    /**
     * Constructor.
     * @param x - the center X-coordinate of the shape.
     * @param y - the center Y-coordinate of the shape.
     */
    public ShapeDefinition(long x, long y) {
        this.centerX = x;
        this.centerY = y;
    }

    /**
     * Gets centerX.
     * @return the center X-coordinate of the point.
     */
    public long getCenterX() {
        return centerX;
    }

    /**
     * Gets centerY.
     * @return the center Y-coordinate of the point.
     */
    public long getCenterY() {
        return centerY;
    }
}
