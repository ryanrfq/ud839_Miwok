package com.example.android.miwok.helpers;

import android.media.MediaPlayer;

public class MediaPlayerHelper {
    public void nullifyMediaPlayer(MediaPlayer mp) {
        if (mp != null) {
            mp.release();
            mp = null;
        }
    }
}
