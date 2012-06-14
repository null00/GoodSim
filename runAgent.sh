#!/bin/bash

cd `dirname $0`
BASEDIR=`pwd`
cd -

jarfile=$BASEDIR/goodsim/target/goodsim-0.1-jar-with-dependencies.jar
client=pl.edu.agh.goodsim.client.ClientAgent
producer=pl.edu.agh.goodsim.producer.ProducerAgent
java -cp $jarfile \
 -Dcom.sun.management.jmxremote.port=1099 \
 -Dcom.sun.management.jmxremote.authenticate=false \
 -Dcom.sun.management.jmxremote.ssl=false \
 pl.edu.agh.goodsim.jmx.JademxAgentTesting

