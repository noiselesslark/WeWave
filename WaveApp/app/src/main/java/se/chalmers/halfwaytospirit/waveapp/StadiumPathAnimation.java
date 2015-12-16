package se.chalmers.halfwaytospirit.waveapp;

import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Created by Elise on 07/12/2015.
 */
public class StadiumPathAnimation extends Animation {
    private StadiumView stadium;

    private float oldAngle;
    private float newAngle;

    public StadiumPathAnimation(StadiumView stadium, int newAngle) {
        this.oldAngle = stadium.getSweepAngle();
        this.newAngle = newAngle;
        this.stadium = stadium;
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation transformation) {
        float angle = oldAngle + ((newAngle - oldAngle) * interpolatedTime);

        stadium.setSweepAngle(angle);
        stadium.requestLayout();
    }
}
