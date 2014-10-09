package com.univocity.articles.jmh;

import java.util.List;
import java.util.concurrent.TimeUnit;

import net.quux00.simplecsv.CsvReader;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;

import com.univocity.articles.jmh.params.FileToProcess;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Fork(1)
@Warmup(iterations=5,batchSize=1)
@Measurement(iterations=5,batchSize=1)
public class SimpleCsvParser {

	@Benchmark
	public void parseFile(final FileToProcess fileToProcess, final Blackhole blackhole) throws Exception {
		CsvReader reader = new CsvReader(fileToProcess.getReader());
		try {
			List<String> data;
			while ((data = reader.readNext()) != null) {
				blackhole.consume(data);
			}
		} finally {
			reader.close();
		}
	}


}
