package se.chalmers.halfwaytospirit.waveapp;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

/**
 * This class is an extension of the view that handles the zoness within which users can touch.
 * as well as detecting multi-touch events.
 */
public abstract class TouchZonesView extends View {

    protected final int MAX_POINT_NUMBER = 6;

    private ArrayList<TouchZone> allowedPoints;

    public TouchZonesView(Context context, AttributeSet attrs, int defStyle){
        super(context, attrs, defStyle);
        initView();
    }

    /**
     * Constructor for MultiTouchView.
     * @param context - the context.
     * @param attrs - the attributes.
     */
    public TouchZonesView(Context context, AttributeSet attrs){
        super(context, attrs);
        initView();
    }

    /**
     * Constructor for MultiTouchView.
     * @param context - the context.
     */
    public TouchZonesView(Context context) {
        super(context);
        initView();
    }

    /**
     * Initialise things.
     */
    protected void initView(){
        allowedPoints = new ArrayList<>();

        defineAllowedTouchPoints();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec),
                MeasureSpec.getSize(heightMeasureSpec));
    }

    @Override
    protected void onDraw(Canvas canvas){
        for(int i = 0; i < MAX_POINT_NUMBER; i++){
            TouchZone tp = allowedPoints.get(i);

            canvas.drawCircle(tp.getX(), tp.getY(), TouchZone.OUTER_CIRCLE_RADIUS, tp.getOuterCirclePaint());
            if(tp.isTouching()){
                canvas.drawCircle(tp.getX(), tp.getY(), TouchZone.INNER_CIRCLE_RADIUS, tp.getInnerCirclePaint());
            }
        }
    }

    /**
     * Overrides the View class's event handler for touch events.
     * @param event - the touch event.
     * @return boolean representing whether touch event was consumed.
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = (event.getAction() & MotionEvent.ACTION_MASK);
        int pointCount = event.getPointerCount();

        for (int i = 0; i < pointCount; i++){
            int id = event.getPointerId(i);

            if(id < MAX_POINT_NUMBER) {
                int xPoint = (int)event.getX(i) - this.getLeft();
                int yPoint = (int)event.getY(i) - this.getTop();

                handleTouchEvent(xPoint, yPoint, action);
            }
        }

        invalidate(); //Refresh canvas.
        return true;
    }

    /**
     * Handles touch being imposed.
     * @param x - the x position of the touch event.
     * @param y - the y position of the touch event.
     * @param action - the action of the event.
     */
    protected void handleTouchEvent(int x, int y, int action) {
        for(TouchZone zone : allowedPoints){
            if(zone.isPointWithin(x, y)){
                if(action == MotionEvent.ACTION_POINTER_DOWN || action == MotionEvent.ACTION_DOWN){
                    zone.setTouching(true);
                } else if (action == MotionEvent.ACTION_POINTER_UP || action == MotionEvent.ACTION_UP){
                    zone.setTouching(false);
                }
            }
        }
    }

    /** Private Methods */

    /**
     * Draw circles within which user needs to press to have their touch event detected.
     */
    private void defineAllowedTouchPoints() {
        for( int i = 0; i<MAX_POINT_NUMBER; i++){
            TouchZone tp = new TouchZone((i+1) * 150, (i+1) * 200);
            allowedPoints.add(tp);
        }
    }
}
