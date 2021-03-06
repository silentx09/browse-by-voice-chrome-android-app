package browse.by.voice.materialnavigationdrawer.tools;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;

public class Utils {
    public static int getDrawerWidth(Resources res, int ownDP) {
        if(ownDP == 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {

                if (res.getConfiguration().smallestScreenWidthDp >= 600 || res.getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    // device is a tablet
                    return (int) (320 * res.getDisplayMetrics().density);
                } else {
                    return (int) (res.getDisplayMetrics().widthPixels - (56 * res.getDisplayMetrics().density));
                }
            } else { // for devices without smallestScreenWidthDp reference calculate if device screen is over 600 dp
                if ((res.getDisplayMetrics().widthPixels / res.getDisplayMetrics().density) >= 600 || res.getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
                    return (int) (320 * res.getDisplayMetrics().density);
                else
                    return (int) (res.getDisplayMetrics().widthPixels - (56 * res.getDisplayMetrics().density));
            }
        } else {
            return ownDP;
        }
    }

   /* public static int getDrawerWidth(Resources res) {
        if(res.getConfiguration().smallestScreenWidthDp >= 600 || res.getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // device is a tablet
            return (int) (320 * res.getDisplayMetrics().density);
        }
        else {
            return (int) (res.getDisplayMetrics().widthPixels - (56 * res.getDisplayMetrics().density));
        }

    }*/

    public static Point getUserPhotoSize(Resources res) {
        int size = (int) (64 * res.getDisplayMetrics().density);

        return new Point(size,size);
    }

    public static Point getBackgroundSize(Resources res, int drawerDPWidth) {
        int width = drawerDPWidth;
        if(drawerDPWidth == 0)
            width= getDrawerWidth(res, drawerDPWidth);

        int height = (9 * width) / 16;

        return new Point(width,height);
    }

  /*  public static Bitmap resizeBitmap(Bitmap bitmap, int reqWidth,int reqHeight) {
        return Bitmap.createScaledBitmap(bitmap,reqWidth,reqHeight,true);

    }*/

    public static int calculateSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public static Bitmap resizeBitmapFromResource(Resources res, int resId,int reqWidth, int reqHeight) {
        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);
        // Calculate inSampleSize
        options.inSampleSize = calculateSize(options, reqWidth, reqHeight);
        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    public static void recycleDrawable(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            //if (bitmapDrawable.getBitmap() != null && !bitmapDrawable.getBitmap().isRecycled())
            //{
                bitmapDrawable.getBitmap().recycle();
                System.gc();
            //}
        }
    }

    public static boolean isTablet(Resources res) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            return res.getConfiguration().smallestScreenWidthDp >= 600;
        }
        else { // for devices without smallestScreenWidthDp reference calculate if device screen is over 600
            return (res.getDisplayMetrics().widthPixels/res.getDisplayMetrics().density) >= 600;

        }
    }
}