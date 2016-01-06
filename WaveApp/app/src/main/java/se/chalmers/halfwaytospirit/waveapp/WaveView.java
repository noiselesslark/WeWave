package se.chalmers.halfwaytospirit.waveapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;

/**
 * This class manages the position of the wave.
 */
public abstract class WaveView extends TouchZonesView {
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
    public WaveView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * Constructor.
     * @param context - the context.
     * @param attrs - the attributes.
     */
    public WaveView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Constructor.
     * @param context - the context.
     */
    public WaveView(Context context) {
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

    /**
     * Gets the stadium shape.
     * @return the stadium shape.
     */
    public StadiumShape getStadium() {
        return this.stadium;
    }

    /**
     * Sets the wave position.
     * @param wavePosition - the wave position.
     */
    public void setWavePosition(ShapeDefinition wavePosition) {
        this.wavePosition = wavePosition;

        TouchZone currentZone = getZoneAt((int)wavePosition.getCenterX(), (int)wavePosition.getCenterY());

        if(currentZone!= null && currentZone.isEnabled() && !currentZone.isEliminated()) {
            this.checkTouchZoneState(currentZone);
        }
    }



    /**
     * Gets the sweep angle of the arc that the position is at.
     * @return the sweep angle.
     */
    public float getSweepAngle() {
        return sweepAngle;
    }

    /**
     * Sets the sweep angle.
     * @param sweepAngle - the sweep angle.
     */
    public void setSweepAngle(float sweepAngle) {
        this.sweepAngle = sweepAngle;
    }
}
