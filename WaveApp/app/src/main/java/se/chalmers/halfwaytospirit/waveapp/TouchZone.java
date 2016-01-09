package se.chalmers.halfwaytospirit.waveapp;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * This class defines the TouchZone object, which retains data on zones on the app screen that
 * the user needs to touch within.
 */
public class TouchZone extends ShapeDefinition {
    private boolean isTouched = false;
    private boolean isEnabled = true;
    private boolean isEliminated = false;
    private boolean isWaving = false;

    static float OUTER_RADIUS = 0;
    static float INNER_RADIUS = 0;
    static float OUTER_STROKE_WIDTH = 0;

    private Paint defaultInnerPaint;
    private Paint defaultOuterPaint;
    private Paint wavingOuterPaint;
    private Paint eliminatedOuterPaint;

    private Player player;
    private int colourName;
    private AvatarView avatar;

    /**
     * Constructor.
     * @param x - the x-coordinate on the view for the centre of the touch zone.
     * @param y - the y-coordinate on the view fr the centre of the touch zone.
     */
    public TouchZone(int x, int y, int colour, int colourName) {
        super(x, y);
        this.colourName = colourName;

        defaultInnerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        defaultInnerPaint.setColor(colour);
        defaultInnerPaint.setStyle(Paint.Style.FILL);

        defaultOuterPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        defaultOuterPaint.setColor(colour);
        defaultOuterPaint.setStrokeWidth(OUTER_STROKE_WIDTH);
        defaultOuterPaint.setStyle(Paint.Style.STROKE);

        wavingOuterPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        wavingOuterPaint.setColor(Color.WHITE);
        wavingOuterPaint.setStyle(Paint.Style.STROKE);
        wavingOuterPaint.setStrokeWidth(OUTER_STROKE_WIDTH);

        eliminatedOuterPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        eliminatedOuterPaint.setColor(Color.GRAY);
        eliminatedOuterPaint.setStrokeWidth(OUTER_STROKE_WIDTH);
        eliminatedOuterPaint.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    /**
     * Checks whether the current zone is being touched or not.
     * @return isTouched.
     */
    public boolean isTouched() {
        this.avatar.setIsSitting(true);
        this.avatar.refreshDrawableState();
        return isTouched;
    }

    /**
     * Sets whether the touch zone is being touched  or not.
     * @param touched - the boolean value specifying whether zone is touched.
     */
    public void setTouched(boolean touched) {
        this.isTouched = touched;
    }

    /**
     * Checks whether the specified point is within the bounds of the touch zone.
     *
     * @param xPt - the x-coordinate of the point.
     * @param yPt - the y-coordinate of the point.
     * @return whether the point is within the touch zone.
     */
    public boolean isPointWithin(int xPt, int yPt) {
        int radiusInt = Math.round(OUTER_RADIUS);

        int left = (int)getCenterX() - radiusInt;
        int right = (int)getCenterX() + radiusInt;
        int top = (int)getCenterY() - radiusInt;
        int bottom = (int)getCenterY() + radiusInt;

        Rect bounds = new Rect(left, top, right, bottom);

        return bounds.contains(xPt, yPt);
    }

    /**
     * Get the paint of the inner circle, i.e. the circle specifying the zone is being touched.
     *
     * @return the inner circle paint.
     */
    public Paint getInnerPaint(){
        if(this.isEnabled && !this.isEliminated && this.isTouched) {
            return this.defaultInnerPaint;
        }

        return null;
    }

    /**
     * Get the paint of the outer circle, i.e. the circle specifying the boundary of the point.
     *
     * @return the paint of the outerCircle.
     */
    public Paint getOuterPaint() {
        if(this.isEnabled) {
            if (this.isEliminated) {
                return this.eliminatedOuterPaint;
            } else if (this.isWaving) {
                return this.wavingOuterPaint;
            }else {
                return this.defaultOuterPaint;
            }
        }

        return null;
    }

    /**
     * Gets whether the touch zone is enabled or not.
     * @return isEnabled.
     */
    public boolean isEnabled() {
        return this.isEnabled;
    }

    /**
     * Sets whether the touch zone is enabled or not.
     * @param isEnabled - the boolean indicating whether it is enabled or not.
     */
    public void setEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    /**
     * Gets whether the touch zone is active or not.
     * @return isWaving.
     */
    public boolean isWaving() {
        return this.isWaving;
    }

    /**
     * Sets whether this zone is active or not.
     * @param isWaving - whether the zone is waving.
     */
    public void setWaving(boolean isWaving) {
        this.isWaving = isWaving;
    }

    /**
     * Sets whether this zone is eliminated or not.
     * @return isEliminated.
     */
    public boolean isEliminated() {
        return this.isEliminated;
    }

    /**
     * Sets whether the zone has been eliminated or not.
     * @param isEliminated - whether the zone has been eliminated.
     */
    public void setEliminated(boolean isEliminated) {
        this.isEliminated = isEliminated;
    }

    /**
     * Gets the player.
     * @return the player.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Sets the player.
     * @param player - the player.
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * Gets the name of the colour.
     * @return the name of the colour.
     */
    public int getColourName() {
        return this.colourName;
    }

    /**
     * Gets the avatar.
     * @return the avatar.
     */
    public AvatarView getAvatar() {
        return this.avatar;
    }

    /**
     * Sets the avatar.
     * @param avatar - the avatar view.
     */
    public void setAvatar(AvatarView avatar) {
        this.avatar = avatar;
    }
}
