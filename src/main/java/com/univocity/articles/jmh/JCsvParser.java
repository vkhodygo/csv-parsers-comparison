package com.univocity.articles.jmh;

import java.io.Reader;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.infra.Blackhole;

import com.googlecode.jcsv.reader.CSVReader;
import com.googlecode.jcsv.reader.internal.CSVReaderBuilder;
import com.univocity.articles.jmh.params.FileToProcess;

public class JCsvParser {

	@Benchmark
	public void parseFile(final FileToProcess fileToProcess,
			final Blackhole blackhole) throws Exception {
		Reader r = fileToProcess.getReader();
		try {
			CSVReader<String[]> reader = CSVReaderBuilder.newDefaultReader(r);
			String[] data;
			while ((data = reader.readNext()) != null) {
				blackhole.consume(data);
			}
		} finally {
			r.close();
		}
	}
}
