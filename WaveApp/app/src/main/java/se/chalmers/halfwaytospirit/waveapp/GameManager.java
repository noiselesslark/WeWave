package se.chalmers.halfwaytospirit.waveapp;

import java.util.ArrayList;

/**
 * This class keeps track of the game state.
 */
public class GameManager {
    private ArrayList<Player> players = new ArrayList<>();

    private boolean isGameRunning = false;

    /**
     * Constructor.
     */
    public GameManager() {
        ArrayList<Player> playerList = new ArrayList<>();
    }

    /**
     * Getter of the player list.
     * @return the list of players.
     */
    public ArrayList<Player> getPlayers() {
        return this.players;
    }

    /**
     * Gets whether the game is running.
     * @return whether the game is running.
     */
    public boolean gameIsRunning() {
        return isGameRunning;
    }

    /**
     * Sets whether the game is running or not.
     * @param isRunning - is the game running or not?
     */
    public void setGameIsRunning(boolean isRunning) {
        this.isGameRunning = isRunning;
    }
}
