package se.chalmers.halfwaytospirit.waveapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.HashMap;

/**
 * This class is an extension of the view that handles the zoness within which users can touch.
 * as well as detecting multi-touch events.
 */
public abstract class TouchZonesView extends View {
    private int screenWidth = 0;
    private int screenHeight = 0;

    private float innerCircleRadius = 90f;
    private float outerCircleRadius = 100f;
    private int stadiumOffset = 0;

    protected final int MAX_NUMBER_OF_PLAYERS = 6;
    private HashMap<String, TouchZone> touchZones;

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
     * Get circle radius as integer value. 
     * @return
     */
    protected int getCircleRadiusInt() {
        return Math.round(this.outerCircleRadius);
    }

    /**
     * Initialise the view. Draw basics.
     */
    protected void initView(){
        screenWidth = getResources().getDisplayMetrics().widthPixels;
        screenHeight = getResources().getDisplayMetrics().heightPixels;

        outerCircleRadius = this.screenWidth/10f;
        innerCircleRadius = outerCircleRadius - 5f;

        int refWidth = 480; // Width from reference phone for which the offset was initially calculated.
        stadiumOffset = Math.round(screenWidth * 10 / refWidth);

        touchZones = new HashMap<>();

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
        for(int i = 1; i <= MAX_NUMBER_OF_PLAYERS; i++){
            TouchZone tp = touchZones.get("Player" + i);

            canvas.drawCircle(tp.getX(), tp.getY(), outerCircleRadius, tp.getOuterCirclePaint());
            if(tp.isTouched()){
                canvas.drawCircle(tp.getX(), tp.getY(), innerCircleRadius, tp.getInnerCirclePaint());
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
        int action = (event.getAction() & MotionEvent.ACTION_MASK);
        int pointCount = event.getPointerCount();

        for (int i = 0; i < pointCount; i++){
            int id = event.getPointerId(i);

            if(id < MAX_NUMBER_OF_PLAYERS) {
                int xPoint = (int)event.getX(i) - this.getLeft();
                int yPoint = (int)event.getY(i) - this.getTop();

                handleTouchEvent(xPoint, yPoint, action);
            }
        }

        invalidate(); //Refresh canvas.
        return true;
    }

    /**
     * Handles touch events logic.
     *
     * @param x - the x position of the touch event.
     * @param y - the y position of the touch event.
     * @param action - the action of the event.
     */
    protected void handleTouchEvent(int x, int y, int action) {
        for(TouchZone zone : touchZones.values()){
            if(zone.isPointWithin(x, y)){
                if(action == MotionEvent.ACTION_POINTER_DOWN || action == MotionEvent.ACTION_DOWN){
                    zone.setTouched(true);
                } else if (action == MotionEvent.ACTION_POINTER_UP || action == MotionEvent.ACTION_UP){
                    zone.setTouched(false);
                }
            }
        }
    }

    /** Private Methods */

    /**
     * Defines where on the canvas the touch zones should be drawn.
     */
    private void defineTouchZones() {
        int integerOuterCircleRadius = this.getCircleRadiusInt();
        
        int xLeft = integerOuterCircleRadius + this.stadiumOffset;
        int xCentre = Math.round(this.screenWidth/2);
        int xRight = this.screenWidth - integerOuterCircleRadius - this.stadiumOffset;

        int yTop = integerOuterCircleRadius + this.stadiumOffset;
        int yHigh =  Math.round(this.screenHeight/3);
        int yLow = Math.round(2*this.screenHeight/3);
        int yDown = this.screenHeight - integerOuterCircleRadius - this.stadiumOffset;

        TouchZone topTouchZone = new TouchZone(xCentre, yTop, integerOuterCircleRadius);
        TouchZone leftHighTouchZone = new TouchZone(xLeft, yHigh, integerOuterCircleRadius);
        TouchZone leftLowTouchZone = new TouchZone(xLeft, yLow, integerOuterCircleRadius);
        TouchZone rightHighTouchZone = new TouchZone(xRight, yHigh, integerOuterCircleRadius);
        TouchZone rightLowTouchZone = new TouchZone(xRight, yLow, integerOuterCircleRadius);
        TouchZone downTouchZone = new TouchZone(xCentre, yDown, integerOuterCircleRadius);

        setTouchZoneColour (topTouchZone, R.color.colorPink);
        setTouchZoneColour (leftHighTouchZone, R.color.colorYellow);
        setTouchZoneColour (leftLowTouchZone, R.color.colorGreen);
        setTouchZoneColour (rightHighTouchZone, R.color.colorPurple);
        setTouchZoneColour (rightLowTouchZone, R.color.colorTurquoise);
        setTouchZoneColour(downTouchZone, R.color.colorBlue);

        touchZones.put(GameManager.PLAYER_1, topTouchZone);
        touchZones.put(GameManager.PLAYER_2, leftHighTouchZone);
        touchZones.put(GameManager.PLAYER_3, leftLowTouchZone);
        touchZones.put(GameManager.PLAYER_4, rightHighTouchZone);
        touchZones.put(GameManager.PLAYER_5, rightLowTouchZone);
        touchZones.put(GameManager.PLAYER_6, downTouchZone);
    }

    /**
     * Set the colour of the players in the touch zones
     */
    private void setTouchZoneColour (TouchZone touchZone, int colour) {
        Paint innerCirclePaint = touchZone.getInnerCirclePaint();
        Paint outerCirclePaint = touchZone.getOuterCirclePaint();

        innerCirclePaint.setColor(ContextCompat.getColor(getContext(), colour));
        outerCirclePaint.setColor(ContextCompat.getColor(getContext(), colour));

        // TODO adapt the stroke to several screens?
        innerCirclePaint.setStrokeWidth(5);
        outerCirclePaint.setStrokeWidth(5);
    }
}
