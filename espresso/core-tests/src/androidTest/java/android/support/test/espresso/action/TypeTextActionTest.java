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

import android.support.annotation.NonNull;
import android.support.test.espresso.InjectEventSecurityException;
import android.support.test.espresso.PerformException;
import android.support.test.espresso.UiController;
import android.support.test.runner.AndroidJUnit4;
import android.view.MotionEvent;
import android.view.View;

import org.hamcrest.CustomTypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import static org.hamcrest.CoreMatchers.not;
import static org.junit.rules.ExpectedException.none;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Unit tests for {@link TypeTextAction}.
 */
@RunWith(AndroidJUnit4.class)
public class TypeTextActionTest {
  @Rule
  public ExpectedException expectedException = none();
  @Mock
  private UiController mockUiController;

  @Mock
  private View mockView;

  private TypeTextAction typeTextAction;

  @Before
  public void setUp() throws Exception {
    initMocks(this);
  }

  @Test
  public void typeTextActionPerform() throws InjectEventSecurityException {
    String stringToBeTyped = "Hello!";
    typeTextAction = new TypeTextAction(stringToBeTyped);
    when(mockUiController.injectMotionEvent(isA(MotionEvent.class))).thenReturn(true);
    when(mockUiController.injectString(stringToBeTyped)).thenReturn(true);
    typeTextAction.perform(mockUiController, mockView);
  }

  @Test
  public void typeTextActionPerformFailed() throws InjectEventSecurityException {
    String stringToBeTyped = "Hello!";
    typeTextAction = new TypeTextAction(stringToBeTyped);
    when(mockUiController.injectMotionEvent(isA(MotionEvent.class))).thenReturn(true);
    when(mockUiController.injectString(stringToBeTyped)).thenReturn(false);

    expectedException.expect(PerformException.class);
    expectedException.expectCause(not(instanceOfInjectEventSecurityException()));
    typeTextAction.perform(mockUiController, mockView);
  }

  @Test
  public void typeTextActionPerformInjectEventSecurityException()
      throws InjectEventSecurityException {
    String stringToBeTyped = "Hello!";
    typeTextAction = new TypeTextAction(stringToBeTyped);
    when(mockUiController.injectMotionEvent(isA(MotionEvent.class))).thenReturn(true);
    when(mockUiController.injectString(stringToBeTyped))
        .thenThrow(new InjectEventSecurityException(""));

    expectedException.expect(PerformException.class);
    expectedException.expectCause(instanceOfInjectEventSecurityException());
    typeTextAction.perform(mockUiController, mockView);
  }

  @NonNull
  private CustomTypeSafeMatcher<Throwable> instanceOfInjectEventSecurityException() {
    return new CustomTypeSafeMatcher<Throwable>(
        "instanceof InjectEventSecurityException") {
      @Override
      protected boolean matchesSafely(Throwable throwable) {
        return throwable instanceof InjectEventSecurityException;
      }
    };
  }
}
