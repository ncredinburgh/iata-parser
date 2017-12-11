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

public enum InternationalDocumentVerification
{
    NA           (" ", "Not applicable to flight"),
    NOT_REQUIRED ("0", "Travel document verification not required"),
    REQUIRED     ("1", "Travel document verification required"),
    PERFORMED    ("2", "Travel document verification performed"),
    UNKNOWN      ("", "<unknown>");

    private final String value;
    private final String description;

    InternationalDocumentVerification(String value, String description)
    {
        this.value = value;
        this.description = description;
    }

    public static InternationalDocumentVerification parse(CharSequence s)
    {
        for (InternationalDocumentVerification verification : values())
        {
            if (verification.getValue().equals(s))
            {
                return verification;
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
