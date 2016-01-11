package se.chalmers.halfwaytospirit.waveapp;

import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * This class manages the game activity.
 */
public class MainGameActivity extends AppCompatActivity {

    private GameManager gameManager;
    private TextView countDownText;
    private RelativeLayout countDownBackground;
    private TextView playerLostArea;
    private GameView gameView;
    private LinearLayout tryAgainWidget;
    private RelativeLayout waveStartView;

    private WaveAnimation wave;

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

        countDownText = (TextView) findViewById(R.id.countdownText);
        countDownBackground = (RelativeLayout) findViewById(R.id.countdownBackground);
        playerLostArea = (TextView) findViewById(R.id.playerLostText);
        tryAgainWidget = (LinearLayout) findViewById(R.id.tryAgainLayout);
        gameView = (GameView) findViewById(R.id.stadium_view);
        waveStartView = (RelativeLayout) findViewById(R.id.startWaveField);

        Button tryAgainButton = ((Button)findViewById(R.id.tryAgainButton));

        Typeface pixelFont = Typeface.createFromAsset(getAssets(), "fonts/motorola.ttf");
        countDownText.setTypeface(pixelFont);
        playerLostArea.setTypeface(pixelFont);
        tryAgainButton.setTypeface(pixelFont);
        ((TextView)findViewById(R.id.startWaveLabel)).setTypeface(pixelFont);

        tryAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restartGame();
            }
        });

        for(TouchZone zone: gameView.getTouchZones()) {
            drawAvatar(zone);
        }

        startCountdown();
    }

    /**
     * Starts the timer before the actual game
     */
    private void startCountdown() {
        new CountDownTimer(6000, 500) {
            public void onTick(long millisUntilFinished) {
                int count = Math.round(millisUntilFinished/1000);
                countDownText.setText(getString(R.string.countDownText, count));

                if(count < 1) {
                    countDownText.setText(getString(R.string.letsWeWaveText));
                }
            }

            public void onFinish() {
                startGame();
            }
        }.start();
    }

    /**
     * Initialises and starts the game.
     */
    private void startGame() {

        GameView gameView = (GameView) findViewById(R.id.stadium_view);

        for (TouchZone zone : gameView.getTouchZones()) {
            if (zone.isTouched()) {
                Player player = new Player(getString(zone.getColourId()));
                zone.setPlayer(player);

                gameManager.getActivePlayers().add(player);

            } else {
                zone.setEnabled(false);
                zone.getAvatar().setIsEmpty(true);
            }
        }

        if(gameManager.getActivePlayers().size()==1) {
            showOnePlayerError();
        }else if (gameManager.getActivePlayers().size()==0) {
            showNoPlayerError();
        } else {
            AnimatorUtility.hideView(countDownBackground);

            gameView.setPlayerLostListener(new IOnPlayerLostListener() {
                @Override
                public void onPlayerLost(Player player) {
                    player.setCircuitCount(gameManager.getCompletedCircuits());
                    boolean playerWon = gameManager.eliminatePlayer(player);
                    showPlayerLostNotification(player.getPlayerName());

                    if (playerWon) {
                        endGame(player);
                    }
                }
            });

            gameManager.setGameRunning(true);

            // Starts the animation of the path.
            wave = new WaveAnimation(gameView, 360);
            wave.setCircuitCompleteListener(new IOnCircuitCompleteListener() {
                @Override
                public void onCircuitComplete() {
                    gameManager.incrementCircuitCount();
                }
            });

            gameView.startAnimation(wave);
        }
    }

    /**
     * Shows the error when no players entered the game.
     */
    private void showNoPlayerError() {
        countDownText.setText(getString(R.string.noPlayerError));
        countDownBackground.setBackgroundColor(ContextCompat.getColor(this, R.color.transparent));
        countDownText.setTextColor(ContextCompat.getColor(this, R.color.colorWhite));
        countDownText.setBackgroundColor(ContextCompat.getColor(this, R.color.blackOverlay));

        for (TouchZone zone : gameView.getTouchZones()) {
            zone.setEnabled(true);
            zone.setEliminated(true);
        }
        gameView.invalidate();
        gameManager.setGameRunning(true);
        AnimatorUtility.showView(tryAgainWidget);
        AnimatorUtility.hideView(waveStartView);
    }

    /**
     * Shows when a player has lost.
     * @param name - the name.
     */
    private void showPlayerLostNotification(String name) {
        playerLostArea.setText(getString(R.string.playerMissedWaveText, name));
        AnimatorUtility.showView(playerLostArea);
        playerLostArea.postDelayed(new Runnable() {
            @Override
            public void run() {
                AnimatorUtility.hideView(playerLostArea);
            }
        }, 100);
    }

    /**
     * Shows the error for only one player playing.
     */
    private void showOnePlayerError() {
        countDownText.setText(getString(R.string.playerNumberError));

        AnimatorUtility.showView(tryAgainWidget);
        AnimatorUtility.hideView(waveStartView);
    }

    /**
     * Resets and restarts the game.
     */
    private void restartGame() {
        countDownBackground.setBackgroundColor(ContextCompat.getColor(this, R.color.fadeOutBackground));
        AnimatorUtility.showView(countDownBackground);
        countDownText.setTextColor(ContextCompat.getColor(this, R.color.colorBlack));
        countDownText.setBackgroundColor(ContextCompat.getColor(this, R.color.transparent));

        for (TouchZone zone : gameView.getTouchZones()) {
            zone.setEnabled(true);
            zone.setEliminated(false);
            zone.getAvatar().setIsEmpty(true);
        }
        gameView.invalidate();
        gameManager.resetGame();

        AnimatorUtility.hideView(tryAgainWidget);
        AnimatorUtility.showView(waveStartView);
        startCountdown();
    }

    /**
     * Draws an avatar for the specified touch zone.
     * @param zone - the touch zone.
     */
    private void drawAvatar(TouchZone zone) {
        AvatarView avatarView = new AvatarView(this);
        avatarView.setIsEmpty(true);

        int size = Math.round(TouchZone.OUTER_RADIUS * 3);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(size, size);
        params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
        params.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
        avatarView.setScaleType(ImageView.ScaleType.FIT_XY);

        int marginX = Math.round(zone.getCenterX()) - size/2;
        int marginY = Math.round(zone.getCenterY()) - size/2;

        int drawableId = R.drawable.avatar_blue;
        float offset = 2*size/3;

        switch (getString(zone.getColourId())) {
            case "Turquoise":
                drawableId = R.drawable.avatar_turquoise;
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
            case "Green":
                drawableId = R.drawable.avatar_green;
                marginY -= offset;
                break;
        }

        Drawable drawable = ResourcesCompat.getDrawable(getResources(), drawableId, getTheme());

        avatarView.setImageDrawable(drawable);
        params.setMargins(marginX, marginY, 0, 0);

        ((ViewGroup)findViewById(R.id.gameRootView)).addView(avatarView, params);
        zone.setAvatar(avatarView);
    }

    /**
     * Ends the game.
     * @param winningPlayer - the winning player.
     */
    private void endGame(Player winningPlayer) {
        wave.cancel();
        gameManager.setGameRunning(false);
        this.finish();

        Intent intent = new Intent(this, EndGameActivity.class);
        Bundle playerData = new Bundle();

        playerData.putString(getString(R.string.winningPlayerNameId), winningPlayer.getPlayerName());
        playerData.putInt(getString(R.string.winningWaveCountId), winningPlayer.getCircuitCount());

        int playerPos = 2;
        for(Player lostPlayer : gameManager.getEliminatedPlayers()) {
            playerData.putString(getString(R.string.playeridprefix) + playerPos, lostPlayer.getPlayerName());
            playerData.putInt(lostPlayer.getPlayerName(), lostPlayer.getCircuitCount());
            playerPos++;
        }

        intent.putExtras(playerData);

        startActivity(intent);
    }

    /**
     * Called when activity is destroyed.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();

        unbindDrawables(findViewById(R.id.gameRootView));
        System.gc();
    }

    /**
     * Unbinds all drawables.
     * @param view - the root view.
     */
    private void unbindDrawables(View view) {
        if (view.getBackground() != null) {
            view.getBackground().setCallback(null);
        }
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                unbindDrawables(((ViewGroup) view).getChildAt(i));
            }
            ((ViewGroup) view).removeAllViews();
        }
    }
}
