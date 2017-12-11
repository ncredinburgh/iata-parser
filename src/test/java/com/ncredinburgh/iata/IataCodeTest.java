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

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.ncredinburgh.iata.model.FlightSegment;
import com.ncredinburgh.iata.model.IataCode;
import com.ncredinburgh.iata.model.SecurityData;
import com.ncredinburgh.iata.specs.Element;
import com.ncredinburgh.iata.specs.Occurrence;

import nl.jqno.equalsverifier.EqualsVerifier;

public class IataCodeTest
{
    @Test
    public void testMandatoryOnly() throws ParseException
    {
        Assert.assertEquals(
            new Parser().strict().parse(
                "M1DESMARAIS/LUC       EABC123 YULFRAAC 0834 226F001A0025 100^16" +
                "4GIWVC5EH7JNT684FVNJ91W2QA4DVN5J8K4F0L0GEQ3DF5TGBN8709HKT5D3DW3" +
                "GBHFCVHMY7J5T6HFR41W2QA4DVN5J8K4F0L0GE"),
            IataCode.builder()
                .element(FORMAT_CODE, "M")
                .element(NUMBER_OF_SEGMENTS, "1")
                .element(PASSENGER_NAME, "DESMARAIS/LUC       ")
                .element(ELECTRONIC_TICKET_INDICATOR, "E")
                // -----------------------------------------------
                // Flight Segment [1]
                // -----------------------------------------------
                .flightSegment(
                    FlightSegment.builder()
                        .element(OPERATING_CARRIER_PNR_CODE, "ABC123 ")
                        .element(FROM_CITY_AIRPORT_CODE, "YUL")
                        .element(TO_CITY_AIRPORT_CODE, "FRA")
                        .element(OPERATING_CARRIER_DESIGNATOR, "AC ")
                        .element(FLIGHT_NUMBER, "0834 ")
                        .element(DATE_OF_FLIGHT, "226")
                        .element(COMPARTMENT_CODE, "F")
                        .element(SEAT_NUMBER, "001A")
                        .element(CHECK_IN_SEQUENCE_NUMBER, "0025 ")
                        .element(PASSENGER_STATUS, "1")
                        .element(CONDITIONALS_SIZE, "00")
                        .build())
                // -----------------------------------------------
                // Security Data
                // -----------------------------------------------
                .element(BEGINNING_OF_SECURITY_DATA, "^")
                .element(TYPE_OF_SECURITY_DATA, "1")
                .element(LENGTH_OF_SECURITY_DATA, "64")
                .element(SECURITY_DATA, "GIWVC5EH7JNT684FVNJ91W2QA4DVN5J8K4F0L0GEQ3DF5TGBN8709HKT5D3DW3GBHFCVHMY7J5T6HFR41W2QA4DVN5J8K4F0L0GE")
                .build());
    }

    @Test
    public void testMandatoryWithoutFirstnameExcludeSlash() throws ParseException
    {
        Assert.assertEquals(
            new Parser().strict().parse(
                "M1DESMARAIS           EABC123 YULFRAAC 0834 226F001A0025 100^16" +
                "4GIWVC5EH7JNT684FVNJ91W2QA4DVN5J8K4F0L0GEQ3DF5TGBN8709HKT5D3DW3" +
                "GBHFCVHMY7J5T6HFR41W2QA4DVN5J8K4F0L0GE"),
            IataCode.builder()
                .element(FORMAT_CODE, "M")
                .element(NUMBER_OF_SEGMENTS, "1")
                .element(PASSENGER_NAME, "DESMARAIS           ")
                .element(ELECTRONIC_TICKET_INDICATOR, "E")
                // -----------------------------------------------
                // Flight Segment [1]
                // -----------------------------------------------
                .flightSegment(
                    FlightSegment.builder()
                        .element(OPERATING_CARRIER_PNR_CODE, "ABC123 ")
                        .element(FROM_CITY_AIRPORT_CODE, "YUL")
                        .element(TO_CITY_AIRPORT_CODE, "FRA")
                        .element(OPERATING_CARRIER_DESIGNATOR, "AC ")
                        .element(FLIGHT_NUMBER, "0834 ")
                        .element(DATE_OF_FLIGHT, "226")
                        .element(COMPARTMENT_CODE, "F")
                        .element(SEAT_NUMBER, "001A")
                        .element(CHECK_IN_SEQUENCE_NUMBER, "0025 ")
                        .element(PASSENGER_STATUS, "1")
                        .element(CONDITIONALS_SIZE, "00")
                        .build())
                // -----------------------------------------------
                // Security Data
                // -----------------------------------------------
                .element(BEGINNING_OF_SECURITY_DATA, "^")
                .element(TYPE_OF_SECURITY_DATA, "1")
                .element(LENGTH_OF_SECURITY_DATA, "64")
                .element(SECURITY_DATA, "GIWVC5EH7JNT684FVNJ91W2QA4DVN5J8K4F0L0GEQ3DF5TGBN8709HKT5D3DW3GBHFCVHMY7J5T6HFR41W2QA4DVN5J8K4F0L0GE")
                .build());
    }

