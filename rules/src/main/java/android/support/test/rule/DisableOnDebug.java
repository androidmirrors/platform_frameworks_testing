/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the Lice`nse is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package android.support.test.rule;

import android.os.Debug;
import org.junit.rules.TestRule;
import org.junit.rules.Timeout;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/**
 * The {@code DisableOnDebug} Rule allows you to label certain rules to be
 * disabled when debugging.
 * <p>
 * The most illustrative use case is for tests that make use of the
 * {@link Timeout} rule, when ran in debug mode the test may terminate on
 * timeout abruptly during debugging. Developers may disable the timeout, or
 * increase the timeout by making a code change on tests that need debugging and
 * remember revert the change afterwards or rules such as {@link Timeout} that
 * may be disabled during debugging may be wrapped in a {@code DisableOnDebug}.
 * <p>
 * The important benefit of this feature is that you can disable such rules
 * without any making any modifications to your test class to remove them during
 * debugging.
 * <p>
 * This does nothing to tackle timeouts or time sensitive code under test when
 * debugging and may make this less useful in such circumstances.
 * <p>
 * Example usage:
 *
 * <pre>
 * public static class DisableTimeoutOnDebugSampleTest {
 *
 *     &#064;Rule
 *     public TestRule timeout = new DisableOnDebug(new Timeout(20));
 *
 *     &#064;Test
 *     public void myTest() {
 *         int i = 0;
 *         assertEquals(0, i); // suppose you had a break point here to inspect i
 *     }
 * }
 * </pre>
 */
// NOTE The docs and API of this class is copied from JUnit 4.12. The implementation is not.
public class DisableOnDebug implements TestRule {
    private final TestRule delegate;

    public DisableOnDebug(TestRule delegate) {
        this.delegate = delegate;
    }

    @Override
    public final Statement apply(final Statement base, Description description) {
        if (isDebugging()) {
            return base;
        }
        return delegate.apply(base, description);
    }

    public boolean isDebugging() {
        return Debug.isDebuggerConnected();
    }
}
