package se.chalmers.halfwaytospirit.waveapp;

import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * This class manages the game activity.
 */
public class MainGameActivity extends AppCompatActivity {

    private GameManager gameManager;
    private TextView countDownArea;
    private TextView playerLostArea;
    private GameView gameView;

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

        Typeface pixelFont = Typeface.createFromAsset(getAssets(), "fonts/motorola.ttf");
        countDownArea = ((TextView) findViewById(R.id.countdownArea));
        playerLostArea = ((TextView) findViewById(R.id.playerLostText));
        countDownArea.setTypeface(pixelFont);
        playerLostArea.setTypeface(pixelFont);
        gameView = (GameView) findViewById(R.id.stadium_view);

        for(TouchZone zone: gameView.getTouchZones()) {
            drawAvatar(zone);
        }

        startTimer();
    }

    /**
     * Starts the timer before the actual game
     */
    private void startTimer() {
        new CountDownTimer(6000, 1000) {
            public void onTick(long millisUntilFinished) {
                countDownArea.setText("" + millisUntilFinished/1000);

            }

            public void onFinish() {
                findViewById(R.id.countdownBackground).postDelayed(new Runnable() {
                    public void run() {
               countDownArea.setText(
                        getString(R.string.countdownLetsText)+ System.getProperty("line.separator") +
                                getString(R.string.weWaveText));

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
                gameManager.eliminatePlayer(player);
            }
        });

        for (TouchZone zone : gameView.getTouchZones()) {
            if (zone.isTouched()) {
                Player player = new Player(zone.getColourName());
                zone.setPlayer(player);

                gameManager.getActivePlayers().add(player);

            } else {
                zone.setEnabled(false);
            }
        }

        if(gameManager.getActivePlayers().size() > 1) {
            // Hide the countdown.
            findViewById(R.id.countdownBackground).setVisibility(View.INVISIBLE);

            gameManager.setGameRunning(true);

            // Starts the animation of the path.
            WaveAnimation pathAnimation = new WaveAnimation(gameView, 360);
            gameView.startAnimation(pathAnimation);
        }
    }

    /**
     * Draws an avatar for the specified touch zone.
     * @param zone
     */
    private void drawAvatar(TouchZone zone){
        AvatarView avatarView = new AvatarView(this);
        avatarView.setIsEmpty(true);

        int size = Math.round(TouchZone.OUTER_RADIUS*3);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(size, size);
        params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
        params.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
        avatarView.setScaleType(ImageView.ScaleType.FIT_XY);

        int marginX = Math.round(zone.getCenterX()) - size/2;
        int marginY = Math.round(zone.getCenterY()) - size/2;

        int drawableId = R.drawable.avatar_blue;
        float offset = 2*size/3;

        switch (getString(zone.getColourName())) {
            case "Green":
                drawableId = R.drawable.avatar_green;
                marginX += offset;
                break;
            case "Yellow":
                drawableId = R.drawable.avatar_yellow;
                marginX += offset;
                break;
            case "Pink":
                drawableId = R.drawable.avatar_pink;
                marginY += offset;
                break;
            case "Purple":
                drawableId = R.drawable.avatar_purple;
                marginX -= offset;
                break;
            case "Blue":
                drawableId = R.drawable.avatar_blue;
                marginX -= offset;
                break;
            case "Turquoise":
                drawableId = R.drawable.avatar_turquoise;
                marginY -= offset;
                break;
        }

        Drawable drawable = getResources().getDrawable(drawableId);

        avatarView.setImageDrawable(drawable);
        params.setMargins(marginX, marginY, 0, 0);

        ((ViewGroup)findViewById(R.id.stadium_view_layout)).addView(avatarView, params);
        zone.setAvatar(avatarView);
    }
}
