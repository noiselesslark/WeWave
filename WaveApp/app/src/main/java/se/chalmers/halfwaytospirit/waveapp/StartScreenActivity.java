package se.chalmers.halfwaytospirit.waveapp;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

/**
 * This class manages the start screen activity.
 */
public class StartScreenActivity extends AppCompatActivity {

    /**
     * Called when the activity is created.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_start_screen);
        Typeface pixelFont = Typeface.createFromAsset(getAssets(), "fonts/motorola.ttf");

        TextView logoText = (TextView) findViewById(R.id.logoText);
        logoText.setTypeface(pixelFont);

        Button startButton = (Button) findViewById(R.id.startPlayButton);
        startButton.setTypeface(pixelFont);

        startButton.setOnClickListener(new View.OnClickListener() {

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
