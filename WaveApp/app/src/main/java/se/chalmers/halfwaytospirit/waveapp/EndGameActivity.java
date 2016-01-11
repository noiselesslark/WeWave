package se.chalmers.halfwaytospirit.waveapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
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
                ((Activity)view.getContext()).finish();
            }
        });

        Bundle extras = getIntent().getExtras();
        String winnerName = extras.getString(getString(R.string.winningPlayerNameId));
        int winnerCount = extras.getInt(getString(R.string.winningWaveCountId));

        TextView winLabel = (TextView)findViewById(R.id.youWinLabel);
        winLabel.setText(getString(R.string.winnerText, winnerName));
        winLabel.setTypeface(pixelFont);
        winLabel.setTextColor(getPlayerColour(winnerName));

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

                table.addView(buildTableRow(name, count, pixelFont));
            }
        }
    }

    /**
     * Builds a table row for the specified player.
     * @param name - the name.
     * @param count - the circuit count.
     * @param pixelFont - the font to render with.
     * @return A table row.
     */
    private TableRow buildTableRow(String name, int count, Typeface pixelFont) {
        TableLayout.LayoutParams params = new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        TableRow row = new TableRow(this);
        row.setPadding(5, 5, 5, 5);
        row.setLayoutParams(params);

        TextView nameTv = new TextView(this);
        nameTv.setTextColor(getPlayerColour(name));
        nameTv.setPadding(5, 5, 5, 5);
        nameTv.setTypeface(pixelFont);
        nameTv.setText(getString(R.string.playerText, name));

        TextView countTv = new TextView(this);
        countTv.setPadding(10, 5, 5, 5);
        countTv.setTypeface(pixelFont);
        countTv.setText(getString(R.string.wavesText, count));

        row.addView(nameTv);
        row.addView(countTv);

        return row;
    }

    /**
     * This method returns the appropriate colour resource for the player name.
     * @param colourName - the name of the colour.
     * @return the colour identifier.
     */
    private int getPlayerColour(String colourName) {
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
                break;
            case "Green":
                id = R.color.colorGreen;
                break;
        }

        return ContextCompat.getColor(this, id);
    }

    private void showWinner(String winnerName) {
        ImageView winnerView = new ImageView(this);
        int drawableId = 0;

        switch (winnerName) {
            case "Blue":
                drawableId = R.drawable.winning_blue;
                break;
            case "Green":
                drawableId = R.drawable.winning_green;
                break;
            case "Pink":
                drawableId = R.drawable.winning_pink;
                break;
            case "Purple":
                drawableId = R.drawable.winning_purple;
                break;
            case "Turquoise":
                drawableId = R.drawable.winning_turquoise;
                break;
            case "Yellow":
                drawableId = R.drawable.winning_yellow;
                break;
            }

        Drawable drawable = ResourcesCompat.getDrawable(getResources(), drawableId, getTheme());

        winnerView.setImageDrawable(drawable);
        drawable.setCallback(winnerView);
        drawable.setVisible(true, true);
        ((Animatable)drawable).start();

        ((ViewGroup)findViewById(R.id.winnerPictureField)).addView(winnerView);

        TableLayout scoreTable = (TableLayout)findViewById(R.id.otherPlayersTable);
        scoreTable.getLayoutParams().height = RelativeLayout.LayoutParams.WRAP_CONTENT;
    }
}
