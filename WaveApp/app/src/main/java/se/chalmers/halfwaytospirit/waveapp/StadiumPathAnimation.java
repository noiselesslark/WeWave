package se.chalmers.halfwaytospirit.waveapp;

import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;

/**
 * This class animates a path around the stadium.
 */
public class StadiumPathAnimation extends Animation {
    private StadiumView stadium;
    private float oldAngle;
    private float newAngle;

    /**
     * Constructor.
     * @param stadium - the stadium view to animate.
     * @param newAngle - the final angle to reach.
     */
    public StadiumPathAnimation(StadiumView stadium, int newAngle) {
        this.oldAngle = stadium.getSweepAngle();
        this.newAngle = newAngle;
        this.stadium = stadium;

        this.setDuration(10000);
        this.setInterpolator(new LinearInterpolator());
        this.setRepeatCount(Animation.INFINITE);

        this.setAnimationListener(new AnimationListener() {
            /**
             * Called when the animation starts.
             * @param animation - the animation.
             */
            @Override
            public void onAnimationStart(Animation animation) {

            }

            /**
             * Called when the animation ends.
             * @param animation - the animation.
             */
            @Override
            public void onAnimationEnd(Animation animation) {

            }

            /**
             * Called each time the animation repeats.
             * @param animation - the animation.
             */
            @Override
            public void onAnimationRepeat(Animation animation) {
                long newSpeed = animation.getDuration()/10*9;
                if (newSpeed <= 2000) newSpeed = 2000;
                animation.setDuration(newSpeed);
            }
        });
    }

    /**
     * Applies the transformation to the line.
     * @param interpolatedTime - the interpolated time.
     * @param transformation - the transformation.
     */
    @Override
    protected void applyTransformation(float interpolatedTime, Transformation transformation) {
        float angle = oldAngle + ((newAngle - oldAngle) * interpolatedTime);

        stadium.setSweepAngle(angle);

        stadium.requestLayout();
    }
}
