package se.chalmers.halfwaytospirit.waveapp;

import android.graphics.Color;
import android.graphics.Paint;

/**
 * This class defines the TouchZone object, which retains data on zones on the app screen that
 * the user needs to touch within.
 */
public class TouchZone {
    public static final float INNER_CIRCLE_RADIUS = 90f;
    public static final float OUTER_CIRCLE_RADIUS = 100f;

    private boolean isTouched;
    private int x;
    private int y;

    protected Paint innerCirclePaint;
    protected Paint outerCirclePaint;

    /**
     * Constructor.
     * @param x - the x-coordinate on the view for the centre of the touch zone.
     * @param y - the y-coordinate on the view fr the centre of the touch zone.
     */
    public TouchZone(int x, int y) {
        this.x = x;
        this.y = y;

        innerCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        innerCirclePaint.setColor(Color.BLACK);
        innerCirclePaint.setStrokeWidth(1);
        innerCirclePaint.setStyle(Paint.Style.FILL);

        outerCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        outerCirclePaint.setColor(Color.BLACK);
        outerCirclePaint.setStrokeWidth(1);
        outerCirclePaint.setStyle(Paint.Style.STROKE);
        // TODO: Write constructor with colours specifiable. Or find some other solution to this....
    }

    /**
     * Gets x.
     * @return the x-coordinate of the centre of the touch zone.
     */
    public int getX() {
        return x;
    }

    /**
     * Gets y.
     * @return the y-coordinate of the centre of the touch zone.
     */
    public int getY() {
        return y;
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
        int radiusInt = Math.round(OUTER_CIRCLE_RADIUS);
        int xMin = x - radiusInt;
        int xMax = x + radiusInt;
        int yMin = y - radiusInt;
        int yMax = y + radiusInt;

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
     * TODO: Make this settable from the outside somehow.
     * @return the inner circle paint.
     */
    public Paint getInnerCirclePaint(){
        return innerCirclePaint;
    }

    /**
     * Get the paint of the outer circle, i.e. the circle specifying the boundary of the point. 
     * @return
     */
    public Paint getOuterCirclePaint() {
        return outerCirclePaint;
    }

    // TODO: add icons to top of button.
}
