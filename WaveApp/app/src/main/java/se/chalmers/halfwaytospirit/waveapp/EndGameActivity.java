package se.chalmers.halfwaytospirit.waveapp;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.List;

public class EndGameActivity extends AppCompatActivity {
    List<PlayerData> playerData;

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
}
