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

public enum IDADIndicator
{
    IDN1       ("0", "IDN1 positive space"),
    IDN2       ("1", "IDN2 Space available"),
    IDB1       ("2", "IDB1 positive space"),
    IDB2       ("3", "IDB2 Space available"),
    AD         ("4", "AD"),
    DG         ("5", "DG"),
    DM         ("6", "DM"),
    GE         ("7", "GE"),
    IG         ("8", "IG"),
    RG         ("9", "RG"),
    UD         ("A", "UD"),
    ID         ("B", "ID - Industry discount not followed any classification"),
    IDFS1      ("C", "IDFS1"),
    IDFS2      ("D", "IDFS2"),
    IDR1       ("E", "IDR1"),
    IDR2       ("F", "IDR2"),
    RESERVED_G ("G", "Future industry use"),
    RESERVED_H ("H", "Future industry use"),
    RESERVED_I ("I", "Future industry use"),
    RESERVED_J ("J", "Future industry use"),
    RESERVED_K ("K", "Future industry use"),
    RESERVED_L ("L", "Future industry use"),
    RESERVED_M ("M", "Future industry use"),
    RESERVED_N ("N", "Future industry use"),
    RESERVED_O ("O", "Future industry use"),
    RESERVED_P ("P", "Future industry use"),
    RESERVED_Q ("Q", "Future industry use"),
    RESERVED_R ("R", "Future industry use"),
    RESERVED_S ("S", "Future industry use"),
    RESERVED_T ("T", "Future industry use"),
    RESERVED_U ("U", "Future industry use"),
    RESERVED_V ("V", "Future industry use"),
    RESERVED_W ("W", "Future industry use"),
    RESERVED_X ("X", "Future industry use"),
    RESERVED_Y ("Y", "Future industry use"),
    RESERVED_Z ("Z", "Future industry use"),
    UNKNOWN    ("", "<unknown>");

    private final String value;
    private final String description;

    IDADIndicator(String value, String description)
    {
        this.value = value;
        this.description = description;
    }

    public static IDADIndicator parse(CharSequence s)
    {
        for (IDADIndicator indicator : values())
        {
            if (indicator.getValue().equals(s))
            {
                return indicator;
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
