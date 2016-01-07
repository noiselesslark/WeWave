package se.chalmers.halfwaytospirit.waveapp;

/**
 * This interface defines a listener for when a player has lost.
 */
public interface IOnPlayerLostListener {

    /**
     * Called when player has lost.
     * @param player - the player that has lost. 
     */
    void onPlayerLost(Player player);
}
