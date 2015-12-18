package se.chalmers.halfwaytospirit.waveapp;

import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.widget.Switch;

/**
 * This class defines the TouchZone object, which retains data on zones on the app screen that
 * the user needs to touch within.
 */
public class TouchZone extends Point{

    private boolean isTouched;
    //TODO add a isDisabled parameter

    private int touchZoneRadius = 0;

    protected Paint innerCirclePaint;
    protected Paint outerCirclePaint;

    /**
     * Constructor.
     * @param x - the x-coordinate on the view for the centre of the touch zone.
     * @param y - the y-coordinate on the view fr the centre of the touch zone.
     * @param touchZoneRadius - the radius of the outer circle.
     */
    public TouchZone(int x, int y, int touchZoneRadius) {
        super(x, y);
        this.touchZoneRadius = touchZoneRadius;

        innerCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        innerCirclePaint.setColor(Color.BLACK);
        innerCirclePaint.setStrokeWidth(1);
        innerCirclePaint.setStyle(Paint.Style.FILL);

        outerCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        outerCirclePaint.setColor(Color.BLACK);
        outerCirclePaint.setStrokeWidth(1);
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
        int radiusInt = touchZoneRadius;
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
