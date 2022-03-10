package com.univocity.articles.jmh;

import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;

import com.univocity.articles.jmh.params.FileToProcess;

import au.com.bytecode.opencsv.CSVReader;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Fork(1)
@Warmup(iterations=5,batchSize=1)
@Measurement(iterations=5,batchSize=1)
public class OpenCsvParser {
	
	@Benchmark
	public void parseFile(final FileToProcess fileToProcess, final Blackhole blackhole) throws Exception {
		CSVReader reader = new CSVReader(fileToProcess.getReader());
		try {
			String[] data;
			while((data = reader.readNext())!= null)  {
				blackhole.consume(data);
			}
		} finally {
			reader.close();
		}
	}
}
