package se.chalmers.halfwaytospirit.waveapp;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * This class defines the avatar image view.
 */
public class WinnerView extends ImageView {
    private static final int[] WINNER_BLUE = {R.attr.winner_blue};
    private static final int[] WINNER_GREEN = {R.attr.winner_green};
    private static final int[] WINNER_PINK = {R.attr.winner_pink};
    private static final int[] WINNER_PURPLE = {R.attr.winner_purple};
    private static final int[] WINNER_TURQUOISE = {R.attr.winner_turquoise};
    private static final int[] WINNER_YELLOW = {R.attr.winner_yellow};


    private boolean isWinnerBlue = false;
    private boolean isWinnerGreen = false;
    private boolean isWinnerPink = false;
    private boolean isWinnerPurple = false;
    private boolean isWinnerTurquoise = false;
    private boolean isWinnerYellow = false;

    /*private static final int[] STATE_WINNING = {R.attr.state_empty};
    private static final int[] STATE_LOSING = {R.attr.state_sitting};*/

    /**
     * Constructor.
     * @param context - the context.
     */
    public WinnerView(Context context) {
        super(context);
    }

    /**
     * Constructor.
     * @param context - the context.
     * @param attrs - the attributes.
     */
    public WinnerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Constructor.
     * @param context - the context.
     * @param attrs - the attributes.
     * @param defStyleAttr - the style definition.
     */
    public WinnerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * Called when the possible states of the view are created. This override creates a number
     * of custom states.
     * @param extraSpace - the extra space.
     * @return the set of drawable states.
     */
    @Override
    public int[] onCreateDrawableState(int extraSpace) {
        final int[] drawableState = super.onCreateDrawableState(extraSpace + 4);

        if(isWinnerBlue) {
            mergeDrawableStates(drawableState, WINNER_BLUE);
        }

        if(isWinnerGreen) {
            mergeDrawableStates(drawableState, WINNER_GREEN);
        }

        if(isWinnerPink) {
            mergeDrawableStates(drawableState, WINNER_PINK);
        }

        if(isWinnerPurple) {
            mergeDrawableStates(drawableState, WINNER_PURPLE);
        }

        if(isWinnerTurquoise) {
            mergeDrawableStates(drawableState, WINNER_TURQUOISE);
        }

        if(isWinnerYellow) {
            mergeDrawableStates(drawableState, WINNER_YELLOW);
        }

        return drawableState;
    }

    /**
     * Sets whether the winner player is blue.
     * @param isWinnerBlue - whether the winner is the blue player or not.
     */
    public void setIsWinnerBlue(boolean isWinnerBlue) {
        this.resetAllStates();
        this.isWinnerBlue = isWinnerBlue;
    }

    /**
     * Sets whether the winner player is green.
     * @param isWinnerGreen - whether the winner is the green player or not.
     */
    public void setIsWinnerGreen(boolean isWinnerGreen) {
        this.resetAllStates();
        this.isWinnerGreen = isWinnerGreen;
    }

    /**
     * Sets whether the winner player is pink.
     * @param isWinnerPink - whether the winner is the pink player or not.
     */
    public void setIsWinnerPink(boolean isWinnerPink) {
        this.resetAllStates();
        this.isWinnerPink = isWinnerPink;
    }

    /**
     * Sets whether the winner player is purple.
     * @param isWinnerPurple - whether the winner is the purple player or not.
     */
    public void setIsWinnerPurple(boolean isWinnerPurple) {
        this.resetAllStates();
        this.isWinnerPurple = isWinnerPurple;
    }

    /**
     * Sets whether the winner player is turquoise.
     * @param isWinnerTurquoise - whether the winner is the turquoise player or not.
     */
    public void setIsWinnerTurquoise(boolean isWinnerTurquoise) {
        this.resetAllStates();
        this.isWinnerTurquoise = isWinnerTurquoise;
    }
    
    /**
     * Sets whether the winner player is yellow.
     * @param isWinnerYellow - whether the winner is the yellow player or not.
     */
    public void setIsWinnerYellow(boolean isWinnerYellow) {
        this.resetAllStates();
        this.isWinnerYellow = isWinnerYellow;
    }

    /**
     * Reset the states in order to change one to true afterwards.
     */
    private void resetAllStates() {
        this.isWinnerBlue = false;
        this.isWinnerGreen = false;
        this.isWinnerPink = false;
        this.isWinnerPurple = false;
        this.isWinnerTurquoise = false;
        this.isWinnerYellow = false;
    }
}
