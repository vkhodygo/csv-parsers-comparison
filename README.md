Fork of [uniVocity/csv-parsers-comparison/](https://github.com/uniVocity/csv-parsers-comparison/)

The goal is to provides more details results and cross validation. I still have issue to reproduce the univocity parser result. 
There is some change that went in there that improve results needs to run it again - it takes overnight -.


with java 7.
Java 8 TieredCompilation seems to affect the results quite a bit.

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

Non quoted results

|Parser/nbrows|1|10|100|1000|10000|100000|1000000|3173959
|------|------:|----:|----:|----:|----:|----:|----:|----:|
|JacksonParser|25.4|33.9|118.0|935.5|9,531.2|95,635.7|932,760.4|2,966,018.8
|JCsvParser|26.2|33.7|109.6|1,003.2|9,432.1|95,050.9|922,299.2|2,776,024.7
|SimpleFlatMapperParser|25.7|33.0|104.4|1,134.0|8,406.9|84,223.2|835,148.6|2,771,474.3
|OsterMillerParser|28.6|38.5|147.6|1,253.3|11,886.6|120,832.3|1,193,369.5|4,010,008.4
|SimpleCsvParser|22.1|35.4|169.5|1,397.1|17,075.3|158,092.5|1,539,145.3|4,984,203.5
|JavaCsvParser|33.7|46.2|179.4|1,590.2|15,120.3|150,648.7|1,490,998.4|4,748,643.7
|OpenCsvParser|26.9|38.9|166.1|1,599.2|15,717.9|159,016.0|1,597,855.5|4,275,694.1
|SuperCsvParser|27.4|39.5|157.0|1,649.3|15,656.4|130,558.6|1,280,508.6|3,860,114.0
|CommonsCsvParser|19.2|34.1|176.8|1,755.2|16,693.0|168,543.8|1,646,064.9|5,275,065.5
|GenJavaParser|21.8|33.3|149.1|2,039.0|14,588.2|146,585.4|1,430,528.4|4,549,869.5
|WayIoParser|33.8|49.6|212.4|2,046.2|18,916.9|200,385.1|1,953,943.1|5,585,643.8
|UnivocityParser|4,319.8|4,404.0|4,660.0|5,842.5|17,329.7|129,057.5|1,193,539.4|3,802,930.2


![Difference from jackson parser](https://raw.githubusercontent.com/arnaudroger/csv-parsers-comparison/master/src/main/resources/charts/jmh-difference-noquote.png)


Quoted results

|Parser/nbrows|1|10|100|1000|10000|100000|1000000|3173959
|------|------:|----:|----:|----:|----:|----:|----:|----:|
|JacksonParser|25.9|36.5|155.4|1,450.2|13,924.5|140,094.7|1,374,519.2|4,395,992.9
|JCsvParser|26.8|38.3|149.8|1,352.1|13,330.0|134,604.6|1,308,747.0|4,159,390.0
|SimpleFlatMapperParser|25.8|36.6|133.2|1,574.7|12,201.5|122,437.0|1,181,398.6|3,622,156.1
|OsterMillerParser|28.8|44.5|199.0|1,699.8|17,194.8|164,694.9|1,641,240.1|5,184,944.0
|SimpleCsvParser|22.8|40.5|209.9|2,189.2|19,877.4|201,525.7|1,965,663.3|6,290,170.4
|JavaCsvParser|33.7|48.9|202.8|1,879.8|17,581.3|175,881.3|1,725,298.5|5,488,762.1
|OpenCsvParser|27.2|42.0|181.5|2,116.9|20,248.0|206,368.5|2,027,979.6|6,289,100.0
|SuperCsvParser|27.5|42.2|169.1|1,815.6|15,834.3|160,276.6|1,583,347.7|5,001,051.4
|CommonsCsvParser|20.3|41.1|249.5|2,367.8|23,998.3|242,037.2|2,390,732.2|7,608,694.8
|GenJavaParser|22.1|34.9|169.6|2,366.1|16,865.7|170,714.5|1,657,820.4|4,615,315.3
|WayIoParser|34.2|54.9|234.3|2,614.1|24,157.8|250,319.9|2,398,465.8|7,822,576.0
|UnivocityParser|4,342.5|4,445.2|4,622.5|6,000.0|20,099.6|164,562.4|1,574,205.0|4,903,673.4

![Difference from jackson parser](https://raw.githubusercontent.com/arnaudroger/csv-parsers-comparison/master/src/main/resources/charts/jmh-difference-quote.png)
