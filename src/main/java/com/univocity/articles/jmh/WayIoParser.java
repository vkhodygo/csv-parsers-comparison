package com.univocity.articles.jmh;

import java.io.Reader;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;

import br.com.objectos.comuns.io.Line;
import br.com.objectos.comuns.io.ParsedLines;
import br.com.objectos.comuns.io.csv.CsvFile;

import com.univocity.articles.jmh.params.FileToProcess;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Fork(1)
@Warmup(iterations=5,batchSize=1)
@Measurement(iterations=5,batchSize=1)
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
