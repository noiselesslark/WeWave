package se.chalmers.halfwaytospirit.waveapp;

import java.util.ArrayList;

/**
 * This class keeps track of the game state.
 */
public class GameManager {
    private ArrayList<Player> players = new ArrayList<>();

    private boolean isRunning = false;

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

    public boolean gameIsRunning() {
        return isRunning;
    }

    public void setGameIsRunning(boolean isRunning) {
        this.isRunning = isRunning;
    }
}