    @Test
    public void testMandatoryWithoutFirstnameIncludeSlash() throws ParseException
    {
        Assert.assertEquals(
            new Parser().strict().parse(
                "M1DESMARAIS/          EABC123 YULFRAAC 0834 226F001A0025 100^16" +
                "4GIWVC5EH7JNT684FVNJ91W2QA4DVN5J8K4F0L0GEQ3DF5TGBN8709HKT5D3DW3" +
                "GBHFCVHMY7J5T6HFR41W2QA4DVN5J8K4F0L0GE"),
            IataCode.builder()
                .element(FORMAT_CODE, "M")
                .element(NUMBER_OF_SEGMENTS, "1")
                .element(PASSENGER_NAME, "DESMARAIS/          ")
                .element(ELECTRONIC_TICKET_INDICATOR, "E")
                // -----------------------------------------------
                // Flight Segment [1]
                // -----------------------------------------------
                .flightSegment(
                    FlightSegment.builder()
                        .element(OPERATING_CARRIER_PNR_CODE, "ABC123 ")
                        .element(FROM_CITY_AIRPORT_CODE, "YUL")
                        .element(TO_CITY_AIRPORT_CODE, "FRA")
                        .element(OPERATING_CARRIER_DESIGNATOR, "AC ")
                        .element(FLIGHT_NUMBER, "0834 ")
                        .element(DATE_OF_FLIGHT, "226")
                        .element(COMPARTMENT_CODE, "F")
                        .element(SEAT_NUMBER, "001A")
                        .element(CHECK_IN_SEQUENCE_NUMBER, "0025 ")
                        .element(PASSENGER_STATUS, "1")
                        .element(CONDITIONALS_SIZE, "00")
                        .build())
                // -----------------------------------------------
                // Security Data
                // -----------------------------------------------
                .element(BEGINNING_OF_SECURITY_DATA, "^")
                .element(TYPE_OF_SECURITY_DATA, "1")
                .element(LENGTH_OF_SECURITY_DATA, "64")
                .element(SECURITY_DATA, "GIWVC5EH7JNT684FVNJ91W2QA4DVN5J8K4F0L0GEQ3DF5TGBN8709HKT5D3DW3GBHFCVHMY7J5T6HFR41W2QA4DVN5J8K4F0L0GE")
                .build());
    }

    @Test
    public void testMandatoryWithoutSecurity() throws ParseException
    {
        Assert.assertEquals(
            new Parser().strict().parse("M1DESMARAIS/LUC       EABC123 YULFRAAC 0834 226F001A0025 100"),
            IataCode.builder()
                .element(FORMAT_CODE, "M")
                .element(NUMBER_OF_SEGMENTS, "1")
                .element(PASSENGER_NAME, "DESMARAIS/LUC       ")
                .element(ELECTRONIC_TICKET_INDICATOR, "E")
                // -----------------------------------------------
                // Flight Segment [1]
                // -----------------------------------------------
                .flightSegment(
                    FlightSegment.builder()
                        .element(OPERATING_CARRIER_PNR_CODE, "ABC123 ")
                        .element(FROM_CITY_AIRPORT_CODE, "YUL")
                        .element(TO_CITY_AIRPORT_CODE, "FRA")
                        .element(OPERATING_CARRIER_DESIGNATOR, "AC ")
                        .element(FLIGHT_NUMBER, "0834 ")
                        .element(DATE_OF_FLIGHT, "226")
                        .element(COMPARTMENT_CODE, "F")
                        .element(SEAT_NUMBER, "001A")
                        .element(CHECK_IN_SEQUENCE_NUMBER, "0025 ")
                        .element(PASSENGER_STATUS, "1")
                        .element(CONDITIONALS_SIZE, "00")
                        .build())
                .build());
    }

    @Test
    public void testMandatoryWithLongSurnameExcludeSlash() throws ParseException
    {
        Assert.assertEquals(
            new Parser().strict().parse(
                "M1DESMARAISDESMARAISABEABC123 YULFRAAC 0834 226F001A0025 100^16" +
                "4GIWVC5EH7JNT684FVNJ91W2QA4DVN5J8K4F0L0GEQ3DF5TGBN8709HKT5D3DW3" +
                "GBHFCVHMY7J5T6HFR41W2QA4DVN5J8K4F0L0GE"),
            IataCode.builder()
                .element(FORMAT_CODE, "M")
                .element(NUMBER_OF_SEGMENTS, "1")
                .element(PASSENGER_NAME, "DESMARAISDESMARAISAB")
                .element(ELECTRONIC_TICKET_INDICATOR, "E")
                // -----------------------------------------------
                // Flight Segment [1]
                // -----------------------------------------------
                .flightSegment(
                    FlightSegment.builder()
                        .element(OPERATING_CARRIER_PNR_CODE, "ABC123 ")
                        .element(FROM_CITY_AIRPORT_CODE, "YUL")
                        .element(TO_CITY_AIRPORT_CODE, "FRA")
                        .element(OPERATING_CARRIER_DESIGNATOR, "AC ")
                        .element(FLIGHT_NUMBER, "0834 ")
                        .element(DATE_OF_FLIGHT, "226")
                        .element(COMPARTMENT_CODE, "F")
                        .element(SEAT_NUMBER, "001A")
                        .element(CHECK_IN_SEQUENCE_NUMBER, "0025 ")
                        .element(PASSENGER_STATUS, "1")
                        .element(CONDITIONALS_SIZE, "00")
                        .build())
                // -----------------------------------------------
                // Security Data
                // -----------------------------------------------
                .element(BEGINNING_OF_SECURITY_DATA, "^")
                .element(TYPE_OF_SECURITY_DATA, "1")
                .element(LENGTH_OF_SECURITY_DATA, "64")
                .element(SECURITY_DATA, "GIWVC5EH7JNT684FVNJ91W2QA4DVN5J8K4F0L0GEQ3DF5TGBN8709HKT5D3DW3GBHFCVHMY7J5T6HFR41W2QA4DVN5J8K4F0L0GE")
                .build());
    }

