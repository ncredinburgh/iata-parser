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

import static com.ncredinburgh.iata.specs.Element.*;
import static java.lang.String.format;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.ncredinburgh.iata.model.FlightSegment;
import com.ncredinburgh.iata.model.IataCode;
import com.ncredinburgh.iata.specs.Element;
import com.ncredinburgh.iata.specs.Occurrence;

public final class Parser
{
    public static final int VERSION = 5;

    private static final int MAX_IATA_CODE_LENGTH = 600;
    private static final List<Element> UNIQUE_CONDITIONALS;
    private static final List<Element> REPEATED_CONDITIONALS;
    static
    {
        // Build lists of unqiue & repeated conditionals, minus the elements parsed "directly"

        List<Element> unique = Element.getUniqueConditionals();
        unique.remove(BEGINNING_OF_VERSION_NUMBER);
        unique.remove(VERSION_NUMBER);
        unique.remove(UNIQUE_CONDITIONALS_SIZE);
        UNIQUE_CONDITIONALS = Collections.unmodifiableList(unique);

        List<Element> repeated = Element.getRepeatedConditionals();
        repeated.remove(REPEATED_CONDITIONALS_SIZE);
        REPEATED_CONDITIONALS = Collections.unmodifiableList(repeated);
    }

    private final boolean isStrict;

    public Parser()
    {
        this(false);
    }

    private Parser(boolean isStrict)
    {
        this.isStrict = isStrict;
    }

    public Parser strict()
    {
        return isStrict ? this : new Parser(true);
    }

    public IataCode parse(CharSequence s) throws ParseException
    {
        Builder builder = new Builder();
        parse(s, builder);
        return builder.getIataCode();
    }

    void parse(CharSequence s, Builder builder) throws ParseException
    {
        assertLength(s);
        Reader reader = new Reader(s);

        // Unique mandatory & repeated
        int noOfSegments = parseUniqueMandatoryFields(reader, builder);
        int conditionalLength = parseRepeatedMandatoryFields(reader, builder);
        Reader conditionalReader = reader.nextAsReader(conditionalLength);

        // Check if we have any conditional fields
        if (hasConditionalFields(conditionalLength, conditionalReader))
        {
            parseUniqueConditionalsFields(conditionalReader, builder);
            parseRepeatedConditionalsFields(conditionalReader, builder);
        }
        parseForAirlineUse(conditionalReader, builder);

        // All other segments
        for (int i = 1; i < noOfSegments; i++)
        {
            conditionalLength = parseRepeatedMandatoryFields(reader, builder);
            Reader repeatedReader = reader.nextAsReader(conditionalLength);
            parseRepeatedConditionalsFields(repeatedReader, builder);
            parseForAirlineUse(repeatedReader, builder);
        }

        // Security data
        parseSecurityData(reader, builder);

        // Check for excess data
        if (reader.hasRemaining())
        {
            throw new ParseException(format("Excess data found: '%s'", reader.next(reader.remaining())));
        }
    }

    private int parseUniqueMandatoryFields(Reader reader, Builder builder) throws ParseException
    {
        parse(reader, builder, FORMAT_CODE);
        int noOfSegments = parse(reader, builder, NUMBER_OF_SEGMENTS).toInt();
        parse(reader, builder, PASSENGER_NAME);
        parse(reader, builder, ELECTRONIC_TICKET_INDICATOR);
        return noOfSegments;
    }

    private void parseUniqueConditionalsFields(Reader reader, Builder builder) throws ParseException
    {
        // Check for zero-length unique conditional section
        if (reader.hasRemaining())
        {
            parse(reader, builder, BEGINNING_OF_VERSION_NUMBER);
            parse(reader, builder, VERSION_NUMBER);

            // Determine length of unique conditionals & parse
            int length = parse(reader, builder, UNIQUE_CONDITIONALS_SIZE).toInt();
            parseConditionals(reader, builder, UNIQUE_CONDITIONALS, length);
        }
    }

    private int parseRepeatedMandatoryFields(Reader reader, Builder builder) throws ParseException
    {
        parse(reader, builder, OPERATING_CARRIER_PNR_CODE);
        parse(reader, builder, FROM_CITY_AIRPORT_CODE);
        parse(reader, builder, TO_CITY_AIRPORT_CODE);
        parse(reader, builder, OPERATING_CARRIER_DESIGNATOR);
        parse(reader, builder, FLIGHT_NUMBER);
        parse(reader, builder, DATE_OF_FLIGHT);
        parse(reader, builder, COMPARTMENT_CODE);
        parse(reader, builder, SEAT_NUMBER);
        parse(reader, builder, CHECK_IN_SEQUENCE_NUMBER);
        parse(reader, builder, PASSENGER_STATUS);
        return parse(reader, builder, CONDITIONALS_SIZE).toInt();
    }

    private void parseRepeatedConditionalsFields(Reader reader, Builder builder) throws ParseException
    {
        if (reader.hasRemaining())
        {
            // Determine length of repeated conditionals & parse
            int length = parse(reader, builder, REPEATED_CONDITIONALS_SIZE).toInt();
            parseConditionals(reader, builder, REPEATED_CONDITIONALS, length);
        }
    }

    private void parseConditionals(Reader reader, Builder builder, List<Element> conditionals, int length) throws ParseException
    {
        for (Iterator<Element> iterator = conditionals.iterator(); length > 0 && iterator.hasNext();)
        {
            length -= parse(reader, builder, iterator.next()).getSize();
        }
    }

