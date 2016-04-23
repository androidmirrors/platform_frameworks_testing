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

import java.util.Arrays;


/**
 * Helper methods to compare images.
 */
public final class ImageComparator {

  private ImageComparator() {
  }

  /**
   * Compare bitmap images pixel by pixel
   *
   * Note: Should be used only with API version lower than 12. Use Bitmap#sameAs with version 12
   * and higher
   *
   * @param img1 first image
   * @param img2 second image
   * @return a boolean based on image comparison
   */
  public static boolean isSame(Bitmap img1, Bitmap img2) {
    if (img1.getWidth() == img2.getWidth() && img1.getHeight() == img2.getHeight()) {
      int[] img1Pixels = new int[img1.getWidth() * img1.getHeight()];
      int[] img2Pixels = new int[img2.getWidth() * img2.getHeight()];

      img1.getPixels(img1Pixels, 0, img1.getWidth(), 0, 0, img1.getWidth(), img1.getHeight());
      img2.getPixels(img2Pixels, 0, img2.getWidth(), 0, 0, img2.getWidth(), img2.getHeight());

      return Arrays.equals(img1Pixels, img2Pixels);
    }
    return false;
  }
}
