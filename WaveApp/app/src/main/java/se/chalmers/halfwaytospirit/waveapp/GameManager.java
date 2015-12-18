package se.chalmers.halfwaytospirit.waveapp;

import java.util.ArrayList;

/**
 * This class keeps track of the game state.
 */
public class GameManager {
    private ArrayList<Player> players = new ArrayList<>();

    public static String PLAYER_TEXT = "Player";
    public static String PLAYER_1 = PLAYER_TEXT + "1";
    public static String PLAYER_2 = PLAYER_TEXT + "2";
    public static String PLAYER_3 = PLAYER_TEXT + "3";
    public static String PLAYER_4 = PLAYER_TEXT + "4";
    public static String PLAYER_5 = PLAYER_TEXT + "5";
    public static String PLAYER_6 = PLAYER_TEXT + "6";

    /**
     * Constructor.
     */
    public GameManager() {
        createPlayerList();
    }

    /**
     * Creates the players at the start of the game.
     * @return the player list.
     */
    private ArrayList<Player> createPlayerList() {
        ArrayList<Player> playerList = new ArrayList<>();

        for (int i = 1; i <= 6; i++) {
            playerList.add(new Player(i));
        }

        return playerList;
    }

    /**
     * Getter of the player list.
     * @return the list of players.
     */
    public ArrayList<Player> getPlayers() {
        return this.players;
    }
}
