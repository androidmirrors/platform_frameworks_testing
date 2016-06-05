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

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.testapp.R;
import android.support.test.testapp.VeryLongListViewActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

/**
 * Tests for ScrollToAction Within ListView with long header and footer.
 */
@RunWith(AndroidJUnit4.class)
public class ScrollToActionWithListViewTest {

  @Rule
  public ActivityTestRule rule = new ActivityTestRule(VeryLongListViewActivity.class);

  @Test
  public void testScrollToBottomHeaderTextOfListView() {
    onView(withId(is(R.id.bottom_header_text)))
        .check(matches(not(isDisplayed())))
        .perform(scrollTo())
        .check(matches(isDisplayed()));
  }

  @Test
  public void testScrollToBottomFooterTextOfListView() {
    onData(is(instanceOf(String.class))).atPosition(6).perform(scrollTo());
    onView(withId(is(R.id.bottom_footer_text)))
        .check(matches(not(isDisplayed())))
        .perform(scrollTo())
        .check(matches(isDisplayed()));
  }
}
