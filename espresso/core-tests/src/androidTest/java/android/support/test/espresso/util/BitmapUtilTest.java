package android.support.test.espresso.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.MediumTest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Tests for utility methods related to Bitmap
 */
@MediumTest
@RunWith(AndroidJUnit4.class)
public class BitmapUtilTest {

  private Context context;

  @Before
  public void setUp() throws Exception {
    context = InstrumentationRegistry.getContext();
  }

  @Test
  public void compareSameBitmapImage() {
    Bitmap bitmap = null;
    try {
      bitmap = BitmapFactory.decodeResource(
          context.getResources(), android.R.drawable.alert_dark_frame);
      assertTrue(BitmapUtil.compareBitmaps(bitmap, bitmap));
    } finally {
      recycle(bitmap);
      bitmap = null;
    }
  }

  @Test
  public void compareDifferentBitmapImages() {
    Bitmap bitmap1 = null;
    Bitmap bitmap2 = null;

    try {
      bitmap1 = BitmapFactory.decodeResource(
          context.getResources(), android.R.drawable.alert_dark_frame);
      bitmap2 = BitmapFactory.decodeResource(
          context.getResources(), android.R.drawable.alert_light_frame);

      assertFalse(BitmapUtil.compareBitmaps(bitmap1, bitmap2));
    } finally {
      recycle(bitmap1);
      recycle(bitmap2);
      bitmap1 = null;
      bitmap2 = null;
    }
  }

  private void recycle(Bitmap bitmap) {
    if (bitmap != null) {
      bitmap.recycle();
    }
  }

}
