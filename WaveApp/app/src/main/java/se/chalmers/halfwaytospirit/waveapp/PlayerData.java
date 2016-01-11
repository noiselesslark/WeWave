package se.chalmers.halfwaytospirit.waveapp;

/**
 * This class keeps basic data about each player for use on the end game string.
 */
public class PlayerData {
    private int position;
    private String name;
    private int circuitCount;

    public PlayerData(int position, String name, int circuitCount) {
        this.position = position;
        this.name = name;
        this.circuitCount = circuitCount;
    }

    public int getPosition() {
        return position;
    }

    public String getName() {
        return name;
    }

    public int getCircuitCount() {
        return circuitCount;
    }
}
