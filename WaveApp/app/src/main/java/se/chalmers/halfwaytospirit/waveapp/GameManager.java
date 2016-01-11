package se.chalmers.halfwaytospirit.waveapp;

import java.util.ArrayList;
import java.util.List;

/**
 * This class keeps track of the game state.
 */
public class GameManager {
    private List<Player> activePlayers = new ArrayList<>();
    private List<Player> eliminatedPlayers = new ArrayList<>();

    private boolean isGameRunning = false;

    private int completedCircuits = 0;

    /**
     * Constructor.
     */
    public GameManager() {}

    /**
     * Gets the list of active players.
     * @return the list of active players.
     */
    public List<Player> getActivePlayers() {
        return this.activePlayers;
    }

    /**
     * Gets the list of eliminated players.
     * @return the list of eliminated players.
     */
    public List<Player> getEliminatedPlayers() {
        return eliminatedPlayers;
    }

    /**
     * This method gets the number of completed circuits.
     * @return the number of completed circuits.
     */
    public int getCompletedCircuits() {
        return completedCircuits;
    }

    /**
     * Gets whether the game is running.
     * @return whether the game is running.
     */
    public boolean isGameRunning() {
        return isGameRunning;
    }

    /**
     * Sets whether the game is running or not.
     * @param isRunning - is the game running or not?
     */
    public void setGameRunning(boolean isRunning) {
        this.isGameRunning = isRunning;
    }

    /**
     * Eliminates the specified player from the game.
     * @param player - the player.
     */
    public boolean eliminatePlayer(Player player) {
        activePlayers.remove(player);
        eliminatedPlayers.add(player);

        if(activePlayers.size()==0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * This method increments the number of completed circuits by one.
     */
    public void incrementCircuitCount() {
        this.completedCircuits++;
    }
}