    @Test
    public void testSingleSegmentAllFields() throws ParseException
    {
        Assert.assertEquals(
            new Parser().strict().parse(
                "M1DESMARAIS/LUC       EABC123 YULFRAAC 0834 226F001A0025 14C>31" +
                "81WW6225BAC 0085123456003290141234567890 1AC AC 1234567890123  " +
                "  2PCLX58Z^164GIWVC5EH7JNT684FVNJ91W2QA4DVN5J8K4F0L0GEQ3DF5TGBN" +
                "8709HKT5D3DW3GBHFCVHMY7J5T6HFR41W2QA4DVN5J8K4F0L0GE"),
            IataCode.builder()
                .element(FORMAT_CODE, "M")
                .element(NUMBER_OF_SEGMENTS, "1")
                .element(PASSENGER_NAME, "DESMARAIS/LUC       ")
                .element(ELECTRONIC_TICKET_INDICATOR, "E")
                .element(BEGINNING_OF_VERSION_NUMBER, ">")
                .element(VERSION_NUMBER, "3")
                .element(UNIQUE_CONDITIONALS_SIZE, "18")
                .element(PASSENGER_DESCRIPTION, "1")
                .element(SOURCE_OF_CHECK_IN, "W")
                .element(SOURCE_OF_BOARDING_PASS_ISSUANCE, "W")
                .element(DATE_OF_PASS_ISSUANCE, "6225")
                .element(DOCUMENT_TYPE, "B")
                .element(AIRLINE_DESIGNATOR_OF_ISSUER, "AC ")
                .element(BAGGAGE_TAG_LICENSE_PLATE, "0085123456003")
                // -----------------------------------------------
                // Flight Segment [1]
                // -----------------------------------------------
                .flightSegment(
                    FlightSegment.builder()
                        .element(OPERATING_CARRIER_PNR_CODE, "ABC123 ")
                        .element(FROM_CITY_AIRPORT_CODE, "YUL")
                        .element(TO_CITY_AIRPORT_CODE, "FRA")
                        .element(OPERATING_CARRIER_DESIGNATOR, "AC ")
                        .element(FLIGHT_NUMBER, "0834 ")
                        .element(DATE_OF_FLIGHT, "226")
                        .element(COMPARTMENT_CODE, "F")
                        .element(SEAT_NUMBER, "001A")
                        .element(CHECK_IN_SEQUENCE_NUMBER, "0025 ")
                        .element(PASSENGER_STATUS, "1")
                        .element(CONDITIONALS_SIZE, "4C")
                        .element(REPEATED_CONDITIONALS_SIZE, "29")
                        .element(AIRLINE_NUMERIC_CODE, "014")
                        .element(SERIAL_NUMBER, "1234567890")
                        .element(SELECTEE_INDICATOR, " ")
                        .element(INTERNATIONAL_DOCUMENT_VERIFICATION, "1")
                        .element(MARKETING_CARRIER_DESIGNATOR, "AC ")
                        .element(FREQUENT_FLYER_AIRLINE_DESIGNATOR, "AC ")
                        .element(FREQUENT_FLYER_NUMBER, "1234567890123   ")
                        .element(ID_AD_INDICATOR, " ")
                        .element(FREE_BAGGAGE_ALLOWANCE, "2PC")
                        .element(FOR_AIRLINE_USE, "LX58Z")
                        .build())
                // -----------------------------------------------
                // Security Data
                // -----------------------------------------------
                .element(BEGINNING_OF_SECURITY_DATA, "^")
                .element(TYPE_OF_SECURITY_DATA, "1")
                .element(LENGTH_OF_SECURITY_DATA, "64")
                .element(SECURITY_DATA, "GIWVC5EH7JNT684FVNJ91W2QA4DVN5J8K4F0L0GEQ3DF5TGBN8709HKT5D3DW3GBHFCVHMY7J5T6HFR41W2QA4DVN5J8K4F0L0GE")
                .build());
    }

