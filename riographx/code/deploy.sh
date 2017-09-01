#!/bin/bash

#hdfs dfs -rm -r /spark

mv target/riographx-0.1-jar-with-dependencies.jar target/riographx.jar

hdfs dfs -rm /riographx/riographx.jar
hdfs dfs -put target/riographx.jar /riographx/

