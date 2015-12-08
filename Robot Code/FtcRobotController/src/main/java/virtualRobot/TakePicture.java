package virtualRobot;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceView;
import android.widget.ImageView;

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

        final Bitmap[] bmp = new Bitmap[1];

        Camera mCamera = Camera.open();
        Camera.Parameters parameters = mCamera.getParameters();
        parameters.setPictureSize(352, 288);
        mCamera.setParameters(parameters);
        mCamera.startPreview();

        final AtomicBoolean pictureIsReady = new AtomicBoolean();

        pictureIsReady.set(false);

        Camera.PictureCallback mCall = new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] data, Camera camera) {

                bmp[0] = BitmapFactory.decodeByteArray(data, 0, data.length);

                redIsLeft.set(DavidClass.analyzePic(bmp[0]));

                pictureIsReady.set(true);

            }
        };

        Log.d("mCamera is null", Boolean.toString(mCamera == null));
        Log.d("mCamera pic", "yup");

        mCamera.takePicture(null, null, mCall);
        while (!exitCondition.isConditionMet() && !pictureIsReady.get()) {

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

        mCamera.release();

        return isInterrupted;
    }
}


