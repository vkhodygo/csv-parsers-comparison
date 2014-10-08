package com.univocity.articles.jmh;

import java.io.Reader;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.infra.Blackhole;

import br.com.objectos.comuns.io.Line;
import br.com.objectos.comuns.io.ParsedLines;
import br.com.objectos.comuns.io.csv.CsvFile;

import com.univocity.articles.jmh.params.FileToProcess;

public class WayIoParser {

	@Benchmark
	public void parseFile(final FileToProcess fileToProcess,
			final Blackhole blackhole) throws Exception {
		Reader r = fileToProcess.getReader();
		try {
			CsvFile reader = CsvFile.parseReader(r);
			ParsedLines lines = reader.getLines();
			for (Line line : lines) {
				blackhole.consume(line);
			}
		} finally {
			r.close();
		}

	}

}
