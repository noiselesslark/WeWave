package se.chalmers.halfwaytospirit.waveapp;

import android.content.Context;
import android.graphics.Canvas;
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

    private float innerCircleRadius = 90f;
    private float outerCircleRadius = 100f;
    private int stadiumOffset = 0;

    protected final int MAX_NUMBER_OF_PLAYERS = 6;
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
        for(TouchZone tp : touchZones){

            canvas.drawCircle(tp.getX(), tp.getY(), outerCircleRadius, tp.getOuterCirclePaint());
            if(tp.isTouched() && tp.isEnabled()){
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
        for(TouchZone zone : touchZones){
            // TODO add a || !zone.isDisabled
            if(zone.isPointWithin(x, y)){
                if(action == MotionEvent.ACTION_POINTER_DOWN || action == MotionEvent.ACTION_DOWN){
                    zone.setTouched(true);
                } else if (action == MotionEvent.ACTION_POINTER_UP || action == MotionEvent.ACTION_UP){
                    zone.setTouched(false);
                }
            }
        }
    }

    /**
     * Getter of the Touch Zones
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
        int tzRadius = this.getCircleRadiusInt();
        
        int xLeft = tzRadius + this.stadiumOffset;
        int xCentre = Math.round(this.screenWidth/2);
        int xRight = this.screenWidth - tzRadius - this.stadiumOffset;

        int yTop = tzRadius + this.stadiumOffset;
        int yHigh =  Math.round(this.screenHeight/3);
        int yLow = Math.round(2*this.screenHeight/3);
        int yDown = this.screenHeight - tzRadius - this.stadiumOffset;

        int strokeWidth = this.stadiumOffset/2;

        TouchZone topTouchZone = new TouchZone(xCentre, yTop, tzRadius,
                getColour(R.color.colorPink), strokeWidth);
        TouchZone leftHighTouchZone = new TouchZone(xLeft, yHigh, tzRadius,
                getColour(R.color.colorYellow), strokeWidth);
        TouchZone leftLowTouchZone = new TouchZone(xLeft, yLow, tzRadius,
                getColour(R.color.colorGreen), strokeWidth);
        TouchZone rightHighTouchZone = new TouchZone(xRight, yHigh, tzRadius,
                getColour(R.color.colorPurple), strokeWidth);
        TouchZone rightLowTouchZone = new TouchZone(xRight, yLow, tzRadius,
                getColour(R.color.colorTurquoise), strokeWidth);
        TouchZone downTouchZone = new TouchZone(xCentre, yDown, tzRadius,
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

}
