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

package com.ncredinburgh.iata.print;

import static java.lang.String.format;

import com.ncredinburgh.iata.specs.Element;

public final class SpecificationFormatter implements Formatter
{
    private static final int MAX_DESCRIPTION_LENGTH = getMaxDescriptionLength();
    private static final int MAX_WIDTH = 22;
    private static final String LINE_FORMAT = "%-" + MAX_DESCRIPTION_LENGTH + "s | %-3s | %s | %-" + MAX_WIDTH + "s | %s%n";

    @Override
    public CharSequence formatElement(Element e, CharSequence s)
    {
        StringBuilder builder = new StringBuilder();

        // Only quote lines less or equal than max
        boolean shouldQuoteValue = s.length() + 2 <= MAX_WIDTH;
        for (int i = 0; i < s.length(); i+= MAX_WIDTH)
        {
            CharSequence rawValue = s.subSequence(i, Math.min(s.length(), i + MAX_WIDTH));
            CharSequence value = shouldQuoteValue ? "'" + rawValue + "'" : rawValue;
            if (i == 0)
            {
                // Main content line
                String valueDescription = e.getValueDescription(s);
                builder.append(String.format(LINE_FORMAT, e.getDescription(), s.length(), e.getOccurrence().name(),
                    value, valueDescription != null ? valueDescription : ""));
            }
            else
            {
                // "Spacer" line
                builder.append(String.format(LINE_FORMAT, "", "", " ", value, ""));
            }
        }
        return builder;
    }

    private static int getMaxDescriptionLength()
    {
        int max = 0;
        for (Element e : Element.values())
        {
            if (e.getDescription().length() > max)
            {
                max = e.getDescription().length();
            }
        }
        return max;
    }
}
