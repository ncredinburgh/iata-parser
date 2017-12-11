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

package com.ncredinburgh.iata.model;

import static com.ncredinburgh.iata.model.IataCodes.walk;
import static com.ncredinburgh.iata.specs.Element.*;
import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.ncredinburgh.iata.Parser;
import com.ncredinburgh.iata.specs.Element;

public class IataCodesTest
{
    @Test
    public void shouldWalkIataCodeInSpecificationOrder() throws Exception
    {
        // Create expected list
        List<Element> expected = asList(FORMAT_CODE, NUMBER_OF_SEGMENTS, PASSENGER_NAME, ELECTRONIC_TICKET_INDICATOR,
            OPERATING_CARRIER_PNR_CODE, FROM_CITY_AIRPORT_CODE, TO_CITY_AIRPORT_CODE, OPERATING_CARRIER_DESIGNATOR,
            FLIGHT_NUMBER, DATE_OF_FLIGHT, COMPARTMENT_CODE, SEAT_NUMBER, CHECK_IN_SEQUENCE_NUMBER, PASSENGER_STATUS,
            CONDITIONALS_SIZE, OPERATING_CARRIER_PNR_CODE, FROM_CITY_AIRPORT_CODE, TO_CITY_AIRPORT_CODE,
            OPERATING_CARRIER_DESIGNATOR, FLIGHT_NUMBER, DATE_OF_FLIGHT, COMPARTMENT_CODE, SEAT_NUMBER,
            CHECK_IN_SEQUENCE_NUMBER, PASSENGER_STATUS, CONDITIONALS_SIZE);

        // Walk code and collect elements in order of visiting
        List<Element> actual = new ArrayList<>();
        walk(new Parser().parse("M1DESMARAIS/LUC       EABC123 YULFRAAC 0834 226F001A0025 100"),
             new CollectingVisitor(actual));

        // Check
        Assert.assertEquals(expected, actual);
    }

    private static final class CollectingVisitor implements Visitor
    {
        private final List<Element> list;

        public CollectingVisitor(List<Element> list)
        {
            this.list = list;
        }

        @Override
        public void onElement(Element e, CharSequence value) throws Exception
        {
            list.add(e);
        }
    }
}
