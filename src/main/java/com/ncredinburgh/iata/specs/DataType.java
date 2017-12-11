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

import java.util.regex.Pattern;

public enum DataType
{
    f                     ("[\\u0020-\\u007E]+"),
    N                     ("[0-9]+"),
    a                     ("[A-Z]+"),
    NNNa                  ("[0-9]{3}[A-Z]"),
    NNNN_a                ("[0-9]{4}[A-Z\\s]?"),
    NNNN_f                ("[0-9]{4}[\\u0020-\\u007E]?"),
    ANY                   (".*"),
    GREATER_THAN          (">"),
    CARET_OR_GREATER_THAN ("\\^|>"); // Old version of IATA spec used '>' to denote the start of security data

    private final Pattern typeRegex;

    DataType(String typeRegex)
    {
        this.typeRegex = Pattern.compile(typeRegex);
    }

    public boolean isValid(CharSequence s)
    {
        return typeRegex.matcher(s).matches();
    }

    @Override
    public String toString()
    {
        return String.format("%s('%s')", name(), typeRegex);
    }
}
