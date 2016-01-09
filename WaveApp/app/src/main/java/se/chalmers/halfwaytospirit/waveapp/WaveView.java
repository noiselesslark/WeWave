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
        int zoneRadius = Math.round(TouchZone.OUTER_RADIUS);

        long centerX = TouchZonesView.VIEW_WIDTH /2;
        long centerY = TouchZonesView.VIEW_HEIGHT /2;
        long stadiumRadius = centerX - TouchZonesView.STADIUM_OFFSET - zoneRadius;
        int topY = TouchZonesView.VIEW_HEIGHT /3 - zoneRadius;
        int bottomY = 2*TouchZonesView.VIEW_HEIGHT /3 + zoneRadius;

        this.stadium = new StadiumShape(centerX, centerY, topY, bottomY, stadiumRadius);
        this.wavePosition = new ShapeDefinition(centerX, bottomY + stadiumRadius);

        final int strokeWidth = TouchZonesView.STADIUM_OFFSET *2;

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
        int pointRadius = TouchZonesView.STADIUM_OFFSET;

        canvas.drawArc(stadium.getTopSemiCircle(), 180, 180, false, pathPaint);
        canvas.drawArc(stadium.getBottomSemiCircle(), 0, 180, false, pathPaint);
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

        this.processTouchZoneState(currentZone);
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

    /**
     * Processes the touch zone state - whether the wave is entering or leaving.
     * @param currentZone - the currentZone.
     */
    public abstract void processTouchZoneState(TouchZone currentZone);
}
