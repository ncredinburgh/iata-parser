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

public enum PassengerStatus
{
    TICKET_ISSUANCE_PASSENGER_NOT_CHECKED_IN ("0", "ticket issuance/passenger not checked in"),
    TICKET_ISSUANCE_PASSENGER_CHECKED_IN     ("1", "ticket issuance/passenger checked in"),
    BAGGAGE_CHECKED_PASSENGER_NOT_CHECKED_IN ("2", "baggage checked/passenger not checked in"),
    BAGGAGE_CHECKED_PASSENGER_CHECKED_IN     ("3", "baggage checked/passenger checked in"),
    PASSENGER_PASSED_SECURITY_CHECK          ("4", "Passenger passed security check"),
    PASSENGER_PASSED_EXIT_GATE               ("5", "Passenger passed security gate exit (coupon used)"),
    TRANSIT                                  ("6", "Transit"),
    STANDBY                                  ("7", "Standby"),
    BOARDING_DATA_REVALIDATION_DONE          ("8", "Boarding data revalidation done"),
    BOARDING_LINE_USED                       ("9", "Original boarding line used at time of ticket issuance"),
    UP_OR_DOWN_GRADING_REQUIRED              ("A", "Up- or down-grading required at close out"),
    RESERVED_B                               ("B", "Future industry use"),
    RESERVED_C                               ("C", "Future industry use"),
    RESERVED_D                               ("D", "Future industry use"),
    RESERVED_E                               ("E", "Future industry use"),
    RESERVED_F                               ("F", "Future industry use"),
    RESERVED_G                               ("G", "Future industry use"),
    RESERVED_H                               ("H", "Future industry use"),
    RESERVED_I                               ("I", "Future industry use"),
    RESERVED_J                               ("J", "Future industry use"),
    RESERVED_K                               ("K", "Future industry use"),
    RESERVED_L                               ("L", "Future industry use"),
    RESERVED_M                               ("M", "Future industry use"),
    RESERVED_N                               ("N", "Future industry use"),
    RESERVED_O                               ("O", "Future industry use"),
    RESERVED_P                               ("P", "Future industry use"),
    RESERVED_Q                               ("Q", "Future industry use"),
    RESERVED_R                               ("R", "Future industry use"),
    RESERVED_S                               ("S", "Future industry use"),
    RESERVED_T                               ("T", "Future industry use"),
    RESERVED_U                               ("U", "Future industry use"),
    RESERVED_V                               ("V", "Future industry use"),
    RESERVED_W                               ("W", "Future industry use"),
    RESERVED_X                               ("X", "Future industry use"),
    RESERVED_Y                               ("Y", "Future industry use"),
    RESERVED_Z                               ("Z", "Future industry use"),
    UNKNOWN                                  ("", "<unknown>");

    private final String value;
    private final String description;

    PassengerStatus(String value, String description)
    {
        this.value = value;
        this.description = description;
    }

    public static PassengerStatus parse(CharSequence s)
    {
        for (PassengerStatus status : values())
        {
            if (status.getValue().equals(s))
            {
                return status;
            }
        }
        return UNKNOWN;
    }

    public String getValue()
    {
        return value;
    }

    public String getDescription()
    {
        return description;
    }
}
