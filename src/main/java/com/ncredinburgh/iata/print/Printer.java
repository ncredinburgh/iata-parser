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

import java.io.IOException;
import java.io.Writer;

import com.ncredinburgh.iata.model.Visitor;
import com.ncredinburgh.iata.specs.Element;

public final class Printer implements Visitor<IOException>
{
    private final Formatter formatter;
    private final Writer writer;

    public Printer(Formatter formatter, Writer writer)
    {
        this.formatter = formatter;
        this.writer = writer;
    }

    @Override
    public void onElement(Element element, CharSequence value) throws IOException
    {
        writer.write(formatter.formatElement(element, value).toString());
        writer.flush();
    }
}
