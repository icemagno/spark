#!/bin/bash

param_index=$1

spark-submit --class com.letsprog.learning.samples.transformations.PostgreSQLConnect  --master spark://sparkmaster:6066 --deploy-mode cluster hdfs://sparkmaster:9000/spark/codes/spark-samples.jar $param_index

