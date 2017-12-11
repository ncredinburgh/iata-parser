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

import static com.ncredinburgh.iata.UTCCalendarFactory.UTC;
import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.Test;

import com.ncredinburgh.iata.UTCCalendarFactory;

public class UTCCalendarFactoryTest
{
    @Test
    public void testGetUtcCalendarGivenLastDayOfYearAndCurrentDateEqualNewYear() throws Exception
    {
        GregorianCalendar serverTime = utcCal(2015, 0, 1);
        int dayOfYear = 365;
        GregorianCalendar expected = utcCal(2014, 11, 31);
        
        testGetUtcCalendar(serverTime, dayOfYear, expected);
    }

    @Test
    public void testGetUtcCalendarGivenLastDayOfLeapYearAndCurrentDateEqualNewYear() throws Exception
    {
        GregorianCalendar serverTime = utcCal(2013, 0, 1);
        int dayOfYear = 366;
        GregorianCalendar expected = utcCal(2012, 11, 31);
        
        testGetUtcCalendar(serverTime, dayOfYear, expected);
    }

    @Test
    public void testGetUtcCalendarGivenDayOfYearLessThanCurrent() throws Exception
    {
        GregorianCalendar serverTime = utcCal(2013, 6, 5);
        int dayOfYear = 65;
        GregorianCalendar expected = utcCal(2014, 2, 6);
        
        testGetUtcCalendar(serverTime, dayOfYear, expected);
    }
    
    @Test
    public void testGetUtcInstanceGivenDayOfYearGreaterThanCurrent() throws Exception
    {
        GregorianCalendar serverTime = utcCal(2013, 6, 5);
        int dayOfYear = 365;
        GregorianCalendar expected = utcCal(2013, 11, 31);
        
        testGetUtcCalendar(serverTime, dayOfYear, expected);
    }

    private static void testGetUtcCalendar(GregorianCalendar serverTime, int dayOfYear, GregorianCalendar expected)
    {
        Calendar actual = UTCCalendarFactory.getInstanceForDayOfYear(dayOfYear, serverTime);
        assertEquals(expected, actual);
    }

    private static GregorianCalendar utcCal(int year, int month, int day)
    {
        GregorianCalendar gcal = new GregorianCalendar(year, month, day, 0, 0, 0);
        gcal.setTimeZone(UTC);
        return gcal;
    }

}
