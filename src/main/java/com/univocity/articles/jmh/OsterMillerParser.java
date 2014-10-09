package com.univocity.articles.jmh;

import java.io.Reader;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;

import com.Ostermiller.util.CSVParse;
import com.Ostermiller.util.ExcelCSVParser;
import com.univocity.articles.jmh.params.FileToProcess;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Fork(1)
@Warmup(iterations=5,batchSize=1)
@Measurement(iterations=5,batchSize=1)
public class OsterMillerParser {

	@Benchmark
	public void parseFile(final FileToProcess fileToProcess,
			final Blackhole blackhole) throws Exception {
		Reader r = fileToProcess.getReader();
		try {
			CSVParse csvParser = new ExcelCSVParser(r);
			String[] data;
			while ((data = csvParser.getLine()) != null) {
				blackhole.consume(data);
			}
		} finally {
			r.close();
		}
	}
}