    @Test
    public void testSingleSegmentAllFieldsWithoutSecurity() throws ParseException
    {
        Assert.assertEquals(
            new Parser().strict().parse(
                "M1DESMARAIS/LUC       EABC123 YULFRAAC 0834 226F001A0025 14C>31" +
                "81WW6225BAC 0085123456003290141234567890 1AC AC 1234567890123  " +
                "  2PCLX58Z"),
            IataCode.builder()
                .element(FORMAT_CODE, "M")
                .element(NUMBER_OF_SEGMENTS, "1")
                .element(PASSENGER_NAME, "DESMARAIS/LUC       ")
                .element(ELECTRONIC_TICKET_INDICATOR, "E")
                .element(BEGINNING_OF_VERSION_NUMBER, ">")
                .element(VERSION_NUMBER, "3")
                .element(UNIQUE_CONDITIONALS_SIZE, "18")
                .element(PASSENGER_DESCRIPTION, "1")
                .element(SOURCE_OF_CHECK_IN, "W")
                .element(SOURCE_OF_BOARDING_PASS_ISSUANCE, "W")
                .element(DATE_OF_PASS_ISSUANCE, "6225")
                .element(DOCUMENT_TYPE, "B")
                .element(AIRLINE_DESIGNATOR_OF_ISSUER, "AC ")
                .element(BAGGAGE_TAG_LICENSE_PLATE, "0085123456003")
                // -----------------------------------------------
                // Flight Segment [1]
                // -----------------------------------------------
                .flightSegment(
                    FlightSegment.builder()
                        .element(OPERATING_CARRIER_PNR_CODE, "ABC123 ")
                        .element(FROM_CITY_AIRPORT_CODE, "YUL")
                        .element(TO_CITY_AIRPORT_CODE, "FRA")
                        .element(OPERATING_CARRIER_DESIGNATOR, "AC ")
                        .element(FLIGHT_NUMBER, "0834 ")
                        .element(DATE_OF_FLIGHT, "226")
                        .element(COMPARTMENT_CODE, "F")
                        .element(SEAT_NUMBER, "001A")
                        .element(CHECK_IN_SEQUENCE_NUMBER, "0025 ")
                        .element(PASSENGER_STATUS, "1")
                        .element(CONDITIONALS_SIZE, "4C")
                        .element(REPEATED_CONDITIONALS_SIZE, "29")
                        .element(AIRLINE_NUMERIC_CODE, "014")
                        .element(SERIAL_NUMBER, "1234567890")
                        .element(SELECTEE_INDICATOR, " ")
                        .element(INTERNATIONAL_DOCUMENT_VERIFICATION, "1")
                        .element(MARKETING_CARRIER_DESIGNATOR, "AC ")
                        .element(FREQUENT_FLYER_AIRLINE_DESIGNATOR, "AC ")
                        .element(FREQUENT_FLYER_NUMBER, "1234567890123   ")
                        .element(ID_AD_INDICATOR, " ")
                        .element(FREE_BAGGAGE_ALLOWANCE, "2PC")
                        .element(FOR_AIRLINE_USE, "LX58Z")
                        .build())
                .build());
    }

    @Test
    public void testSingleSegmentPartiallyPopulated() throws ParseException
    {
        Assert.assertEquals(
            new Parser().strict().parse(
                "M1GRANDMAIRE/MELANIE  EABC123 GVACDGAF 0123 228C002F0025 113>30" +
                "00D0571234567890^164GIWVC5EH7JNT684FVNJ91W2QA4DVN5J8K4F0L0GEQ3D" +
                "F5TGBN8709HKT5D3DW3GBHFCVHMY7J5T6HFR41W2QA4DVN5J8K4F0L0GE"),
            IataCode.builder()
                .element(FORMAT_CODE, "M")
                .element(NUMBER_OF_SEGMENTS, "1")
                .element(PASSENGER_NAME, "GRANDMAIRE/MELANIE  ")
                .element(ELECTRONIC_TICKET_INDICATOR, "E")
                .element(BEGINNING_OF_VERSION_NUMBER, ">")
                .element(VERSION_NUMBER, "3")
                .element(UNIQUE_CONDITIONALS_SIZE, "00")
                // -----------------------------------------------
                // Flight Segment [1]
                // -----------------------------------------------
                .flightSegment(
                    FlightSegment.builder()
                        .element(OPERATING_CARRIER_PNR_CODE, "ABC123 ")
                        .element(FROM_CITY_AIRPORT_CODE, "GVA")
                        .element(TO_CITY_AIRPORT_CODE, "CDG")
                        .element(OPERATING_CARRIER_DESIGNATOR, "AF ")
                        .element(FLIGHT_NUMBER, "0123 ")
                        .element(DATE_OF_FLIGHT, "228")
                        .element(COMPARTMENT_CODE, "C")
                        .element(SEAT_NUMBER, "002F")
                        .element(CHECK_IN_SEQUENCE_NUMBER, "0025 ")
                        .element(PASSENGER_STATUS, "1")
                        .element(CONDITIONALS_SIZE, "13")
                        .element(REPEATED_CONDITIONALS_SIZE, "0D")
                        .element(AIRLINE_NUMERIC_CODE, "057")
                        .element(SERIAL_NUMBER, "1234567890")
                        .build())
                // -----------------------------------------------
                // Security Data
                // -----------------------------------------------
                .element(BEGINNING_OF_SECURITY_DATA, "^")
                .element(TYPE_OF_SECURITY_DATA, "1")
                .element(LENGTH_OF_SECURITY_DATA, "64")
                .element(SECURITY_DATA, "GIWVC5EH7JNT684FVNJ91W2QA4DVN5J8K4F0L0GEQ3DF5TGBN8709HKT5D3DW3GBHFCVHMY7J5T6HFR41W2QA4DVN5J8K4F0L0GE")
                .build());
    }

