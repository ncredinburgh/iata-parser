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

import static com.ncredinburgh.iata.specs.Element.*;
import static com.ncredinburgh.iata.specs.Occurrence.U;
import static java.lang.String.format;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ncredinburgh.iata.specs.CheckinSource;
import com.ncredinburgh.iata.specs.DocumentType;
import com.ncredinburgh.iata.specs.Element;
import com.ncredinburgh.iata.specs.FormatCode;
import com.ncredinburgh.iata.specs.PassIssuanceSource;
import com.ncredinburgh.iata.specs.PassengerDescription;

public final class IataCode
{
    private static final Pattern NAME_PATTERN = Pattern.compile("([^/]+)/?(.*)");
    private static final int FIRST_NAME_GROUP = 2;
    private static final int LAST_NAME_GROUP = 1;

    private final Map<Element, CharSequence> elements;
    private final List<FlightSegment> flightSegments;

    private IataCode(Map<Element, CharSequence> elements, List<FlightSegment> flightSegments)
    {
        this.elements = Collections.unmodifiableMap(elements);
        this.flightSegments = Collections.unmodifiableList(flightSegments);
    }

    public static Builder builder()
    {
        return new Builder();
    }

    public FormatCode getFormatCode()
    {
        return FormatCode.parse(getValue(FORMAT_CODE));
    }

    public String getPassengerName()
    {
        return getValue(PASSENGER_NAME).trim();
    }

    public String getPassengerFirstName()
    {
        return getPassengerNamePart(getPassengerName(), FIRST_NAME_GROUP);
    }

    public String getPassengerLastName()
    {
        return getPassengerNamePart(getPassengerName(), LAST_NAME_GROUP);
    }

    public String getVersionNumber()
    {
        return getValue(VERSION_NUMBER);
    }

    public PassengerDescription getPassengerDescription()
    {
        return PassengerDescription.parse(getValue(PASSENGER_DESCRIPTION));
    }

    public CheckinSource getSourceOfCheckIn()
    {
        return CheckinSource.parse(getValue(SOURCE_OF_CHECK_IN));
    }

    public PassIssuanceSource getSourceOfPassIssuance()
    {
        return PassIssuanceSource.parse(getValue(SOURCE_OF_BOARDING_PASS_ISSUANCE));
    }

    public String getDateOfPassIssuance()
    {
        return getValue(DATE_OF_PASS_ISSUANCE);
    }

    public DocumentType getDocumentType()
    {
        return DocumentType.parse(getValue(DOCUMENT_TYPE));
    }

    public String getAirlineDesignatorOfPassIssuer()
    {
        return getValue(AIRLINE_DESIGNATOR_OF_ISSUER);
    }

    public String getBaggageTagLicensePlate()
    {
        return getValue(BAGGAGE_TAG_LICENSE_PLATE);
    }

    public FlightSegment getFirstFlightSegment()
    {
        return flightSegments.get(0);
    }

    public List<FlightSegment> getFlightSegments()
    {
        return flightSegments;
    }

    public SecurityData getSecurityData()
    {
        return new SecurityData(getValue(TYPE_OF_SECURITY_DATA), getValue(SECURITY_DATA));
    }

    private String getValue(Element e)
    {
        CharSequence s = elements.get(e);
        return s != null ? s.toString() : null;
    }

    public Map<Element, CharSequence> getElements()
    {
        return elements;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (!(o instanceof IataCode))
        {
            return false;
        }
        IataCode iataCode = (IataCode) o;
        return Objects.equals(getElements(), iataCode.getElements()) &&
            Objects.equals(getFlightSegments(), iataCode.getFlightSegments());
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(elements, getFlightSegments());
    }

    private static String getPassengerNamePart(String name, int group)
    {
        Matcher matcher = NAME_PATTERN.matcher(name);
        if (!matcher.matches())
        {
            return null;
        }
        return matcher.group(group);
    }

    public static final class Builder
    {
        private static final int MAX_NO_OF_SEGMENTS = 4;

        private final List<FlightSegment> flightSegments = new ArrayList<>(MAX_NO_OF_SEGMENTS);
        private final Map<Element, CharSequence> elements = new HashMap<>();

        private Builder() { /* ... */ }

        public Builder element(Element e, CharSequence s)
        {
            assertUniqueOccurrence(e);
            elements.put(e, s);
            return this;
        }

        public Builder flightSegment(FlightSegment segment)
        {
            flightSegments.add(segment);
            return this;
        }

        public IataCode build()
        {
            return new IataCode(elements, flightSegments);
        }

        private static void assertUniqueOccurrence(Element e)
        {
            if (!e.getOccurrence().equals(U))
            {
                throw new IllegalStateException(format("Element (%s) does not have UNIQUE occurrence.", e.name()));
            }
        }
    }
}
