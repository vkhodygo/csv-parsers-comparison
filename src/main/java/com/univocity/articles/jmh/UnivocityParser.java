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
package com.univocity.articles.jmh;

import com.univocity.articles.jmh.params.FileToProcess;
import com.univocity.parsers.common.ParsingContext;
import com.univocity.parsers.common.processor.AbstractRowProcessor;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.io.Reader;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Fork(1)
@Warmup(iterations=5,batchSize=1)
@Measurement(iterations=5,batchSize=1)
public class UnivocityParser {

	
	@Setup
	public void init() {

	}

	@Benchmark
	public void parseFile(final FileToProcess fileToProcess,
			final Blackhole blackhole) throws Exception {
		
		CsvParserSettings settings = new CsvParserSettings();
		//turning off features enabled by default
		settings.setIgnoreLeadingWhitespaces(false);
		settings.setIgnoreTrailingWhitespaces(false);
		settings.setSkipEmptyLines(false);
		settings.setColumnReorderingEnabled(false);

		settings.setProcessor(new AbstractRowProcessor() {
			@Override
			public void rowProcessed(String[] row, ParsingContext context) {
				blackhole.consume(row);
			}
		});

		CsvParser parser = new CsvParser(settings);

		Reader reader = fileToProcess.getReader();
		try {
			parser.parse(reader);
		} finally {
			reader.close();
		}
	}

	@Benchmark
	public void parseFileInSameThread(final FileToProcess fileToProcess,
						  final Blackhole blackhole) throws Exception {

		CsvParserSettings settings = new CsvParserSettings();
		settings.setIgnoreLeadingWhitespaces(false);
		settings.setIgnoreTrailingWhitespaces(false);
		settings.setSkipEmptyLines(false);
		settings.setColumnReorderingEnabled(false);
		settings.setCommentCollectionEnabled(true);
		settings.setReadInputOnSeparateThread(false);
		settings.setInputBufferSize(16 * 1024);

		settings.setProcessor(new AbstractRowProcessor() {
			@Override
			public void rowProcessed(String[] row, ParsingContext context) {
				blackhole.consume(row);
			}
		});
		CsvParser parser = new CsvParser(settings);

		Reader reader = fileToProcess.getReader();
		try {
			parser.parse(reader);
		} finally {
			reader.close();
		}
	}
}
