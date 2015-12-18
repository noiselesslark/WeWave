package se.chalmers.halfwaytospirit.waveapp;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class MainGameActivity extends AppCompatActivity {

    /**
     * Called when the activity is first created.
     * @param savedInstanceState - the saved instance state.
     */
    private GameManager gameManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);

        this.gameManager = new GameManager();

        setContentView(R.layout.activity_main_game);

        startTimer();
    }

    /**
     * Starts the timer before the actual game
     */
    private void startTimer() {
        new CountDownTimer(6000, 1000) {

            public void onTick(long millisUntilFinished) {
                ((TextView)findViewById(R.id.countdownArea)).setText("" + millisUntilFinished / 1000);
            }

            public void onFinish() {
                ((TextView) findViewById(R.id.countdownArea)).setText(
                        R.string.countdownLetsText + System.getProperty("line.separator") +
                                R.string.countdownWeWaveText);

                findViewById(R.id.countdownBackground).postDelayed(new Runnable() {
                    public void run() {
                        // TODO [EK]: We should probably only be starting the game once we've made
                        // TODO [EK]: sure there is somebody playing.
                        startGame();
                    }
                }, 1000);
            }
        }.start();
    }

    /**
     * Initialises and starts the game.
     */
    private void startGame() {
        findViewById(R.id.countdownBackground).setVisibility(View.INVISIBLE);

        StadiumView stadium = (StadiumView) findViewById(R.id.stadium_view);

        HashMap<String, TouchZone> touchZones = stadium.getTouchZones();
        ArrayList<Player> players = gameManager.getPlayers();
        ArrayList<Integer> eliminatedIndex = new ArrayList<>();
        for (int i=0; i<touchZones.size(); i++) {
            TouchZone tz = touchZones.get("Player" + (i+1));
            Player player = players.get(i);

            if (tz.isTouched()) {
                // Set the player on play
                player.setIsPlaying(true);
                player.setIsEliminated(false);
            } else {
                // Set the player not on play
                player.setIsPlaying(false);
                player.setIsEliminated(true);

                // Change the color to the eliminated one
                stadium.setTouchZoneColour(tz, R.color.colorGrey);

                // Saving the index for later elimination of the player
                eliminatedIndex.add(i);
            }
        }
        // Deleting the non-playing players from the list
        for (int i=eliminatedIndex.size()-1; i>=0; i--) {
            Player eliminatedPlayer = players.get(i);
            players.remove(eliminatedPlayer);
        }

        // Starts the circular animation of the path.
        StadiumPathAnimation pathAnimation = new StadiumPathAnimation(stadium, 360);
        stadium.startAnimation(pathAnimation);
    }
}
