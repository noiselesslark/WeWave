package se.chalmers.halfwaytospirit.waveapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class EndGameActivity extends AppCompatActivity {


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
        String playerName = extras.getString(getString(R.string.playerNameId));

        TextView winLabel = (TextView)findViewById(R.id.youWinLabel);
        String label = getString(R.string.winnerText) + System.getProperty("line.separator") +
                getString(R.string.playerText) + " " + playerName + "!";
        winLabel.setText(label);
        winLabel.setTypeface(pixelFont);

        TextView countText = (TextView)findViewById(R.id.waveCountValue);
        countText.setTypeface(pixelFont);
        countText.setText("" + extras.getInt(getString(R.string.waveCountId)));
    }
}
