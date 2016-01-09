package se.chalmers.halfwaytospirit.waveapp;

/**
 * This class defines the player object, keeping track of all properties to do with the player's
 * game state.
 */
public class Player {
    private String playerName;

    private int circuitCount = 0;

    /**
     * Constructor.
     */
    public Player (String name) {
        this.playerName = name;
    }

    /**
     * Gets the player name.
     * @return the player name.
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * Gets the circuit count.
     * @return the circuit count.
     */
    public int getCircuitCount() {
        return circuitCount;
    }

    /**
     * Sets the circuit count.
     * @param circuitCount - the circuit count.
     */
    public void setCircuitCount(int circuitCount) {
        this.circuitCount = circuitCount;
    }

}
