package se.chalmers.halfwaytospirit.waveapp;

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
}
