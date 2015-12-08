package com.qualcomm.ftcrobotcontroller;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class TestActivity extends Activity {
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);

        final ImageView view = (ImageView) findViewById(R.id.imgview);

        Intent incoming = getIntent();
        Bitmap bitmap = (Bitmap) incoming.getParcelableExtra("bitmap");
        view.setImageBitmap(bitmap);

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                Intent i = new Intent(TestActivity.this, FtcRobotControllerActivity.class);
                startActivity(i);
            }
        }, 1500);
    }
}
