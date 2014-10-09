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

import java.io.Reader;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;

import com.univocity.articles.jmh.params.FileToProcess;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Fork(1)
@Warmup(iterations=5,batchSize=1)
@Measurement(iterations=5,batchSize=1)
public class UnivocityParser {

	CsvParser parser;

	@Setup
	public void init() {
		CsvParserSettings settings = new CsvParserSettings();
		parser = new CsvParser(settings);
	}

	@Benchmark
	public void parseFile(final FileToProcess fileToProcess,
			final Blackhole blackhole) throws Exception {
		Reader reader = fileToProcess.getReader();
		try {
			parser.beginParsing(reader);

			String[] data;
			while ((data = parser.parseNext()) != null) {
				blackhole.consume(data);
			}
		} finally {
			reader.close();
		}
	}
}
