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

public enum PassIssuanceSource
{
    WEB                    ("W", "Web Printed"),
    AIRPORT_KIOSK_PRINTED  ("K", "Airport Kiosk Printed"),
    TRANSFER_KIOSK_PRINTED ("X", "Transfer Kiosk Printed"),
    OFF_SITE_KIOSK_PRINTED ("R", "Remote or Off Site Kiosk Printed"),
    MOBILE_DEVICE_PRINTED  ("M", "Mobile Device Printed"),
    AIRPORT_AGENT_PRINTED  ("O", "Airport Agent Printed"),
    TOWN_AGENT_PRINTED     ("T", "Town Agent Printed"),
    THIRD_PARTY_PRINTED    ("V", "Third Party Vendor Printed"),
    UNABLE_TO_SUPPORT      (" ", "Unable to support"),
    UNKNOWN                ("", "<unknown>");

    private final String value;
    private final String description;

    PassIssuanceSource(String value, String description)
    {
        this.value = value;
        this.description = description;
    }

    public static PassIssuanceSource parse(CharSequence s)
    {
        for (PassIssuanceSource source : values())
        {
            if (source.getValue().equals(s))
            {
                return source;
            }
        }
        return UNKNOWN;
    }

    public String getDescription()
    {
        return description;
    }

    public String getValue()
    {
        return value;
    }
}
