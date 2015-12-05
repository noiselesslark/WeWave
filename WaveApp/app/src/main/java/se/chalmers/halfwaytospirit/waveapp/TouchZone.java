package se.chalmers.halfwaytospirit.waveapp;

import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by Elise on 03/12/2015.
 */
public class TouchZone {
    public static final float INNER_CIRCLE_RADIUS = 90f;
    public static final float OUTER_CIRCLE_RADIUS = 100f;

    private boolean touching;
    private int x;
    private int y;

    protected Paint innerCirclePaint;
    protected Paint outerCirclePaint;

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

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public boolean isTouching() {
        return touching;
    }

    public void setTouching(boolean touching) {
        this.touching = touching;
    }

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

    public Paint getInnerCirclePaint(){
        return innerCirclePaint;
    }

    public Paint getOuterCirclePaint() {
        return outerCirclePaint;
    }
}
