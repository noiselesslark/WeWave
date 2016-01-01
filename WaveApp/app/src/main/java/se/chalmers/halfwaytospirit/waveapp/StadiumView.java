package se.chalmers.halfwaytospirit.waveapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;

/**
 * This class sets up and handles the stadium view.
 */
public class StadiumView extends TouchZonesView {
    private Paint pathPaint;
    private Paint pointPaint;

    private ShapeDefinition wavePosition;
    private StadiumShape stadium;

    private float sweepAngle;

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

    /**
     * Initialises the view.
     */
    @Override
    protected void initView() {
        super.initView();

        long centerX = getScreenWidth()/2;
        long centerY = getScreenHeight()/2;
        long stadiumRadius = centerX - getStadiumOffset() - Math.round(getTouchZoneRadius());
        int topY = getScreenHeight()/3 - Math.round(getTouchZoneRadius());
        int bottomY = 2*getScreenHeight()/3 + Math.round(getTouchZoneRadius());

        this.stadium = new StadiumShape(centerX, centerY, topY, bottomY, stadiumRadius);
        this.wavePosition = new ShapeDefinition(centerX + stadiumRadius, centerY);

        final int strokeWidth = getStadiumOffset()*2;

        pathPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        pathPaint.setColor(Color.BLACK);
        pathPaint.setStrokeWidth(strokeWidth);
        pathPaint.setStyle(Paint.Style.STROKE);

        pointPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        pointPaint.setColor(Color.WHITE);
        pointPaint.setStrokeWidth(strokeWidth);
        pointPaint.setStyle(Paint.Style.FILL);

        sweepAngle = 0;
    }

    /**
     * Called when canvas is re-drawn.
     * @param canvas - the canvas.
     */
    @Override
    protected void onDraw(Canvas canvas){
        int pointRadius = getStadiumOffset();

        canvas.drawArc(stadium.getTopSemiCircle(), 180, 180, false, pathPaint);
        canvas.drawArc(stadium.getBottomSemiCircle(), 0, 180, false,pathPaint);
        canvas.drawLine(stadium.getXLeft(), stadium.getYTop(),
                stadium.getXLeft(), stadium.getYBottom(), pathPaint);
        canvas.drawLine(stadium.getXRight(), stadium.getYTop(),
                stadium.getXRight(), stadium.getYBottom(), pathPaint);

        canvas.drawCircle(wavePosition.getCenterX(), wavePosition.getCenterY(), pointRadius, pointPaint);

        super.onDraw(canvas);
    }

    public StadiumShape getStadium() {
        return this.stadium;
    }

    public void setWavePosition(ShapeDefinition wavePosition) {
        this.wavePosition = wavePosition;
    }

    public float getSweepAngle() {
        return sweepAngle;
    }

    public void setSweepAngle(float sweepAngle) {
        this.sweepAngle = sweepAngle;
    }
}
