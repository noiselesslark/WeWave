package se.chalmers.halfwaytospirit.waveapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

/**
 * This class is an extension of the view that handles the zones within which users can touch.
 * as well as detecting multi-touch events.
 */
public abstract class TouchZonesView extends View {
    private int screenWidth = 0;
    private int screenHeight = 0;

    private float innerTouchZoneRadius;
    private float outerTouchZoneRadius;

    private int stadiumOffset = 0;
    private ArrayList<TouchZone> touchZones;

    /**
     * Constructor.
     * @param context - the context.
     * @param attrs - the attributes.
     * @param defStyle - the style definition.
     */
    public TouchZonesView(Context context, AttributeSet attrs, int defStyle){
        super(context, attrs, defStyle);
        initView();
    }

    /**
     * Constructor.
     * @param context - the context.
     * @param attrs - the attributes.
     */
    public TouchZonesView(Context context, AttributeSet attrs){
        super(context, attrs);
        initView();
    }

    /**
     * Constructor.
     * @param context - the context.
     */
    public TouchZonesView(Context context) {
        super(context);
        initView();
    }

    /**
     * Gets the screen width.
     * @return screen width.
     */
    protected int getScreenWidth() {
        return this.screenWidth;
    }

    /**
     * Gets the screen height.
     * @return screen height. 
     */
    protected int getScreenHeight() {
        return this.screenHeight;
    }

    /**
     * Gets the stadium offset. 
     * @return stadium offset.
     */
    protected int getStadiumOffset() {
        return this.stadiumOffset;
    }

    /**
     * Gets the touch zone radius.
     * @return the touch zone radius.
     */
    public float getTouchZoneRadius() {
        return outerTouchZoneRadius;
    }

    /**
     * Initialise the view. Draw basics.
     */
    protected void initView(){
        screenWidth = getResources().getDisplayMetrics().widthPixels;
        screenHeight = getResources().getDisplayMetrics().heightPixels;

        outerTouchZoneRadius = this.screenWidth/10f;
        innerTouchZoneRadius = outerTouchZoneRadius - 5f;

        stadiumOffset = Math.round(screenWidth / 48);

        touchZones = new ArrayList<>();

        defineTouchZones();
    }

    /**
     * Called when vie is measured.
     * @param widthMeasureSpec - the width measure spec.
     * @param heightMeasureSpec - the height measure spec.
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec),
                MeasureSpec.getSize(heightMeasureSpec));
    }

    /**
     * Called when view is re-drawn. Draws touch zones.
     * @param canvas - the canvas.
     */
    @Override
    protected void onDraw(Canvas canvas){
        for(TouchZone zone : touchZones){
            Paint innerPaint = zone.getInnerPaint();
            Paint outerPaint = zone.getOuterPaint();

            if(outerPaint != null) {
                canvas.drawCircle(zone.getCenterX(), zone.getCenterY(), outerTouchZoneRadius, zone.getOuterPaint());
            }

            if(innerPaint != null && zone.isTouched()) {
                canvas.drawCircle(zone.getCenterX(), zone.getCenterY(), innerTouchZoneRadius, zone.getInnerPaint());
            }
        }
    }

    /**
     * Overrides the View class's event handler for touch events. Detects whether the toouch is
     * within a touch zone.
     *
     * @param event - the touch event.
     * @return boolean representing whether touch event was consumed.
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getActionMasked();
        int pointCount = event.getPointerCount();

        for (int i = 0; i < pointCount; i++){
            int lastX = (int) event.getX(i);
            int lastY = (int) event.getY(i);
            TouchZone lastZone = getZoneAt(lastX, lastY);

            // Handling for move events.
            if(event.getHistorySize() > 0) {
                int firstX = (int) event.getHistoricalX(i, 0);
                int firstY = (int) event.getHistoricalY(i, 0);
                TouchZone firstZone = getZoneAt(firstX, firstY);

                // If movement was not within a zone.
                if (firstZone != lastZone && firstZone != null) {
                    firstZone.setTouched(false);

                    this.checkZoneActive(firstZone);
                }
            }

            if(lastZone!= null) {
                if (action == MotionEvent.ACTION_POINTER_DOWN || action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_MOVE) {
                    lastZone.setTouched(true);
                } else if (action == MotionEvent.ACTION_POINTER_UP || action == MotionEvent.ACTION_UP) {
                    lastZone.setTouched(false);

                    this.checkZoneActive(lastZone);
                }
            }
        }

        invalidate(); //Refresh canvas.
        return true;
    }

    /**
     * Gets the zone that the specified point is within.
     *
     * @param x - the x position of the touch event.
     * @param y - the y position of the touch event.
     */
    protected TouchZone getZoneAt(int x, int y) {
        for(TouchZone zone : touchZones){
            if(zone.isPointWithin(x, y)){
                return zone;
            }
        }

        return null;
    }

    /**
     * Gets the touch zones list.
     * @return the touch zones list
     */
    public ArrayList<TouchZone> getTouchZones() {
        return this.touchZones;
    }

    /** Private Methods */

    /**
     * Defines where on the canvas the touch zones should be drawn.
     */
    private void defineTouchZones() {
        int radius = Math.round(this.outerTouchZoneRadius);
        
        int xLeft = radius + this.stadiumOffset;
        int xCentre = Math.round(this.screenWidth/2);
        int xRight = this.screenWidth - radius - this.stadiumOffset;

        int yTop = radius + this.stadiumOffset;
        int yHigh =  Math.round(this.screenHeight/3);
        int yLow = Math.round(2*this.screenHeight/3);
        int yDown = this.screenHeight - radius - this.stadiumOffset;

        int strokeWidth = this.stadiumOffset/2;

        TouchZone topTouchZone = new TouchZone(xCentre, yTop, radius,
                getColour(R.color.colorPink), strokeWidth);
        TouchZone leftHighTouchZone = new TouchZone(xLeft, yHigh, radius,
                getColour(R.color.colorYellow), strokeWidth);
        TouchZone leftLowTouchZone = new TouchZone(xLeft, yLow, radius,
                getColour(R.color.colorGreen), strokeWidth);
        TouchZone rightHighTouchZone = new TouchZone(xRight, yHigh, radius,
                getColour(R.color.colorPurple), strokeWidth);
        TouchZone rightLowTouchZone = new TouchZone(xRight, yLow, radius,
                getColour(R.color.colorTurquoise), strokeWidth);
        TouchZone downTouchZone = new TouchZone(xCentre, yDown, radius,
                getColour(R.color.colorBlue), strokeWidth);

        touchZones.add(topTouchZone);
        touchZones.add(leftHighTouchZone);
        touchZones.add(leftLowTouchZone);
        touchZones.add(rightHighTouchZone);
        touchZones.add(rightLowTouchZone);
        touchZones.add(downTouchZone);
    }

    /**
     * Gets the colour from resources.
     * @param color - the colour id.
     * @return the colour resource.
     */
    public int getColour(int color) {
        return ContextCompat.getColor(getContext(), color);
    }

    /**
     * Processes the touch zone state - whether the wave is entering or leaving.
     * @param currentZone - the currentZone.
     */
    public abstract void processTouchZoneState(TouchZone currentZone);

    /**
     * Checks whether the zone is active.
     * @param zone - the zone. 
     */
    public abstract void checkZoneActive(TouchZone zone);

}
