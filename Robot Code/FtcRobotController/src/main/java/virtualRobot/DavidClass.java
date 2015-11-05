package virtualRobot;
import java.io.*;
import android.graphics.*;
import java.util.*;
/**
 * Created by DOSullivan on 11/4/15.
 */
public class DavidClass {
    public static final long RED = Color.RED; //note that Color.RED is negative
    //returns true if left is red (right is blue)
    //returns false if left if blue (right is red)
    public static boolean analyzePic(String filepath) {
        Bitmap image;
        image = BitmapFactory.decodeFile(filepath);
        int[] pixels = null;
        int height = image.getHeight(), width = image.getWidth();
       image.getPixels(pixels, 0, image.getWidth(), 0, 0, image.getHeight(), image.getWidth()); //gets pixels in pixel array

        int Midx = roundUp(width, 2)-1, Midy = roundUp(height,2)-1;
        int Q1x = roundUp(Midx, 2), Q3x = roundUp(Midx+width, 2), Q1y = roundUp(Midy,2), Q3y = roundUp(Midy+height,2);
        List<Integer> leftPixels = new ArrayList<Integer>(), rightPixels = new ArrayList<Integer>();
        for (int i = Q1y; i <= Q1y+Q3y; i++){
            int z1 = (width * (i-1))-1;
            int z2 = ((width*(i-1))-1)+Q3x;
            for (int x = z1; x<=z1+(Q1x-1); x++){
               leftPixels.add(pixels[x]);
            }
            for (int x = z2; x<=z2+(width-1-1);x++) {
                rightPixels.add(pixels[x]);
            }
        }
        int lNum = leftPixels.size(), rNum = rightPixels.size();
        long lSum = 0, rSum = 0;
        long lAvg, rAvg;

        for (int i = 0; i < lNum;i++){
           lSum+= Color.red(leftPixels.get(i));
        }
        for (int i = 0; i <rNum;i++) {
            rSum+= Color.red(rightPixels.get(i));
        }
        lAvg = roundUp(lSum, lNum);
        rAvg = roundUp(rSum, rNum);
       return ((lAvg < 0 && rAvg > 0)? true : (rAvg <0 && lAvg > 0) ? false : (Math.abs(lAvg-RED) > Math.abs(rAvg-RED)) ? true: false);



    }
    //ceiling division, assumes both numbers are positive
    private static int roundUp(int num, int divisor) {
        return (num + divisor - 1) / divisor;
    }
    private static long roundUp(long num, long divisor) {
        return (num + divisor - 1) / divisor;
    }

}
