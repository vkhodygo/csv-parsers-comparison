package com.univocity.articles.jmh;

import java.io.Reader;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;
import org.sfm.csv.parser.CsvParser;
import org.sfm.utils.RowHandler;

import com.univocity.articles.jmh.params.BufferSize;
import com.univocity.articles.jmh.params.FileToProcess;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Fork(1)
@Warmup(iterations=5,batchSize=1)
@Measurement(iterations=5,batchSize=1)
public class SimpleFlatMapperParser {
	CsvParser csvParser;

	
	@Param(value={"_8K"})
	public BufferSize bufferSize;
	
	@Setup
	public void init() {
		csvParser = new CsvParser(bufferSize.size);
	}

	@Benchmark
	public void parseFile(final FileToProcess fileToProcess,
			final Blackhole blackhole) throws Exception {
		Reader r = fileToProcess.getReader();
		try {
			csvParser.readRows(r, new RowHandler<String[]>() {
				@Override
				public void handle(String[] t) throws Exception {
					blackhole.consume(t);
				}
			});
		} finally {
			r.close();
		}
	}
}
