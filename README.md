# csv-parsers-comparison

This project aims to compare all CSV parsers for Java in existence, or at least the ones that seem to work and are 
available to the general public. There are too many and the intention here is to help you decide which one is the best 
for you. Commercial parsers are welcome in the test. Please send us the details of your commercial parser and we will 
include the results. 

Currently, we are only testing parsing performance. As the input file, we will be using the 
famous [GeoLite2-City-Blocks-IPv4.csv](https://geolite.maxmind.com/download/geoip/database/GeoLite2-City-CSV.zip), which is made available 
for free by [Maxmind](http://www.maxmind.com). It contains more than 3 million rows, which should be sufficient for our test.

## Building and Running

Prerequisites: Git, Apache Maven 3 and Java 1.8+.

If you wish to reproduce our performance results:

```bash
$ git clone https://github.com/azazar/csv-parsers-comparison.git
$ cd csv-parsers-comparison
$ mvn package exec:java
```

Our test is very simple and involves just counting the number of rows read from the input file. The implementation using 
each parser is [here](./src/main/java/com/univocity/articles/csvcomparison/parser). 

### Important
 The input file is **not** [RFC 4180](https://www.rfc-editor.org/rfc/rfc4180.txt) compliant. We generate a compliant 
 version using the [HugeFileGenerator](./src/main/java/com/univocity/articles/csvcomparison/HugeFileGenerator.java) 
 class to test the parsers against a generated file with the same data, but enclosed within quotes and properly escaped.
 
It's important to notice that there's no such thing as a CSV standard and we do not recommend you to use parsers that 
follow the RFC strictly, as they will blow up in face of non-compliant inputs. The reality is: your parser must be 
ready to process crooked data instead of going belly up. In the end, your client is the one who tells you what you
must swallow, and in many circumstances it's not up to you to decide how your data is going to be generated.

We generate a RFC compliant version to give those sensitive parsers a chance to see how they perform.
Once again, we consider their usage risky.

## CSV Parsers

This is the list of all parsers currently tested.

| Parser                       |   Version         | Website                                                                                            |
|------------------------------|------------------:|----------------------------------------------------------------------------------------------------|
| uniVocity-parsers' CsvParser |             2.8.1 | [www.univocity.com](http://www.univocity.com)                                                      |
| CSVeed                       |             0.6.0 | [csveed.org](http://csveed.org)                                                                    |
| Apache Commons CSV           |               1.6 | [commons.apache.org/proper/commons-csv] (http://commons.apache.org/proper/commons-csv)             |
| OpenCSV                      |               4.5 | [opencsv.sourceforge.net](http://opencsv.sourceforge.net/)                                         |
| SuperCSV                     |             2.4.0 | [supercsv.sourceforge.net](http://supercsv.sourceforge.net/)                                       |
| JavaCSV                      |               2.0 | [sourceforge.net/projects/javacsv](http://sourceforge.net/projects/javacsv)                        |
| jCSV                         |             1.4.0 | [code.google.com/p/jcsv](https://code.google.com/p/jcsv/)                                          |
| flatpack                     |             4.0.4 | [flatpack.sourceforge.net](http://flatpack.sourceforge.net/)                                       |
| SimpleCSV                    |               2.1 | [github.com/quux00/simplecsv](https://github.com/quux00/simplecsv)                                 |
| gj-csv                       |               1.0 | ?                                                                                                  |
| esperio-csv                  |             8.1.0 | [www.espertech.com](http://www.espertech.com/)                                                     |
| way-io                       |             2.1.0 | [www.objectos.com.br](http://www.objectos.com.br/)                                                 |
| beanIO                       |             2.1.0 | [beanio.org](http://beanio.org/)                                                                   |
| jackson-dataformat-csv       |             2.9.8 | [github.com/FasterXML/jackson-dataformat-csv](http://github.com/FasterXML/jackson-dataformat-csv)  |
| OsterMiller Utils            |           1.07.00 | [ostermiller.org/utils/CSV.html](http://ostermiller.org/utils/CSV.html)                            |
| SimpleFlatMapper CSV parser  |             6.4.0 | [github.com/arnaudroger/SimpleFlatMapper](https://github.com/arnaudroger/SimpleFlatMapper)         |
| Diergo Easy CSV Streamable   |                   | [github.com/aburmeis/decs](https://github.com/aburmeis/decs)                                       |
| Product Collections          |             1.4.5 | [github.com/marklister/product-collections](https://github.com/marklister/product-collections)     |
| CSV-Simple                   |             1.1.2 | [https://github.com/azazar/csv-simple](https://github.com/azazar/csv-simple)                       |

## Statistics (updated 18th of March, 2019)

Results will vary depending on your setup and hardware, here is mine: 

 * CPU: Intel(R) Core(TM) i7-3930K CPU @ 3.20GHz
 * RAM: 24 GB
 * Storage: 500GB RAID1 SSD
 * OS: Fedora release 29 64-bit 
 * JDK: OpenJDK Runtime Environment (build 1.8.0_201-b09) 64-Bit Server VM
 * JDK: OpenJDK Runtime Environment 18.9 (build 11.0.1+13) openjdk version "11.0.1"

*Note* [uniVocity-parsers](http://github.com/uniVocity/univocity-parsers/) provides an option to select the fields you
 are interested in, and our parsers will execute faster by not processing values that are not selected. It makes quite 
 a difference in performance but we removed this test as the other parsers don't have a similar feature.

### Processing 3,230,544 rows of non [RFC 4180](https://www.rfc-editor.org/rfc/rfc4180.txt) compliant input. No quoted values.

## JDK 8
| Parser                                     | Average time       | % Slower than best | Best time | Worst time |
|--------------------------------------------|-------------------:|-------------------:|----------:|-----------:|
| uniVocity CSV parser   | 1314 ms       | Best time!    | 1308 ms       | 1334 ms |
| SimpleFlatMapper CSV parser    | 1352 ms       | 2%    | 1342 ms       | 1394 ms |
| Gen-Java CSV   | 2098 ms       | 59%           | 2090 ms       | 2124 ms |
| Jackson CSV parser     | 2206 ms       | 67%           | 2204 ms       | 2214 ms |
| JCSV Parser    | 2228 ms       | 69%           | 2227 ms       | 2231 ms |
| Java CSV Parser        | 2381 ms       | 81%           | 2370 ms       | 2411 ms |
| SuperCSV       | 2387 ms       | 81%           | 2377 ms       | 2414 ms |
| Product Collections parser     | 2391 ms       | 81%           | 2379 ms       | 2403 ms |
| Simple CSV parser      | 2437 ms       | 85%           | 2426 ms       | 2473 ms |
| Oster Miller CSV parser        | 2654 ms       | 101%          | 2649 ms       | 2663 ms |
| CSV-Simple     | 2672 ms       | 103%          | 2661 ms       | 2696 ms |
| OpenCSV        | 2957 ms       | 125%          | 2950 ms       | 2961 ms |
| Way IO Parser          | 3774 ms       | 187%          | 3767 ms       | 3782 ms |
| Apache Commons CSV     | 3853 ms       | 193%          | 3834 ms       | 3905 ms |
| Bean IO Parser         | 4567 ms       | 247%          | 4544 ms       | 4629 ms |
| Esperio CSV parser     | 23906 ms      | 1719%         | 23842 ms      | 24007 ms |

## JDK 11
| Parser                                     | Average time       | % Slower than best | Best time | Worst time |
|--------------------------------------------|-------------------:|-------------------:|----------:|-----------:|
| SimpleFlatMapper CSV parser    | 1375 ms       | Best time!    | 1366 ms       | 1382 ms |
| uniVocity CSV parser   | 1428 ms       | 3%    | 1356 ms       | 1489 ms |
| Jackson CSV parser     | 2082 ms       | 51%           | 2043 ms       | 2132 ms |
| Product Collections parser     | 2124 ms       | 54%           | 2112 ms       | 2144 ms |
| JCSV Parser    | 2193 ms       | 59%           | 2188 ms       | 2205 ms |
| Java CSV Parser        | 2206 ms       | 60%           | 2193 ms       | 2220 ms |
| Oster Miller CSV parser        | 2545 ms       | 85%           | 2510 ms       | 2648 ms |
| CSV-Simple     | 2695 ms       | 96%           | 2679 ms       | 2709 ms |
| OpenCSV        | 2804 ms       | 103%          | 2782 ms       | 2843 ms |
| Simple CSV parser      | 2868 ms       | 108%          | 2819 ms       | 2983 ms |
| SuperCSV       | 2919 ms       | 112%          | 2906 ms       | 2959 ms |
| Apache Commons CSV     | 3596 ms       | 161%          | 3580 ms       | 3635 ms |
| Way IO Parser          | 3693 ms       | 168%          | 3675 ms       | 3707 ms |
| Gen-Java CSV   | 3813 ms       | 177%          | 3802 ms       | 3819 ms |
| Bean IO Parser         | 4955 ms       | 260%          | 4869 ms       | 5018 ms |
| Esperio CSV parser     | 22837 ms      | 1560%         | 22767 ms      | 22907 ms |

 * `CSVeed` was unable to process the file and threw exception with the message `Illegal state transition: Parsing symbol QUOTE_SYMBOL [34] in state INSIDE_FIELD`.
 * `Flatpack` blew up the Java heap space so I had to remove it from the test [here](./src/main/java/com/univocity/articles/csvcomparison/parser/Parsers.java).


## Reliability (updated 9th of October, 2017)

The following parsers were unable to process the [RFC 4180](https://www.rfc-editor.org/rfc/rfc4180.txt) compliant file
 [correctness.csv](./src/main/resources/correctness.csv). This test is executed using the class [CorrectnessComparison.java](./src/main/java/com/univocity/articles/csvcomparison/CorrectnessComparison.java)

| Parser                                     | Error |
|--------------------------------------------|:------|
|CSVeed										 | CSVeed threw exception "Illegal state transition: Parsing symbol QUOTE_SYMBOL [34] in state INSIDE_FIELD" |
|jCSV Parser                                 | JCSV Parser produced ["Year,Make,Model,Description,Price"] instead of ["Year", "Make", "Model", "Description", "Price"] |
|Simple CSV parser                           | Simple CSV parser threw exception "The separator, quote, and escape characters must be different!" |
|Way IO Parser                               | Way IO Parser threw exception "Could not convert  to class java.lang.String" |
|Gen-Java CSV                                | Gen-Java CSV produced 7 rows instead of 6 |
|Flatpack									 | Flatpack produced 5 rows instead of 6 |

The exact same errors have been reported 3 years ago when I last updated this page. Just avoid these parsers. 

## Conclusion

Currently, three parsers stand out as the fastest CSV parsers for Java:

*uniVocity-parsers*, *SimpleFlatMapper* and *Jackson CSV*. Keep in mind that *Simpleflatmapper* is a very simple 
implementation that does not provide any customization options. Results are affected by a simple change in the JDK version,
however these three parsers are always at the top.

We will keep working to improve the performance of our parsers, and will try to update the results of this benchmark
every time a new parser is added to the list.

Head on to the [uniVocity-parsers github page](http://github.com/uniVocity/univocity-parsers/) to get access to its
source code and documentation. Contributions are welcome.

#### Commercial support is available for your peace of mind. [Click here to learn more.](http://www.univocity.com/products/parsers-support)

