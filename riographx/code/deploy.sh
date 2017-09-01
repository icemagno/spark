#!/bin/bash

#hdfs dfs -rm -r /spark

hdfs dfs -rm /riographx/riographx-0.1.jar
hdfs dfs -put target/riographx-0.1.jar /riographx/

