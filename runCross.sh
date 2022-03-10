export JAVA_HOME=$(/usr/libexec/java_home -v 1.6)
java -jar target/benchmarks.jar "JCsv|SimpleFlat|Univo|Jackson" -p inputFile=./worldcitiespop.txt -p quoted=false -rf csv -rff result_java6.csv -f 5 
export JAVA_HOME=$(/usr/libexec/java_home -v 1.7)
java -jar target/benchmarks.jar "JCsv|SimpleFlat|Univo|Jackson" -p inputFile=./worldcitiespop.txt -p quoted=false -rf csv -rff result_java7.csv -f 5
export JAVA_HOME=$(/usr/libexec/java_home -v 1.8)
java -jar target/benchmarks.jar "JCsv|SimpleFlat|Univo|Jackson" -p inputFile=./worldcitiespop.txt -p quoted=false -rf csv -rff result_java8.csv -f 5

