package se.chalmers.halfwaytospirit.waveapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;

/**
 * This class sets up the stadium view, drawing backgrounds, etc.
 */
public class StadiumView extends TouchZonesView {
    private static final int START_ANGLE_POINT = 180;

    private Paint pathPaint;
    private RectF rect;
    private float sweepAngle;

    private Point centerTopCircle;

    /**
     * Constructor.
     * @param context - the context.
     * @param attrs - the attributes.
     * @param defStyle - the style definition.
     */
    public StadiumView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * Constructor.
     * @param context - the context.
     * @param attrs - the attributes.
     */
    public StadiumView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Constructor.
     * @param context - the context. 
     */
    public StadiumView(Context context) {
        super(context);
    }

    @Override
    protected void initView() {
        super.initView();

        centerTopCircle = new Point(getScreenWidth()/2, getScreenHeight()/3);
        int radius = getScreenWidth()/2 - getStadiumOffset() - getCircleRadiusInt();

        final int strokeWidth = getStadiumOffset();

        pathPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        pathPaint.setColor(Color.WHITE);
        pathPaint.setStrokeWidth(strokeWidth);
        pathPaint.setStyle(Paint.Style.STROKE);


        int left = centerTopCircle.getX() - radius;
        int right = centerTopCircle.getX() + radius;
        int top = centerTopCircle.getY() - radius;
        int bottom = centerTopCircle.getY() + radius;
        rect = new RectF(left, top, right, bottom );

        sweepAngle = 0;
    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        canvas.drawArc(rect, START_ANGLE_POINT, sweepAngle, false, pathPaint);
    }

    public float getSweepAngle() {
        return sweepAngle;
    }

    public void setSweepAngle(float sweepAngle) {
        this.sweepAngle = sweepAngle;
    }


}
