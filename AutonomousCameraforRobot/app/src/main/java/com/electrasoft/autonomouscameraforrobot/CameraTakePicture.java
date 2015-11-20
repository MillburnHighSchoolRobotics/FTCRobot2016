package com.electrasoft.autonomouscameraforrobot;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.hardware.Camera;
/*import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;*/
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Surface;
import android.widget.ImageView;

import java.util.List;
import java.util.logging.Handler;

/**
 * Created by Alex on 11/18/2015.
 */
public abstract class CameraTakePicture {

    //THIS IS ALL GOING TO BE CHANGED
    Intent beaconPic = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
    static final int REQUEST_IMAGE_CAPTURE = 1;

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);

            @Override
            protected void onActivityResult(int requestCode, int resultCode, Intent data)
            {
                if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
                    Bundle extras = data.getExtras();
                    Bitmap imageBitmap = (Bitmap) extras.get("data");
                    mImageView.setImageBitmap(imageBitmap);
                }
            }
        }
    }


}
