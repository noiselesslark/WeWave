package se.chalmers.halfwaytospirit.waveapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

public class StartScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_start_screen);
    }
}
