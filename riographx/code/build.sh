#!/bin/bash


svn update

mvn clean assembly:assembly -DdescriptorId=jar-with-dependencies

chmod 0775 build.sh
chmod 0775 deploy.sh
chmod 0775 run.sh



