package com.univocity.articles.jmh;

import java.io.Reader;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.infra.Blackhole;

import com.generationjava.io.CsvReader;
import com.univocity.articles.jmh.params.FileToProcess;

public class GenJavaParser {

	@Benchmark
	public void parseFile(final FileToProcess fileToProcess,
			final Blackhole blackhole) throws Exception {
		Reader r = fileToProcess.getReader();
		try {
			CsvReader reader = new CsvReader(r);
			String[] data;
			while ((data = reader.readLine()) != null) {
				blackhole.consume(data);
			}
		} finally {
			r.close();
		}
	}
}
