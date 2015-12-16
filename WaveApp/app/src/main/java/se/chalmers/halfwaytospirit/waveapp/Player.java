package se.chalmers.halfwaytospirit.waveapp;

import android.graphics.Color;

/**
 * Created by Elise on 03/12/2015.
 */
public class Player {

    /**
     * Identifies whether the player joined the game at the beginning.
     */
    private boolean isPlaying = false;

    /**
     * Identifies whether player has been eliminated from the game.
     */
    private boolean isEliminated = false;

    /**
     * Colour identifying the player
     */
    private int playerColour = Color.BLACK;

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
     * Player identifier
     */
    private int id;

    /**
     * Constructor
     */
    public Player (int id) {
        this.id = id;

    }

    /**
     * Getter for boolean isPlaying.
     * @return isPlaying;
     */
    public boolean isPlaying() {
        return isPlaying;
    }

    /**
     * Setter for boolean isPlaying.
     * @param isPlaying
     */
    public void setIsPlaying(boolean isPlaying) {
        this.isPlaying = isPlaying;
    }

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
     * Getter for boolean playerColour.
     * @return isEliminated
     */
    public int getPlayerColour() {
        return playerColour;
    }

    /**
     * Setter for boolean isEliminated.
     * @param playerColour
     */
    public void setPlayerColour(int playerColour) {
        this.playerColour = playerColour;
    }
}
