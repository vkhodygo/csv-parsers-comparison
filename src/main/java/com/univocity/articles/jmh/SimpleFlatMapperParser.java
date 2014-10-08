package com.univocity.articles.jmh;

import java.io.Reader;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;
import org.sfm.csv.parser.CsvParser;
import org.sfm.utils.RowHandler;

import com.univocity.articles.jmh.params.FileToProcess;

@State(Scope.Benchmark)
public class SimpleFlatMapperParser {
	CsvParser csvParser;

	@Setup
	public void init() {
		csvParser = new CsvParser();
	}

	@Benchmark
	public void parseFile(final FileToProcess fileToProcess,
			final Blackhole blackhole) throws Exception {
		Reader r = fileToProcess.getReader();
		try {
			csvParser.readRows(r, new RowHandler<String[]>() {
				@Override
				public void handle(String[] t) throws Exception {
					blackhole.consume(t);
				}
			});
		} finally {
			r.close();
		}
	}
}
