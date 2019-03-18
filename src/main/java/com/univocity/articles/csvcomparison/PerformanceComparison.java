/** *****************************************************************************
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
 ***************************************************************************** */
package com.univocity.articles.csvcomparison;

import java.io.*;
import java.net.URI;
import java.net.URL;
import java.util.*;
import java.util.Map.Entry;

import com.univocity.articles.csvcomparison.parser.*;
import java.lang.management.ManagementFactory;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import org.apache.xmlbeans.impl.common.IOUtil;

public class PerformanceComparison {

    private static final String GEOLITE2_CITY_CSV_URL = "https://geolite.maxmind.com/download/geoip/database/GeoLite2-City-CSV.zip";
    private static final String GEOLITE2_CITY_CSV_FILE = "GeoLite2-City-Blocks-IPv4.csv";
    private static final String GEOLITE2_CITY_CSV_FILE_ENCODING = "ISO-8859-1";
    private static final String GEOLITE2_CITY_CSV_HUGE_FILE = "geolite2_huge.txt";
    private static final String GEOLITE2_CITY_CSV_HUGE_FILE_ENCODING = "ISO-8859-1";

    private final File file;
    private final String fileEncoding;

    PerformanceComparison(File file, String fileEncoding) {
        this.file = file;
        this.fileEncoding = fileEncoding;
    }

