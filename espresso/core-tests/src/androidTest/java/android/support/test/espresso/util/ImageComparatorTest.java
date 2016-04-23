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

package android.support.test.espresso.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import junit.framework.TestCase;

import static android.support.test.InstrumentationRegistry.getInstrumentation;

public class ImageComparatorTest extends TestCase {

  public void testCompareSameBitmapImage() {
    Bitmap bitmap = BitmapFactory.decodeResource(
        getInstrumentation().getContext().getResources(), android.R.drawable.alert_dark_frame);
    assertTrue(ImageComparator.isSame(bitmap, bitmap));
  }

  public void testCompareDifferentBitmapImages() {
    Bitmap bitmap1 = BitmapFactory.decodeResource(
        getInstrumentation().getContext().getResources(), android.R.drawable.alert_dark_frame);
    Bitmap bitmap2 = BitmapFactory.decodeResource(
        getInstrumentation().getContext().getResources(), android.R.drawable.alert_light_frame);
    assertFalse(ImageComparator.isSame(bitmap1, bitmap2));
  }
}