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
     * Gets the game manager.
     * @return the game manager.
     */
    public GameManager getGameManager() {
        return gameManager;
    }

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

        GameView gameView = (GameView) findViewById(R.id.stadium_view);
        gameView.setPlayerLostListener(new IOnPlayerLostListener() {
            @Override
            public void onPlayerLost(Player player) {
                gameManager.getPlayers().remove(player);
            }
        });

        for (TouchZone zone : gameView.getTouchZones()) {
            if (zone.isTouched()) {
                Player player = new Player();

                zone.setPlayer(player);

                gameManager.getPlayers().add(player);

            } else {
                zone.setEnabled(false);
            }
        }

        if(gameManager.getPlayers().size() > 1) {
            // Hide the countdown.
            findViewById(R.id.countdownBackground).setVisibility(View.INVISIBLE);

            gameManager.setGameIsRunning(true);
            // Starts the circular animation of the path.
            StadiumPathAnimation pathAnimation = new StadiumPathAnimation(gameView, 360);
            gameView.startAnimation(pathAnimation);
        }
    }
}
