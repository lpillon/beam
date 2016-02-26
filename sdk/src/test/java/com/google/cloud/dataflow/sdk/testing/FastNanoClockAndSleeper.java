/*
 * Copyright (C) 2015 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.google.cloud.dataflow.sdk.testing;

import com.google.api.client.util.NanoClock;
import com.google.api.client.util.Sleeper;

import org.junit.rules.ExternalResource;
import org.junit.rules.TestRule;

/**
 * This object quickly moves time forward based upon how much it has been asked to sleep,
 * without actually sleeping, to simulate the backoff.
 */
public class FastNanoClockAndSleeper extends ExternalResource
    implements NanoClock, Sleeper, TestRule {
  private long fastNanoTime;

  @Override
  public long nanoTime() {
    return fastNanoTime;
  }

  @Override
  protected void before() throws Throwable {
    fastNanoTime = NanoClock.SYSTEM.nanoTime();
  }

  @Override
  public void sleep(long millis) throws InterruptedException {
    fastNanoTime += millis * 1000000L;
  }
}
