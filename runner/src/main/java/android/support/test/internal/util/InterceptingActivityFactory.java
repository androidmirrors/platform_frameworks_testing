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

package android.support.test.internal.util;

import android.app.Activity;
import android.content.Intent;
import android.support.test.runner.MonitoringInstrumentation;

/**
 * Implement this interface to provide custom implementation of Activity under test. It is used by
 * {@link MonitoringInstrumentation#newActivity(ClassLoader, String, Intent)} to create instance of
 * the activity under test. Please see
 * {@link MonitoringInstrumentation#interceptActivityUsing(InterceptingActivityFactory)}
 * for more details
 */
public interface InterceptingActivityFactory {
    boolean shouldIntercept(ClassLoader classLoader, String className, Intent intent);

    Activity create(ClassLoader classLoader, String className, Intent intent);
}
