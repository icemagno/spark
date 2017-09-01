#!/bin/bash

param_index=$1

spark-submit --class br.com.cmabreu.Main  --master spark://sparkmaster:6066 --deploy-mode cluster hdfs://sparkmaster:9000/riographx/riographx-0.1.jar $param_index

