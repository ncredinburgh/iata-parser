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

package com.ncredinburgh.iata;

import java.io.IOException;

import org.junit.Test;

public class ParserTest
{
    @Test(expected = ParseException.class)
    public void testShouldThrowExceptionWhenCodeTooLong() throws ParseException
    {
        new Parser().parse(new String(new char[1000]));
    }

    @Test(expected = ParseException.class)
    public void testShouldThrowExceptionWhenCodeContainsExcessData() throws ParseException
    {
        // Excess data '-EXTRA'
        new Parser().strict().parse("M1DESMARAIS/LUC       EABC123 YULFRAAC 0834 226F001A0025 10001A-EXTRA");
    }

    @Test(expected = ParseException.class)
    public void testShouldThrowExceptionWhenStrictAndDataTypeInvalid() throws ParseException
    {
        new Parser().strict().parse("M1DESMARAIS/LUC       EABC123 000FRAAC 0834 226F001A0025 100");
    }

    @Test(expected = ParseException.class)
    public void testShouldThrowExceptionWhenStrictAndValueInvalid() throws ParseException
    {
        new Parser().strict().parse("M1DESMARAIS/LUC       XABC123 YULFRAAC 0834 226F001A0025 100");
    }

    @Test(expected = ParseException.class)
    public void testShouldThrowExceptionWhenInsufficientData() throws ParseException
    {
        new Parser().strict().parse("M1DESMARAIS/LUC       ");
    }

    @Test(expected = ParseException.class)
    public void testShouldThrowExceptionWhenCodeContainsNegativeNumber() throws ParseException, IOException
    {
        new Parser().parse("M1DESMARAIS/LUC       EABC123 YULFRAAC 0834 226F001A0025 1-1");
    }
}