    private long run(AbstractParser parser) throws Exception {
        Reader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), fileEncoding));

            long start = System.currentTimeMillis();

            parser.processRows(reader);

            long time = (System.currentTimeMillis() - start);
            System.out.println("took " + time + " ms to read " + parser.getRowCount() + " rows. ");
            parser.resetRowCount();
            System.setProperty("blackhole", parser.getBlackhole());
            return time;
        }
        finally {
            if (reader != null) {
                reader.close();
            }
        }
    }

    private TreeMap<Long, String> orderByAverageTime(int loops, Map<String, Long[]> stats) {
        TreeMap<Long, String> averages = new TreeMap<Long, String>();

        for (Entry<String, Long[]> parserTimes : stats.entrySet()) {
            Long[] times = parserTimes.getValue();
            long average = 0L;
            //we are discarding the first recorded time here to take into account JIT optimizations
            for (int i = 1; i < times.length; i++) {
                average = average + times[i];
            }
            average = average / (loops - 1);
            averages.put(average, parserTimes.getKey());
        }

        return averages;
    }

    private long getBestTime(Long[] times) {
        long best = times[1];
        for (int i = 1; i < times.length; i++) {
            if (times[i] < best) {
                best = times[i];
            }
        }
        return best;
    }

    private long getWorstTime(Long[] times) {
        long worst = times[1];
        for (int i = 1; i < times.length; i++) {
            if (times[i] > worst) {
                worst = times[i];
            }
        }
        return worst;
    }

    private void printResults(int loops, Map<String, Long[]> stats) {
        System.out.println("\n=== JVM: " + ManagementFactory.getRuntimeMXBean().getVmVersion() + "\n");

        System.out.println("\n=========\n AVERAGES \n=========\n");

        Map<Long, String> averages = orderByAverageTime(loops, stats);
        long bestTime = 0;
        for (Entry<Long, String> average : averages.entrySet()) {
            long time = average.getKey();
            String parser = average.getValue();
            System.out.print("| " + parser + " \t | " + time + " ms ");

            if (time == -1) {
                System.out.println("Could not execute");
                continue;
            }

            if (bestTime != 0) {
                long increasePercentage = time * 100 / bestTime - 100;
                System.out.print(" \t | " + increasePercentage + "% ");
            }
            else {
                bestTime = time;
                System.out.print(" \t | Best time! ");
            }

            long best = getBestTime(stats.get(parser));
            long worst = getWorstTime(stats.get(parser));

            System.out.println(" \t | " + best + " ms \t | " + worst + " ms |");

        }
    }

    public void execute(final int loops) throws Exception {
        Map<String, Long[]> stats = new HashMap<String, Long[]>();

        for (final AbstractParser parser : ParsersRegistry.getParsers()) {
            Long[] times = new Long[loops];
            Arrays.fill(times, -1L);
            stats.put(parser.getName(), times);
        }

        for (int i = 0; i < loops; i++) {
            for (final AbstractParser parser : ParsersRegistry.getParsers()) {
                try {
                    System.out.print("Loop " + (i + 1) + " - executing " + parser.getName() + "... ");
                    long time = run(parser);

                    stats.get(parser.getName())[i] = time;
                }
                catch (Throwable ex) {
                    System.out.println("Parser " + parser.getName() + " threw exception: " + ex.getMessage());
                }
                System.gc();
                Thread.sleep(500);
            }
        }

        printResults(loops, stats);
    }

    public static void main(String... args) throws Exception {

        int loops = 6;

        File input = null;
        URL inputUrl = PerformanceComparison.class.getClassLoader().getResource(GEOLITE2_CITY_CSV_FILE);

        if (inputUrl != null) {
            try {
                input = new File(inputUrl.toURI());
            }
            catch (Exception ex) {
                System.err.println("Error reading file from " + inputUrl + ": " + ex.getMessage());
            }
        }

        if (input == null) {
            if (args.length > 0) {
                input = new File(args[0], GEOLITE2_CITY_CSV_FILE);
                if (!input.exists()) {
                    throw new IllegalStateException("Could not find '" + GEOLITE2_CITY_CSV_FILE + "' in classpath or in folder: " + args[0]);
                }
            }
            else {
                File tmpDir = File.createTempFile("tmp", "");
                tmpDir.delete();
                tmpDir = tmpDir.getParentFile();

                input = new File(tmpDir, GEOLITE2_CITY_CSV_FILE);
                inputUrl = input.toURI().toURL();

                if (!input.exists()) {
                    try (ZipInputStream zin = new ZipInputStream(new URL(GEOLITE2_CITY_CSV_URL).openStream())) {

                        boolean found = false;

                        for(;;) {
                            ZipEntry e = zin.getNextEntry();

                            if (e.getName().endsWith(GEOLITE2_CITY_CSV_FILE)) {
                                found = true;
                                try (FileOutputStream out = new FileOutputStream(input)) {
                                    IOUtil.copyCompletely(zin, out);
                                }
                                break;
                            }

                        }

                        if (!found) {
                            throw new Error(GEOLITE2_CITY_CSV_FILE + " not found in " + GEOLITE2_CITY_CSV_URL);
                        }
                    }
                }
            }
        }

        new PerformanceComparison(input, GEOLITE2_CITY_CSV_FILE_ENCODING).execute(loops);

        File hugeInput = null;
        final URL hugeInputUrl = PerformanceComparison.class.getClassLoader().getResource(GEOLITE2_CITY_CSV_HUGE_FILE);
        if (hugeInputUrl != null) {
            try {
                hugeInput = new File(hugeInputUrl.toURI());
            }
            catch (Exception ex) {
                System.err.println("Error reading file from " + inputUrl + ": " + ex.getMessage());
            }
        }

        if (hugeInput == null) {
            if (args.length > 0) {
                hugeInput = new File(args[0], GEOLITE2_CITY_CSV_HUGE_FILE);
            }
            else {
                throw new IllegalStateException("Could not find '" + GEOLITE2_CITY_CSV_HUGE_FILE + "' in classpath, or path not specified as arg[0]");
            }
        }

        //executes only if the file has not been generated yet.
        //Previously, we created a huge file with the original input, replicated 15 times. All fields enclosed within quotes.
        //It would generate a file with 47,609,385 rows
        //Now, creates a copy of the original input. All fields enclosed within quotes. 
        //Overall performance is the similar in percentage terms, regardless of size. No point in melting our CPU's to get the same result.
        HugeFileGenerator.generateHugeFile(input, GEOLITE2_CITY_CSV_FILE_ENCODING, 1, hugeInput);

        System.out.println("==================================");
        System.out.println("=== Processing huge input file ===");
        System.out.println("==================================");

        new PerformanceComparison(hugeInput, GEOLITE2_CITY_CSV_HUGE_FILE_ENCODING).execute(loops);
    }

}
