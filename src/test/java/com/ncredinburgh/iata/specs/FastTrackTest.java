/*
 * Copyright 2015 the original author or authors.
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

package com.ncredinburgh.iata.specs;

import static com.ncredinburgh.iata.specs.FastTrack.*;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class FastTrackTest
{
   @Test
   public void testFastTrackParse()
   {
      assertEquals(ELLIGIBLE, FastTrack.parse("Y"));
      assertEquals(NOT_ELLIGIBLE, FastTrack.parse("N"));
      assertEquals(NOT_QUALIFIED, FastTrack.parse(" "));
      assertEquals(UNKNOWN, FastTrack.parse("???"));
    }
}
