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

/**
 * An Interface representing a Device Setting of a device.
 * Implementation of this interface used by {@link android.support.test.annotation.WithSetting}
 * annotation to mark a test to be run under this Device Setting.
 *
 * Ex: Below is an example of a {@link DeviceSetting} implementation which changes the device's
 * current time format to 12 Hour Format.
 *
 * public class TwelveHourFormatSetting implements DeviceSetting {
 *
 *    @Override
 *    public void apply() {
 *      // changing the time format setting to twelve hour format.
 *    }

 *    @Override
 *    public void reset() {
 *      // undo the setting.
 *    }
 * }
 *
 * Note: For a {@link DeviceSetting} to work properly, We might need to mention the required
 * permissions in the AndroidManifest.xml file
 *
 * @see android.support.test.annotation.WithSetting
 * @see android.support.test.rule.DeviceSettingTestRule
 */
public interface DeviceSetting {
  void apply();
  void reset();
}
