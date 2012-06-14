#!/bin/bash

cd `dirname $0`
BASEDIR=`pwd`
cd -

cd $BASEDIR/goodsim
mvn org.apache.maven.plugins:maven-install-plugin:2.3.1:install-file -Dfile=${project}./../sources/jademx.jar -DgroupId=jade.jademx -DartifactId=jademx -Dversion=0.4.1 -Dpackaging=jar -DlocalRepositoryPath=$BASEDIR/goodsim/local-repo
mvn org.apache.maven.plugins:maven-install-plugin:2.3.1:install-file -Dfile=${project}./../sources/jade.jar -DgroupId=com.tilab.jade -DartifactId=jade -Dversion=3.4.0 -Dpackaging=jar -DlocalRepositoryPath=$BASEDIR/goodsim/local-repo
mvn clean install assembly:single
cd $BASEDIR
