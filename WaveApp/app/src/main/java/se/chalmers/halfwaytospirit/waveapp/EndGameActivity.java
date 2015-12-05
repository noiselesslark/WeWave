package se.chalmers.halfwaytospirit.waveapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class EndGameActivity extends AppCompatActivity {

    private String highScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game);

        // Maybe not here
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        this.load(sharedPreferences);

        ImageButton playAgainButton = (ImageButton) findViewById(R.id.lastScreenPlayAgainButton);
        playAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MainGameActivity.class);
                startActivity(intent);
            }
        });

        String waveTime = getIntent().getExtras().getString("waveTime");
        ((TextView)findViewById(R.id.lastScreenWaveTimeText)).setText(waveTime);

        /*if (waveTime>this.highScore) {
            this.highScore = waveTime;
            ((TextView)findViewById(R.id.newHighScoreLabel)).setVisibility(View.VISIBLE);
            saveChanges(sharedPreferences);
        } else {
            ((TextView)findViewById(R.id.newHighScoreLabel)).setVisibility(View.INVISIBLE);
        }*/

        int winnerImagePosition = getIntent().getExtras().getInt("winnerPosition");
        int winnerImageID = 0;
        switch (winnerImagePosition) {
            case 0:
                break;
            case 1: winnerImageID = R.drawable.winner_picture1;
                break;
        }
        ((ImageView)findViewById(R.id.winnerImage)).setImageResource(winnerImageID);
    }

    public void saveChanges(SharedPreferences sharedPreferences) {
        /*Gson gson = new Gson();
        String jsonHighScore = gson.toJson(highScore);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("newHighScore", jsonHighScore);

        // Commit the edits!
        editor.commit();*/
    }

    public void load(SharedPreferences sharedPreferences) {
        /*Gson gson = new Gson();
        String jsonHighScore = gson.fromJson(sharedPreferences.getString("newHighScore", null), new TypeToken<String>(){}.getType());
        if(jsonHighScore != null) {
            this.highScore = jsonHighScore;
        }*/
    }
}
