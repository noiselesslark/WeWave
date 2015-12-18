package se.chalmers.halfwaytospirit.waveapp;

import android.graphics.Color;
import android.graphics.Paint;

/**
 * This class defines the TouchZone object, which retains data on zones on the app screen that
 * the user needs to touch within.
 */
public class TouchZone extends Point{
    private boolean isTouched = false;
    private boolean isEnabled = true;

    private int radius = 0;

    protected Paint innerCirclePaint;
    protected Paint outerCirclePaint;

    /**
     * Constructor.
     * @param x - the x-coordinate on the view for the centre of the touch zone.
     * @param y - the y-coordinate on the view fr the centre of the touch zone.
     * @param radius - the radius of the outer circle.
     */
    public TouchZone(int x, int y, int radius, int colour, int strokeWidth) {
        super(x, y);
        this.radius = radius;

        innerCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        innerCirclePaint.setColor(colour);
        innerCirclePaint.setStrokeWidth(1);
        innerCirclePaint.setStyle(Paint.Style.FILL);

        outerCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        outerCirclePaint.setColor(colour);
        outerCirclePaint.setStrokeWidth(strokeWidth);
        outerCirclePaint.setStyle(Paint.Style.STROKE);
    }

    /**
     * Checks whether the current zone is being touched or not.
     * @return isTouched.
     */
    public boolean isTouched() {
        return isTouched;
    }

    /**
     * Sets whether the touch zone is being touched  or not.
     * @param touched - the boolean value specifying whether zone is touched.
     */
    public void setTouched(boolean touched) {
        this.isTouched = touched;
    }

    /**
     * Checks whether the specified point is within the bounds of the touch zone.
     *
     * @param xPt - the x-coordinate of the point.
     * @param yPt - the y-coordinate of the point.
     * @return whether the point is within the touch zone.
     */
    public boolean isPointWithin(int xPt, int yPt) {
        int radiusInt = radius;
        long xMin = getX() - radiusInt;
        long xMax = getX() + radiusInt;
        long yMin = getY() - radiusInt;
        long yMax = getY() + radiusInt;

        if(xPt> xMin
                && xPt < xMax
                && yPt > yMin
                && yPt < yMax){
            return true;
        }

        return false;
    }

    /**
     * Get the paint of the inner circle, i.e. the circle specifying the zone is being touched.
     *
     * @return the inner circle paint.
     */
    public Paint getInnerCirclePaint(){
        return innerCirclePaint;
    }

    /**
     * Get the paint of the outer circle, i.e. the circle specifying the boundary of the point.
     *
     * @return the paint of the outerCircle.
     */
    public Paint getOuterCirclePaint() {
        return outerCirclePaint;
    }

    /**
     * Gets whether this touch zone is enabled.
     * @return whether it is enabled (true) or not (false).
     */
    public boolean isEnabled(){
        return this.isEnabled;
    }

    /**
     * Sets whether the touch zone is enabled or not.
     * @param isEnabled - the bool indicating whether it is enabled or not.
     * @param colour - the colour to set the touchZone to.
     */
    public void setEnabled(boolean isEnabled, int colour) {
        this.isEnabled = isEnabled;

        innerCirclePaint.setColor(colour);
        outerCirclePaint.setColor(colour);
    }
}
