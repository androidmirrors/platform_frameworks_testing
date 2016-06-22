/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package android.support.test.espresso.matcher;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.test.annotation.Beta;
import android.support.test.espresso.util.BitmapUtil;
import android.util.Log;
import android.view.View;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import java.util.Arrays;

import static android.support.test.espresso.util.BitmapUtil.compareBitmaps;

/**
 * Matcher to match {@link android.view.View} based on its background resource.
 */
@Beta
public final class HasBackgroundMatcher extends TypeSafeMatcher<View> {

  private static final String TAG = "HasBackgroundMatcher";
  private final int drawableId;

  public HasBackgroundMatcher(int drawableId) {
    this.drawableId = drawableId;
  }

  @Override
  protected boolean matchesSafely(View view) {
    return assertDrawable(view.getBackground(), drawableId, view);
  }

  @Override
  public void describeTo(Description description) {
    description.appendText("has background with drawable ID: " + drawableId);
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
      if (expectedBitmap != null) {
        expectedBitmap.recycle();
        expectedBitmap = null;
      }
    }
  }
}
