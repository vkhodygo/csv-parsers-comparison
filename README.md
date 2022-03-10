Fork of [uniVocity/csv-parsers-comparison/](https://github.com/uniVocity/csv-parsers-comparison/)

# Summary

[SimpleFlatMapper](https://github.com/arnaudroger/SimpleFlatMapper/) and [Jackson](https://github.com/FasterXML/jackson-dataformat-csv) are the fastest one in those runs. SFM tends to be faster in most cases apart from the 1000 rows with no quotes in java 6 and 7.

[Univocity](https://github.com/uniVocity/univocity-parsers) has a stiff initialisation price to pay that makes a lean heavily on the run under 100000 rows.

Some parser can have sensibly different resuslt depending on the java version. TieredCompilation - which is enabled by default in java 8 - seems to have mixed results accross the parsers. Jcsv for example is pretty bad under java 6, pretty good on java 7 but degrade quote a bit in java 8.

Some of the result don't match the orginal benchmark. It could be link to the difference of hardware - also I do think that the  fit in the page cache - or of the benchmark technology used.
Because I get the same result running the jmh and the original benchmark running on my machine I doubt the code is the issue.
### Important
 The input file is **not** [RFC 4180](https://www.rfc-editor.org/rfc/rfc4180.txt) compliant. We generate a compliant version using the [HugeFileGenerator](./src/main/java/com/univocity/articles/csvcomparison/HugeFileGenerator.java) class to test the parsers against a generated file with the same data, but enclosed within quotes and properly escaped.
 
It's important to notice that there's no such thing as a CSV standard and we do not recommend you to use parsers that follow the RFC strictly, as they will blow up in face of non-compliant inputs. The reality is: your parser must be ready to process crooked data instead of going belly up. In the end, your client is the one who tells you what you must swallow, and in many circumstances it's not up to you to decide how your data is going to be generated.


BeanIO and GenJava have the worst performance overall.	

This is the list of all parsers currently tested. Parsers followed by * are commercial an their jars are not included in this project. You have to download them independently:

# Benchmarks
| Parser                       |   Version | Website                                                                                            |
|------------------------------|----------:|----------------------------------------------------------------------------------------------------|
| uniVocity-parsers' CsvParser |     1.3.0 | [www.univocity.com](http://www.univocity.com)                                                      |
| CSVeed                       |     0.4.0 | [csveed.org](http://csveed.org)                                                                    |
| Apache Commons CSV           |       1.0 | [commons.apache.org/proper/commons-csv] (http://commons.apache.org/proper/commons-csv)             |
| OpenCSV                      |       2.3 | [opencsv.sourceforge.net](http://opencsv.sourceforge.net/)                                         |
| SuperCSV                     |     2.2.0 | [supercsv.sourceforge.net](http://supercsv.sourceforge.net/)                                       |
| JavaCSV                      |       2.0 | [sourceforge.net/projects/javacsv](http://sourceforge.net/projects/javacsv)                        |
| jCSV                         |     1.4.0 | [code.google.com/p/jcsv](https://code.google.com/p/jcsv/)                                          |
| flatpack                     |     3.4.2 | [flatpack.sourceforge.net](http://flatpack.sourceforge.net/)                                       |
| SimpleCSV                    |       2.0 | [github.com/quux00/simplecsv](https://github.com/quux00/simplecsv)                                 |
| gj-csv                       |       1.0 | ?                                                                                                  |
| esperio-csv                  |    4.11.0 | [www.espertech.com](http://www.espertech.com/)                                                     |
| way-io                       |     1.6.0 | [www.objectos.com.br](http://www.objectos.com.br/)                                                 |
| beanIO                       |     2.1.0 | [beanio.org](http://beanio.org/)                                                                   |
| DataPipeline's CSVReader*    |     2.3.5 | [northconcepts.com/data-pipeline/](http://northconcepts.com/data-pipeline/)                        |
| jackson-dataformat-csv       |     2.4.2 | [github.com/FasterXML/jackson-dataformat-csv](http://github.com/FasterXML/jackson-dataformat-csv)  |
| OsterMiller Utils            |     1.0.6 | [ostermiller.org/utils/CSV.html](http://ostermiller.org/utils/CSV.html)                            |
| SimpleFlatMapper CSV parser  |  1.0.0rc2 | [github.com/arnaudroger/SimpleFlatMapper](https://github.com/arnaudroger/SimpleFlatMapper)         |

[worldcitiespop.txt](https://worlddatapro.googlecode.com/svn-history/r2/trunk/data/worldcitiespop.txt)

```
echo "performance" | sudo tee /sys/devices/system/cpu/cpu*/cpufreq/scaling_governor;
echo "2000000" | sudo tee /sys/devices/system/cpu/cpu*/cpufreq/scaling_min_freq;
echo "2000000" | sudo tee /sys/devices/system/cpu/cpu*/cpufreq/scaling_max_freq
## Statistics (updated 28th of November, 2014)


java -jar target/benchmarks.jar -tu us \
  -wi 10 -i 10 -f 5 \
  -p nbRows=1,10,100,1000,10000,100000,1000000,-1 \
  -rf csv -foe -p inputFile=./worldcitiespop.txt \
  -rff jmh.csv -jvmArgs="-Xmx1g -Xms1g"
```
Results will vary depending on your setup and hardware. For reference, here's my (very) modest hardware, an ultrabook: 

 * CPU: Intel i5-3337U @ 1.8 GHz
 * RAM: 4 GB
 * Storage: 128GB SSD drive
 * OS: Arch Linux 64-bit 
 * JDK: 1.8.0_25 64-bit (Linux)
 * JDK: 1.7.0_71 64-bit (Linux)
 * JDK: 1.6.0_45 64-bit (Linux)
- Intel(R) Core(TM) i5 CPU         750  @ 2.67GHz
- Oracle Java 1.6.0_45
- Oracle Java 1.7.0_72
- Oracle Java 1.8.0_25
- 6G Ram
- Ubuntu
- HDD

[Excel data](https://raw.githubusercontent.com/arnaudroger/csv-parsers-comparison/master/src/main/results/consolidate.xls)
*Note* [uniVocity-parsers](http://github.com/uniVocity/univocity-parsers/) provides an option to select the fields you are interested in, and our parsers will execute faster by not processing values that are not selected. It makes quite a difference in performance but we removed this test as the other parsers don't have a similar feature.

# Java 6 Results

## No quote results
### Processing 3,173,958 rows of non [RFC 4180](https://www.rfc-editor.org/rfc/rfc4180.txt) compliant input.

the unit is us per reading the file of nbrows. The lower the better.
## JDK 8
| Parser                                     | Average time       | % Slower than best | Best time | Worst time |
|--------------------------------------------|-------------------:|-------------------:|----------:|-----------:|
| uniVocity CSV parser 	 | 1792 ms  	 | Best time!  	 | 1654 ms 	 | 1930 ms |
| Jackson CSV parser 	 | 1992 ms  	 | 11%  	 | 1897 ms 	 | 2130 ms |
| SimpleFlatMapper CSV parser 	 | 2324 ms  	 | 29%  	 | 2120 ms 	 | 2581 ms |
| JCSV Parser 	 | 2733 ms  	 | 52%  	 | 2458 ms 	 | 2886 ms |
| SuperCSV 	 | 2840 ms  	 | 58%  	 | 2631 ms 	 | 3184 ms |
| Oster Miller CSV parser 	 | 2876 ms  	 | 60%  	 | 2647 ms 	 | 3193 ms |
| Simple CSV parser 	 | 2887 ms  	 | 61%  	 | 2815 ms 	 | 3012 ms |
| Java CSV Parser 	 | 2905 ms  	 | 62%  	 | 2747 ms 	 | 3172 ms |
| OpenCSV 	 | 3112 ms  	 | 73%  	 | 2995 ms 	 | 3226 ms |
| Apache Commons CSV 	 | 3136 ms  	 | 75%  	 | 3057 ms 	 | 3332 ms |
| Data pipeline 	 | 3434 ms  	 | 91%  	 | 3198 ms 	 | 3567 ms |
| Way IO Parser 	 | 4313 ms  	 | 140%  	 | 4133 ms 	 | 4467 ms |
| Gen-Java CSV 	 | 6715 ms  	 | 274%  	 | 6634 ms 	 | 6797 ms |

|Parser/nbrows|1|10|100|1000|10000|100000|1000000|3173959
|------|------:|----:|----:|----:|----:|----:|----:|----:|
|[JacksonParser 2.4.2](https://github.com/FasterXML/jackson-dataformat-csv)|20.56|28.91|109.42|959.92|9,259.31|91,718.92|904,262.33|2,862,200.83
|[SimpleFlatMapperParser 1.2.0](https://github.com/arnaudroger/SimpleFlatMapper)|20.97|27.70|92.92|890.45|7,425.59|75,174.15|736,202.60|2,342,475.25
|[JCsvParser 1.4.0](https://code.google.com/p/jcsv/)|22.00|34.37|162.13|1,341.85|12,723.23|127,770.35|1,246,264.46|4,522,937.51
|[SimpleCsvParser 2.0](https://github.com/quux00/simplecsv)|22.39|36.34|177.18|1,387.69|13,706.27|167,896.96|1,641,417.61|5,221,632.46
|[OpenCsvParser 2.3](http://opencsv.sourceforge.net/)|21.65|33.82|149.24|1,396.74|12,959.22|133,597.68|1,304,542.01|4,156,496.28
|[SuperCsvParser 2.2.0](http://supercsv.sourceforge.net/)|22.46|35.18|169.48|1,430.02|13,860.77|165,970.79|1,606,424.38|5,256,495.20
|[OsterMillerParser 1.07.00](http://ostermiller.org/utils/)|23.61|35.75|157.77|1,555.74|14,918.44|151,041.99|1,484,759.67|4,746,388.14
|[JavaCsvParser 2.0](http://sourceforge.net/projects/javacsv/)|31.09|41.39|176.16|1,588.15|15,130.67|152,590.81|1,504,618.33|4,806,283.53
|[WayIoParser 1.6.0](http://www.objectos.com.br/)|28.39|44.13|201.51|1,836.43|17,624.63|174,649.84|1,764,296.31|5,702,440.66
|[CommonsCsvParser 1.0](http://commons.apache.org/proper/commons-csv)|20.18|35.31|187.04|1,870.36|17,812.89|179,474.87|1,747,955.82|5,539,298.62
|[GenJavaParser 1.0](http://www.osjava.org/genjava/multiproject/gj-csv/)|22.14|44.03|310.80|3,645.57|34,678.04|346,876.24|3,558,724.86|9,636,547.34
|[UnivocityParser 1.0.3](https://github.com/uniVocity/univocity-parsers)|4,447.20|4,457.70|4,568.01|5,729.47|17,630.42|132,168.34|1,219,204.75|3,909,024.04
## JDK 7
| Parser                                     | Average time       | % Slower than best | Best time | Worst time |
|--------------------------------------------|-------------------:|-------------------:|----------:|-----------:|
| uniVocity CSV parser 	 | 1870 ms  	 | Best time!  	 | 1840 ms 	 | 1900 ms |
| SimpleFlatMapper CSV parser 	 | 1904 ms  	 | 1%  	 | 1720 ms 	 | 2097 ms |
| Jackson CSV parser 	 | 1975 ms  	 | 5%  	 | 1862 ms 	 | 2099 ms |
| JCSV Parser 	 | 2497 ms  	 | 33%  	 | 2323 ms 	 | 2755 ms |
| Oster Miller CSV parser 	 | 2795 ms  	 | 49%  	 | 2659 ms 	 | 2975 ms |
| SuperCSV 	 | 2862 ms  	 | 53%  	 | 2715 ms 	 | 3029 ms |
| Java CSV Parser 	 | 2894 ms  	 | 54%  	 | 2667 ms 	 | 3137 ms |
| OpenCSV 	 | 2957 ms  	 | 58%  	 | 2788 ms 	 | 3122 ms |
| Data pipeline 	 | 3049 ms  	 | 63%  	 | 2903 ms 	 | 3267 ms |
| Apache Commons CSV 	 | 3192 ms  	 | 70%  	 | 3112 ms 	 | 3292 ms |
| Simple CSV parser 	 | 3322 ms  	 | 77%  	 | 3205 ms 	 | 3537 ms |
| Way IO Parser 	 | 4150 ms  	 | 121%  	 | 3942 ms 	 | 4394 ms |
| Gen-Java CSV 	 | 8858 ms  	 | 373%  	 | 8519 ms 	 | 9215 ms |

Latency difference in % from jackson parser
![Difference from jackson parser](https://raw.githubusercontent.com/arnaudroger/csv-parsers-comparison/master/src/main/resources/charts/java_6_noquote.png)
## JDK 6
| Parser                                     | Average time       | % Slower than best | Best time | Worst time |
|--------------------------------------------|-------------------:|-------------------:|----------:|-----------:|
| SimpleFlatMapper CSV parser 	 | 1749 ms  	 | Best time!  	 | 1718 ms 	 | 1790 ms |
| uniVocity CSV parser 	 | 1911 ms  	 | 9%  	 | 1796 ms 	 | 2031 ms |
| Jackson CSV parser 	 | 1983 ms  	 | 13%  	 | 1906 ms 	 | 2103 ms |
| OpenCSV 	 | 2611 ms  	 | 49%  	 | 2567 ms 	 | 2675 ms |
| JCSV Parser 	 | 2653 ms  	 | 51%  	 | 2600 ms 	 | 2707 ms |
| Java CSV Parser 	 | 2782 ms  	 | 59%  	 | 2706 ms 	 | 2879 ms |
| Data pipeline 	 | 2805 ms  	 | 60%  	 | 2688 ms 	 | 2894 ms |
| Simple CSV parser 	 | 3098 ms  	 | 77%  	 | 2945 ms 	 | 3421 ms |
| Oster Miller CSV parser 	 | 3122 ms  	 | 78%  	 | 3022 ms 	 | 3210 ms |
| SuperCSV 	 | 3185 ms  	 | 82%  	 | 3059 ms 	 | 3351 ms |
| Apache Commons CSV 	 | 3358 ms  	 | 91%  	 | 3280 ms 	 | 3445 ms |
| Way IO Parser 	 | 3743 ms  	 | 114%  	 | 3683 ms 	 | 3827 ms |
| Gen-Java CSV 	 | 7144 ms  	 | 308%  	 | 6991 ms 	 | 7278 ms |

 * `Esperio-csv` and `CSVeed` were unable to process the file and threw exceptions.
 * `Flatpack` hanged so I had to remove it from the test [here](./src/main/java/com/univocity/articles/csvcomparison/parser/Parsers.java).
 * `BeanIO` threw an exception I could understand and debug. Turns out it is unable to parse fields when the quote character is part of the value, e.g. `value1, val"ue2, value3 `. 
 
### Processing 3,173,958 rows of [RFC 4180](https://www.rfc-editor.org/rfc/rfc4180.txt) compliant input.


## Quote results

## JDK 8
| Parser                                     | Average time       | % Slower than best | Best time  | Worst time |
|--------------------------------------------|-------------------:|-------------------:|-----------:|-----------:|
| uniVocity CSV parser 	 | 2270 ms  	 | Best time!  	 | 2178 ms 	 | 2352 ms |
| Jackson CSV parser 	 | 2416 ms  	 | 6%  	 | 2294 ms 	 | 2490 ms |
| SuperCSV 	 | 3116 ms  	 | 37%  	 | 2956 ms 	 | 3267 ms |
| JCSV Parser 	 | 3172 ms  	 | 39%  	 | 3049 ms 	 | 3325 ms |
| SimpleFlatMapper CSV parser 	 | 3405 ms  	 | 50%  	 | 3177 ms 	 | 3709 ms |
| Data pipeline 	 | 3424 ms  	 | 50%  	 | 3355 ms 	 | 3478 ms |
| Apache Commons CSV 	 | 3620 ms  	 | 59%  	 | 3500 ms 	 | 3769 ms |
| Simple CSV parser 	 | 3793 ms  	 | 67%  	 | 3635 ms 	 | 3896 ms |
| Java CSV Parser 	 | 3864 ms  	 | 70%  	 | 3703 ms 	 | 4046 ms |
| OpenCSV 	 | 4115 ms  	 | 81%  	 | 3955 ms 	 | 4532 ms |
| Oster Miller CSV parser 	 | 4165 ms  	 | 83%  	 | 3988 ms 	 | 4343 ms |
| Esperio CSV parser 	 | 5355 ms  	 | 135%  	 | 5122 ms 	 | 5591 ms |
| Way IO Parser 	 | 5400 ms  	 | 137%  	 | 5016 ms 	 | 5588 ms |
| Gen-Java CSV 	 | 8558 ms  	 | 277%  	 | 8106 ms 	 | 8844 ms |
| Bean IO Parser 	 | 10796 ms  	 | 375%  	 | 10504 ms 	 | 11341 ms |

the unit is us per reading the file of nbrows. The lower the better.
## JDK 7
| Parser                                     | Average time       | % Slower than best | Best time  | Worst time |
|--------------------------------------------|-------------------:|-------------------:|-----------:|-----------:|
| uniVocity CSV parser 	 | 2352 ms  	 | Best time!  	 | 2286 ms 	 | 2449 ms |
| Jackson CSV parser 	 | 2432 ms  	 | 3%  	 | 2234 ms 	 | 2627 ms |
| SimpleFlatMapper CSV parser 	 | 2556 ms  	 | 8%  	 | 2373 ms 	 | 2836 ms |
| Data pipeline 	 | 3281 ms  	 | 39%  	 | 3084 ms 	 | 3596 ms |
| SuperCSV 	 | 3296 ms  	 | 40%  	 | 2989 ms 	 | 3500 ms |
| JCSV Parser 	 | 3305 ms  	 | 40%  	 | 3145 ms 	 | 3692 ms |
| OpenCSV 	 | 3859 ms  	 | 64%  	 | 3671 ms 	 | 4092 ms |
| Java CSV Parser 	 | 3884 ms  	 | 65%  	 | 3749 ms 	 | 4139 ms |
| Oster Miller CSV parser 	 | 4054 ms  	 | 72%  	 | 3804 ms 	 | 4255 ms |
| Simple CSV parser 	 | 4246 ms  	 | 80%  	 | 4093 ms 	 | 4516 ms |
| Apache Commons CSV 	 | 4303 ms  	 | 82%  	 | 4197 ms 	 | 4405 ms |
| Way IO Parser 	 | 5095 ms  	 | 116%  	 | 4935 ms 	 | 5270 ms |
| Esperio CSV parser 	 | 6001 ms  	 | 155%  	 | 5760 ms 	 | 6375 ms |
| Gen-Java CSV 	 | 10918 ms  	 | 364%  	 | 10345 ms 	 | 11305 ms |
| Bean IO Parser 	 | 12839 ms  	 | 445%  	 | 11992 ms 	 | 13641 ms |

## JDK 6
| Parser                                     | Average time       | % Slower than best | Best time  | Worst time |
|--------------------------------------------|-------------------:|-------------------:|-----------:|-----------:|
| uniVocity CSV parser 	 | 2395 ms  	 | Best time!  	 | 2368 ms 	 | 2444 ms |
| SimpleFlatMapper CSV parser 	 | 2539 ms  	 | 6%  	 | 2480 ms 	 | 2582 ms |
| Jackson CSV parser 	 | 2809 ms  	 | 17%  	 | 2669 ms 	 | 2982 ms |
| Data pipeline 	 | 3197 ms  	 | 33%  	 | 3067 ms 	 | 3395 ms |
| JCSV Parser 	 | 3423 ms  	 | 42%  	 | 3293 ms 	 | 3678 ms |
| OpenCSV 	 | 3570 ms  	 | 49%  	 | 3457 ms 	 | 3847 ms |
| Java CSV Parser 	 | 3745 ms  	 | 56%  	 | 3541 ms 	 | 3977 ms |
| SuperCSV 	 | 3808 ms  	 | 58%  	 | 3638 ms 	 | 4062 ms |
| Oster Miller CSV parser 	 | 3944 ms  	 | 64%  	 | 3750 ms 	 | 4161 ms |
| Simple CSV parser 	 | 3962 ms  	 | 65%  	 | 3776 ms 	 | 4226 ms |
| Apache Commons CSV 	 | 4315 ms  	 | 80%  	 | 4189 ms 	 | 4448 ms |
| Way IO Parser 	 | 4667 ms  	 | 94%  	 | 4566 ms 	 | 4773 ms |
| Esperio CSV parser 	 | 5625 ms  	 | 134%  	 | 5496 ms 	 | 5778 ms |
| Gen-Java CSV 	 | 8977 ms  	 | 274%  	 | 8759 ms 	 | 9293 ms |
| Bean IO Parser 	 | 10848 ms  	 | 352%  	 | 10385 ms 	 | 11225 ms |


 * `CSVeed` was unable to process the file and threw exception with the message "Parsing symbol OTHER_SYMBOL [44] in state ESCAPING".
 * `Flatpack` blew up the Java heap space so I had to remove it from the test [here](./src/main/java/com/univocity/articles/csvcomparison/parser/Parsers.java).

|Parser/nbrows|1|10|100|1000|10000|100000|1000000|3173959
|------|------:|----:|----:|----:|----:|----:|----:|----:|
|[JacksonParser 2.4.2](https://github.com/FasterXML/jackson-dataformat-csv)|20.80|31.80|144.23|1,462.09|13,966.33|140,360.80|1,380,760.39|4,401,189.30
|[SimpleFlatMapperParser 1.2.0](https://github.com/arnaudroger/SimpleFlatMapper)|21.08|31.96|104.00|1,027.17|12,013.12|100,785.66|977,777.05|2,647,928.02
|[JCsvParser 1.4.0](https://code.google.com/p/jcsv/)|22.94|41.51|228.19|2,156.42|21,638.66|221,701.13|2,117,656.18|6,822,423.28
|[SimpleCsvParser 2.0](https://github.com/quux00/simplecsv)|22.51|40.04|217.32|1,787.30|19,384.62|200,927.10|1,965,590.72|6,284,008.83
|[OpenCsvParser 2.3](http://opencsv.sourceforge.net/)|22.75|41.31|231.37|2,020.68|18,658.59|189,861.09|1,857,790.99|5,969,062.93
|[SuperCsvParser 2.2.0](http://supercsv.sourceforge.net/)|23.16|42.58|233.84|1,996.42|19,450.24|200,009.79|1,968,554.76|6,261,176.90
|[OsterMillerParser 1.07.00](http://ostermiller.org/utils/)|23.95|41.54|212.57|1,796.83|18,045.74|183,739.90|1,801,485.56|5,831,664.22
|[JavaCsvParser 2.0](http://sourceforge.net/projects/javacsv/)|31.81|43.27|194.01|1,820.18|17,776.71|184,548.24|1,769,503.49|5,613,036.50
|[WayIoParser 1.6.0](http://www.objectos.com.br/)|29.39|52.39|250.10|2,522.89|23,610.58|234,847.74|2,293,163.63|7,435,385.66
|[CommonsCsvParser 1.0](http://commons.apache.org/proper/commons-csv)|21.12|42.51|254.67|2,558.45|24,915.98|247,869.21|2,427,771.47|7,717,016.95
|[GenJavaParser 1.0](http://www.osjava.org/genjava/multiproject/gj-csv/)|22.90|51.06|388.23|4,516.87|43,293.45|439,501.07|4,696,391.12|14,800,000.00
|[UnivocityParser 1.0.3](https://github.com/uniVocity/univocity-parsers)|4,467.26|4,476.80|4,603.88|5,994.07|20,270.97|155,576.21|1,459,677.87|4,639,997.25
|[Bean IO 2.1.0](http://beanio.org/)|24.12|61.14|501.49|5,656.26|54,608.29|587,428.25|5,652,539.16|17,933,796.24

Latency difference in % from jackson parser
![Difference from jackson parser](https://raw.githubusercontent.com/arnaudroger/csv-parsers-comparison/master/src/main/resources/charts/java_6_quote.png)

# Java 7 Results

## No quote results

the unit is us per reading the file of nbrows. The lower the better.

|Parser/nbrows|1|10|100|1000|10000|100000|1000000|3173959
|------|------:|----:|----:|----:|----:|----:|----:|----:|
|[JacksonParser 2.4.2](https://github.com/FasterXML/jackson-dataformat-csv)|20.24|28.21|111.92|955.18|9,493.98|94,809.19|930,487.07|2,976,824.49
|[SimpleFlatMapperParser 1.2.0](https://github.com/arnaudroger/SimpleFlatMapper)|20.55|27.28|91.43|1,023.67|7,459.68|74,660.33|744,521.08|2,321,910.07
|[JCsvParser 1.4.0](https://code.google.com/p/jcsv/)|21.26|28.68|105.15|1,011.24|9,524.07|94,574.76|934,419.76|2,811,022.42
|[SimpleCsvParser 2.0](https://github.com/quux00/simplecsv)|22.04|34.95|172.58|1,412.42|16,995.40|160,371.04|1,603,714.95|4,987,105.28
|[OpenCsvParser 2.3](http://opencsv.sourceforge.net/)|21.57|33.50|165.77|1,627.72|14,437.39|148,230.18|1,424,704.26|4,355,408.10
|[SuperCsvParser 2.2.0](http://supercsv.sourceforge.net/)|22.53|34.14|146.19|1,641.76|15,492.30|133,140.56|1,282,506.94|3,968,221.65
|[OsterMillerParser 1.07.00](http://ostermiller.org/utils/)|23.32|32.93|142.80|1,254.54|11,989.83|122,604.50|1,205,519.51|4,075,179.72
|[JavaCsvParser 2.0](http://sourceforge.net/projects/javacsv/)|29.17|40.69|171.89|1,583.93|15,452.34|151,460.41|1,497,675.96|4,789,507.69
|[WayIoParser 1.6.0](http://www.objectos.com.br/)|28.63|44.43|212.83|1,896.42|19,045.36|181,337.58|1,832,200.21|5,644,370.67
|[CommonsCsvParser 1.0](http://commons.apache.org/proper/commons-csv)|19.28|34.18|178.34|1,780.89|16,980.91|171,601.39|1,667,304.99|5,343,597.19
|[GenJavaParser 1.0](http://www.osjava.org/genjava/multiproject/gj-csv/)|22.18|43.27|308.31|3,376.42|38,819.72|391,391.19|4,099,708.92|9,630,511.79
|[UnivocityParser 1.0.3](https://github.com/uniVocity/univocity-parsers)|4,329.47|4,332.47|4,516.22|5,855.99|17,534.36|130,439.24|1,217,036.03|3,875,463.28
Currently, three parsers stand out as the fastest CSV parsers for Java:

Latency difference in % from jackson parser
![Difference from jackson parser](https://raw.githubusercontent.com/arnaudroger/csv-parsers-comparison/master/src/main/resources/charts/java_7_noquote.png)
*uniVocity-parsers*, *SimpleFlatMapper* and *Jackson CSV*. Keep in mind that *Simpleflatmapper* is a very simple implementation that does not provide any customization options.

It is impressive how a change in the JDK affects the results. We are extremely proud to see the huge performance advantage [uniVocity-parsers](http://www.univocity.com/pages/about-parsers) offers over most of the other parsers. Not to mention the unique features that put our parsers among the faster, most feature complete and extensible text parsing architectures available for the Java platform.

We will keep working to improve the performance of our parsers, and will update the results of this benchmark every time a new parser is added to the list.


## Quote results

the unit is us per reading the file of nbrows. The lower the better.

|Parser/nbrows|1|10|100|1000|10000|100000|1000000|3173959
|------|------:|----:|----:|----:|----:|----:|----:|----:|
|[JacksonParser 2.4.2](https://github.com/FasterXML/jackson-dataformat-csv)|20.47|31.27|148.96|1,453.41|13,931.26|138,685.49|1,365,335.11|4,363,888.96
|[SimpleFlatMapperParser 1.2.0](https://github.com/arnaudroger/SimpleFlatMapper)|20.80|32.08|107.77|942.46|11,647.54|102,628.48|982,690.25|2,635,456.90
|[JCsvParser 1.4.0](https://code.google.com/p/jcsv/)|21.80|33.19|146.64|1,360.52|13,421.54|136,428.87|1,325,533.89|4,236,211.65
|[SimpleCsvParser 2.0](https://github.com/quux00/simplecsv)|22.73|40.94|212.33|2,203.63|20,136.39|203,944.21|2,002,961.29|6,348,187.18
|[OpenCsvParser 2.3](http://opencsv.sourceforge.net/)|22.13|36.84|177.19|2,185.44|19,897.37|199,481.46|2,081,256.96|6,610,777.70
|[SuperCsvParser 2.2.0](http://supercsv.sourceforge.net/)|22.57|36.87|168.28|1,714.92|15,760.05|163,915.88|1,616,134.18|4,932,034.29
|[OsterMillerParser 1.07.00](http://ostermiller.org/utils/)|23.53|39.27|195.17|1,706.05|17,354.48|168,445.49|1,673,356.38|5,297,194.44
|[JavaCsvParser 2.0](http://sourceforge.net/projects/javacsv/)|29.40|43.03|193.09|1,840.75|17,614.13|176,080.17|1,732,943.33|5,525,471.43
|[WayIoParser 1.6.0](http://www.objectos.com.br/)|28.73|49.97|230.92|2,637.67|24,563.24|246,650.74|2,451,107.83|7,725,632.94
|[CommonsCsvParser 1.0](http://commons.apache.org/proper/commons-csv)|20.36|41.24|252.90|2,385.53|24,277.71|244,260.14|2,403,832.58|7,658,031.91
|[GenJavaParser 1.0](http://www.osjava.org/genjava/multiproject/gj-csv/)|22.58|50.01|378.46|4,069.74|48,406.71|479,780.80|4,869,382.85|15,486,546.01
|[UnivocityParser 1.0.3](https://github.com/uniVocity/univocity-parsers)|4,349.93|4,356.36|4,492.51|6,085.43|20,121.76|154,325.93|1,460,668.11|4,618,588.49
|[Bean IO 2.1.0](http://beanio.org/)|24.15|62.62|503.72|5,371.11|59,605.92|608,495.75|5,708,588.10|18,253,867.13

Latency difference in % from jackson parser
![Difference from jackson parser](https://raw.githubusercontent.com/arnaudroger/csv-parsers-comparison/master/src/main/resources/charts/java_7_quote.png)


# Java 8 Results

## No quote results

the unit is us per reading the file of nbrows. The lower the better.

|Parser/nbrows|1|10|100|1000|10000|100000|1000000|3173959
|------|------:|----:|----:|----:|----:|----:|----:|----:|
|[JacksonParser 2.4.2](https://github.com/FasterXML/jackson-dataformat-csv)|16.97|24.77|100.55|901.69|8,896.07|94,738.75|853,498.25|2,632,276.44
|[SimpleFlatMapperParser 1.2.0](https://github.com/arnaudroger/SimpleFlatMapper)|17.53|23.58|94.14|885.84|8,468.54|71,266.72|687,330.28|2,544,713.83
|[JCsvParser 1.4.0](https://code.google.com/p/jcsv/)|18.08|27.02|117.38|1,156.94|10,631.32|108,310.56|1,043,085.15|3,538,204.72
|[SimpleCsvParser 2.0](https://github.com/quux00/simplecsv)|19.18|32.37|169.22|1,628.87|15,464.43|157,553.29|1,562,049.57|4,877,498.34
|[OpenCsvParser 2.3](http://opencsv.sourceforge.net/)|18.84|31.93|161.40|1,554.06|15,182.50|151,518.48|1,518,520.20|4,789,898.43
|[SuperCsvParser 2.2.0](http://supercsv.sourceforge.net/)|19.21|32.62|168.36|1,302.04|12,223.01|128,351.83|1,254,158.67|4,406,608.49
|[OsterMillerParser 1.07.00](http://ostermiller.org/utils/)|20.10|30.42|138.27|1,240.67|12,019.66|122,622.17|1,215,447.04|3,778,397.37
|[JavaCsvParser 2.0](http://sourceforge.net/projects/javacsv/)|26.02|37.50|167.64|1,540.67|14,925.34|154,624.11|1,513,953.07|4,714,272.93
|[WayIoParser 1.6.0](http://www.objectos.com.br/)|24.68|41.87|217.66|2,065.52|19,179.45|193,070.96|1,924,073.63|6,076,373.58
|[CommonsCsvParser 1.0](http://commons.apache.org/proper/commons-csv)|16.41|30.18|169.53|1,728.94|15,484.71|170,333.01|1,683,754.64|5,322,571.13
|[GenJavaParser 1.0](http://www.osjava.org/genjava/multiproject/gj-csv/)|18.45|42.60|308.68|3,326.24|32,579.51|339,035.43|3,756,598.00|8,704,666.27
|[UnivocityParser 1.0.3](https://github.com/uniVocity/univocity-parsers)|4,201.40|4,218.65|4,336.74|5,640.11|17,485.71|131,026.03|1,219,344.32|3,840,187.86

Latency difference in % from jackson parser
![Difference from jackson parser](https://raw.githubusercontent.com/arnaudroger/csv-parsers-comparison/master/src/main/resources/charts/java_8_noquote.png)


## Quote results

the unit is us per reading the file of nbrows. The lower the better.

|Parser/nbrows|1|10|100|1000|10000|100000|1000000|3173959
|------|------:|----:|----:|----:|----:|----:|----:|----:|
|[JacksonParser 2.4.2](https://github.com/FasterXML/jackson-dataformat-csv)|17.05|27.53|133.20|1,363.76|13,582.91|141,354.32|1,198,963.25|3,855,814.01
|[SimpleFlatMapperParser 1.2.0](https://github.com/arnaudroger/SimpleFlatMapper)|17.96|28.95|103.55|903.07|10,399.61|98,014.00|987,683.82|2,579,806.64
|[JCsvParser 1.4.0](https://code.google.com/p/jcsv/)|18.35|29.79|144.35|1,392.68|13,206.78|140,968.94|1,360,875.42|4,232,535.86
|[SimpleCsvParser 2.0](https://github.com/quux00/simplecsv)|19.60|35.99|205.69|2,017.18|19,509.33|195,672.45|1,923,729.58|6,147,489.91
|[OpenCsvParser 2.3](http://opencsv.sourceforge.net/)|19.75|38.65|225.62|2,272.33|20,009.25|204,661.36|2,046,529.59|6,358,606.13
|[SuperCsvParser 2.2.0](http://supercsv.sourceforge.net/)|19.59|34.89|186.03|1,664.23|16,066.05|164,121.47|1,609,226.70|5,159,231.48
|[OsterMillerParser 1.07.00](http://ostermiller.org/utils/)|20.78|35.23|181.34|1,656.05|16,340.60|167,926.99|1,666,674.45|5,311,749.90
|[JavaCsvParser 2.0](http://sourceforge.net/projects/javacsv/)|26.44|39.48|186.03|1,814.79|17,795.90|181,730.28|1,796,715.00|5,736,254.13
|[WayIoParser 1.6.0](http://www.objectos.com.br/)|25.16|49.25|282.85|2,645.04|24,383.32|249,550.40|2,392,075.74|7,806,415.77
|[CommonsCsvParser 1.0](http://commons.apache.org/proper/commons-csv)|16.87|34.23|209.50|2,074.49|20,196.62|200,649.14|1,974,438.21|6,298,800.79
|[GenJavaParser 1.0](http://www.osjava.org/genjava/multiproject/gj-csv/)|19.01|47.86|373.35|3,941.18|39,546.31|422,574.06|4,899,920.83|16,005,336.01
|[UnivocityParser 1.0.3](https://github.com/uniVocity/univocity-parsers)|4,208.47|4,205.89|4,359.03|5,949.49|20,120.54|153,626.12|1,445,956.53|4,607,323.12
|[Bean IO 2.1.0](http://beanio.org/)|20.41|58.87|463.19|4,987.58|52,391.02|535,415.79|6,081,133.21|19,282,239.96

Latency difference in % from jackson parser
![Difference from jackson parser](https://raw.githubusercontent.com/arnaudroger/csv-parsers-comparison/master/src/main/resources/charts/java_8_quote.png)


# Comparaison across Java 6, 7 and 8

the unit is us per reading the file of 3173959 rows. The lower the better.

|Parser|Java 6|Java 7|Java 8
|----|---:|---:|----:|
|SimpleFlatMapperParser|2,342,475.25|2,321,910.07|2,544,713.83
|JCsvParser|4,522,937.51|2,811,022.42|3,538,204.72
|JacksonParser|2,862,200.83|2,976,824.49|2,632,276.44
|UnivocityParser|3,909,024.04|3,875,463.28|3,840,187.86
|SuperCsvParser|5,256,495.20|3,968,221.65|4,406,608.49
|OsterMillerParser|4,746,388.14|4,075,179.72|3,778,397.37
|OpenCsvParser|4,156,496.28|4,355,408.10|4,789,898.43

![Effect of java version](https://raw.githubusercontent.com/arnaudroger/csv-parsers-comparison/master/src/main/resources/charts/javacrossversion.png)

