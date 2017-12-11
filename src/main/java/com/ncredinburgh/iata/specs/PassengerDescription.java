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

public enum PassengerDescription
{
    ADULT                        ("0", "adult"),
    MALE                         ("1", "Male"),
    FEMALE                       ("2", "Female"),
    CHILD                        ("3", "child"),
    INFANT                       ("4", "infant"),
    CABIN_BAGGAGE                ("5", "no passenger (cabin baggage)"),
    ADULT_TRAVELLING_WITH_INFANT ("6", "adult travelling with infant"),
    UNACCOMPANIED_MINOR          ("7", "unaccompanied minor"),
    RESERVED_8                   ("8", "Future industry use"),
    RESERVED_9                   ("9", "Future industry use"),
    RESERVED_A                   ("A", "Future industry use"),
    RESERVED_B                   ("B", "Future industry use"),
    RESERVED_C                   ("C", "Future industry use"),
    RESERVED_D                   ("D", "Future industry use"),
    RESERVED_E                   ("E", "Future industry use"),
    RESERVED_F                   ("F", "Future industry use"),
    RESERVED_G                   ("G", "Future industry use"),
    RESERVED_H                   ("H", "Future industry use"),
    RESERVED_I                   ("I", "Future industry use"),
    RESERVED_J                   ("J", "Future industry use"),
    RESERVED_K                   ("K", "Future industry use"),
    RESERVED_L                   ("L", "Future industry use"),
    RESERVED_M                   ("M", "Future industry use"),
    RESERVED_N                   ("N", "Future industry use"),
    RESERVED_O                   ("O", "Future industry use"),
    RESERVED_P                   ("P", "Future industry use"),
    RESERVED_Q                   ("Q", "Future industry use"),
    RESERVED_R                   ("R", "Future industry use"),
    RESERVED_S                   ("S", "Future industry use"),
    RESERVED_T                   ("T", "Future industry use"),
    RESERVED_U                   ("U", "Future industry use"),
    RESERVED_V                   ("V", "Future industry use"),
    RESERVED_W                   ("W", "Future industry use"),
    RESERVED_X                   ("X", "Future industry use"),
    RESERVED_Y                   ("Y", "Future industry use"),
    RESERVED_Z                   ("Z", "Future industry use"),
    UNKNOWN                      ("", "<unknown>");

    private final String value;
    private final String description;

    PassengerDescription(String value, String description)
    {
        this.value = value;
        this.description = description;
    }

    public String getDescription()
    {
        return description;
    }

    public static PassengerDescription parse(CharSequence s)
    {
        for(PassengerDescription passenger : values())
        {
            if(passenger.getValue().equals(s))
            {
                return passenger;
            }
        }
        return UNKNOWN;
    }

    public String getValue()
    {
        return value;
    }
}
