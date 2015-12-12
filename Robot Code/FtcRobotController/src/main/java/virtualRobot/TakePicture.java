package virtualRobot;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;

import static com.qualcomm.ftcrobotcontroller.FtcRobotControllerActivity.*;

import java.io.ByteArrayOutputStream;
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
        
        imageLock.lock();

        YuvImage yuvImage = new YuvImage(imageByteData, imageParameters.getPreviewFormat(), imageParameters.getPreviewSize().width, imageParameters.getPreviewSize().height, null);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Rect rect = new Rect(0, 0, imageParameters.getPreviewSize().width, imageParameters.getPreviewSize().height);
        yuvImage.compressToJpeg(rect, 75, byteArrayOutputStream);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPurgeable = true;
        options.inInputShareable = true;
        Bitmap mBitmap = BitmapFactory.decodeByteArray(byteArrayOutputStream.toByteArray(), 0, byteArrayOutputStream.size(), options);

        redIsLeft.set(DavidClass.analyzePic(mBitmap));
        
        imageLock.unlock();
        
        return false;
    }
}


