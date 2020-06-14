package com.study.classapp.util;

import android.content.Context;
import android.media.AudioManager;
import android.util.Log;

import com.study.classapp.MyApp;

public class SilentManager {
    public void silentSwitchOn() {
        AudioManager audioManager = (AudioManager) MyApp.getMyApplication().getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
        if (audioManager != null) {
            audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
            audioManager.getStreamVolume(AudioManager.STREAM_RING);
            Log.d("Silent:", "RINGING 已被静音");
        }
    }

    public void silentSwitchOff() {
        AudioManager audioManager = (AudioManager) MyApp.getMyApplication().getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
        if (audioManager != null) {
            audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
            audioManager.getStreamVolume(AudioManager.STREAM_RING);
            Log.d("SilentListenerService", "RINGING 取消静音");
        }
    }
}
