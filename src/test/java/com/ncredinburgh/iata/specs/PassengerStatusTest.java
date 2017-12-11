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

import static com.ncredinburgh.iata.specs.PassengerStatus.*;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.ncredinburgh.iata.specs.PassengerStatus;

public class PassengerStatusTest
{
   @Test
   public void testPassengerStatusParse()
   {
      assertEquals(TICKET_ISSUANCE_PASSENGER_NOT_CHECKED_IN, PassengerStatus.parse("0"));
      assertEquals(TICKET_ISSUANCE_PASSENGER_CHECKED_IN, PassengerStatus.parse("1"));
      assertEquals(BAGGAGE_CHECKED_PASSENGER_NOT_CHECKED_IN, PassengerStatus.parse("2"));
      assertEquals(BAGGAGE_CHECKED_PASSENGER_CHECKED_IN, PassengerStatus.parse("3"));
      assertEquals(PASSENGER_PASSED_SECURITY_CHECK, PassengerStatus.parse("4"));
      assertEquals(PASSENGER_PASSED_EXIT_GATE, PassengerStatus.parse("5"));
      assertEquals(TRANSIT, PassengerStatus.parse("6"));
      assertEquals(STANDBY, PassengerStatus.parse("7"));
      assertEquals(BOARDING_DATA_REVALIDATION_DONE, PassengerStatus.parse("8"));
      assertEquals(BOARDING_LINE_USED, PassengerStatus.parse("9"));
      assertEquals(UP_OR_DOWN_GRADING_REQUIRED, PassengerStatus.parse("A"));
      assertEquals(RESERVED_B, PassengerStatus.parse("B"));
      assertEquals(RESERVED_C, PassengerStatus.parse("C"));
      assertEquals(RESERVED_D, PassengerStatus.parse("D"));
      assertEquals(RESERVED_E, PassengerStatus.parse("E"));
      assertEquals(RESERVED_F, PassengerStatus.parse("F"));
      assertEquals(RESERVED_G, PassengerStatus.parse("G"));
      assertEquals(RESERVED_H, PassengerStatus.parse("H"));
      assertEquals(RESERVED_I, PassengerStatus.parse("I"));
      assertEquals(RESERVED_J, PassengerStatus.parse("J"));
      assertEquals(RESERVED_K, PassengerStatus.parse("K"));
      assertEquals(RESERVED_L, PassengerStatus.parse("L"));
      assertEquals(RESERVED_M, PassengerStatus.parse("M"));
      assertEquals(RESERVED_N, PassengerStatus.parse("N"));
      assertEquals(RESERVED_O, PassengerStatus.parse("O"));
      assertEquals(RESERVED_P, PassengerStatus.parse("P"));
      assertEquals(RESERVED_Q, PassengerStatus.parse("Q"));
      assertEquals(RESERVED_R, PassengerStatus.parse("R"));
      assertEquals(RESERVED_S, PassengerStatus.parse("S"));
      assertEquals(RESERVED_T, PassengerStatus.parse("T"));
      assertEquals(RESERVED_U, PassengerStatus.parse("U"));
      assertEquals(RESERVED_V, PassengerStatus.parse("V"));
      assertEquals(RESERVED_W, PassengerStatus.parse("W"));
      assertEquals(RESERVED_X, PassengerStatus.parse("X"));
      assertEquals(RESERVED_Y, PassengerStatus.parse("Y"));
      assertEquals(RESERVED_Z, PassengerStatus.parse("Z"));
      assertEquals(UNKNOWN, PassengerStatus.parse("???"));
    }
}
