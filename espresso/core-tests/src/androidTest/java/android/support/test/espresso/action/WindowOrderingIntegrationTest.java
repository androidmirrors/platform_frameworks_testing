/*
 * Copyright (C) 2014 The Android Open Source Project
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

package android.support.test.espresso.action;

import android.support.test.filters.SdkSuppress;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.testapp.R;
import android.support.test.testapp.SendActivity;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Ensures view root ordering works properly.
 */
@LargeTest
@RunWith(AndroidJUnit4.class)
public class WindowOrderingIntegrationTest {
  @Rule
  public ActivityTestRule<SendActivity> rule = new ActivityTestRule<>(SendActivity.class);

  // popup menus are post honeycomb.
  @SdkSuppress(minSdkVersion=11)
  @Test
  public void popupMenuTesting() {
    onView(withText(R.string.item_1_text))
        .check(doesNotExist());
    onView(withId(R.id.make_popup_menu_button))
        .perform(scrollTo(), click());
    onView(withText(R.string.item_1_text))
        .check(matches(isDisplayed()))
        .perform(click());
    onView(withText(R.string.item_1_text))
        .check(doesNotExist());
  }

  @Test
  public void popupWindowTesting() {
    onView(withId(R.id.popup_title))
        .check(doesNotExist());
    onView(withId(R.id.make_popup_view_button))
        .perform(scrollTo(), click());
    onView(withId(R.id.popup_title))
        .check(matches(withText(R.string.popup_title)))
        .perform(click());
    onView(withId(R.id.popup_title))
        .check(doesNotExist());
  }

  @Test
  public void dialogTesting() {
    onView(withText(R.string.dialog_title))
        .check(doesNotExist());
    onView(withId(R.id.make_alert_dialog))
        .perform(scrollTo(), click());
    onView(withText(R.string.dialog_title))
        .check(matches(isDisplayed()));

    onView(withText("Fine"))
        .perform(click());

    onView(withText(R.string.dialog_title))
        .check(doesNotExist());
  }
}
