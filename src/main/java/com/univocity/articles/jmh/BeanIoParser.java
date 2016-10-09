package com.univocity.articles.jmh;

import com.univocity.articles.jmh.params.FileToProcess;
import org.beanio.stream.csv.CsvReader;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.concurrent.TimeUnit;


@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Fork(1)
@Warmup(iterations=5,batchSize=1)
@Measurement(iterations=5,batchSize=1)
public class BeanIoParser {
	
	//@Benchmark
	public void parseFile(final FileToProcess fileToProcess, final Blackhole blackhole) throws Exception {
		CsvReader reader = new CsvReader(fileToProcess.getReader());
		try {
			String[] data;
			while((data = reader.read())!= null)  {
				blackhole.consume(data);
			}
		} finally {
			reader.close();
		}
	}
}
