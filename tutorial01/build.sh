#!/bin/bash


svn update

mvn clean assembly:assembly -DdescriptorId=jar-with-dependencies

cp target/spark-samples-0.0.1-SNAPSHOT-jar-with-dependencies.jar ./spark-samples.jar

