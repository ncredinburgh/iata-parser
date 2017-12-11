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

import static com.ncredinburgh.iata.specs.PassengerDescription.*;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.ncredinburgh.iata.specs.PassengerDescription;

public class PassengerDescriptionTest
{
   @Test
   public void testPassengerDescriptionParse()
   {
      assertEquals(ADULT, PassengerDescription.parse("0"));
      assertEquals(MALE, PassengerDescription.parse("1"));
      assertEquals(FEMALE, PassengerDescription.parse("2"));
      assertEquals(CHILD, PassengerDescription.parse("3"));
      assertEquals(INFANT, PassengerDescription.parse("4"));
      assertEquals(CABIN_BAGGAGE, PassengerDescription.parse("5"));
      assertEquals(ADULT_TRAVELLING_WITH_INFANT, PassengerDescription.parse("6"));
      assertEquals(UNACCOMPANIED_MINOR, PassengerDescription.parse("7"));
      assertEquals(RESERVED_8, PassengerDescription.parse("8"));
      assertEquals(RESERVED_9, PassengerDescription.parse("9"));
      assertEquals(RESERVED_A, PassengerDescription.parse("A"));
      assertEquals(RESERVED_B, PassengerDescription.parse("B"));
      assertEquals(RESERVED_C, PassengerDescription.parse("C"));
      assertEquals(RESERVED_D, PassengerDescription.parse("D"));
      assertEquals(RESERVED_E, PassengerDescription.parse("E"));
      assertEquals(RESERVED_F, PassengerDescription.parse("F"));
      assertEquals(RESERVED_G, PassengerDescription.parse("G"));
      assertEquals(RESERVED_H, PassengerDescription.parse("H"));
      assertEquals(RESERVED_I, PassengerDescription.parse("I"));
      assertEquals(RESERVED_J, PassengerDescription.parse("J"));
      assertEquals(RESERVED_K, PassengerDescription.parse("K"));
      assertEquals(RESERVED_L, PassengerDescription.parse("L"));
      assertEquals(RESERVED_M, PassengerDescription.parse("M"));
      assertEquals(RESERVED_N, PassengerDescription.parse("N"));
      assertEquals(RESERVED_O, PassengerDescription.parse("O"));
      assertEquals(RESERVED_P, PassengerDescription.parse("P"));
      assertEquals(RESERVED_Q, PassengerDescription.parse("Q"));
      assertEquals(RESERVED_R, PassengerDescription.parse("R"));
      assertEquals(RESERVED_S, PassengerDescription.parse("S"));
      assertEquals(RESERVED_T, PassengerDescription.parse("T"));
      assertEquals(RESERVED_U, PassengerDescription.parse("U"));
      assertEquals(RESERVED_V, PassengerDescription.parse("V"));
      assertEquals(RESERVED_W, PassengerDescription.parse("W"));
      assertEquals(RESERVED_X, PassengerDescription.parse("X"));
      assertEquals(RESERVED_Y, PassengerDescription.parse("Y"));
      assertEquals(RESERVED_Z, PassengerDescription.parse("Z"));
      assertEquals(UNKNOWN, PassengerDescription.parse("???"));
    }
}
