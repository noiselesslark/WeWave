package se.chalmers.halfwaytospirit.waveapp;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class MainGameActivity extends AppCompatActivity {

    private GameManager gameManager;

    /**
     * Called when the activity is created.
     * @param savedInstanceState - the saved instance state.
     */
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
                        getString(R.string.countdownLetsText)+ System.getProperty("line.separator") +
                                getString(R.string.countdownWeWaveText));

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

        StadiumView gameView = (StadiumView) findViewById(R.id.stadium_view);

        for (TouchZone tz : gameView.getTouchZones()) {

            if (tz.isTouched()) {
                Player player = new Player();
                player.setTouchZone(tz);

                gameManager.getPlayers().add(player);

            } else {
                // Change the color to the eliminated one.
                tz.setEnabled(false, gameView.getColour(R.color.colorGrey));
            }
        }

        // Hide the countdown.
        findViewById(R.id.countdownBackground).setVisibility(View.INVISIBLE);

        // Starts the circular animation of the path.
        StadiumPathAnimation pathAnimation = new StadiumPathAnimation(gameView, 360);
        gameView.startAnimation(pathAnimation);
    }
}