    @Test
    public void testSingleSegmentPartiallyPopulatedWithoutSecurity() throws ParseException
    {
        Assert.assertEquals(
            new Parser().strict().parse(
                "M1GRANDMAIRE/MELANIE  EABC123 GVACDGAF 0123 228C002F0025 113>30" +
                "00D0571234567890"),
            IataCode.builder()
                .element(FORMAT_CODE, "M")
                .element(NUMBER_OF_SEGMENTS, "1")
                .element(PASSENGER_NAME, "GRANDMAIRE/MELANIE  ")
                .element(ELECTRONIC_TICKET_INDICATOR, "E")
                .element(BEGINNING_OF_VERSION_NUMBER, ">")
                .element(VERSION_NUMBER, "3")
                .element(UNIQUE_CONDITIONALS_SIZE, "00")
                // -----------------------------------------------
                // Flight Segment [1]
                // -----------------------------------------------
                .flightSegment(
                    FlightSegment.builder()
                        .element(OPERATING_CARRIER_PNR_CODE, "ABC123 ")
                        .element(FROM_CITY_AIRPORT_CODE, "GVA")
                        .element(TO_CITY_AIRPORT_CODE, "CDG")
                        .element(OPERATING_CARRIER_DESIGNATOR, "AF ")
                        .element(FLIGHT_NUMBER, "0123 ")
                        .element(DATE_OF_FLIGHT, "228")
                        .element(COMPARTMENT_CODE, "C")
                        .element(SEAT_NUMBER, "002F")
                        .element(CHECK_IN_SEQUENCE_NUMBER, "0025 ")
                        .element(PASSENGER_STATUS, "1")
                        .element(CONDITIONALS_SIZE, "13")
                        .element(REPEATED_CONDITIONALS_SIZE, "0D")
                        .element(AIRLINE_NUMERIC_CODE, "057")
                        .element(SERIAL_NUMBER, "1234567890")
                        .build())
                .build());
    }

    @Test
    public void testTwoSegmentsAllFields() throws ParseException
    {
        Assert.assertEquals(
            new Parser().strict().parse(
                "M2DESMARAIS/LUC       EABC123 YULFRAAC 0834 226F001A0025 14C>31" +
                "81WW6225BAC 0085123456003290141234567890 1AC AC 1234567890123  " +
                "  20KLX58ZDEF456 FRAGVALH 3664 227C012C0002 12D290140987654321 " +
                "1AC AC 1234567890123    2PCWQ^164GIWVC5EH7JNT684FVNJ91W2QA4DVN5" +
                "J8K4F0L0GEQ3DF5TGBN8709HKT5D3DW3GBHFCVHMY7J5T6HFR41W2QA4DVN5J8K" +
                "4F0L0GE"),
            IataCode.builder()
                .element(FORMAT_CODE, "M")
                .element(NUMBER_OF_SEGMENTS, "2")
                .element(PASSENGER_NAME, "DESMARAIS/LUC       ")
                .element(ELECTRONIC_TICKET_INDICATOR, "E")
                .element(BEGINNING_OF_VERSION_NUMBER, ">")
                .element(VERSION_NUMBER, "3")
                .element(UNIQUE_CONDITIONALS_SIZE, "18")
                .element(PASSENGER_DESCRIPTION, "1")
                .element(SOURCE_OF_CHECK_IN, "W")
                .element(SOURCE_OF_BOARDING_PASS_ISSUANCE, "W")
                .element(DATE_OF_PASS_ISSUANCE, "6225")
                .element(DOCUMENT_TYPE, "B")
                .element(AIRLINE_DESIGNATOR_OF_ISSUER, "AC ")
                .element(BAGGAGE_TAG_LICENSE_PLATE, "0085123456003")
                // -----------------------------------------------
                // Flight Segment [1]
                // -----------------------------------------------
                .flightSegment(
                    FlightSegment.builder()
                        .element(OPERATING_CARRIER_PNR_CODE, "ABC123 ")
                        .element(FROM_CITY_AIRPORT_CODE, "YUL")
                        .element(TO_CITY_AIRPORT_CODE, "FRA")
                        .element(OPERATING_CARRIER_DESIGNATOR, "AC ")
                        .element(FLIGHT_NUMBER, "0834 ")
                        .element(DATE_OF_FLIGHT, "226")
                        .element(COMPARTMENT_CODE, "F")
                        .element(SEAT_NUMBER, "001A")
                        .element(CHECK_IN_SEQUENCE_NUMBER, "0025 ")
                        .element(PASSENGER_STATUS, "1")
                        .element(CONDITIONALS_SIZE, "4C")
                        .element(REPEATED_CONDITIONALS_SIZE, "29")
                        .element(AIRLINE_NUMERIC_CODE, "014")
                        .element(SERIAL_NUMBER, "1234567890")
                        .element(SELECTEE_INDICATOR, " ")
                        .element(INTERNATIONAL_DOCUMENT_VERIFICATION, "1")
                        .element(MARKETING_CARRIER_DESIGNATOR, "AC ")
                        .element(FREQUENT_FLYER_AIRLINE_DESIGNATOR, "AC ")
                        .element(FREQUENT_FLYER_NUMBER, "1234567890123   ")
                        .element(ID_AD_INDICATOR, " ")
                        .element(FREE_BAGGAGE_ALLOWANCE, "20K")
                        .element(FOR_AIRLINE_USE, "LX58Z")
                        .build())
                // -----------------------------------------------
                // Flight Segment [2]
                // -----------------------------------------------
                .flightSegment(
                    FlightSegment.builder()
                        .element(OPERATING_CARRIER_PNR_CODE, "DEF456 ")
                        .element(FROM_CITY_AIRPORT_CODE, "FRA")
                        .element(TO_CITY_AIRPORT_CODE, "GVA")
                        .element(OPERATING_CARRIER_DESIGNATOR, "LH ")
                        .element(FLIGHT_NUMBER, "3664 ")
                        .element(DATE_OF_FLIGHT, "227")
                        .element(COMPARTMENT_CODE, "C")
                        .element(SEAT_NUMBER, "012C")
                        .element(CHECK_IN_SEQUENCE_NUMBER, "0002 ")
                        .element(PASSENGER_STATUS, "1")
                        .element(CONDITIONALS_SIZE, "2D")
                        .element(REPEATED_CONDITIONALS_SIZE, "29")
                        .element(AIRLINE_NUMERIC_CODE, "014")
                        .element(SERIAL_NUMBER, "0987654321")
                        .element(SELECTEE_INDICATOR, " ")
                        .element(INTERNATIONAL_DOCUMENT_VERIFICATION, "1")
                        .element(MARKETING_CARRIER_DESIGNATOR, "AC ")
                        .element(FREQUENT_FLYER_AIRLINE_DESIGNATOR, "AC ")
                        .element(FREQUENT_FLYER_NUMBER, "1234567890123   ")
                        .element(ID_AD_INDICATOR, " ")
                        .element(FREE_BAGGAGE_ALLOWANCE, "2PC")
                        .element(FOR_AIRLINE_USE, "WQ")
                        .build())
                // -----------------------------------------------
                // Security Data
                // -----------------------------------------------
                .element(BEGINNING_OF_SECURITY_DATA, "^")
                .element(TYPE_OF_SECURITY_DATA, "1")
                .element(LENGTH_OF_SECURITY_DATA, "64")
                .element(SECURITY_DATA, "GIWVC5EH7JNT684FVNJ91W2QA4DVN5J8K4F0L0GEQ3DF5TGBN8709HKT5D3DW3GBHFCVHMY7J5T6HFR41W2QA4DVN5J8K4F0L0GE")
                .build());
    }

