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

import static com.ncredinburgh.iata.specs.Element.FORMAT_CODE;
import static com.ncredinburgh.iata.specs.Element.SECURITY_DATA;
import static java.lang.String.format;
import static org.junit.Assert.assertTrue;

import java.nio.file.Files;

import org.junit.Test;

import com.ncredinburgh.iata.specs.Element;

public class SpecificationFormatterTest
{
    // Match output ignoring whitespace and specific values of occurrence & description
    private static final String SINGLE_LINE_REGEX = "%s\\s*\\|\\s*\\d{1,3}\\s*\\|\\s*.\\s*\\|\\s*'?%s'?\\s*\\|.*%n";
    private static final String SPACER_LINE_REGEX = "\\s*\\|\\s*\\|\\s*\\|\\s*%s\\s*\\|.*%n";

    @Test
    public void shouldMatchSingleLineRegexWhenValueFitsOnSingleLine()
    {
        // Element & value
        Element element = FORMAT_CODE;
        String value = "S";

        // Format line
        CharSequence line = formatter().formatElement(element, value);

        // Test line against regex
        String regex = format(SINGLE_LINE_REGEX, element.getDescription(), value);
        assertTrue(errorMessage(line, regex), line.toString().matches(regex));
    }

    @Test
    public void shouldMatchMultiLineRegexWhenValueFitsOnMultipleLines()
    {
        // Element & value
        Element e = SECURITY_DATA;
        String value = "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"; // Should span two lines

        // Format line
        CharSequence line = formatter().formatElement(e, value);

        // Test line against regex
        String regex = format(SINGLE_LINE_REGEX + SPACER_LINE_REGEX, e.getDescription(), "X+", "X+");
        assertTrue(errorMessage(line, regex), line.toString().matches(regex));
    }

    private static String errorMessage(CharSequence line, String regex)
    {
        return format("%nLine: '%s'%nRegex: '%s'%nExpected: line to match regex, Actual: no match%n", line, regex);
    }

    private SpecificationFormatter formatter()
    {
        return new SpecificationFormatter();
    }
}
