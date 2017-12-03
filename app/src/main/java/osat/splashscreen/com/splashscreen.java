package osat.splashscreen.com;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.tochalumni.osat.osat.MainActivity;
import com.tochalumni.osat.osat.R;

public class splashscreen extends AppCompatActivity {
private static int SPLASH_TIME_OUT = 2500;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run(){
                Intent homeIntent = new Intent(splashscreen.this, MainActivity.class);
                startActivity(homeIntent);
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
