package se.chalmers.halfwaytospirit.waveapp;

import android.graphics.RectF;

/**
 * This class defines the shape of a stadium in 2D co-ordinates, as defined by a rectangle with an
 * adjoining semi-circle on each end, aligned vertically.
 */
public class StadiumShape extends ShapeDefinition {
    private long yTop;
    private long yBottom;
    private long radius;

    /**
     * Constructor.
     * @param xCenter - the x-co-ordinate of the center of the stadium.
     * @param yCenter - the y-co-ordinate of the center of the stadium.
     * @param yTop - the y-co-ordinate of the center of the top semi-circle.
     * @param yBottom - the y-co-ordinate of the center of the bottom semi-circle.
     * @param radius - the radius of the semi-circles.
     */
    public StadiumShape(long xCenter, long yCenter, long yTop, long yBottom, long radius) {
        super(xCenter,yCenter);

        this.yTop = yTop;
        this.yBottom = yBottom;
        this.radius = radius;
    }

    /**
     * Gets the y-co-ordinate of the top semi-circle's centre.
     * @return the y-co-ordinate.
     */
    public long getYTop() {
        return yTop;
    }

    /**
     * Gets the y-co-ordinate of the bottom semi-circle's centre.
     * @return the y-co-ordinate.
     */
    public long getYBottom() {
        return yBottom;
    }

    /**
     * Returns the radius of the stadium semi-circles.
     * @return the radius.
     */
    public long getRadius() {
        return radius;
    }

    /**
     * Returns the x-co-ordinate of the left side.
     * @return the co-ordinate.
     */
    public long getXLeft() {
        return this.getCenterX() - radius;
    }

    /**
     * Returns the x-co-ordinate of the right side.
     * @return the co-ordinate.
     */
    public long getXRight(){
        return this.getCenterX() + radius;
    }

    /**
     * Returns a rectangle defining the top semi-circle.
     * @return a rectangle.
     */
    public RectF getTopSemiCircle() {
        return getCircle(yTop);
    }

    /**
     * Returns a rectangle defining the bottom semi-circle.
     * @return a rectangle.
     */
    public RectF getBottomSemiCircle() {
        return getCircle(yBottom);
    }

    /**
     * Returns a rectangle defining the circle around the specified y-co-ordinate, w.r.t.
     * the stadium measurements.
     * @param y - the y-co-ordinate.
     * @return a rectangle.
     */
    private RectF getCircle(long y) {
        long left = this.getCenterX() - radius;
        long right = this.getCenterX() + radius;
        long top = y - radius;
        long bottom = y + radius;

        return new RectF(left, top, right, bottom );
    }
}
