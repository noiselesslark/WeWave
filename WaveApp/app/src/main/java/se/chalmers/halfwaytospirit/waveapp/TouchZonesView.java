package se.chalmers.halfwaytospirit.waveapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

/**
 * This class is an extension of the view that handles the zones within which users can touch.
 * as well as detecting multi-touch events.
 */
public abstract class TouchZonesView extends View {
    static int VIEW_WIDTH = 0;
    static int VIEW_HEIGHT = 0;
    static int STADIUM_OFFSET = 0;

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
     * Initialise the view. Draw basics.
     */
    protected void initView(){
        VIEW_WIDTH = getResources().getDisplayMetrics().widthPixels;
        VIEW_HEIGHT = getResources().getDisplayMetrics().heightPixels;

        STADIUM_OFFSET = Math.round(VIEW_WIDTH / 48);

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

            long x = zone.getCenterX();
            long y = zone.getCenterY();

            if(innerPaint != null) {
                canvas.drawCircle(x, y, TouchZone.INNER_RADIUS, zone.getInnerPaint());
            }

            if(outerPaint != null) {
                canvas.drawCircle(x, y, TouchZone.OUTER_RADIUS, zone.getOuterPaint());
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
        int action = MotionEventCompat.getActionMasked(event);
        int index = MotionEventCompat.getActionIndex(event);

        //int pointerId = event.getPointerId(0);
        //int pointerIndex = event.findPointerIndex(pointerId);

            int lastX = (int) MotionEventCompat.getX(event, index);
            int lastY = (int) MotionEventCompat.getY(event, index);
            TouchZone lastZone = getZoneAt(lastX, lastY);
            // Handling for move events.
            if(event.getHistorySize() > 0) {
                // TODO [EK]: do we need to do this for all historical xs?
                int firstX = (int) event.getHistoricalX(index, 0);
                int firstY = (int) event.getHistoricalY(index, 0);
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

        invalidate();
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
        TouchZone.OUTER_RADIUS = VIEW_WIDTH /10;;
        TouchZone.INNER_RADIUS = TouchZone.OUTER_RADIUS - 10f;
        TouchZone.OUTER_STROKE_WIDTH = STADIUM_OFFSET /2;

        int radius = Math.round(TouchZone.OUTER_RADIUS);
        
        int xLeft = radius + STADIUM_OFFSET;
        int xCentre = Math.round(VIEW_WIDTH /2);
        int xRight = VIEW_WIDTH - radius - STADIUM_OFFSET;

        int yTop = radius + STADIUM_OFFSET;
        int yHigh =  Math.round(VIEW_HEIGHT /3);
        int yLow = Math.round(2* VIEW_HEIGHT /3);
        // int yDown = VIEW_HEIGHT - radius - STADIUM_OFFSET;

        TouchZone topTouchZone = new TouchZone(xCentre, yTop,
                getColour(R.color.colorPink), R.string.playerPink);
        TouchZone leftHighTouchZone = new TouchZone(xLeft, yHigh,
                getColour(R.color.colorYellow), R.string.playerYellow);
        TouchZone leftLowTouchZone = new TouchZone(xLeft, yLow,
                getColour(R.color.colorGreen),R.string.playerGreen);
        TouchZone rightHighTouchZone = new TouchZone(xRight, yHigh,
                getColour(R.color.colorPurple), R.string.playerPurple);
        TouchZone rightLowTouchZone = new TouchZone(xRight, yLow,
                getColour(R.color.colorBlue), R.string.playerBlue);
        /* TouchZone downTouchZone = new TouchZone(xCentre, yDown,
                getColour(R.color.colorTurquoise), getString(R.string.playerTurquoise), strokeWidth); */

        touchZones.add(topTouchZone);
        touchZones.add(leftHighTouchZone);
        touchZones.add(leftLowTouchZone);
        touchZones.add(rightHighTouchZone);
        touchZones.add(rightLowTouchZone);
        //touchZones.add(downTouchZone);
    }

    /**
     * Gets the colour from resources.
     * @param color - the colour id.
     * @return the colour resource.
     */
    public int getColour(int color) {
        return ContextCompat.getColor(getContext(), color);
    }

    public String getString(int string) {
        return getContext().getString(string);
    }

    /**
     * Checks whether the zone is active.
     * @param zone - the zone.
     */
    public abstract void checkZoneActive(TouchZone zone);
}
