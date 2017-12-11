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

import static com.ncredinburgh.iata.UTCCalendarFactory.UTC;
import static com.ncredinburgh.iata.UTCCalendarFactory.getInstanceForDayOfYear;
import static java.lang.Integer.parseInt;
import static java.lang.String.format;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ncredinburgh.iata.UTCCalendarFactory;

final class ValueDescriptions
{
    private static final int HEX = 16;
    private static final String HEX_TO_DECIMAL_FORMAT = "%d in Decimal = %s in Hexadecimal";
    private static final Pattern BAGGAGE_PATTERN = Pattern.compile("(\\d\\d|\\d)(.*)");
    private static final String YEAR_OF_FLIGHT_FORMAT = "%s = %s";
    private static final String DATE_OF_FLIGHT_FORMAT = "%s = %s%s";
    private static final String DATE_OF_FLIGHT_PATTERN = "MMMM d";

    private ValueDescriptions() { /* ... */ }

    public static ValueDescription formatCode()
    {
        return new ValueDescription()
        {
            @Override
            public String getValueDescription(Element e, CharSequence s)
            {
                return FormatCode.parse(s).getDescription();
            }
        };
    }

    public static ValueDescription sourceOfCheckin()
    {
        return new ValueDescription()
        {
            @Override
            public String getValueDescription(Element e, CharSequence s)
            {
                return CheckinSource.parse(s).getDescription();
            }
        };
    }

    public static ValueDescription electronicTicketIndicator()
    {
        return new ValueDescription()
        {
            @Override
            public String getValueDescription(Element e, CharSequence s)
            {
                return "Boarding pass issued against an Electronic Ticket";
            }
        };
    }

    public static ValueDescription documentType()
    {
        return new ValueDescription()
        {
            @Override
            public String getValueDescription(Element e, CharSequence s)
            {
                return DocumentType.parse(s).getDescription();
            }
        };
    }

    public static ValueDescription freeBaggageAllowance()
    {
        return new ValueDescription()
        {
            @Override
            public String getValueDescription(Element e, CharSequence s)
            {
                Matcher matcher = BAGGAGE_PATTERN.matcher(s);
                if (!matcher.matches())
                {
                    return "";
                }

                // Get details
                return format("%s %s", matcher.group(1), BaggageUnit.parse(matcher.group(2)).getDescription());
            }
        };
    }

    public static ValueDescription compartmentCode()
    {
        return new ValueDescription()
        {
            @Override
            public String getValueDescription(Element e, CharSequence s)
            {
                return Compartment.parse(s).getDescription();
            }
        };
    }

    public static ValueDescription fastTrack()
    {
        return new ValueDescription()
        {
            @Override
            public String getValueDescription(Element e, CharSequence s)
            {
                return FastTrack.parse(s).getDescription();
            }
        };
    }

    public static ValueDescription idAdIndicator()
    {
        return new ValueDescription()
        {
            @Override
            public String getValueDescription(Element e, CharSequence s)
            {
                return IDADIndicator.parse(s).getDescription();
            }
        };
    }

    public static ValueDescription passengerStatus()
    {
        return new ValueDescription()
        {
            @Override
            public String getValueDescription(Element e, CharSequence s)
            {
                return PassengerStatus.parse(s).getDescription();
            }
        };
    }

    public static ValueDescription selecteeIndicator()
    {
        return new ValueDescription()
        {
            @Override
            public String getValueDescription(Element e, CharSequence s)
            {
                return SelecteeIndicator.parse(s).getDescription();
            }
        };
    }

    public static ValueDescription sourceOfPassIssuance()
    {
        return new ValueDescription()
        {
            @Override
            public String getValueDescription(Element e, CharSequence s)
            {
                return PassIssuanceSource.parse(s).getDescription();
            }
        };
    }

    public static ValueDescription passengerDescription()
    {
        return new ValueDescription()
        {
            @Override
            public String getValueDescription(Element e, CharSequence s)
            {
                return PassengerDescription.parse(s).getDescription();
            }
        };
    }

    public static ValueDescription documentVerification()
    {
        return new ValueDescription()
        {
            @Override
            public String getValueDescription(Element e, CharSequence s)
            {
                return InternationalDocumentVerification.parse(s).getDescription();
            }
        };
    }

    public static ValueDescription hexValue()
    {
        return new ValueDescription()
        {
            @Override
            public String getValueDescription(Element e, CharSequence s)
            {
                try
                {
                    return format(HEX_TO_DECIMAL_FORMAT, parseInt(s.toString(), HEX), s.toString());
                }
                catch (NumberFormatException nfe)
                {
                    return "";
                }
            }
        };
    }

    public static ValueDescription dateOfFlight()
    {
        return new ValueDescription()
        {
            @Override
            public String getValueDescription(Element e, CharSequence s)
            {
                try
                {
                    return getDateOfFlight(s, parseInt(s.toString()));
                }
                catch (NumberFormatException nfe)
                {
                    return "";
                }
            }
        };
    }

    public static ValueDescription dateOfPassIssuance()
    {
        return new ValueDescription()
        {
            @Override
            public String getValueDescription(Element e, CharSequence s)
            {
                try
                {
                    if (s.length() != 4)
                    {
                        return "";
                    }
                    CharSequence year = s.subSequence(0, 1);
                    CharSequence date = s.subSequence(1, s.length());
                    return getYearOfFlight(year, parseInt(year.toString())) + ", " +
                            getDateOfFlight(date, parseInt(date.toString()));
                }
                catch (NumberFormatException nfe)
                {
                    return "";
                }
            }
        };
    }

    private static String getYearOfFlight(CharSequence s, int year)
    {
        Calendar c = getInstanceForDayOfYear(1);
        c.add(Calendar.YEAR, -c.get(Calendar.YEAR) % 10);
        return format(YEAR_OF_FLIGHT_FORMAT, s, c.get(Calendar.YEAR) + year);
    }

    private static String getDateOfFlight(CharSequence s, int dayOfYear)
    {
        Calendar date = getInstanceForDayOfYear(dayOfYear);
        return format(DATE_OF_FLIGHT_FORMAT,
            s,
            new SimpleDateFormat(DATE_OF_FLIGHT_PATTERN).format(date.getTime()),
            getDayOfMonthSuffix(date.get(Calendar.DAY_OF_MONTH)));
    }

    private static String getDayOfMonthSuffix(int dayOfMonth)
    {
        switch(dayOfMonth)
        {
            case 1: return "st";
            case 2: return "nd";
            case 3: return "rd";
            default: return "th";
        }
    }
}
