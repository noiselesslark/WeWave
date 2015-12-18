package se.chalmers.halfwaytospirit.waveapp;

import java.util.ArrayList;

/**
 * This class keeps track of the game state.
 */
public class GameManager {
    private ArrayList<Player> players = new ArrayList<>();

    public static String PLAYER_TEXT = "Player";
    public static String PLAYER_TOP = PLAYER_TEXT + "1";
    public static String PLAYER_LEFT_HIGH = PLAYER_TEXT + "2";
    public static String PLAYER_LEFT_LOW = PLAYER_TEXT + "3";
    public static String PLAYER_RIGHT_HIGH = PLAYER_TEXT + "4";
    public static String PLAYER_RIGHT_LOW = PLAYER_TEXT + "5";
    public static String PLAYER_BOTTOM = PLAYER_TEXT + "6";

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
}
