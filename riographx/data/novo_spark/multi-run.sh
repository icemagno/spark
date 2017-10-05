#!/bin/bash


ssh sparkmaster "rm /usr/local/spark/logs/*; rm /usr/local/hadoop/logs/*; rm -r /usr/lib/riographx/grafos/"

ssh sparknode01 "rm /usr/local/spark/logs/*; rm /usr/local/hadoop/logs/*; rm -r /usr/lib/riographx/grafos/"

ssh sparknode02 "rm /usr/local/spark/logs/*; rm /usr/local/hadoop/logs/*; rm -r /usr/lib/riographx/grafos/"

ssh sparknodedatabase "rm /usr/local/spark/logs/*; rm /usr/local/hadoop/logs/*; rm -r /usr/lib/riographx/grafos/"

 


