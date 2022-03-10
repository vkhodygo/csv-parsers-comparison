/*******************************************************************************
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
 ******************************************************************************/
package com.univocity.articles.csvcomparison.parser;

import java.io.*;
import java.util.*;

import com.googlecode.jcsv.reader.*;
import com.googlecode.jcsv.reader.internal.*;

class JCsvParser extends AbstractParser {

	protected JCsvParser() {
		super("JCSV Parser");
	}

	@Override
	public void processRows(File input) throws Exception {

		CSVReader<String[]> reader = CSVReaderBuilder.newDefaultReader(toReader(input));

		while (process(reader.readNext()));
	}

	@Override
	public List<String[]> parseRows(File input) throws Exception {
		List<String[]> rows = new ArrayList<String[]>();
		CSVReader<String[]> reader = CSVReaderBuilder.newDefaultReader(toReader(input));
		String[] row;

		while ((row = reader.readNext()) != null) {
			rows.add(row);
		}
		return rows;
	}

}
