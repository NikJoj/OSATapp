package com.tochalumni.osat.osat;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.imangazaliev.circlemenu.CircleMenu;
import com.imangazaliev.circlemenu.CircleMenuButton;

import osat.home.com.homeActivity;

import static android.os.SystemClock.sleep;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CircleMenu circleMenu = (CircleMenu) findViewById(R.id.circleMenu);
        circleMenu.setOnItemClickListener(new CircleMenu.OnItemClickListener() {
            @Override
            public void onItemClick(CircleMenuButton menuButton) {
                switch (menuButton.getId()) {
                    case R.id.favorite:
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable(){
                            public void run() {
                                Intent intent = new Intent(MainActivity.this, homeActivity.class);
                                startActivity(intent);
                                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                            }
                        }, 600);

                        break;
                    case R.id.search:
                        Handler handler2 = new Handler();
                        handler2.postDelayed(new Runnable(){
                            public void run() {
                                Intent intent = new Intent(MainActivity.this, eventActivity.class);
                                startActivity(intent);
                                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                            }
                        }, 600);
                        break;
                    case R.id.alert:
                        Handler handler3 = new Handler();
                        handler3.postDelayed(new Runnable(){
                            public void run() {
                                Intent intent = new Intent(MainActivity.this, alumniActivity.class);
                                startActivity(intent);
                                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                            }
                        }, 600);
                        break;
                    case R.id.noti:
                        Handler handler4 = new Handler();
                        handler4.postDelayed(new Runnable(){
                            public void run() {
                                Intent intent = new Intent(MainActivity.this, notif.class);
                                startActivity(intent);
                                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                            }
                        }, 600);
                        break;
                }
            }
        });

        circleMenu.setStateUpdateListener(new CircleMenu.OnStateUpdateListener() {
            @Override
            public void onMenuExpanded() {
                Log.d("CircleMenuStatus", "Expanded");
            }

            @Override
            public void onMenuCollapsed() {
                Log.d("CircleMenuStatus", "Collapsed");
            }
        });

    }
    private void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}