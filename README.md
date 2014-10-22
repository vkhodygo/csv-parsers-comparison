Fork of [uniVocity/csv-parsers-comparison/](https://github.com/uniVocity/csv-parsers-comparison/)

# Summary

[SimpleFlatMapper](https://github.com/arnaudroger/SimpleFlatMapper/) and [Jackson](https://github.com/FasterXML/jackson-dataformat-csv) are the fastest one in those runs. SFM tends to be faster in most cases apart from the 1000 rows with no quotes in java 6 and 7.

[Univocity](https://github.com/uniVocity/univocity-parsers) has a stiff initialisation price to pay that makes a lean heavily on the run under 100000 rows.

Some parser can have sensibly different resuslt depending on the java version. TieredCompilation - which is enabled by default in java 8 - seems to have mixed results accross the parsers. Jcsv for example is pretty bad under java 6, pretty good on java 7 but degrade quote a bit in java 8.

Some of the result don't match the orginal benchmark. It could be link to the difference of hardware - also I do think that the  fit in the page cache - or of the benchmark technology used. Jmh take care of lot of benchmark pitfall that the original benchmark could have fall into.

I  can think of the following explanation:
 - hardware difference - Quote skeptical about that as the file would fit in the page cache -.
 - dead code elimination, possible to check looking at the bytecode.
 - hotspot optimisation interference, possible to check running the original benchmark in separate jvm.
	


# Benchmarks

[worldcitiespop.txt](https://worlddatapro.googlecode.com/svn-history/r2/trunk/data/worldcitiespop.txt)

```
echo "performance" | sudo tee /sys/devices/system/cpu/cpu*/cpufreq/scaling_governor;
echo "2000000" | sudo tee /sys/devices/system/cpu/cpu*/cpufreq/scaling_min_freq;
echo "2000000" | sudo tee /sys/devices/system/cpu/cpu*/cpufreq/scaling_max_freq


java -jar target/benchmarks.jar -tu us \
  -wi 10 -i 10 -f 5 \
  -p nbRows=1,10,100,1000,10000,100000,1000000,-1 \
  -rf csv -foe -p inputFile=./worldcitiespop.txt \
  -rff jmh.csv -jvmArgs="-Xmx1g -Xms1g"
```

[Excel data](https://raw.githubusercontent.com/arnaudroger/csv-parsers-comparison/master/src/main/results/consolidate.xls)

# Java 6 Results

## No quote results

|Parser/nbrows|1|10|100|1000|10000|100000|1000000|3173959
|------|------:|----:|----:|----:|----:|----:|----:|----:|
|[JacksonParser 2.4.2](https://github.com/FasterXML/jackson-dataformat-csv)|20.14|28.54|108.97|952.58|9,222.06|91,757.79|902,942.50|2,851,125.72
|[SimpleFlatMapperParser 0.9.12](https://github.com/arnaudroger/SimpleFlatMapper)|20.34|28.13|104.90|1,149.31|9,044.64|90,003.15|873,912.55|2,789,921.22
|[JCsvParser 1.4.0](https://code.google.com/p/jcsv/)|21.69|33.91|162.28|1,344.48|12,630.11|128,097.57|1,231,854.16|4,549,693.90
|[SimpleCsvParser 2.0](https://github.com/quux00/simplecsv)|21.60|35.47|176.17|1,391.70|14,512.91|167,615.28|1,641,115.82|5,214,694.74
|[OpenCsvParser 2.3](http://opencsv.sourceforge.net/)|21.17|33.34|145.33|1,429.06|12,940.15|130,245.52|1,310,801.09|4,172,193.53
|[SuperCsvParser 2.2.0](http://supercsv.sourceforge.net/)|22.54|35.68|170.59|1,442.26|13,832.13|167,284.68|1,612,030.55|5,196,234.91
|[OsterMillerParser 1.07.00](http://ostermiller.org/utils/)|22.90|34.96|157.76|1,562.64|14,861.27|152,103.07|1,482,191.05|4,746,071.90
|[JavaCsvParser 2.0](http://sourceforge.net/projects/javacsv/)|29.70|41.16|176.35|1,590.14|15,074.16|152,171.43|1,494,948.99|4,775,132.43
|[WayIoParser 1.6.0](http://www.objectos.com.br/)|28.50|43.98|200.35|1,828.90|17,885.29|182,689.14|1,791,003.18|5,705,520.95
|[CommonsCsvParser 1.0](http://commons.apache.org/proper/commons-csv)|19.87|35.06|187.23|1,873.53|17,784.82|180,073.61|1,741,326.68|5,578,865.26
|[GenJavaParser 1.0](http://www.osjava.org/genjava/multiproject/gj-csv/)|22.09|43.14|310.17|3,646.43|34,528.72|346,179.33|3,562,115.56|9,631,608.76
|[UnivocityParser 1.0.3](https://github.com/uniVocity/univocity-parsers)|4,624.52|5,006.20|5,008.80|6,075.66|17,654.54|129,042.37|1,201,905.84|3,795,047.80

![Difference from jackson parser](https://raw.githubusercontent.com/arnaudroger/csv-parsers-comparison/master/src/main/resources/charts/java_6_noquote.png)


## Quote results

|Parser/nbrows|1|10|100|1000|10000|100000|1000000|3173959
|------|------:|----:|----:|----:|----:|----:|----:|----:|
|[JacksonParser 2.4.2](https://github.com/FasterXML/jackson-dataformat-csv)|20.63|31.69|142.94|1,454.11|13,957.70|139,468.68|1,370,024.09|4,379,875.03
|[SimpleFlatMapperParser 0.9.12](https://github.com/arnaudroger/SimpleFlatMapper)|21.04|31.16|142.52|1,326.36|13,237.90|132,848.42|1,316,683.27|4,175,796.02
|[JCsvParser 1.4.0](https://code.google.com/p/jcsv/)|22.46|40.89|227.27|2,139.80|21,736.68|219,171.21|2,116,901.19|6,718,787.43
|[SimpleCsvParser 2.0](https://github.com/quux00/simplecsv)|22.41|39.46|217.11|1,785.78|19,308.70|200,930.63|1,972,971.98|6,268,393.50
|[OpenCsvParser 2.3](http://opencsv.sourceforge.net/)|22.34|40.73|230.77|1,973.83|19,127.88|195,573.53|1,899,979.28|6,131,922.58
|[SuperCsvParser 2.2.0](http://supercsv.sourceforge.net/)|23.46|42.61|233.93|2,087.51|19,765.44|199,409.67|1,975,705.32|6,198,280.47
|[OsterMillerParser 1.07.00](http://ostermiller.org/utils/)|23.24|40.92|210.68|1,796.92|18,007.22|183,491.11|1,810,160.73|5,797,864.25
|[JavaCsvParser 2.0](http://sourceforge.net/projects/javacsv/)|30.86|43.03|194.30|1,827.08|18,049.43|185,671.19|1,772,861.87|5,680,237.18
|[WayIoParser 1.6.0](http://www.objectos.com.br/)|29.52|52.39|249.02|2,483.28|23,765.91|235,044.64|2,316,912.92|7,394,645.84
|[CommonsCsvParser 1.0](http://commons.apache.org/proper/commons-csv)|20.71|42.01|254.71|2,571.94|24,876.07|247,931.27|2,420,602.49|7,705,605.83
|[GenJavaParser 1.0](http://www.osjava.org/genjava/multiproject/gj-csv/)|22.76|50.71|386.60|4,512.58|43,296.35|439,620.82|4,703,368.15|14,728,170.89
|[UnivocityParser 1.0.3](https://github.com/uniVocity/univocity-parsers)|4,732.06|4,947.59|5,071.73|6,473.15|20,828.25|165,044.87|1,513,026.28|4,816,604.56


![Difference from jackson parser](https://raw.githubusercontent.com/arnaudroger/csv-parsers-comparison/master/src/main/resources/charts/java_6_quote.png)

# Java 7 Results

## No quote results

|Parser/nbrows|1|10|100|1000|10000|100000|1000000|3173959
|------|------:|----:|----:|----:|----:|----:|----:|----:|
|[JacksonParser 2.4.2](https://github.com/FasterXML/jackson-dataformat-csv)|19.72|28.09|111.16|955.60|9,486.58|94,629.70|930,430.98|2,960,501.85
|[SimpleFlatMapperParser 0.9.12](https://github.com/arnaudroger/SimpleFlatMapper)|20.36|27.61|103.33|1,124.17|8,918.57|88,887.92|873,013.64|2,714,072.18
|[JCsvParser 1.4.0](https://code.google.com/p/jcsv/)|21.08|28.28|105.21|994.30|9,477.74|96,240.90|926,729.23|2,802,376.69
|[SimpleCsvParser 2.0](https://github.com/quux00/simplecsv)|21.67|34.88|172.28|1,412.61|17,309.43|162,912.74|1,570,197.61|5,025,006.71
|[OpenCsvParser 2.3](http://opencsv.sourceforge.net/)|21.54|33.77|164.46|1,529.12|13,782.21|147,967.29|1,398,025.42|4,300,140.60
|[SuperCsvParser 2.2.0](http://supercsv.sourceforge.net/)|22.11|34.06|154.33|1,631.42|15,814.73|135,454.36|1,301,827.13|3,967,037.89
|[OsterMillerParser 1.07.00](http://ostermiller.org/utils/)|23.04|32.64|143.07|1,256.13|11,970.70|122,735.16|1,202,657.13|4,064,797.80
|[JavaCsvParser 2.0](http://sourceforge.net/projects/javacsv/)|28.75|40.16|171.69|1,562.31|15,254.07|151,074.98|1,503,069.17|4,787,941.69
|[WayIoParser 1.6.0](http://www.objectos.com.br/)|28.23|43.78|214.94|1,941.54|18,724.94|189,917.52|1,856,675.25|5,652,267.80
|[CommonsCsvParser 1.0](http://commons.apache.org/proper/commons-csv)|19.33|34.10|178.25|1,774.34|16,902.11|169,558.47|1,659,921.29|5,352,189.12
|[GenJavaParser 1.0](http://www.osjava.org/genjava/multiproject/gj-csv/)|22.27|43.13|306.12|3,345.93|38,595.54|388,596.62|3,986,230.89|9,642,639.70
|[UnivocityParser 1.0.3](https://github.com/uniVocity/univocity-parsers)|4,215.42|4,293.13|4,418.93|5,664.93|17,217.57|129,340.12|1,195,599.78|3,822,596.72

![Difference from jackson parser](https://raw.githubusercontent.com/arnaudroger/csv-parsers-comparison/master/src/main/resources/charts/java_7_noquote.png)


## Quote results

|Parser/nbrows|1|10|100|1000|10000|100000|1000000|3173959
|------|------:|----:|----:|----:|----:|----:|----:|----:|
|[JacksonParser 2.4.2](https://github.com/FasterXML/jackson-dataformat-csv)|19.92|30.72|148.93|1,446.06|13,840.25|138,428.79|1,358,130.02|4,376,274.69
|[SimpleFlatMapperParser 0.9.12](https://github.com/arnaudroger/SimpleFlatMapper)|20.61|31.25|135.15|1,446.35|12,455.33|125,513.91|1,230,899.12|3,995,240.37
|[JCsvParser 1.4.0](https://code.google.com/p/jcsv/)|21.75|32.60|145.16|1,385.20|13,489.66|135,248.58|1,324,806.99|4,207,007.68
|[SimpleCsvParser 2.0](https://github.com/quux00/simplecsv)|22.27|40.39|209.84|2,197.65|20,101.59|202,636.50|2,011,112.49|6,388,397.12
|[OpenCsvParser 2.3](http://opencsv.sourceforge.net/)|21.69|36.43|176.95|2,117.74|19,991.12|201,681.86|1,987,692.73|6,376,011.61
|[SuperCsvParser 2.2.0](http://supercsv.sourceforge.net/)|22.16|35.96|167.93|1,729.45|16,155.68|160,623.71|1,596,830.02|4,926,784.69
|[OsterMillerParser 1.07.00](http://ostermiller.org/utils/)|23.51|38.47|195.66|1,709.86|17,358.62|169,070.09|1,664,658.16|5,266,317.34
|[JavaCsvParser 2.0](http://sourceforge.net/projects/javacsv/)|29.02|42.45|195.94|1,852.78|17,586.17|176,731.05|1,738,325.62|5,528,868.09
|[WayIoParser 1.6.0](http://www.objectos.com.br/)|28.27|49.42|230.69|2,501.01|24,486.33|243,148.63|2,412,708.84|7,465,988.17
|[CommonsCsvParser 1.0](http://commons.apache.org/proper/commons-csv)|20.57|41.34|252.79|2,387.38|24,230.68|242,771.72|2,405,679.05|7,660,950.74
|[GenJavaParser 1.0](http://www.osjava.org/genjava/multiproject/gj-csv/)|22.60|49.65|377.72|4,066.59|47,756.03|480,664.71|4,874,111.33|15,190,696.92
|[UnivocityParser 1.0.3](https://github.com/uniVocity/univocity-parsers)|4,203.45|4,295.34|4,456.82|5,855.16|20,074.64|153,470.56|1,502,527.95|4,836,070.85

![Difference from jackson parser](https://raw.githubusercontent.com/arnaudroger/csv-parsers-comparison/master/src/main/resources/charts/java_7_quote.png)


# Java 8 Results

## No quote results

|Parser/nbrows|1|10|100|1000|10000|100000|1000000|3173959
|------|------:|----:|----:|----:|----:|----:|----:|----:|
|[JacksonParser 2.4.2](https://github.com/FasterXML/jackson-dataformat-csv)|16.80|24.76|100.85|903.99|8,699.96|95,342.90|851,312.95|2,607,463.27
|[SimpleFlatMapperParser 0.9.12](https://github.com/arnaudroger/SimpleFlatMapper)|17.28|23.80|87.96|797.60|8,471.34|78,414.08|691,011.68|2,581,990.07
|[JCsvParser 1.4.0](https://code.google.com/p/jcsv/)|17.99|26.86|118.79|1,137.38|10,770.70|108,396.97|1,066,288.51|3,328,120.16
|[SimpleCsvParser 2.0](https://github.com/quux00/simplecsv)|18.97|32.36|167.50|1,656.80|15,349.95|157,912.31|1,563,322.82|4,969,438.74
|[OpenCsvParser 2.3](http://opencsv.sourceforge.net/)|18.63|31.61|165.82|1,571.35|15,123.48|152,320.41|1,527,324.80|5,041,943.11
|[SuperCsvParser 2.2.0](http://supercsv.sourceforge.net/)|18.75|33.29|171.55|1,266.39|12,122.56|128,987.98|1,255,768.81|4,359,582.60
|[OsterMillerParser 1.07.00](http://ostermiller.org/utils/)|19.69|29.78|138.25|1,238.56|11,945.85|122,829.59|1,205,319.73|3,775,870.09
|[JavaCsvParser 2.0](http://sourceforge.net/projects/javacsv/)|25.79|37.29|167.28|1,539.67|14,820.35|152,660.45|1,512,063.66|4,780,302.99
|[WayIoParser 1.6.0](http://www.objectos.com.br/)|24.28|41.48|221.10|2,073.73|19,332.86|197,685.68|1,931,331.52|6,040,715.29
|[CommonsCsvParser 1.0](http://commons.apache.org/proper/commons-csv)|16.22|30.40|172.80|1,762.79|15,553.07|170,576.74|1,668,526.41|5,266,204.43
|[GenJavaParser 1.0](http://www.osjava.org/genjava/multiproject/gj-csv/)|18.35|42.38|309.84|3,319.83|31,267.07|338,284.36|3,824,016.51|8,809,981.80
|[UnivocityParser 1.0.3](https://github.com/uniVocity/univocity-parsers)|4,005.71|3,996.69|4,135.28|5,320.38|17,188.48|129,398.67|1,202,707.05|3,770,764.67

![Difference from jackson parser](https://raw.githubusercontent.com/arnaudroger/csv-parsers-comparison/master/src/main/resources/charts/java_8_noquote.png)


## Quote results

|Parser/nbrows|1|10|100|1000|10000|100000|1000000|3173959
|------|------:|----:|----:|----:|----:|----:|----:|----:|
|[JacksonParser 2.4.2](https://github.com/FasterXML/jackson-dataformat-csv)|16.99|27.37|133.32|1,361.05|13,616.01|138,347.92|1,202,962.07|3,935,519.98
|[SimpleFlatMapperParser 0.9.12](https://github.com/arnaudroger/SimpleFlatMapper)|17.48|27.59|134.89|1,121.10|11,571.75|121,116.62|1,272,813.64|3,975,643.19
|[JCsvParser 1.4.0](https://code.google.com/p/jcsv/)|18.04|29.94|142.80|1,414.93|13,364.13|134,749.64|1,330,923.09|4,270,070.58
|[SimpleCsvParser 2.0](https://github.com/quux00/simplecsv)|19.20|35.74|205.25|2,065.90|19,518.48|195,135.97|1,929,006.50|6,142,186.33
|[OpenCsvParser 2.3](http://opencsv.sourceforge.net/)|19.28|38.06|228.39|2,315.46|20,025.41|204,206.07|2,009,642.13|6,348,949.22
|[SuperCsvParser 2.2.0](http://supercsv.sourceforge.net/)|19.22|34.12|184.82|1,678.90|15,886.01|164,673.11|1,621,133.58|5,082,909.78
|[OsterMillerParser 1.07.00](http://ostermiller.org/utils/)|20.39|34.91|181.92|1,650.80|16,109.15|167,275.59|1,675,419.18|5,328,273.52
|[JavaCsvParser 2.0](http://sourceforge.net/projects/javacsv/)|26.27|39.39|186.39|1,832.84|17,840.73|181,308.08|1,799,855.68|5,731,016.69
|[WayIoParser 1.6.0](http://www.objectos.com.br/)|24.82|47.80|283.37|2,585.85|24,792.92|251,959.82|2,414,096.33|7,699,580.26
|[CommonsCsvParser 1.0](http://commons.apache.org/proper/commons-csv)|16.78|34.09|210.30|2,063.55|20,033.62|202,089.81|1,989,681.94|6,285,918.15
|[GenJavaParser 1.0](http://www.osjava.org/genjava/multiproject/gj-csv/)|18.81|47.48|376.40|3,954.85|40,847.36|418,792.38|4,998,343.04|16,011,964.79
|[UnivocityParser 1.0.3](https://github.com/uniVocity/univocity-parsers)|3,993.46|4,000.42|4,171.43|5,531.57|18,806.43|143,271.91|1,344,102.72|4,275,403.94

![Difference from jackson parser](https://raw.githubusercontent.com/arnaudroger/csv-parsers-comparison/master/src/main/resources/charts/java_8_quote.png)


# Comparaison across Java 6, 7 and 8


Us per op, the lower the better. Read full file.

|Parser|Java 6|Java 7|Java 8
|----|---:|---:|----:|
|SimpleFlatMapperParser|2,789,921.22|2,714,072.18|2,581,990.07
|JCsvParser|4,549,693.90|2,802,376.69|3,328,120.16
|JacksonParser|2,851,125.72|2,960,501.85|2,607,463.27
|UnivocityParser|3,795,047.80|3,822,596.72|3,770,764.67
|SuperCsvParser|5,196,234.91|3,967,037.89|4,359,582.60
|OsterMillerParser|4,746,071.90|4,064,797.80|3,775,870.09
|OpenCsvParser|4,172,193.53|4,300,140.60|5,041,943.11

![Effect of java version](https://raw.githubusercontent.com/arnaudroger/csv-parsers-comparison/master/src/main/resources/charts/javacrossversion.png)

