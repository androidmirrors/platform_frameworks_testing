package android.support.test.espresso.matcher;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.test.annotation.Beta;
import android.util.Log;
import android.view.View;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import java.util.Arrays;

public final class HasBackgroundMatcher {

  private static final String TAG = "HasBackgroundMatcher";

  /**
   * Returns a matcher that matches {@link android.view.View} based on background resource.
   * <p/>
   * Note: This method compares images at a pixel level and might have significant performance
   * implications for larger bitmaps.
   */
  @Beta
  public static Matcher<View> hasBackground(final int drawableId) {
    return new TypeSafeMatcher<View>() {
      @Override
      protected boolean matchesSafely(View view) {
        return assertDrawable(view.getBackground(), drawableId, view);
      }

      @Override
      public void describeTo(Description description) {
        description.appendText("has background with drawable ID: " + drawableId);
      }
    };
  }

  protected static boolean compareBitmaps(Bitmap img1, Bitmap img2) {
    if (img1.getWidth() == img2.getWidth() && img1.getHeight() == img2.getHeight()) {
      int[] img1Pixels = new int[img1.getWidth() * img1.getHeight()];
      int[] img2Pixels = new int[img2.getWidth() * img2.getHeight()];

      img1.getPixels(img1Pixels, 0, img1.getWidth(), 0, 0, img1.getWidth(), img1.getHeight());
      img2.getPixels(img2Pixels, 0, img2.getWidth(), 0, 0, img2.getWidth(), img2.getHeight());

      return Arrays.equals(img1Pixels, img2Pixels);
    }
    return false;
  }

  private static boolean assertDrawable(Drawable actual, int expectedId, View v) {
    if (null == actual || !(actual instanceof BitmapDrawable)) {
      return false;
    }

    Bitmap expectedBitmap = null;
    try {
      expectedBitmap = BitmapFactory.decodeResource(v.getContext().getResources(), expectedId);
      if (Build.VERSION.SDK_INT >= 12) {
        return ((BitmapDrawable) actual).getBitmap().sameAs(expectedBitmap);
      } else {
        return compareBitmaps(((BitmapDrawable) actual).getBitmap(), expectedBitmap);
      }
    } catch (OutOfMemoryError error) {
      Log.e(TAG, error.getMessage(), error.getCause());
      return false;

    } finally {
      if (null != expectedBitmap) {
        expectedBitmap.recycle();
        expectedBitmap = null;
      }
    }
  }
}
