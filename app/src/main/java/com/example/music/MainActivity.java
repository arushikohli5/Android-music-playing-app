package com.example.music;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements MusicStoppedListener{
ImageView ivplaystop;
String audiolink="https://dl.dropbox.com/s/pfpys0810sdarwg/lag%20jaa%20gale%20-%20sadhana%2C%20lata%20mangeshkar%2C%20woh%20kaun%20thi%20romantic%20song.mp3?dl=0";
Boolean MusicPlaying=false;
Intent serviceIntent;
private MusicStoppedListener musicStoppedListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ivplaystop=(ImageView) findViewById(R.id.ivplayStop);
        ivplaystop.setBackgroundResource(R.drawable.play);
        serviceIntent=new Intent(this,MyService.class);

        ApplicationClass.context=(Context) MainActivity.this;

        ivplaystop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!MusicPlaying){
                    playAudio();
                    ivplaystop.setImageResource(R.drawable.stop);
                    MusicPlaying=true;
                }
                else{
                    stopPlayingService();
                    ivplaystop.setImageResource(R.drawable.play);
                    MusicPlaying=false;
                }
            }
        });
    }

    private void stopPlayingService() {

        try {
            stopService(serviceIntent);
        }
        catch (SecurityException e)
        {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void playAudio() {
        serviceIntent.putExtra("audiolink",audiolink);

        try {
            startService(serviceIntent);
        }
        catch (SecurityException e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onMusicStopped() {
        ivplaystop.setImageResource(R.drawable.play);
    }
}