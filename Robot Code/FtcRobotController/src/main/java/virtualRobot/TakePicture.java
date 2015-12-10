package virtualRobot;

import android.graphics.Bitmap;

import com.qualcomm.ftcrobotcontroller.FtcRobotControllerActivity;

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
       /* Camera mCamera = Camera.open();
        Camera.Parameters params = mCamera.getParameters();
        params.setPictureSize(352, 288);
        List<Camera.Size> sizes = params.getSupportedPreviewSizes();
        Camera.Size selected = sizes.get(0);
        params.setPreviewSize(selected.width, selected.height);
        mCamera.setParameters(params);

        mCamera.setDisplayOrientation(90);
        mCamera.startPreview();*/
       /*FtcRobotControllerActivity.this.takeScreenshot();
        bmp[0] = FtcRobotControllerActivity.this.openScreenshot();

        redIsLeft.set(DavidClass.analyzePic(bmp[0]));
        pictureIsReady.set(true);
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

        return isInterrupted;*/
        return false;

    }

}


