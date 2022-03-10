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

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvParser;
import com.univocity.articles.jmh.params.FileToProcess;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Fork(1)
@Warmup(iterations=5,batchSize=1)
@Measurement(iterations=5,batchSize=1)
@State(Scope.Benchmark)
public class JacksonParser {
	CsvMapper csvMapper;

	@Setup
	public void init() {
		csvMapper = new CsvMapper();
		csvMapper.enable(CsvParser.Feature.WRAP_AS_ARRAY);
	}

	@Benchmark
	public void parseFile(final FileToProcess fileToProcess,
			final Blackhole blackhole) throws Exception {

		Reader reader = fileToProcess.getReader();
		try {
			MappingIterator<String[]> iterator = csvMapper.reader(
					String[].class).readValues(reader);

			while (iterator.hasNext()) {
				blackhole.consume(iterator.next());
			}
		} finally {
			reader.close();
		}
	}
}
