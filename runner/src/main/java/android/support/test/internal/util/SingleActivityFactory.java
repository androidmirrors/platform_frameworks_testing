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

/**
 * Abstract implementation of {@link InterceptingActivityFactory} which allows to intercept only one
 * activity at a time. Child classes are responsible for creating activity object.
 */
public abstract class SingleActivityFactory<T extends Activity>
        implements InterceptingActivityFactory {

    private Class<T> mActivityClassToIntercept;

    public SingleActivityFactory(Class<T> activityClassToIntercept) {
        Checks.checkNotNull(activityClassToIntercept);
        mActivityClassToIntercept = activityClassToIntercept;
    }

    @Override
    public final boolean shouldIntercept(ClassLoader classLoader, String className, Intent intent) {
        return mActivityClassToIntercept.getName().equals(className);
    }

    @Override
    public final Activity create(ClassLoader classLoader, String className, Intent intent) {
        if(!shouldIntercept(classLoader, className, intent))
            throw new UnsupportedOperationException(String.format("Can't create instance of %s",
                className));
        return create(intent);
    }

    public final Class<T> getActivityClassToIntercept() {
        return mActivityClassToIntercept;
    }

    protected abstract T create(Intent intent);
}
