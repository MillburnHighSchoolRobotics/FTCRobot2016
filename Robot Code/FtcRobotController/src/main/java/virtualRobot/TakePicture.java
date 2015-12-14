package virtualRobot;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import static com.qualcomm.ftcrobotcontroller.FtcRobotControllerActivity.imageByteData;
import static com.qualcomm.ftcrobotcontroller.FtcRobotControllerActivity.imageLock;
import static com.qualcomm.ftcrobotcontroller.FtcRobotControllerActivity.imageParameters;
import static com.qualcomm.ftcrobotcontroller.FtcRobotControllerActivity.mCamera;


/**
 * Created by DOSullivan on 11/25/15.
 */
public class TakePicture implements Command {
     boolean[] redIsLeft;
    ArrayList<Command> commands;
    boolean isRed;

    private ExitCondition exitCondition;
    private boolean isInterrupted;
    public TakePicture(ArrayList<Command> commands, String color) {
        
        exitCondition = new ExitCondition() {
            @Override
            public boolean isConditionMet() {
                return false;
            }
        };
        
        this.commands = commands;

        isRed = color.equals("red");
    }
    public void setExitCondition (ExitCondition e) {
        exitCondition = e;
    }
    public boolean changeRobotState() throws InterruptedException {
        
        imageLock.lock();

        mCamera.setPreviewCallback(new Camera.PreviewCallback() {

            @Override
            public void onPreviewFrame(byte[] data, Camera camera) {
                imageLock.lock();
                imageByteData = data;
                imageLock.unlock();
            }
        });

        mCamera.startPreview();

        imageLock.unlock();

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            return true;
        }

        imageLock.lock();

        YuvImage yuvImage = new YuvImage(imageByteData, imageParameters.getPreviewFormat(), imageParameters.getPreviewSize().width, imageParameters.getPreviewSize().height, null);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Rect rect = new Rect(0, 0, imageParameters.getPreviewSize().width, imageParameters.getPreviewSize().height);
        yuvImage.compressToJpeg(rect, 75, byteArrayOutputStream);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPurgeable = true;
        options.inInputShareable = true;
        Bitmap mBitmap = BitmapFactory.decodeByteArray(byteArrayOutputStream.toByteArray(), 0, byteArrayOutputStream.size(), options);

        boolean redL = DavidClass.analyzePic(mBitmap);

        if ((redL && isRed) || (!redL && !isRed)) {
            commands.add(new MoveServo(
                    new Servo[]{
                            AUTO_ROBOT.getButtonPusherServo()
                    },
                    new double[]{
                            0.45
                    }
            ));
        } else {
            commands.add(new MoveServo(
                    new Servo[]{
                            AUTO_ROBOT.getButtonPusherServo()
                    },
                    new double[]{
                            0.05
                    }
            ));
        }
        
        imageLock.unlock();

        mCamera.stopPreview();
        
        return false;
    }
}


