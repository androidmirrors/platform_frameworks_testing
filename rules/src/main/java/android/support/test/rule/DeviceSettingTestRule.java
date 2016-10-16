/*
 * Copyright (C) 2014 The Android Open Source Project
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

import android.support.annotation.NonNull;
import android.support.test.annotation.WithSetting;
import android.support.test.internal.setting.DeviceSetting;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;


/**
 * This rule allows the test method annotated with {@link WithSetting} annotation to execute under
 * a given {@link DeviceSetting}.
 * <p/>
 * Note, methods annotated with
 * <a href="http://junit.sourceforge.net/javadoc/org/junit/Before.html"><code>Before</code></a> and
 * <a href="http://junit.sourceforge.net/javadoc/org/junit/After.html"><code>After</code></a> will
 * also be executed after the given {@link DeviceSetting} is applied.
 *
 * This rule will take care to undo the setting by calling the {@link DeviceSetting#reset()} method,
 * once the test has finished running.
 *
 * Device Settings can be created by implementing the {@link DeviceSetting} interface.
 * Ex: Below is an example of a {@link DeviceSetting} implementation which changes the device's current
 * time format to 12 Hour Format.
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
 * Note: For a {@link DeviceSetting} to work properly, We might need to mention the required permissions in
 * the AndroidManifest.xml file
 *
 * @see android.support.test.annotation.WithSetting
 * @see android.support.test.internal.setting.DeviceSetting
 */
public class DeviceSettingTestRule implements TestRule {
  @Override
  public Statement apply(Statement base, Description description) {
    final WithSetting setting = description.getAnnotation(WithSetting.class);
    if (setting == null) {
      return base;
    } else {
      return deviceSettingApplierStatement(setting, base);
    }
  }

  @NonNull
  private Statement deviceSettingApplierStatement(final WithSetting settingAnnotation, final Statement base) {
    return new Statement() {
      @Override
      public void evaluate() throws Throwable {
        DeviceSetting setting = settingAnnotation.value().newInstance();
        try {
          setting.apply();
          base.evaluate();
        } finally {
          setting.reset();
        }
      }
    };
  }
}
