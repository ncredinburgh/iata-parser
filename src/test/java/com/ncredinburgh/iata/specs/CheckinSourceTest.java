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

import static com.ncredinburgh.iata.specs.CheckinSource.*;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.ncredinburgh.iata.specs.CheckinSource;

public class CheckinSourceTest
{
   @Test
   public void testCheckinSourceParse()
   {
      assertEquals(WEB, CheckinSource.parse("W"));
      assertEquals(AIRPORT_KIOSK, CheckinSource.parse("K"));
      assertEquals(OFF_SITE_KIOSK, CheckinSource.parse("R"));
      assertEquals(MOBILE_DEVICE, CheckinSource.parse("M"));
      assertEquals(AIRPORT_AGENT, CheckinSource.parse("O"));
      assertEquals(TOWN_AGENT, CheckinSource.parse("T"));
      assertEquals(THIRD_PARTY, CheckinSource.parse("V"));
      assertEquals(UNKNOWN, CheckinSource.parse("???"));
    }
}