    @Test
    public void testTwoSegmentsAllFieldsWithoutSecurity() throws ParseException
    {
        Assert.assertEquals(
            new Parser().strict().parse(
                "M2DESMARAIS/LUC       EABC123 YULFRAAC 0834 226F001A0025 14C>31" +
                "81WW6225BAC 0085123456003290141234567890 1AC AC 1234567890123  " +
                "  20KLX58ZDEF456 FRAGVALH 3664 227C012C0002 12D290140987654321 " +
                "1AC AC 1234567890123    2PCWQ"),
            IataCode.builder()
                .element(FORMAT_CODE, "M")
                .element(NUMBER_OF_SEGMENTS, "2")
                .element(PASSENGER_NAME, "DESMARAIS/LUC       ")
                .element(ELECTRONIC_TICKET_INDICATOR, "E")
                .element(BEGINNING_OF_VERSION_NUMBER, ">")
                .element(VERSION_NUMBER, "3")
                .element(UNIQUE_CONDITIONALS_SIZE, "18")
                .element(PASSENGER_DESCRIPTION, "1")
                .element(SOURCE_OF_CHECK_IN, "W")
                .element(SOURCE_OF_BOARDING_PASS_ISSUANCE, "W")
                .element(DATE_OF_PASS_ISSUANCE, "6225")
                .element(DOCUMENT_TYPE, "B")
                .element(AIRLINE_DESIGNATOR_OF_ISSUER, "AC ")
                .element(BAGGAGE_TAG_LICENSE_PLATE, "0085123456003")
                // -----------------------------------------------
                // Flight Segment [1]
                // -----------------------------------------------
                .flightSegment(
                    FlightSegment.builder()
                        .element(OPERATING_CARRIER_PNR_CODE, "ABC123 ")
                        .element(FROM_CITY_AIRPORT_CODE, "YUL")
                        .element(TO_CITY_AIRPORT_CODE, "FRA")
                        .element(OPERATING_CARRIER_DESIGNATOR, "AC ")
                        .element(FLIGHT_NUMBER, "0834 ")
                        .element(DATE_OF_FLIGHT, "226")
                        .element(COMPARTMENT_CODE, "F")
                        .element(SEAT_NUMBER, "001A")
                        .element(CHECK_IN_SEQUENCE_NUMBER, "0025 ")
                        .element(PASSENGER_STATUS, "1")
                        .element(CONDITIONALS_SIZE, "4C")
                        .element(REPEATED_CONDITIONALS_SIZE, "29")
                        .element(AIRLINE_NUMERIC_CODE, "014")
                        .element(SERIAL_NUMBER, "1234567890")
                        .element(SELECTEE_INDICATOR, " ")
                        .element(INTERNATIONAL_DOCUMENT_VERIFICATION, "1")
                        .element(MARKETING_CARRIER_DESIGNATOR, "AC ")
                        .element(FREQUENT_FLYER_AIRLINE_DESIGNATOR, "AC ")
                        .element(FREQUENT_FLYER_NUMBER, "1234567890123   ")
                        .element(ID_AD_INDICATOR, " ")
                        .element(FREE_BAGGAGE_ALLOWANCE, "20K")
                        .element(FOR_AIRLINE_USE, "LX58Z")
                        .build())
                // -----------------------------------------------
                // Flight Segment [2]
                // -----------------------------------------------
                .flightSegment(
                    FlightSegment.builder()
                        .element(OPERATING_CARRIER_PNR_CODE, "DEF456 ")
                        .element(FROM_CITY_AIRPORT_CODE, "FRA")
                        .element(TO_CITY_AIRPORT_CODE, "GVA")
                        .element(OPERATING_CARRIER_DESIGNATOR, "LH ")
                        .element(FLIGHT_NUMBER, "3664 ")
                        .element(DATE_OF_FLIGHT, "227")
                        .element(COMPARTMENT_CODE, "C")
                        .element(SEAT_NUMBER, "012C")
                        .element(CHECK_IN_SEQUENCE_NUMBER, "0002 ")
                        .element(PASSENGER_STATUS, "1")
                        .element(CONDITIONALS_SIZE, "2D")
                        .element(REPEATED_CONDITIONALS_SIZE, "29")
                        .element(AIRLINE_NUMERIC_CODE, "014")
                        .element(SERIAL_NUMBER, "0987654321")
                        .element(SELECTEE_INDICATOR, " ")
                        .element(INTERNATIONAL_DOCUMENT_VERIFICATION, "1")
                        .element(MARKETING_CARRIER_DESIGNATOR, "AC ")
                        .element(FREQUENT_FLYER_AIRLINE_DESIGNATOR, "AC ")
                        .element(FREQUENT_FLYER_NUMBER, "1234567890123   ")
                        .element(ID_AD_INDICATOR, " ")
                        .element(FREE_BAGGAGE_ALLOWANCE, "2PC")
                        .element(FOR_AIRLINE_USE, "WQ")
                        .build())
                .build());
    }

