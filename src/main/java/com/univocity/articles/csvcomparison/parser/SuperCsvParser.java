/** *****************************************************************************
 * Copyright 2014 uniVocity Software Pty Ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ***************************************************************************** */
package com.univocity.articles.csvcomparison.parser;

import java.io.*;
import java.util.*;

import org.supercsv.io.*;
import org.supercsv.prefs.*;

class SuperCsvParser extends AbstractParser {

    protected SuperCsvParser() {
        super("SuperCSV");
    }

    @Override
    public void processRows(final Reader input) throws Exception {
        ICsvListReader listReader = new CsvListReader(input, CsvPreference.STANDARD_PREFERENCE);
        try {
            listReader.getHeader(true);
            while (process(listReader.read()));

        }
        finally {
            if (listReader != null) {
                listReader.close();
            }
        }
    }

    @Override
    public List<String[]> parseRows(final Reader input) throws Exception {
        List<String[]> rows = new ArrayList<String[]>();

        ICsvListReader listReader = new CsvListReader(input, CsvPreference.STANDARD_PREFERENCE);
        try {
            List<String> row = null;
            while ((row = listReader.read()) != null) {
                rows.add(row.toArray(new String[0]));
            }

        }
        finally {
            if (listReader != null) {
                listReader.close();
            }
        }

        return rows;
    }

}
