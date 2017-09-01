#!/bin/bash


svn update

mvn clean assembly:assembly -DdescriptorId=jar-with-dependencies



