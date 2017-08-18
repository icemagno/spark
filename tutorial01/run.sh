#!/bin/bash

spark-submit --class com.letsprog.learning.samples.transformations.PostgreSQLConnect  --master spark://sparkmaster:6066 --deploy-mode cluster hdfs://sparkmaster:9000/spark/codes/spark-samples.jar

