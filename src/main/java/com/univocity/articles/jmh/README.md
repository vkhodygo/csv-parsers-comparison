
# How to run the jmh test

## Build from maven

```bash
mvn clean install
```

## Run the benchmark command
 
```bash
java -jar target/benchmarks.jar  -foe -p nbRows=10,1000,100000
```

## wait for the result

```
Benchmark                                                             (inputFile)  (nbRows)   Mode  Samples      Score  Score error  Units
c.u.a.j.JacksonParser.parseFile             src/main/resources/worldcitiespop.txt        10  thrpt      200  10634.664      103.170  ops/s
c.u.a.j.JacksonParser.parseFile             src/main/resources/worldcitiespop.txt      1000  thrpt      200   1433.715       19.636  ops/s
c.u.a.j.JacksonParser.parseFile             src/main/resources/worldcitiespop.txt    100000  thrpt      200     17.159        0.197  ops/s
c.u.a.j.OpenCsvParser.parseFile             src/main/resources/worldcitiespop.txt        10  thrpt      200  10529.881      128.572  ops/s
c.u.a.j.OpenCsvParser.parseFile             src/main/resources/worldcitiespop.txt      1000  thrpt      200    952.713       18.801  ops/s
c.u.a.j.OpenCsvParser.parseFile             src/main/resources/worldcitiespop.txt    100000  thrpt      200     13.013        0.153  ops/s
c.u.a.j.SimpleFlatMapperParser.parseFile    src/main/resources/worldcitiespop.txt        10  thrpt      200  10867.497      124.177  ops/s
c.u.a.j.SimpleFlatMapperParser.parseFile    src/main/resources/worldcitiespop.txt      1000  thrpt      200   1331.827       24.662  ops/s
c.u.a.j.SimpleFlatMapperParser.parseFile    src/main/resources/worldcitiespop.txt    100000  thrpt      200     21.349        0.201  ops/s
c.u.a.j.UnivocityParser.parseFile           src/main/resources/worldcitiespop.txt        10  thrpt      200    279.792        2.084  ops/s
c.u.a.j.UnivocityParser.parseFile           src/main/resources/worldcitiespop.txt      1000  thrpt      200    231.601        2.383  ops/s
c.u.a.j.UnivocityParser.parseFile           src/main/resources/worldcitiespop.txt    100000  thrpt      200     15.588        0.245  ops/s
```