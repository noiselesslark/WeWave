package se.chalmers.halfwaytospirit.waveapp;

import android.content.Intent;
import android.content.SharedPreferences;
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

        ImageButton playAgainButton = (ImageButton) findViewById(R.id.lastScreenPlayAgainButton);
        playAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MainGameActivity.class);
                startActivity(intent);
            }
        });

        /* TODO link the counter from the winner
        String waveSuccess = getIntent().getExtras().getString("waveSuccess");
        ((TextView)findViewById(R.id.lastScreenWaveSuccessText)).setText(waveSuccess);*/
    }
}
