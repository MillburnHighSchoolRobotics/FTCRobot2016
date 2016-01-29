package co.millburnrobotics.ftcscoutingapp;

import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;

/**
 * Created by Yanjun on 1/28/2016.
 */
public class AdvButton {
    private ImageButton imageButton;
    private int resUp;
    private int resDown;

    private View.OnClickListener onClickListener;

    public AdvButton(ImageButton imageButton, int resUp, int resDown) {
        this.imageButton = imageButton;
        this.resDown = resDown;
        this.resUp = resUp;

        setOnTouchListener();
    }

    public void setOnTouchListener() {
        imageButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    imageButton.setImageResource(resDown);
                }

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    imageButton.setImageResource(resUp);
                }

                return false;
            }
        });

    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
        imageButton.setOnClickListener(onClickListener);
    }
}
