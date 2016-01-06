package se.chalmers.halfwaytospirit.waveapp;

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
     * Number of consecutive success in the wave
     * For personal record
     */
    private int circuitCount = 0;

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
     * @param isEliminated - whether the player is eliminated or not.
     */
    public void setIsEliminated(boolean isEliminated) {
        this.isEliminated = isEliminated;
    }

}
