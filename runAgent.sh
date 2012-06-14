#!/bin/bash

cd `dirname $0`
BASEDIR=`pwd`
cd -

jarfile=$BASEDIR/goodsim/target/goodsim-0.1-jar-with-dependencies.jar
client=pl.edu.agh.goodsim.client.ClientAgent
producer=pl.edu.agh.goodsim.producer.ProducerAgent
java -cp $jarfile pl.edu.agh.goodsim.jmx.JademxAgentTesting

