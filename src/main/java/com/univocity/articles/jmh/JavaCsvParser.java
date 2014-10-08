package com.univocity.articles.jmh;

import java.io.Reader;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.infra.Blackhole;

import com.csvreader.CsvReader;
import com.univocity.articles.jmh.params.FileToProcess;

public class JavaCsvParser {

	@Benchmark
	public void parseFile(final FileToProcess fileToProcess,
			final Blackhole blackhole) throws Exception {
		Reader r = fileToProcess.getReader();
		try {
			CsvReader reader = new CsvReader(r);
			while (reader.readRecord()) {
				blackhole.consume(reader.getValues());
			}
		} finally {
			r.close();
		}
	}

}
