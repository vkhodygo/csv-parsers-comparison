package com.univocity.articles.jmh.params;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

@State(Scope.Benchmark)
public class FileToProcess {

	
	public Charset charset = Charset.forName("ISO-8859-1");
	
	@Param("src/main/resources/worldcitiespop.txt")
	public String inputFile;
	
	@Param(value= { "-1"})
	public int nbRows;
	
	@Param(value= { "true", "false"})
	public boolean quoted;
	
	public File file;
	
	@TearDown
	public void destroy() throws IOException {
		if (nbRows != -1 || quoted) {
			file.delete();
		}	
	}
	
	@Setup
	public void init() throws IOException {
		if (nbRows == -1 && ! quoted) {
			file = new File(inputFile);
		} else {
			file = File.createTempFile("bench" + nbRows, ".txt");
			System.out.println("create file "+ file + " with " + nbRows);
			char quoteSeparator = '"';
			if (!quoted) {
				quoteSeparator = CSVWriter.NO_QUOTE_CHARACTER;
			}
			CSVWriter csvWriter = new CSVWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), charset)), ',', quoteSeparator);
			try {
				
				int i = 0;
				do {
					CSVReader reader = new CSVReader(new BufferedReader(new InputStreamReader(new FileInputStream(inputFile), charset)));
					try {
						
						String[] line;
						while((nbRows < 0 || i < nbRows) && (line = reader.readNext()) != null) {
							csvWriter.writeNext(line);
							i++;
						};
						
					} finally {
						reader.close();
					}
				} while(i < nbRows);
				
			} finally {
				csvWriter.flush();
				csvWriter.close();
			}
			System.out.println("file created");
		}
	}

	public Reader getReader() throws FileNotFoundException, UnsupportedEncodingException {
		return new BufferedReader(new InputStreamReader(new FileInputStream(file), charset));
	}
	
}