    @Test
    public void testTwoSegmentsPartiallyPopulated() throws ParseException
    {
        Assert.assertEquals(
            new Parser().strict().parse(
                "M2GRANDMAIRE/MELANIE  EABC123 GVACDGAF 0123 228C002F0025 12F>30" +
                "0290571234567890                         20KDEF456 CDGDTWNW 004" +
                "9 228F001A0002 12B29012098765432101                       2PC^1" +
                "64GIWVC5EH7JNT684FVNJ91W2QA4DVN5J8K4F0L0GEQ3DF5TGBN8709HKT5D3DW" +
                "3GBHFCVHMY7J5T6HFR41W2QA4DVN5J8K4F0L0GE"),
            IataCode.builder()
                .element(FORMAT_CODE, "M")
                .element(NUMBER_OF_SEGMENTS, "2")
                .element(PASSENGER_NAME, "GRANDMAIRE/MELANIE  ")
                .element(ELECTRONIC_TICKET_INDICATOR, "E")
                .element(BEGINNING_OF_VERSION_NUMBER, ">")
                .element(VERSION_NUMBER, "3")
                .element(UNIQUE_CONDITIONALS_SIZE, "00")
                // -----------------------------------------------
                // Flight Segment [1]
                // -----------------------------------------------
                .flightSegment(
                    FlightSegment.builder()
                        .element(OPERATING_CARRIER_PNR_CODE, "ABC123 ")
                        .element(FROM_CITY_AIRPORT_CODE, "GVA")
                        .element(TO_CITY_AIRPORT_CODE, "CDG")
                        .element(OPERATING_CARRIER_DESIGNATOR, "AF ")
                        .element(FLIGHT_NUMBER, "0123 ")
                        .element(DATE_OF_FLIGHT, "228")
                        .element(COMPARTMENT_CODE, "C")
                        .element(SEAT_NUMBER, "002F")
                        .element(CHECK_IN_SEQUENCE_NUMBER, "0025 ")
                        .element(PASSENGER_STATUS, "1")
                        .element(CONDITIONALS_SIZE, "2F")
                        .element(REPEATED_CONDITIONALS_SIZE, "29")
                        .element(AIRLINE_NUMERIC_CODE, "057")
                        .element(SERIAL_NUMBER, "1234567890")
                        .element(SELECTEE_INDICATOR, " ")
                        .element(INTERNATIONAL_DOCUMENT_VERIFICATION, " ")
                        .element(MARKETING_CARRIER_DESIGNATOR, "   ")
                        .element(FREQUENT_FLYER_AIRLINE_DESIGNATOR, "   ")
                        .element(FREQUENT_FLYER_NUMBER, "                ")
                        .element(ID_AD_INDICATOR, " ")
                        .element(FREE_BAGGAGE_ALLOWANCE, "20K")
                        .build())
                // -----------------------------------------------
                // Flight Segment [2]
                // -----------------------------------------------
                .flightSegment(
                    FlightSegment.builder()
                        .element(OPERATING_CARRIER_PNR_CODE, "DEF456 ")
                        .element(FROM_CITY_AIRPORT_CODE, "CDG")
                        .element(TO_CITY_AIRPORT_CODE, "DTW")
                        .element(OPERATING_CARRIER_DESIGNATOR, "NW ")
                        .element(FLIGHT_NUMBER, "0049 ")
                        .element(DATE_OF_FLIGHT, "228")
                        .element(COMPARTMENT_CODE, "F")
                        .element(SEAT_NUMBER, "001A")
                        .element(CHECK_IN_SEQUENCE_NUMBER, "0002 ")
                        .element(PASSENGER_STATUS, "1")
                        .element(CONDITIONALS_SIZE, "2B")
                        .element(REPEATED_CONDITIONALS_SIZE, "29")
                        .element(AIRLINE_NUMERIC_CODE, "012")
                        .element(SERIAL_NUMBER, "0987654321")
                        .element(SELECTEE_INDICATOR, "0")
                        .element(INTERNATIONAL_DOCUMENT_VERIFICATION, "1")
                        .element(MARKETING_CARRIER_DESIGNATOR, "   ")
                        .element(FREQUENT_FLYER_AIRLINE_DESIGNATOR, "   ")
                        .element(FREQUENT_FLYER_NUMBER, "                ")
                        .element(ID_AD_INDICATOR, " ")
                        .element(FREE_BAGGAGE_ALLOWANCE, "2PC")
                        .build())
                // -----------------------------------------------
                // Security Data
                // -----------------------------------------------
                .element(BEGINNING_OF_SECURITY_DATA, "^")
                .element(TYPE_OF_SECURITY_DATA, "1")
                .element(LENGTH_OF_SECURITY_DATA, "64")
                .element(SECURITY_DATA, "GIWVC5EH7JNT684FVNJ91W2QA4DVN5J8K4F0L0GEQ3DF5TGBN8709HKT5D3DW3GBHFCVHMY7J5T6HFR41W2QA4DVN5J8K4F0L0GE")
                .build());
    }

