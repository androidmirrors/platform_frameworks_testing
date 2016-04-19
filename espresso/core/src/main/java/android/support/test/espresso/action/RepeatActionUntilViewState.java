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

import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.view.View;

import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;

import static android.support.test.espresso.matcher.ViewMatchers.assertThat;

/**
 * Enables performing a given action on a view until it reaches desired state matched by given View matcher
 */
public final class RepeatActionUntilViewState implements ViewAction {

  public static final int DEFAULT_MAX_ATTEMPTS = 10;

  private ViewAction action;
  private Matcher<View> desiredStateMatcher;
  private int maxAttempts;

  public RepeatActionUntilViewState(ViewAction action, Matcher<View> desiredStateMatcher) {
    this(action, desiredStateMatcher, DEFAULT_MAX_ATTEMPTS);
  }

  public RepeatActionUntilViewState(ViewAction action, Matcher<View> desiredStateMatcher, int maxAttempts) {
    this.action = action;
    this.desiredStateMatcher = desiredStateMatcher;
    this.maxAttempts = maxAttempts;
  }

  @Override
  public Matcher<View> getConstraints() {
    return action.getConstraints();
  }

  @Override
  public String getDescription() {
    StringDescription stringDescription = new StringDescription();
    desiredStateMatcher.describeTo(stringDescription);
    return String.format("%s until: %s", action.getDescription(), stringDescription);
  }

  @Override
  public void perform(UiController uiController, View view) {
    int noOfAttempts = 0;
    do {
      action.perform(uiController, view);
      uiController.loopMainThreadUntilIdle();
      noOfAttempts++;
    } while (!desiredStateMatcher.matches(view) && noOfAttempts < maxAttempts);
    assertThat(view, desiredStateMatcher);
  }

}
