#!/bin/bash

hdfs dfs -rm /spark/codes/spark-samples.jar

hdfs dfs -put spark-samples.jar /spark/codes/

