package virtualRobot;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.hardware.Camera;
import android.view.View;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by DOSullivan on 11/25/15.
 */
public class TakePicture implements Command {
    final AtomicBoolean redIsLeft;

    private ExitCondition exitCondition;
    private boolean isInterrupted;
    public TakePicture(AtomicBoolean redIsLeft) {
        exitCondition = new ExitCondition() {
            @Override
            public boolean isConditionMet() {
                return false;
            }
        };
          this.redIsLeft = redIsLeft;
        }
    public void setExitCondition (ExitCondition e) {
        exitCondition = e;
    }
    public boolean changeRobotState() throws InterruptedException {

<<<<<<< HEAD
       // final Bitmap[] bmp = new Bitmap[1];
=======
        final Bitmap[] bmp = new Bitmap[1];
>>>>>>> 083c60351aae2761c3ebb32f20e096f157cd7551

        Camera mCamera = Camera.open();
        Camera.Parameters parameters = mCamera.getParameters();
        parameters.setPictureSize(352, 288);
        mCamera.setParameters(parameters);
        mCamera.startPreview();
       // ImageView iv_image = null;

        //SurfaceView sv = null;


        final Bitmap[] bm2p = new Bitmap[1];

        //Camera mCamera;

        mCamera = Camera.open();
        Camera.Parameters params = mCamera.getParameters();
        List<Camera.Size> sizes = params.getSupportedPreviewSizes();
        Camera.Size selected = sizes.get(0);
        params.setPreviewSize(selected.width, selected.height);
        mCamera.setParameters(params);

        mCamera.setDisplayOrientation(90);
        mCamera.startPreview();

<<<<<<< HEAD

=======
/*
>>>>>>> 083c60351aae2761c3ebb32f20e096f157cd7551
        final AtomicBoolean pictureIsReady = new AtomicBoolean();

        pictureIsReady.set(false);

<<<<<<< HEAD
        //Camera.PictureCallback mCall = new Camera.PictureCallback() {
=======
        Camera.PictureCallback mCall = new Camera.PictureCallback() {
>>>>>>> 083c60351aae2761c3ebb32f20e096f157cd7551

        //final ImageView finalIv_image = iv_image;
        /*Camera.PictureCallback mCall = new Camera.PictureCallback() {
>>>>>>> 00c5ae89a5f1da0b87b15e95fbb9066c980c3c7c
            @Override
            public void onPictureTaken(byte[] data, Camera camera) {

                bmp[0] = BitmapFactory.decodeByteArray(data, 0, data.length);

<<<<<<< HEAD
=======
               // finalIv_image.setImageBitmap(Bitmap.createScaledBitmap(bmp[0], bmp[0].getWidth() / 2, bmp[0].getHeight() / 2, false));
>>>>>>> 00c5ae89a5f1da0b87b15e95fbb9066c980c3c7c
                redIsLeft.set(DavidClass.analyzePic(bmp[0]));

                pictureIsReady.set(true);

            }
        };

<<<<<<< HEAD
        Log.d("mCamera is null", Boolean.toString(mCamera == null));
        Log.d("mCamera pic", "yup");

        mCamera.takePicture(null, null, mCall);
=======
<<<<<<< HEAD
        mCamera.takePicture(null, null, mCall);*/
        //bmp[0] = screenShot(sv);
        redIsLeft.set(DavidClass.analyzePic(bmp[0]));
        pictureIsReady.set(true);
            while (!exitCondition.isConditionMet() && !pictureIsReady.get()) {
=======
        mCamera.takePicture(null, null, mCall);
        bmp[0] = screenShot(sv);
        //redIsLeft.set(DavidClass.analyzePic(bmp[0]));
       // pictureIsReady.set(true);
       // while (!exitCondition.isConditionMet() && !pictureIsReady.get()) {
>>>>>>> 083c60351aae2761c3ebb32f20e096f157cd7551

            if (Thread.currentThread().isInterrupted()) {
                isInterrupted = true;
                break;
            }

            try {
                Thread.currentThread().sleep(10);
            } catch (InterruptedException e) {
                isInterrupted = true;
                break;
            }
        }
        */

        mCamera.release();

        return isInterrupted;
    }
    public static Bitmap screenShot(View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(),
                view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }
}


