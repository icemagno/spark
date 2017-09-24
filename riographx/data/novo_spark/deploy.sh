#!/bin/bash

#hdfs dfs -rm -r /spark

mv target/riographx-0.1-jar-with-dependencies.jar target/riographx.jar

hdfs dfs -rm -skipTrash /riographx/riographx.jar
hdfs dfs -put target/riographx.jar /riographx/

#hdfs dfs -rm /riographx/sage.sh
#hdfs dfs -put sage.sh /riographx/



