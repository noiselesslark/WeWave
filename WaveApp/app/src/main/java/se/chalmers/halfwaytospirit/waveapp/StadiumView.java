package se.chalmers.halfwaytospirit.waveapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.util.AttributeSet;

/**
 * This class sets up the stadium view, drawing backgrounds, etc.
 */
public class StadiumView extends TouchZonesView {
    private static final int START_ANGLE_TOP = 180;
    private static final int START_ANGLE_BOTTOM = 0;

    private boolean lineLeft = false;
    private boolean lineRight = false;

    private Paint pathPaint;
    private float sweepAngle;
    private int radius;


    private Point centerTopCircle;
    private Point centerBottomCircle;
    private Point topLineLeft;
    private Point topLineRight;
    private Point bottomLineLeft;
    private Point bottomLineRight;

    private RectF topRectangle;
    private RectF bottomRectangle;

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

        this.radius = getScreenWidth()/2 - getStadiumOffset() - getCircleRadiusInt();

        int circleCenterX = getScreenWidth()/2;
        int topY = getScreenHeight()/3 - getCircleRadiusInt();
        int bottomY = 2*getScreenHeight()/3 + getCircleRadiusInt();

        centerTopCircle = new Point(circleCenterX, topY);
        centerBottomCircle = new Point(circleCenterX, bottomY);

        int leftX = circleCenterX - radius;
        int rightX = circleCenterX + radius;

        topLineLeft = new Point(leftX, topY);
        bottomLineLeft = new Point(leftX, bottomY);
        topLineRight = new Point(rightX, topY);
        bottomLineRight = new Point(rightX, bottomY);

        final int strokeWidth = getStadiumOffset()*2;

        pathPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        pathPaint.setColor(Color.WHITE);
        pathPaint.setStrokeWidth(strokeWidth);
        pathPaint.setStyle(Paint.Style.STROKE);
        pathPaint.setShader(new SweepGradient(getScreenWidth()/2, getScreenHeight()/2, Color.RED, Color.BLUE));


        topRectangle = defineSemiCircle(centerTopCircle);
        bottomRectangle = defineSemiCircle(centerBottomCircle);

        sweepAngle = 0;
    }

    private RectF defineSemiCircle(Point point) {
        int left = point.getX() - this.radius;
        int right = point.getX() + this.radius;
        int top = point.getY() - this.radius;
        int bottom = point.getY() + this.radius;

        return new RectF(left, top, right, bottom );
    }

    @Override
    protected void onDraw(Canvas canvas){
        if(sweepAngle > 180 && sweepAngle <= 360) {
            canvas.drawArc(topRectangle, START_ANGLE_TOP, sweepAngle -180, false, pathPaint);
        }/* else if(sweepAngle > 180) {
            lineLeft = true;

        }*/ else {



            canvas.drawArc(bottomRectangle, START_ANGLE_BOTTOM, sweepAngle, false, pathPaint);
        }

        canvas.drawLine(topLineLeft.getX(), topLineLeft.getY(), bottomLineLeft.getX(), bottomLineLeft.getY(), pathPaint);
        canvas.drawLine(topLineRight.getX(), topLineRight.getY(), bottomLineRight.getX(), bottomLineRight.getY(), pathPaint);

        super.onDraw(canvas);
    }

    /**
     * Gets the current sweepAngle.
     * @return
     */
    public float getSweepAngle() {
        return sweepAngle;
    }

    /**
     * Sets the sweep angle.
     * @param sweepAngle - the new sweep angle.
     */
    public void setSweepAngle(float sweepAngle) {
        this.sweepAngle = sweepAngle;
    }
}
