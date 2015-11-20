package com.electrasoft.autonomouscameraforrobot;

/**
 * Created by DOSullivan on 11/20/15.
 */

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;



public class TakePicture extends Activity //implements SurfaceHolder.Callback
{
    //a variable to store a reference to the Image View at the main.xml file
    private ImageView iv_image;
    //a variable to store a reference to the Surface View at the main.xml file
    private SurfaceView sv;

    //a bitmap to display the captured image
    private Bitmap bmp;

    //Camera variables
    //a surface holder
   // private SurfaceHolder sHolder;
    //a variable to control the camera
    private Camera mCamera;
    //the camera parameters
    private Parameters parameters;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_main);

        //get the Image View at the main.xml file
        iv_image = (ImageView) findViewById(R.id.imageView);

        //get the Surface View at the main.xml file

        //Get a surface
        //sHolder = sv.getHolder();

        //add the callback interface methods defined below as the Surface View callbacks
        //sHolder.addCallback(this);

        //tells Android that this surface will have its data constantly replaced
        //sHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        Button takePic = (Button) findViewById(R.id.takeButton);
        mCamera = Camera.open();
        takePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                //get camera parameters
                parameters = mCamera.getParameters();

                //set camera parameters
                mCamera.setParameters(parameters);
                mCamera.startPreview();

                //sets what code should be executed after the picture is taken
                Camera.PictureCallback mCall = new Camera.PictureCallback()
                {
                    @Override
                    public void onPictureTaken(byte[] data, Camera camera)
                    {


                        //decode the data obtained by the camera into a Bitmap
                        bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
                        iv_image.setImageBitmap(bmp);
                        boolean redisLeft = DavidClass.analyzePic(bmp);
                        Toast.makeText(v.getContext(), Boolean.toString(redisLeft), Toast.LENGTH_LONG).show();
                    }
                };

                mCamera.takePicture(null, null, mCall);
            }
        });
    }



    public void onStart() {
        super.onStart();

        mCamera.lock();
    }

    public void onRestart() {
        super.onRestart();

        mCamera.lock();
    }

    public void onResume() {
        super.onResume();

        mCamera.lock();
    }

    public void onPause() {
        super.onPause();

        mCamera.release();
    }

    public void onStop() {
        super.onStop();

        mCamera.release();
    }

    public void onDestroy() {
        super.onDestroy();

        mCamera.release();
    }



/*
    public static Bitmap RotateBitmap(Bitmap source, float angle)
    {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

    @Override
    public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3)
    {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder)
    {
        // The Surface has been created, acquire the camera and tell it where
        // to draw the preview.
        mCamera = Camera.open();
        try {
            mCamera.setPreviewDisplay(holder);

        } catch (IOException exception) {
            mCamera.release();
            mCamera = null;
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder)
    {
        //stop the preview
        mCamera.stopPreview();
        //release the camera
        mCamera.release();
        //unbind the camera from this object
        mCamera = null;
    }*/
}
