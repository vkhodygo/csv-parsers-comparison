mvn clean install
~/orm-benchmark/src/main/scripts/cpuPreBenchmark.sh 
sudo update-alternatives --set java /usr/lib/jvm/java-6-oracle/jre/bin/java
java -jar target/benchmarks.jar -tu us -wi 10 -i 10 -f 5 -p nbRows=1,10,100,1000,10000,100000,1000000,-1 -rf csv -foe -p inputFile=./worldcitiespop.txt -rff jmh_java6.csv > log6
sudo update-alternatives --set java /usr/lib/jvm/java-7-oracle/jre/bin/java
java -jar target/benchmarks.jar -tu us -wi 10 -i 10 -f 5 -p nbRows=1,10,100,1000,10000,100000,1000000,-1 -rf csv -foe -p inputFile=./worldcitiespop.txt -rff jmh_java7.csv > log7
sudo update-alternatives --set java /usr/lib/jvm/java-8-oracle/jre/bin/java
java -jar target/benchmarks.jar -tu us -wi 10 -i 10 -f 5 -p nbRows=1,10,100,1000,10000,100000,1000000,-1 -rf csv -foe -p inputFile=./worldcitiespop.txt -rff jmh_java8.csv > log8
~/orm-benchmark/src/main/scripts/cpuPostBenchmark.sh 
echo over

