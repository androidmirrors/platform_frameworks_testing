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

package android.support.test.internal.setting;

import android.content.Context;
import android.provider.Settings;
import android.support.test.InstrumentationRegistry;
import android.text.format.DateFormat;

import static android.provider.Settings.System.TIME_12_24;

public class TwelveHourFormatSetting implements DeviceSetting {

  private final Context context;
  private String currentFormat;

  public TwelveHourFormatSetting() {
    context = InstrumentationRegistry.getTargetContext();
    currentFormat = DateFormat.is24HourFormat(context) ? "24" : "12";
  }

  @Override
  public void apply() {
    Settings.System.putString(context.getContentResolver(), TIME_12_24, "12");
  }

  @Override
  public void reset() {
    Settings.System.putString(context.getContentResolver(), TIME_12_24, currentFormat);
  }
}
