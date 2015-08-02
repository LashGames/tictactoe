package com.android.lash.tictactoe;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;


public class Splash extends AppCompatActivity {
    MediaPlayer player;
    ImageView slideView;
    GestureDetectorCompat gestureDetectorCompat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        slideView = (ImageView)findViewById(R.id.slide_view);

        gestureDetectorCompat=new GestureDetectorCompat(Splash.this,new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                float diff=e1.getX()-e2.getX();
                if(diff>500){
                    //Toast.makeText(getBaseContext(),"Swipe Distance:"+diff,Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(Splash.this,MainMenu.class);
                    startActivity(intent);
                }
                return super.onFling(e1, e2, velocityX, velocityY);
            }
        });

        slideView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                gestureDetectorCompat.onTouchEvent(motionEvent);
                //Toast.makeText(getBaseContext(),"Touched",Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        player = MediaPlayer.create(Splash.this, R.raw.lash_welcome_sound);
        player.start();
        final int[] slideList = {R.drawable.slide_0, R.drawable.slide_1, R.drawable.slide_2, R.drawable.slide_3, R.drawable.slide_4, R.drawable.slide_5,
                R.drawable.slide_6, R.drawable.slide_7, R.drawable.slide_8, R.drawable.slide_9, R.drawable.slide_10, R.drawable.slide_11};

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            int slideCount = 0;

            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (slideCount == slideList.length) {
                            slideCount = 0;
                        }
                        slideView.setImageResource(slideList[slideCount]);
                        slideCount++;
                    }
                });

            }
        }, 500, 150);
    }

    @Override
    protected void onPause() {
        super.onPause();
        player.stop();
        finish();
    }

    /*@Override
    public boolean onTouchEvent(MotionEvent event) {
        this.gestureDetectorCompat.onTouchEvent(event);
        return super.onTouchEvent(event);
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_splash, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
