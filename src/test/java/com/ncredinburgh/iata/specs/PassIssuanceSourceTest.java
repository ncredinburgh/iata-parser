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

import static com.ncredinburgh.iata.specs.PassIssuanceSource.*;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.ncredinburgh.iata.specs.PassIssuanceSource;

public class PassIssuanceSourceTest
{
   @Test
   public void testPassIssuanceSourceParse()
   {
      assertEquals(WEB, PassIssuanceSource.parse("W"));
      assertEquals(AIRPORT_KIOSK_PRINTED, PassIssuanceSource.parse("K"));
      assertEquals(TRANSFER_KIOSK_PRINTED, PassIssuanceSource.parse("X"));
      assertEquals(OFF_SITE_KIOSK_PRINTED, PassIssuanceSource.parse("R"));
      assertEquals(MOBILE_DEVICE_PRINTED, PassIssuanceSource.parse("M"));
      assertEquals(AIRPORT_AGENT_PRINTED, PassIssuanceSource.parse("O"));
      assertEquals(TOWN_AGENT_PRINTED, PassIssuanceSource.parse("T"));
      assertEquals(THIRD_PARTY_PRINTED, PassIssuanceSource.parse("V"));
      assertEquals(UNABLE_TO_SUPPORT, PassIssuanceSource.parse(" "));
      assertEquals(UNKNOWN, PassIssuanceSource.parse("???"));
    }
}
