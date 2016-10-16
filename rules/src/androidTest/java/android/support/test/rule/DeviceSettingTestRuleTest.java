/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package android.support.test.rule;

import android.content.Context;
import android.provider.Settings;
import android.support.test.InstrumentationRegistry;
import android.support.test.annotation.WithSetting;
import android.support.test.internal.exception.PermissionDenialException;
import android.support.test.internal.setting.TwelveHourFormatSetting;
import android.support.test.internal.setting.WifiStateSetting;
import android.support.test.runner.AndroidJUnit4;
import android.text.format.DateFormat;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.Result;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.runner.JUnitCore.runClasses;


@RunWith(AndroidJUnit4.class)
public class DeviceSettingTestRuleTest {


  private static Context context;

  @Before
  public void setUp() throws Exception {
    context = InstrumentationRegistry.getTargetContext();
  }

  public static class TwelveHourTimeFormatTest {
    @Rule
    public TestRule rule = new DeviceSettingTestRule();

    @Before
    public void setUp() throws Exception {
      verifyTwelveHourFormat();
    }

    @Test
    @WithSetting(TwelveHourFormatSetting.class)
    public void shouldRunUnderTwelveHourTimeFormatSetting() throws Exception {
      verifyTwelveHourFormat();
    }

    @After
    public void tearDown() throws Exception {
      verifyTwelveHourFormat();
    }
  }

  public static class WifiStateTest {
    @Rule
    public TestRule rule = new DeviceSettingTestRule();

    @Test(expected = PermissionDenialException.class)
    @WithSetting(WifiStateSetting.class)
    public void shouldRunUnderTwelveHourTimeFormatSetting() throws Exception {
      // Dummy test
    }
  }

  @Test
  public void shouldApplyGivenSettingBeforeTestAndResetSettingAfterTest() throws Exception {
    applyTwentyFourHourFormat();
    verifyTwentyFourHourFormat();
    Result result = runClasses(TwelveHourTimeFormatTest.class);
    assertThat(result.getFailureCount(), is(0));
    verifyTwentyFourHourFormat();
  }

  @Test
  public void shouldThrowPermissionDenialExceptionWhenEnoughPermissionNotFound() throws Exception {
    Result result = runClasses(WifiStateTest.class);
    assertThat(result.getFailureCount(), is(1));
    assertTrue(result.getFailures().get(0).getException() instanceof PermissionDenialException);
  }

  private static void verifyTwentyFourHourFormat() {
    assertTrue(DateFormat.is24HourFormat(context));
  }

  private static void verifyTwelveHourFormat() {
    assertFalse(DateFormat.is24HourFormat(context));
  }

  private void applyTwentyFourHourFormat() {
    Settings.System.putString(context.getContentResolver(), Settings.System.TIME_12_24, "24");
  }
}