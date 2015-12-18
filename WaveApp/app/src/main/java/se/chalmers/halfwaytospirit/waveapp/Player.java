package se.chalmers.halfwaytospirit.waveapp;

import android.graphics.Color;

/**
 * This class defines the player object, keeping track of all properties to do with the player's
 * game state.
 */
public class Player {
    /**
     * Identifies whether player has been eliminated from the game.
     */
    private boolean isEliminated = false;

    /**
     * The touch-zone that the player is associated with.
     */
    private TouchZone touchZone;

    /**
     * Number of consecutive success in the wave
     * For personal record
     */
    private int consecutiveSuccess = 0;

    /**
     * If the player is the one currently supposed to wave
     */
    private boolean isCurrentPlayer = false;

    /**
     * Constructor.
     */
    public Player () {}

    /**
     * Getter for boolean isEliminated.
     * @return isEliminated
     */
    public boolean isEliminated() {
        return isEliminated;
    }

    /**
     * Setter for boolean isEliminated.
     * @param isEliminated
     */
    public void setIsEliminated(boolean isEliminated) {
        this.isEliminated = isEliminated;
    }

    /**
     * Sets the touch zone of the player.
     * @param touchZone - the touch zone.
     */
    public void setTouchZone(TouchZone touchZone) {
        this.touchZone = touchZone;
    }
}
