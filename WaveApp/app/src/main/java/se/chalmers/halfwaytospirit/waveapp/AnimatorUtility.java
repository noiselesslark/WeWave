package se.chalmers.halfwaytospirit.waveapp;

import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

/**
 * This class is a utility for animating views.
 */
public class AnimatorUtility {

    /**
     * Hides the view by having it fade away.
     * @param view - the view.
     */
    public static void hideView(final View view, int duration) {
        AlphaAnimation fade_out = new AlphaAnimation(1.0f, 0.0f);
        fade_out.setDuration(duration);
        fade_out.setAnimationListener(new Animation.AnimationListener()
        {
            public void onAnimationStart(Animation arg0)
            {
            }
            public void onAnimationRepeat(Animation arg0)
            {
            }

            public void onAnimationEnd(Animation arg0)
            {
                view.setVisibility(View.GONE);
            }
        });
        view.startAnimation(fade_out);
    }

    /**
     * Shows the view by having it fade in.
     * @param view - the view.
     */
    public static void showView(final View view, int duration) {
        AlphaAnimation fade_in = new AlphaAnimation(0.0f, 1.0f);
        fade_in.setDuration(duration);
        fade_in.setAnimationListener(new Animation.AnimationListener()
        {
            public void onAnimationStart(Animation animation)
            {
                view.setVisibility(View.VISIBLE);
            }
            public void onAnimationRepeat(Animation animation)
            {
            }

            public void onAnimationEnd(Animation animation)
            {
            }
        });
        view.startAnimation(fade_in);
    }
}
