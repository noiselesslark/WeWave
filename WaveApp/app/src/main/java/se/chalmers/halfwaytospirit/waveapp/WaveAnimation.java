package se.chalmers.halfwaytospirit.waveapp;

import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;

/**
 * This class animates a circle moving around the stadiumView, following the path of the stadium.
 */
public class WaveAnimation extends Animation {
    private static final int[] SECTOR_BOUNDARIES = {0, 45, 135, 180, 225, 315};
    private static final int QUARTER = 90;
    private static final int HALF = 180;
    private static final int THREE_QUARTERS = 270;
    private static final int FULL = 360;

    private WaveView stadiumView;
    private float oldAngle;
    private float newAngle;

    /**
     * Constructor.
     * @param stadiumView - the stadiumView view to animate.
     * @param newAngle - the final angle to reach.
     */
    public WaveAnimation(WaveView stadiumView, int newAngle) {
        this.oldAngle = stadiumView.getSweepAngle();
        this.newAngle = newAngle;
        this.stadiumView = stadiumView;

        this.setDuration(10000);
        this.setInterpolator(new LinearInterpolator());
        this.setRepeatCount(Animation.INFINITE);

        this.setAnimationListener(new AnimationListener() {
            /**
             * Called when the animation starts.
             * @param animation - the animation.
             */
            @Override
            public void onAnimationStart(Animation animation) {}

            /**
             * Called when the animation ends.
             * @param animation - the animation.
             */
            @Override
            public void onAnimationEnd(Animation animation) {}

            /**
             * Called each time the animation repeats.
             * @param animation - the animation.
             */
            @Override
            public void onAnimationRepeat(Animation animation) {
                // Increase the speed each time by 10%.
                long newSpeed = animation.getDuration()/10*9;
                if (newSpeed <= 2000) newSpeed = 2000;
                animation.setDuration(newSpeed);
            }
        });
    }

    /**
     * Calculates new co-ordinates for the wave.
     * @param interpolatedTime - the interpolated time.
     * @param transformation - the transformation.
     */
    @Override
    protected void applyTransformation(float interpolatedTime, Transformation transformation) {
        float sweepAngle = oldAngle + ((newAngle - oldAngle) * interpolatedTime) + 90;
        if(sweepAngle >= 360) {
            sweepAngle-=360;
        }


        StadiumShape stadium = stadiumView.getStadium();
        long radius = stadium.getRadius();
        long centerX = stadium.getCenterX();
        long centerY = stadium.getCenterY();

        double xPos = 0;
        double yPos = 0;
        double angle;

        if(sweepAngle >= SECTOR_BOUNDARIES[0]  && sweepAngle <= SECTOR_BOUNDARIES[1]) {
            angle = Math.toRadians(sweepAngle);
            xPos = stadium.getCenterX() + radius;
            yPos = stadium.getCenterY() + Math.tan(angle) * radius;

        } else if (sweepAngle > SECTOR_BOUNDARIES[1]  && sweepAngle <= SECTOR_BOUNDARIES[2]) {
            // Bottom semicircle.
            angle = Math.toRadians(sweepAngle * 2 - QUARTER);
            xPos = centerX + Math.cos(angle) * radius;
            yPos = stadium.getYBottom() + Math.sin(angle) * radius;

        } else if (sweepAngle > SECTOR_BOUNDARIES[2]  && sweepAngle <= SECTOR_BOUNDARIES[3]) {
            angle = Math.toRadians(HALF-sweepAngle);
            xPos = centerX - radius;
            yPos = centerY + Math.tan(angle) * radius;

        } else if (sweepAngle > SECTOR_BOUNDARIES[3]  && sweepAngle <= SECTOR_BOUNDARIES[4]) {
            angle = Math.toRadians(sweepAngle - HALF);
            xPos = centerX - radius;
            yPos = centerY - Math.tan(angle) * radius;

        } else if (sweepAngle > SECTOR_BOUNDARIES[4]  && sweepAngle <= SECTOR_BOUNDARIES[5]){
            // Top semicircle.
            angle = Math.toRadians(sweepAngle*2 - THREE_QUARTERS);
            xPos = centerX + Math.cos(angle)* radius;
            yPos = stadium.getYTop() + Math.sin(angle)* radius;

        } else if( sweepAngle > SECTOR_BOUNDARIES[5]){
            angle = Math.toRadians(FULL - sweepAngle);
            xPos = centerX + radius;
            yPos = centerY - Math.tan(angle) * radius;
        }

        stadiumView.setWavePosition(new ShapeDefinition(Math.round(xPos), Math.round(yPos)));
        stadiumView.setSweepAngle(sweepAngle);

        stadiumView.requestLayout();
    }
}
