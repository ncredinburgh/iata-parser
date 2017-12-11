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

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static com.ncredinburgh.iata.specs.DataType.*;
import static com.ncredinburgh.iata.specs.ValueDescriptions.*;
import static com.ncredinburgh.iata.specs.Element.Type.*;
import static com.ncredinburgh.iata.specs.Occurrence.*;

public enum Element
{
    // -------------------------------------------------------------------------
    // Mandatory
    // -------------------------------------------------------------------------

    FORMAT_CODE                  (MAN, 1,  U, f, "S|M", "Format Code", formatCode()),
    NUMBER_OF_SEGMENTS           (MAN, 1,  U, N, "[1-4]", 10, "Number of Legs Encoded"),
    PASSENGER_NAME               (MAN, 20, U, f, "Passenger Name"),
    ELECTRONIC_TICKET_INDICATOR  (MAN, 1,  U, f, "E", "Electronic Ticket Indicator", electronicTicketIndicator()),
    OPERATING_CARRIER_PNR_CODE   (MAN, 7,  R, f, "Operating Carrier PNR Code"),
    FROM_CITY_AIRPORT_CODE       (MAN, 3,  R, a, "From City Airport Code"),
    TO_CITY_AIRPORT_CODE         (MAN, 3,  R, a, "To City Airport Code"),
    OPERATING_CARRIER_DESIGNATOR (MAN, 3,  R, f, "Operating carrier Designator"),
    FLIGHT_NUMBER                (MAN, 5,  R, NNNN_a, "Flight Number"),
    DATE_OF_FLIGHT               (MAN, 3,  R, N, "[0-9]{3}", 10, "Date of Flight (Julian Date)", dateOfFlight()),
    COMPARTMENT_CODE             (MAN, 1,  R, a, "[A-Z]", "Compartment Code", compartmentCode()),
    SEAT_NUMBER                  (MAN, 4,  R, NNNa, "Seat Number"),
    CHECK_IN_SEQUENCE_NUMBER     (MAN, 5,  R, NNNN_f, "Check-in Sequence Number"),
    PASSENGER_STATUS             (MAN, 1,  R, f, "[0-9A-Z]", "Passenger Status", passengerStatus()),
    CONDITIONALS_SIZE            (MAN, 2,  R, f, "[0-F]{2}", 16, "Field size of variable field (Conditional + Airline item 4)", hexValue()),

    // -------------------------------------------------------------------------
    // Conditional
    // -------------------------------------------------------------------------

    BEGINNING_OF_VERSION_NUMBER         (COND, 1,  U, GREATER_THAN, "Beginning of version number"),
    VERSION_NUMBER                      (COND, 1,  U, f, "[1-5]", 16, "Version number"),
    UNIQUE_CONDITIONALS_SIZE            (COND, 2,  U, f, "[0-F]{2}", 16, "Field size of following structured message - unique", hexValue()),
    PASSENGER_DESCRIPTION               (COND, 1,  U, f, "[0-9A-Z\\s]", "Passenger Description", passengerDescription()),
    SOURCE_OF_CHECK_IN                  (COND, 1,  U, f, "[WKRMOTV\\s]", "Source of check-in", sourceOfCheckin()),
    SOURCE_OF_BOARDING_PASS_ISSUANCE    (COND, 1,  U, f, "[WKXRMOTV\\s]", "Source of Boarding Pass Issuance", sourceOfPassIssuance()),
    DATE_OF_PASS_ISSUANCE               (COND, 4,  U, N, "[0-9]{4}", "Date of Issue of Boarding Pass (Julian Date)", dateOfPassIssuance()),
    DOCUMENT_TYPE                       (COND, 1,  U, f, "B|I", "Document Type", documentType()),
    AIRLINE_DESIGNATOR_OF_ISSUER        (COND, 3,  U, f, "Airline Designator of boarding pass issuer"),
    BAGGAGE_TAG_LICENSE_PLATE           (COND, 13, U, f, "Baggage Tag License Plate Number (s)"),
    FIRST_BAGGAGE_TAG_LICENSE_PLATE     (COND, 13, U, f, "1st Non-Consecutive Baggage Tag License Plate Number"),
    SECOND_BAGGAGE_TAG_LICENSE_PLATE    (COND, 13, U, f, "2nd Non-Consecutive Baggage Tag License Plate Number"),
    REPEATED_CONDITIONALS_SIZE          (COND, 2,  R, f, "[0-F]{2}", 16, "Field size of following structured message - repeated", hexValue()),
    AIRLINE_NUMERIC_CODE                (COND, 3,  R, N, "[0-9]{3}", 10, "Airline Numeric Code"),
    SERIAL_NUMBER                       (COND, 10, R, f, "Document Form/Serial Number"),
    SELECTEE_INDICATOR                  (COND, 1,  R, f, "[\\s0-1]", "Selectee Indicator", selecteeIndicator()),
    INTERNATIONAL_DOCUMENT_VERIFICATION (COND, 1,  R, f, "[\\s0-2]","International Document Verification", documentVerification()),
    MARKETING_CARRIER_DESIGNATOR        (COND, 3,  R, f, "Marketing Carrier Designator"),
    FREQUENT_FLYER_AIRLINE_DESIGNATOR   (COND, 3,  R, f, "Frequent Flyer Airline Designator"),
    FREQUENT_FLYER_NUMBER               (COND, 16, R, f, "Frequent Flyer Number"),
    ID_AD_INDICATOR                     (COND, 1,  R, f, "ID/AD Indicator", idAdIndicator()),
    FREE_BAGGAGE_ALLOWANCE              (COND, 3,  R, f, "Free Baggage Allowance", freeBaggageAllowance()),
    FAST_TRACK                          (COND, 1,  R, f, "[YN\\s]", "Fast Track", fastTrack()),
    FOR_AIRLINE_USE                     (NONE, R, ANY, "For individual airline use"),

