package com.univocity.articles.csvcomparison.parser;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvParser;

import java.io.File;
import java.io.Reader;
import java.util.*;

public class JacksonParser extends AbstractParser {

	protected JacksonParser() {

		super("Jackson CSV parser");
		csvMapper = new CsvMapper();
		csvMapper.enable(CsvParser.Feature.WRAP_AS_ARRAY);
	}
	CsvMapper csvMapper; 
	
	@Override
	public void processRows(final File input) throws Exception {

		Reader reader = toReader(input);
		try {
			MappingIterator<String[]> iterator = csvMapper.reader(
					String[].class).readValues(reader);

			while (iterator.hasNext()) {
				process(iterator.next());
			}
		} finally {
			reader.close();
		}

	}

	@Override
	public List<String[]> parseRows(final File input) throws Exception {

		CsvMapper csvMapper = new CsvMapper();
		csvMapper.enable(CsvParser.Feature.WRAP_AS_ARRAY);

		MappingIterator<String[]> iterator = csvMapper.reader(String[].class).readValues(input);

		List<String[]> values = new ArrayList<String[]>();
		while (iterator.hasNext()) {
			values.add(iterator.next());
		}

		return values;
	}

}
