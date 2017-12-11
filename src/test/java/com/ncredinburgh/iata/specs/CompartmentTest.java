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

import static com.ncredinburgh.iata.specs.Compartment.*;

import org.junit.Assert;
import org.junit.Test;

public class CompartmentTest
{
    @Test
    public void testCompartmentParse()
    {
        parseAndAssert(SUPERSONIC, "R");
        parseAndAssert(FIRST_CLASS_PREMIUM, "P");
        parseAndAssert(FIRST_CLASS, "F");
        parseAndAssert(FIRST_CLASS_DISCOUNTED, "A");

        parseAndAssert(BUSINESS_CLASS_PREMIUM, "J");
        parseAndAssert(BUSINESS_CLASS, "C");
        parseAndAssert(BUSINESS_CLASS_DISCOUNTED, "D", "I", "Z");

        parseAndAssert(ECONOMY_PREMIUM, "W");
        parseAndAssert(ECONOMY, "S", "Y");
        parseAndAssert(ECONOMY_DISCOUNTED, "B", "H", "K", "L", "M", "N", "Q", "T", "X");

        parseAndAssert(UNKNOWN, "???");
    }

    private static void parseAndAssert(Compartment c, String...values)
    {
        for (String value : values)
        {
            Assert.assertEquals(c, Compartment.parse(value));
        }
    }
}
