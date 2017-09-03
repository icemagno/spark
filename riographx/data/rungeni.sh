#!/bin/bash

# rungeni.sh ./geni.py ./output.txt ./graphtest.g6 "-a -b -c -d -e 3 -g"

echo "Geni File  : " $1
echo "Save to    : " $2
echo "Source File: " $3
echo "Parameters : " $4
sage -c 'load("'"$1"'");geni("'"$2"'","'"$3"'","'"$4"'")'
