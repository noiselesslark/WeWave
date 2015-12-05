package se.chalmers.halfwaytospirit.waveapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StartScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);

        Button playButton = (Button) findViewById(R.id.firstScreenPlayButton);
        playButton.setOnClickListener(new View.OnClickListener() {

            /**
             * On click handler for button.
             * @param view - the view.
             */
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(view.getContext(), MainGameActivity.class);
                startActivity(intent);
            }
        });
    }
}
