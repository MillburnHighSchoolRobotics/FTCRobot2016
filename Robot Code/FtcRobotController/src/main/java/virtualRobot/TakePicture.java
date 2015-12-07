package virtualRobot;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
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

       // ImageView iv_image = null;

        SurfaceView sv;


        final Bitmap[] bmp = new Bitmap[1];

        Camera mCamera;
        sv = (SurfaceView)findViewById(R.id.preview);
        sv.getHolder().addCallback(this);
        sv.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        mCamera = Camera.open();
        Camera.Parameters parameters = mCamera.getParameters();
        List<Camera.Size> sizes = params.getSupportedPreviewSizes();
        Camera.Size selected = sizes.get(0);
        params.setPreviewSize(selected.width,selected.height);
        mCamera.setParameters(params);

        mCamera.setDisplayOrientation(90);
        mCamera.startPreview();


        final AtomicBoolean pictureIsReady = new AtomicBoolean();

        pictureIsReady.set(false);


        //final ImageView finalIv_image = iv_image;
        /*Camera.PictureCallback mCall = new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] data, Camera camera) {


                bmp[0] = BitmapFactory.decodeByteArray(data, 0, data.length);

               // finalIv_image.setImageBitmap(Bitmap.createScaledBitmap(bmp[0], bmp[0].getWidth() / 2, bmp[0].getHeight() / 2, false));
                redIsLeft.set(DavidClass.analyzePic(bmp[0]));

                pictureIsReady.set(true);

            }
        };

        mCamera.takePicture(null, null, mCall);*/
        bmp[0] = screenShot(sv);
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

        return isInterrupted;
    }
    public Bitmap screenShot(View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(),
                view.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }
}


