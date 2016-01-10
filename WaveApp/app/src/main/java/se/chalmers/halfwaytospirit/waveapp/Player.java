package se.chalmers.halfwaytospirit.waveapp;

/**
 * This class defines the player object, keeping track of all properties to do with the player's
 * game state.
 */
public class Player {
    private int playerId;

    /**
     * Number of consecutive success in the wave
     * For personal record
     */
    private int circuitCount = 0;

    /**
     * Constructor.
     */
    public Player (int id) {
        this.playerId = id;
    }

    /**
     * Getter for the number of wave success of the player
     * @return the counter
     */
    public int getCircuitCount() {
        return this.circuitCount;
    }

    /**
     * Setter for the circuit count
     * @param circuitCount the new count
     */
    public void setCircuitCount(int circuitCount) {
        this.circuitCount = circuitCount;
    }
}