    @Test
    public void testTwoSegmentsPartiallyPopulatedWithoutSecurity() throws ParseException
    {
        Assert.assertEquals(
            new Parser().strict().parse(
                "M2GRANDMAIRE/MELANIE  EABC123 GVACDGAF 0123 228C002F0025 12F>30" +
                "0290571234567890                         20KDEF456 CDGDTWNW 004" +
                "9 228F001A0002 12B29012098765432101                       2PC"),
            IataCode.builder()
                .element(FORMAT_CODE, "M")
                .element(NUMBER_OF_SEGMENTS, "2")
                .element(PASSENGER_NAME, "GRANDMAIRE/MELANIE  ")
                .element(ELECTRONIC_TICKET_INDICATOR, "E")
                .element(BEGINNING_OF_VERSION_NUMBER, ">")
                .element(VERSION_NUMBER, "3")
                .element(UNIQUE_CONDITIONALS_SIZE, "00")
                // -----------------------------------------------
                // Flight Segment [1]
                // -----------------------------------------------
                .flightSegment(
                    FlightSegment.builder()
                        .element(OPERATING_CARRIER_PNR_CODE, "ABC123 ")
                        .element(FROM_CITY_AIRPORT_CODE, "GVA")
                        .element(TO_CITY_AIRPORT_CODE, "CDG")
                        .element(OPERATING_CARRIER_DESIGNATOR, "AF ")
                        .element(FLIGHT_NUMBER, "0123 ")
                        .element(DATE_OF_FLIGHT, "228")
                        .element(COMPARTMENT_CODE, "C")
                        .element(SEAT_NUMBER, "002F")
                        .element(CHECK_IN_SEQUENCE_NUMBER, "0025 ")
                        .element(PASSENGER_STATUS, "1")
                        .element(CONDITIONALS_SIZE, "2F")
                        .element(REPEATED_CONDITIONALS_SIZE, "29")
                        .element(AIRLINE_NUMERIC_CODE, "057")
                        .element(SERIAL_NUMBER, "1234567890")
                        .element(SELECTEE_INDICATOR, " ")
                        .element(INTERNATIONAL_DOCUMENT_VERIFICATION, " ")
                        .element(MARKETING_CARRIER_DESIGNATOR, "   ")
                        .element(FREQUENT_FLYER_AIRLINE_DESIGNATOR, "   ")
                        .element(FREQUENT_FLYER_NUMBER, "                ")
                        .element(ID_AD_INDICATOR, " ")
                        .element(FREE_BAGGAGE_ALLOWANCE, "20K")
                        .build())
                // -----------------------------------------------
                // Flight Segment [2]
                // -----------------------------------------------
                .flightSegment(
                    FlightSegment.builder()
                        .element(OPERATING_CARRIER_PNR_CODE, "DEF456 ")
                        .element(FROM_CITY_AIRPORT_CODE, "CDG")
                        .element(TO_CITY_AIRPORT_CODE, "DTW")
                        .element(OPERATING_CARRIER_DESIGNATOR, "NW ")
                        .element(FLIGHT_NUMBER, "0049 ")
                        .element(DATE_OF_FLIGHT, "228")
                        .element(COMPARTMENT_CODE, "F")
                        .element(SEAT_NUMBER, "001A")
                        .element(CHECK_IN_SEQUENCE_NUMBER, "0002 ")
                        .element(PASSENGER_STATUS, "1")
                        .element(CONDITIONALS_SIZE, "2B")
                        .element(REPEATED_CONDITIONALS_SIZE, "29")
                        .element(AIRLINE_NUMERIC_CODE, "012")
                        .element(SERIAL_NUMBER, "0987654321")
                        .element(SELECTEE_INDICATOR, "0")
                        .element(INTERNATIONAL_DOCUMENT_VERIFICATION, "1")
                        .element(MARKETING_CARRIER_DESIGNATOR, "   ")
                        .element(FREQUENT_FLYER_AIRLINE_DESIGNATOR, "   ")
                        .element(FREQUENT_FLYER_NUMBER, "                ")
                        .element(ID_AD_INDICATOR, " ")
                        .element(FREE_BAGGAGE_ALLOWANCE, "2PC")
                        .build())
                .build());
    }

    @Test
    public void testEqualsContract()
    {
        EqualsVerifier.forClass(IataCode.class).verify();
        EqualsVerifier.forClass(FlightSegment.class).verify();
        EqualsVerifier.forClass(SecurityData.class).verify();
    }
}
