package com.univocity.articles.jmh;

import com.univocity.articles.jmh.params.FileToProcess;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.simpleflatmapper.csv.CsvParser;
import org.simpleflatmapper.util.CheckedConsumer;

import java.io.Reader;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Fork(1)
@Warmup(iterations=5,batchSize=1)
@Measurement(iterations=5,batchSize=1)
public class SimpleFlatMapperParser {

	
	@Setup
	public void init() {

	}

	@Benchmark
	public void parseFile(final FileToProcess fileToProcess,
			final Blackhole blackhole) throws Exception {
		Reader r = fileToProcess.getReader();
		try {
			CsvParser.reader(r).read(new CheckedConsumer<String[]>() {
				@Override
				public void accept(String[] strings) throws Exception {
					blackhole.consume(strings);
				}
			});
		} finally {
			r.close();
		}
	}
}
