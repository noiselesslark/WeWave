package se.chalmers.halfwaytospirit.waveapp;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
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
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

/**
 * This class manages the end game screen.
 */
public class EndGameActivity extends AppCompatActivity {

    /**
     * Called when the activity is created.
     * @param savedInstanceState - the saved instance state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game);

        Typeface pixelFont = Typeface.createFromAsset(getAssets(), "fonts/motorola.ttf");

        Button playAgainButton = (Button) findViewById(R.id.replayButton);
        playAgainButton.setTypeface(pixelFont);
        playAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MainGameActivity.class);
                startActivity(intent);
            }
        });

        Bundle extras = getIntent().getExtras();

        String winnerName = extras.getString(getString(R.string.winningPlayerNameId));
        int winnerCount = extras.getInt(getString(R.string.winningWaveCountId));
        TextView winLabel = (TextView)findViewById(R.id.youWinLabel);
        String label = getString(R.string.winnerText, winnerName);
        winLabel.setText(label);
        winLabel.setTypeface(pixelFont);

        ((TextView)findViewById(R.id.winningWaveCountLabel)).setTypeface(pixelFont);

        TextView countText = (TextView)findViewById(R.id.waveCountValue);
        countText.setTypeface(pixelFont);
        countText.setText(getString(R.string.wavesText, winnerCount));

        showWinner(winnerName);

        TableLayout table = (TableLayout)findViewById(R.id.otherPlayersTable);

        for(int pos=2; pos<=6; pos++){
            String playerId = getString(R.string.playeridprefix) + pos;
            if(extras.containsKey(playerId)) {
                String name = extras.getString(playerId);
                int count = extras.getInt(name);

                TableLayout.LayoutParams params = new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                TableRow row = new TableRow(this);
                row.setPadding(5,5,5,5);
                row.setLayoutParams(params);

                TextView nameTv = new TextView(this);
                nameTv.setTextColor(getColour(name));
                nameTv.setPadding(5, 5, 5, 5);
                nameTv.setTypeface(pixelFont);
                nameTv.setText(getString(R.string.playerText, name));

                TextView countTv = new TextView(this);
                countTv.setPadding(10,5,5,5);
                countTv.setTypeface(pixelFont);
                countTv.setText(getString(R.string.wavesText, count));

                row.addView(nameTv);
                row.addView(countTv);

                table.addView(row);
            }
        }
    }

    /**
     * This method returns the appropriate colour resource for the player name.
     * @param colourName - the name of the colour.
     * @return the colour identifier.
     */
    private int getColour(String colourName) {
        int id = 0;
        switch(colourName) {
            case "Yellow":
                id = R.color.colorYellow;
                break;
            case "Blue":
                id = R.color.colorBlue;
                break;
            case "Turquoise":
                id =  R.color.colorTurquoise;
                break;
            case "Purple":
                id = R.color.colorPurple;
                break;
            case "Pink":
                id = R.color.colorPink;
            case "Green":
                id = R.color.colorGreen;
        }

        return ContextCompat.getColor(this, id);
    }

    private void showWinner(String winnerName) {
        WinnerView winnerView = new WinnerView(this);
        winnerView.setIsWinnerBlue(true);

        int size = Math.round(TouchZone.OUTER_RADIUS * 10);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(size, size);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        params.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
        winnerView.setScaleType(ImageView.ScaleType.CENTER);

        int drawableId = R.drawable.winning_animation;

        switch (winnerName) {
            case "Blue":
                winnerView.setIsWinnerBlue(true);
                break;
            case "Green":
                winnerView.setIsWinnerGreen(true);
                break;
            case "Pink":
                winnerView.setIsWinnerPink(true);
                break;
            case "Purple":
                winnerView.setIsWinnerPurple(true);
                break;
            case "Turquoise":
                winnerView.setIsWinnerTurquoise(true);
                break;
            case "Yellow":
                winnerView.setIsWinnerYellow(true);
                break;
            }

        Drawable drawable = ResourcesCompat.getDrawable(getResources(), drawableId, getTheme());

        winnerView.setImageDrawable(drawable);

        ((ViewGroup)findViewById(R.id.endRootView)).addView(winnerView, params);

        TableLayout scoreTable = (TableLayout)findViewById(R.id.otherPlayersTable);
        scoreTable.getLayoutParams().height = RelativeLayout.LayoutParams.WRAP_CONTENT;
    }
}
