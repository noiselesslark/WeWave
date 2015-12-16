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

    public StadiumPathAnimation(StadiumView stadium, int newAngle) {
        this.oldAngle = stadium.getSweepAngle();
        this.newAngle = newAngle;
        this.stadium = stadium;

        this.setDuration(10000);
        this.setInterpolator(new LinearInterpolator());
        this.setRepeatCount(Animation.INFINITE);

        this.setAnimationListener(new AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                long newSpeed = animation.getDuration()/10*9;
                if (newSpeed <= 2000) newSpeed = 2000;
                animation.setDuration(newSpeed);
            }
        });
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation transformation) {

        float angle = oldAngle + ((newAngle - oldAngle) * interpolatedTime);

        if(angle >= 180){

        }

        stadium.setSweepAngle(angle);


        stadium.requestLayout();
    }
}
