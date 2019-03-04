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

import java.util.Calendar;
import java.util.TimeZone;

/**
 * This class provides static factory methods for creating {@link Calendar} instances in UTC.
 */
public final class UTCCalendarFactory
{
    public static final TimeZone UTC = TimeZone.getTimeZone("UTC");
    

    private UTCCalendarFactory()
    {
    }

    public static Calendar getInstanceForDayOfYear(int dayOfYear, Boolean forward)
    {
    	forward = (forward != null) ? forward : true;
        return getInstanceForDayOfYear(dayOfYear, Calendar.getInstance(UTC), forward);
    }

    /**
     * Creates {@link Calendar} instance in UTC from the given <code>dayOfYear</code>
     *
     * @param dayOfYear the day of year
     * @param forward : indicates if the evaluation has to be done for a future or a past flight
     * 					Default value is true to respect the previous code	
     * @param now the calendar representing the current date/time in UTC.
     * @return the new {@link Calendar} instance in UTC
     */
    static Calendar getInstanceForDayOfYear(int dayOfYear, Calendar utc, Boolean forward)
    {
    	forward = (forward != null) ? forward : true;
    	
    	// getting current date in UTC
        int utcDayOfYear = utc.get(Calendar.DAY_OF_YEAR);
        Calendar c = (Calendar) utc.clone();
        
        if (dayOfYear < utcDayOfYear && forward)
        {
            // given dayOfYear must be in the following year
            c.roll(Calendar.YEAR, 1);
        }
        else if (utcDayOfYear == 1 && (dayOfYear == 365 || dayOfYear == 366))
        {
            // check for edge case at the end of year
            // when given dayOfYear still in previous year but server time in new year
            // this way trying to compensate for lack of time zone information

            // given dayOfYear must still be in the previous year
            c.roll(Calendar.YEAR, -1);
        }


        c.set(Calendar.DAY_OF_YEAR, dayOfYear);
        // truncating the calendar, leaving only date part
        c.set(Calendar.MILLISECOND, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.HOUR_OF_DAY, 0);
        return c;
    }
}