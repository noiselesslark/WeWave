package se.chalmers.halfwaytospirit.waveapp;

import android.content.Context;
import android.util.AttributeSet;

/**
 * This class manages the game logic for the view, and communication with the main activity.
 */
public class GameView extends WaveView {
    private TouchZone activeTouchZone;
    private boolean detectedPlayerLift = false;

    private GameManager manager;

    private IOnPlayerLostListener playerLostListener;

    /**
     * Constructor.
     * @param context - the context.
     * @param attrs - The attributes.
     * @param defStyle - The style definition.
     */
    public GameView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * Constructor.
     * @param context - the context.
     * @param attrs - the attributes.
     */
    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Constructor.
     * @param context - the context.
     */
    public GameView(Context context) {
        super(context);
    }

    /**
     * Initialises the view.
     */
    @Override
    public void initView() {
        super.initView();

        this.manager = ((MainGameActivity) getContext()).getGameManager();
    }

    /**
     * Sets the on player lost listener for this class.
     * @param listener - the listener.
     */
    public void setPlayerLostListener(IOnPlayerLostListener listener){
        this.playerLostListener = listener;
    }

    /**
     * Calls the on player lost.
     * @param zone - the zone in which a player has lost.
     */
    protected void onPlayerLost(TouchZone zone) {
        if(this.playerLostListener != null && zone != null && zone.getPlayer() != null) {
            this.playerLostListener.onPlayerLost(zone.getPlayer());

            zone.setEliminated(true);
            zone.setPlayer(null);
        }
    }

    /**
     * Checks whether the zone is active. If not, the player loses.
     * @param zone - the zone to check.
     */
    @Override
    public void checkZoneActive(TouchZone zone) {

        if(manager.isGameRunning()) {
            if (zone != this.activeTouchZone) {
                this.onPlayerLost(zone);
            } else {
                this.detectedPlayerLift = true;
            }
        }
    }

    /**
     * Checks the touch zone state: whether the wave is leaving or entering the zone.
     * @param currentZone - the zone to check state of.
     */
    @Override
    public void processTouchZoneState(TouchZone currentZone){
        if (this.activeTouchZone == null && currentZone != null && currentZone.isEnabled() && !currentZone.isEliminated()) {
            this.onTouchZoneEnter(currentZone);
        } else if (this.activeTouchZone != null && currentZone == null) {
            this.onTouchZoneLeave();
        }
    }

    /**
     * Handles when a touch zone is entered by the wave.
     *
     * @param currentZone - the zone being entered.
     */
    private void onTouchZoneEnter(TouchZone currentZone) {
        if(!currentZone.isTouched()) {
            this.onPlayerLost(currentZone);
        }

        currentZone.setWaving(true);
        this.activeTouchZone = currentZone;
    }

    /**
     * Handles when a touch zone is left by the wave.
     */
    private void onTouchZoneLeave() {
        if(!this.detectedPlayerLift) {
            this.onPlayerLost(this.activeTouchZone);
        }

        this.activeTouchZone.setWaving(false);
        this.activeTouchZone = null;
    }
}
