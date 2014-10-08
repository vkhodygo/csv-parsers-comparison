package com.univocity.articles.jmh;

import java.util.List;

import net.quux00.simplecsv.CsvReader;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.infra.Blackhole;

import com.univocity.articles.jmh.params.FileToProcess;

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
