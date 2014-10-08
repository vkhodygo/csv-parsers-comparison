package com.univocity.articles.jmh;

import java.io.Reader;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.infra.Blackhole;

import com.Ostermiller.util.CSVParse;
import com.Ostermiller.util.ExcelCSVParser;
import com.univocity.articles.jmh.params.FileToProcess;

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
