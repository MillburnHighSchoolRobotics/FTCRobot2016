package virtualRobot;

import android.app.Activity;
import android.hardware.Camera;
import android.view.Surface;

/**
 * Created by Alex on 11/14/2015.
 */
public class AutoPicture{
    public final void open(1);
    public final void takePicture (Camera.ShutterCallback shutter, Camera.PictureCallback raw, Camera.PictureCallback postview, Camera.PictureCallback jpeg)
    {
        getParameters(shutter, raw, pastview, jpeg);
        setParameters(Camera.Parameters);
        

    }

    // this makes sure the camera picture is at the correct orientation of the phone position if it is moved in any way
    public static void setCameraDisplayOrientation(Activity activity, int cameraId, android.hardware.Camera camera) {
        android.hardware.Camera.CameraInfo info = new android.hardware.Camera.CameraInfo();
        android.hardware.Camera.getCameraInfo(cameraId, info);
        int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();
        int degrees = 0;
        switch (rotation) {
            case Surface.ROTATION_0: degrees = 0; break;
            case Surface.ROTATION_90: degrees = 90; break;
            case Surface.ROTATION_180: degrees = 180; break;
            case Surface.ROTATION_270: degrees = 270; break;
        }

        int result;
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
            result = (info.orientation - degrees + 360) % 360;
            camera.setDisplayOrientation(result);

    }
        }}
