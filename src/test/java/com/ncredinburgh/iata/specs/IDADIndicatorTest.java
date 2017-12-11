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

import static com.ncredinburgh.iata.specs.IDADIndicator.*;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.ncredinburgh.iata.specs.IDADIndicator;

public class IDADIndicatorTest
{
   @Test
   public void testIDADIndicatorParse()
   {
      assertEquals(IDN1, IDADIndicator.parse("0"));
      assertEquals(IDN2, IDADIndicator.parse("1"));
      assertEquals(IDB1, IDADIndicator.parse("2"));
      assertEquals(IDB2, IDADIndicator.parse("3"));
      assertEquals(AD, IDADIndicator.parse("4"));
      assertEquals(DG, IDADIndicator.parse("5"));
      assertEquals(DM, IDADIndicator.parse("6"));
      assertEquals(GE, IDADIndicator.parse("7"));
      assertEquals(IG, IDADIndicator.parse("8"));
      assertEquals(RG, IDADIndicator.parse("9"));
      assertEquals(UD, IDADIndicator.parse("A"));
      assertEquals(ID, IDADIndicator.parse("B"));
      assertEquals(IDFS1, IDADIndicator.parse("C"));
      assertEquals(IDFS2, IDADIndicator.parse("D"));
      assertEquals(IDR1, IDADIndicator.parse("E"));
      assertEquals(IDR2, IDADIndicator.parse("F"));
      assertEquals(RESERVED_G, IDADIndicator.parse("G"));
      assertEquals(RESERVED_H, IDADIndicator.parse("H"));
      assertEquals(RESERVED_I, IDADIndicator.parse("I"));
      assertEquals(RESERVED_J, IDADIndicator.parse("J"));
      assertEquals(RESERVED_K, IDADIndicator.parse("K"));
      assertEquals(RESERVED_L, IDADIndicator.parse("L"));
      assertEquals(RESERVED_M, IDADIndicator.parse("M"));
      assertEquals(RESERVED_N, IDADIndicator.parse("N"));
      assertEquals(RESERVED_O, IDADIndicator.parse("O"));
      assertEquals(RESERVED_P, IDADIndicator.parse("P"));
      assertEquals(RESERVED_Q, IDADIndicator.parse("Q"));
      assertEquals(RESERVED_R, IDADIndicator.parse("R"));
      assertEquals(RESERVED_S, IDADIndicator.parse("S"));
      assertEquals(RESERVED_T, IDADIndicator.parse("T"));
      assertEquals(RESERVED_U, IDADIndicator.parse("U"));
      assertEquals(RESERVED_V, IDADIndicator.parse("V"));
      assertEquals(RESERVED_W, IDADIndicator.parse("W"));
      assertEquals(RESERVED_X, IDADIndicator.parse("X"));
      assertEquals(RESERVED_Y, IDADIndicator.parse("Y"));
      assertEquals(RESERVED_Z, IDADIndicator.parse("Z"));
      assertEquals(UNKNOWN, IDADIndicator.parse("???"));
    }
}
