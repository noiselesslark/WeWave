package se.chalmers.halfwaytospirit.waveapp;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class MainGameActivity extends AppCompatActivity {

    /**
     * Called when the activity is first created.
     * @param savedInstanceState - the saved instance state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_game);

        startTimer();

        // TODO - move within timer.
        StadiumView stadium = (StadiumView) findViewById(R.id.stadium_view);
        StadiumPathAnimation pathAnimation = new StadiumPathAnimation(stadium, 360);
        stadium.startAnimation(pathAnimation);
    }

    /**
     * Starts the timer before the actual game.
     */
    public void startTimer() {
        new CountDownTimer(6000, 1000) {

            public void onTick(long millisUntilFinished) {
                ((TextView)findViewById(R.id.countdownArea)).setText("" + millisUntilFinished / 1000);
            }

            public void onFinish() {
                ((TextView) findViewById(R.id.countdownArea)).setText("Let's" + System.getProperty("line.separator") + "WeWave!");

                findViewById(R.id.countdownBackground).postDelayed(new Runnable() {
                    public void run() {
                        findViewById(R.id.countdownBackground).setVisibility(View.INVISIBLE);
                    }
                }, 1000);
            }
        }.start();
    }
}
