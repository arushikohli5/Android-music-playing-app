package com.example.music;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.widget.Toast;

public class MyService extends Service implements MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener,
        MediaPlayer.OnErrorListener,MediaPlayer.OnSeekCompleteListener,MediaPlayer.OnInfoListener,
        MediaPlayer.OnBufferingUpdateListener{
    private MediaPlayer mp;
    String link;
    private MusicStoppedListener musicStoppedListener;

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mp=new MediaPlayer();
        mp.setOnCompletionListener(this);
        mp.setOnErrorListener(this);
        mp.setOnPreparedListener(this);
        mp.setOnSeekCompleteListener(this);
        mp.setOnBufferingUpdateListener(this);
        mp.setOnInfoListener(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        link=intent.getStringExtra("audiolink");
        mp.reset();
        musicStoppedListener=(MusicStoppedListener) ApplicationClass.context;
        if(!mp.isPlaying())
        {
            try{
                mp.setDataSource(link);
                mp.prepareAsync();
            }
            catch (Exception e)
            {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        return START_STICKY;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mp!=null)
        {
            if(mp.isPlaying()){
                mp.stop();
            }
            mp.release();
        }
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {

        if(mediaPlayer.isPlaying())
        {
            mediaPlayer.stop();
        }
        stopSelf();
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        switch (what)
        {
            case MediaPlayer.MEDIA_ERROR_NOT_VALID_FOR_PROGRESSIVE_PLAYBACK:
                Toast.makeText(this, "MEDIA_ERROR_NOT_VALID_FOR_PROGRESSIVE_PLAYBACK",
                        Toast.LENGTH_SHORT).show();
                break;

            case MediaPlayer.MEDIA_ERROR_UNKNOWN:
                Toast.makeText(this, "MEDIA_ERROR_UNKNOWN", Toast.LENGTH_SHORT).show();
                break;

            case MediaPlayer.MEDIA_ERROR_SERVER_DIED:
                Toast.makeText(this, "MEDIA_ERROR_SERVER_DIED", Toast.LENGTH_SHORT).show();
                break;
        }
        return false;
    }

    @Override
    public boolean onInfo(MediaPlayer mediaPlayer, int i, int i1) {
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        if(!mediaPlayer.isPlaying())
        {
            mp.start();
        }
    }

    @Override
    public void onSeekComplete(MediaPlayer mediaPlayer) {

    }

    @Override
    public void onBufferingUpdate(MediaPlayer mediaPlayer, int i) {

    }
}
