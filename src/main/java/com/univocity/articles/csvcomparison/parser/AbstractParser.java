/*******************************************************************************
 * Copyright 2014 uniVocity Software Pty Ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package com.univocity.articles.csvcomparison.parser;

import java.io.*;
import java.nio.charset.*;
import java.util.*;

public abstract class AbstractParser {
    public int tlr;
    public int tlrMask;

	private final String name;
	private int rowCount;
	public volatile Object obj1; //something to keep values from processed objects to avoid unwanted JIT's dead code removal

	protected AbstractParser(String name) {
		this.name = name;
		
        Random r = new Random(System.nanoTime());
        tlr = r.nextInt();
        tlrMask = 1;
	}

	public final String getName() {
		return name;
	}

    public final void consume(Object obj) {
        int tlr = (this.tlr = (this.tlr * 1664525 + 1013904223));
        if ((tlr & tlrMask) == 0) {
            // SHOULD ALMOST NEVER HAPPEN IN MEASUREMENT
            this.obj1 = obj;
            this.tlrMask = (this.tlrMask << 1) + 1;
        }
    }
    
	protected boolean process(Object row) {
		if(row == null){
			return false;
		}
		consume(row);
		rowCount++;
		return true;
	}

	public int getRowCount() {
		return rowCount;
	}

	
	protected Reader toReader(File input) {
		try {
			return new FileReader(input);
		} catch (FileNotFoundException e) {
			throw new IllegalStateException(e);
		}
	}

	protected Charset getEncoding() {
		return Charset.forName(getEncodingName());
	}

	protected String getEncodingName() {
		return "ISO-8859-1";
	}

	public abstract void processRows(File input) throws Exception;

	public abstract List<String[]> parseRows(File input) throws Exception;

}