    // -------------------------------------------------------------------------
    // Security
    // -------------------------------------------------------------------------

    BEGINNING_OF_SECURITY_DATA (SEC, 1, U, CARET_OR_GREATER_THAN, "Beginning of security data"),
    TYPE_OF_SECURITY_DATA      (SEC, 1, U, f, "Type of Security Data"),
    LENGTH_OF_SECURITY_DATA    (SEC, 2, U, f, "[0-F]{2}", 16, "Length of Security Data", hexValue()),
    SECURITY_DATA              (SEC, U, f, "Security Data");

    // -------------------------------------------------------------------------

    public enum Type
    {
        MAN("Mandatory"),
        COND("Conditional"),
        SEC("Security"),
        NONE("None");

        private final String description;

        Type(String description)
        {
            this.description = description;
        }
    }

    private final Type type;
    private final int size;
    private final Occurrence occurrence;
    private final DataType dataType;
    private final Pattern valueRegex;
    private final int base;
    private final String description;
    private final ValueDescription valueDescription;

    Element(Type type, Occurrence occurrence, DataType v, String description)
    {
        this(type, -1, occurrence, v, null, description, null);
    }

    Element(Type type, int size, Occurrence occurrence, DataType v, String description)
    {
        this(type, size, occurrence, v, null, description, null);
    }

    Element(Type type, int size, Occurrence occurrence, DataType v, String description,
            ValueDescription valueDescription)
    {
        this(type, size, occurrence, v, null, description, valueDescription);
    }

    Element(Type type, int size, Occurrence occurrence, DataType v, String valueRegex, String description,
            ValueDescription valueDescription)
    {
        this(type, size, occurrence, v, valueRegex, -1, description, valueDescription);
    }

    Element(Type type, int size, Occurrence occurrence, DataType v, String valueRegex, int base, String description)
    {
        this(type, size, occurrence, v, valueRegex, base, description, null);
    }

    Element(Type type, int size, Occurrence occurrence, DataType v, String valueRegex, int base, String description,
            ValueDescription valueDescription)
    {
        this.type = type;
        this.size = size;
        this.occurrence = occurrence;
        this.dataType = v;
        this.valueRegex = valueRegex != null ? Pattern.compile(valueRegex) : null;
        this.base = base;
        this.description = description;
        this.valueDescription = valueDescription;
    }

    public Type getType()
    {
        return type;
    }

    public int getSize()
    {
        return size;
    }

    public boolean isDataValid(CharSequence s)
    {
        return valueRegex != null ? valueRegex.matcher(s).matches() : true;
    }

    public DataType getDataType()
    {
        return dataType;
    }

    public Occurrence getOccurrence()
    {
        return occurrence;
    }

    public boolean isNumeric()
    {
        return base != -1;
    }

    public int getBase()
    {
        return base;
    }

    public String getDescription()
    {
        return description;
    }

    public String getValueDescription(CharSequence value)
    {
        return valueDescription != null ? valueDescription.getValueDescription(this, value) : null;
    }

    public static List<Element> getUniqueConditionals()
    {
        return getConditionals(U);
    }

    public static List<Element> getRepeatedConditionals()
    {
        return getConditionals(R);
    }

    private static List<Element> getConditionals(Occurrence occurrence)
    {
        List<Element> elements = new ArrayList<>();
        for (Element element : values())
        {
            if (element.getType().equals(COND) && element.getOccurrence().equals(occurrence))
            {
                elements.add(element);
            }
        }
        return elements;
    }
}
