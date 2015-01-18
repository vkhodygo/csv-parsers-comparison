package com.univocity.articles.csvcomparison.parser;

import java.io.FileInputStream;
import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.Arrays;

import com.webaction.event.Event;
import com.webaction.proc.events.WAEvent;
import com.webaction.source.classloading.ParserLoader; 
import com.webaction.source.lib.intf.Parser; 
import com.webaction.common.exc.RecordException; 

public class WebActionDSVParser extends AbstractParser {
	protected WebActionDSVParser() {
		super("WebAction DSV Parser");
	}

	@Override
	public void processRows(File input) throws Exception {
        Map<String,Object> convertedProperties = new HashMap<String, Object>();
        convertedProperties.put("handler","DSVParser");
        convertedProperties.put("charset","UTF-8");
        convertedProperties.put("columndelimiter",",");
        convertedProperties.put("rowdelimiter","\n");

        Parser parser = ParserLoader.loadParser(convertedProperties, null);
        FileInputStream fis = new FileInputStream(input);
        Iterator<Event> iterator = parser.parse(fis);
        try {

            while(iterator.hasNext()) {
                //            WAEvent evt = (WAEvent)iterator.next();
                process(iterator.next());            
            }        
        } catch(Exception e) {
            Throwable cause = e.getCause();
            if((cause instanceof RecordException) && (((RecordException)cause).type() == RecordException.Type.END_OF_DATASOURCE)) {
            } else {
                throw e;
            }
        }
        fis.close();
	}

	@Override
	public List<String[]> parseRows(File input) throws Exception {
		List<String[]> rows = new ArrayList<String[]>();

        Map<String,Object> convertedProperties = new HashMap<String, Object>();
        convertedProperties.put("handler","DSVParser");
        convertedProperties.put("charset","UTF-8");
        convertedProperties.put("columndelimiter",",");
        convertedProperties.put("rowdelimiter","\n");
        Parser parser = ParserLoader.loadParser(convertedProperties, null);
        FileInputStream fis = new FileInputStream(input);
        try {

            Iterator<Event> iterator = parser.parse(fis);
            while(iterator.hasNext()) {
                WAEvent evt = (WAEvent)iterator.next();
                String[] data = Arrays.copyOf(evt.data, evt.data.length,String[].class);
                rows.add(data);
            }        

        } catch(Exception e) {
            Throwable cause = e.getCause();
            if((cause instanceof RecordException) && (((RecordException)cause).type() == RecordException.Type.END_OF_DATASOURCE)) {
            } else {
                throw e;
            }
        }
        fis.close();
		return rows;
	}
}