    private void parseForAirlineUse(Reader reader, Builder builder) throws ParseException
    {
        if (reader.hasRemaining())
        {
            parse(reader, builder, reader.remaining(), FOR_AIRLINE_USE);
        }
        builder.onEndFlightSegment();
    }

    private void parseSecurityData(Reader reader, Builder builder) throws ParseException
    {
        if (reader.hasRemaining())
        {
            // An old version of the spec does not require this prologue
            if (BEGINNING_OF_SECURITY_DATA.getDataType().isValid(reader.peek()))
            {
                parse(reader, builder, BEGINNING_OF_SECURITY_DATA);
                parse(reader, builder, TYPE_OF_SECURITY_DATA);
            }
            int securityDataLength = parse(reader, builder, LENGTH_OF_SECURITY_DATA).toInt();
            parse(reader, builder, securityDataLength, SECURITY_DATA);
        }
    }

    private Value parse(Reader r, Builder builder, Element element) throws ParseException
    {
        return parse(r, builder, element.getSize(), element);
    }

    private Value parse(Reader r, Builder builder, int size, Element element) throws ParseException
    {
        CharSequence rawValue;
        try
        {
            rawValue = r.next(size);
        }
        catch (ParseException e)
        {
            throw new ParseException(formatError(element, e.getMessage()));
        }

        // Check datatype if we are strict
        if (isStrict && !element.getDataType().isValid(rawValue))
        {
            throw new ParseException(formatError(element, rawValue,
                format("Value does not match data type: %s", element.getDataType())));
        }

        // Check value if we are strict
        if (isStrict && !element.isDataValid(rawValue))
        {
            throw new ParseException(formatError(element, rawValue, format("Value is not in accepted range: %s", element.getDataType())));
        }

        // Create value and pass through callback
        Value value = new Value(rawValue, element);
        builder.onElement(value);
        return value;
    }

    private static boolean hasConditionalFields(int length, Reader reader) throws ParseException
    {
        return length > 0 && BEGINNING_OF_VERSION_NUMBER.getDataType().isValid(reader.peek());
    }

    private static String formatError(Element element, String reason)
    {
        return format("%nElement: %s%nReason : %s", element, reason);
    }

    private static String formatError(Element element, CharSequence s, String reason)
    {
        return format("%nElement: %s%nValue  : '%s'%nReason : %s", element, s, reason);
    }

    private static void assertLength(CharSequence s) throws ParseException
    {
        if (s.length() > MAX_IATA_CODE_LENGTH)
        {
            throw new ParseException(format("Code exceeds max length of %d!", MAX_IATA_CODE_LENGTH));
        }
    }

    private static final class Builder
    {
        private final IataCode.Builder iataCodeBuilder;
        private FlightSegment.Builder flightSegmentBuilder;

        Builder()
        {
            this.iataCodeBuilder = IataCode.builder();
            this.flightSegmentBuilder = FlightSegment.builder();
        }

        public IataCode getIataCode()
        {
            return iataCodeBuilder.build();
        }

        public void onEndFlightSegment()
        {
            iataCodeBuilder.flightSegment(flightSegmentBuilder.build());
            flightSegmentBuilder = FlightSegment.builder();
        }

        public void onElement(Parser.Value value)
        {
            Element e = value.getElement();
            if (e.getOccurrence().equals(Occurrence.U))
            {
                iataCodeBuilder.element(value.getElement(), value.getRawValue());
            }
            else
            {
                flightSegmentBuilder.element(value.getElement(), value.getRawValue());
            }
        }
    }

    private static final class Value
    {
        private final CharSequence rawValue;
        private final Element element;

        Value(CharSequence rawValue, Element element)
        {
            this.rawValue = rawValue;
            this.element = element;
        }

        public int getSize()
        {
            return rawValue.length();
        }

        public CharSequence getRawValue()
        {
            return rawValue;
        }

        public int toInt() throws ParseException
        {
            if (!element.isNumeric())
            {
                // Should never happen/developer error
                throw new IllegalStateException("The supplied field data type is not numeric");
            }

            int i = Integer.parseInt(rawValue.toString(), element.getBase());
            if (i < 0)
            {
                throw new ParseException("Negative numbers are not supported");
            }
            return i;
        }

        public Element getElement()
        {
            return element;
        }
    }

    private static class Reader
    {
        private final CharSequence s;
        private int pos;

        public Reader(CharSequence s)
        {
            this.s = s;
        }

        public Reader nextAsReader(int n) throws ParseException
        {
            return new Reader(next(n));
        }

        public CharSequence next(int n) throws ParseException
        {
            checkBounds(n);
            CharSequence sub = s.subSequence(pos, pos + n);
            pos += n;
            return sub;
        }

        public CharSequence peek() throws ParseException
        {
            checkBounds(1);
            return s.subSequence(pos, pos + 1);
        }

        public boolean hasRemaining()
        {
            return remaining() > 0;
        }

        public int remaining()
        {
            return s.length() - pos;
        }

        private void checkBounds(int n) throws ParseException
        {
            // Check we have sufficient data
            if (pos + n > s.length())
            {
                throw new ParseException(format("Requested: %s, Available: %s", n, remaining()));
            }
        }
    }
}
