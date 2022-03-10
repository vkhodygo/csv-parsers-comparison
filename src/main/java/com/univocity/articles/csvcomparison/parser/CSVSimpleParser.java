package com.univocity.articles.csvcomparison.parser;

import net.uo1.csv.CSV;
import net.uo1.csv.CSVReader;

import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class CSVSimpleParser extends AbstractParser {

    public CSVSimpleParser() {
        super("CSV-Simple");
    }

    @Override
    public void processRows(Reader reader) throws Exception {
        CSV csv = CSV.RFC4180;

        CSVReader r = csv.createReader(reader);

        while (process(r.readLine()));
    }

    @Override
    public List<String[]> parseRows(Reader reader) throws Exception {
        List<String[]> rows = new ArrayList<String[]>();

        CSV csv = CSV.RFC4180;

        CSVReader r = csv.createReader(reader);

        String[] row;

        while ((row = r.readLine()) != null) {
            rows.add(row);
        };

        return rows;
    }
}
