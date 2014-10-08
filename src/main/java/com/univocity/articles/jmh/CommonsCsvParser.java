package com.univocity.articles.jmh;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.infra.Blackhole;

import com.univocity.articles.jmh.params.FileToProcess;

public class CommonsCsvParser  {


	@Benchmark
	public void parseFile(final FileToProcess fileToProcess, final Blackhole blackhole) throws Exception {
		CSVParser parser = CSVParser.parse(fileToProcess.file, fileToProcess.charset, CSVFormat.RFC4180);

		for (CSVRecord record : parser) {
			blackhole.consume(record);
		}
	}


}
