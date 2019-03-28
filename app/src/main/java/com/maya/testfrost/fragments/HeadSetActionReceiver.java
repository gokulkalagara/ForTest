package com.maya.testfrost.fragments;

import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;

public class HeadSetActionReceiver extends BroadcastReceiver {
    private static final String TAG_MEDIA = "MEDIA";
    public static int counter = 0;
    @Override
    public void onReceive(Context context, Intent intent) {
        counter++;
        Log.d(TAG_MEDIA,"Counter "+counter);
        String intentAction = intent.getAction();
        if (!Intent.ACTION_MEDIA_BUTTON.equals(intentAction))
        {
            Log.i(TAG_MEDIA, "no media button information");
            return;
        }
        KeyEvent event = (KeyEvent) intent.getParcelableExtra(Intent.EXTRA_KEY_EVENT);
        if (event == null) {
            Log.i(TAG_MEDIA, "no key press");
            return;
        }

        KeyguardManager keyguardManager = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);

        if (keyguardManager.inKeyguardRestrictedInputMode()) {
            return;
        }

        if(event.getKeyCode() ==  KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE)
        {
            Log.d(TAG_MEDIA,"MEDIA PLAY PAUSE");
            if(VideoPlayerFragment.iHeadSetsController!=null)
                VideoPlayerFragment.iHeadSetsController.applyAction();
            return;
        }

        if(event.getKeyCode() == KeyEvent.KEYCODE_HEADSETHOOK)
        {
            Log.d(TAG_MEDIA,"MEDIA HEADSETHOOK");
            if(counter%2==0)
            if(VideoPlayerFragment.iHeadSetsController!=null)
                VideoPlayerFragment.iHeadSetsController.applyAction();
            return;
        }
    }
}
