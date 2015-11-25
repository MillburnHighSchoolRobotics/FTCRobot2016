package virtualRobot;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.view.SurfaceView;
import android.widget.ImageView;
import android.widget.Toast;

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

        ImageView iv_image = null;

        SurfaceView sv;


        final Bitmap[] bmp = new Bitmap[1];

        Camera mCamera;
        mCamera = Camera.open();
        Camera.Parameters parameters;
        parameters = mCamera.getParameters();
        mCamera.setParameters(parameters);
        mCamera.startPreview();

        final AtomicBoolean pictureIsReady = new AtomicBoolean();

        pictureIsReady.set(false);


        final ImageView finalIv_image = iv_image;
        Camera.PictureCallback mCall = new Camera.PictureCallback()
        {
            @Override
            public void onPictureTaken(byte[] data, Camera camera)
            {



                bmp[0] = BitmapFactory.decodeByteArray(data, 0, data.length);

                finalIv_image.setImageBitmap(Bitmap.createScaledBitmap(bmp[0], bmp[0].getWidth() / 2, bmp[0].getHeight() / 2, false));
                redIsLeft.set(DavidClass.analyzePic(bmp[0]));

                pictureIsReady.set(true);

            }
        };

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


    }
    }


