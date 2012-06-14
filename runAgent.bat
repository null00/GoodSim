@echo off

set basedir=%~dp0

set cp=%basedir%goodsim\target\goodsim-0.1-jar-with-dependencies.jar
set client=pl.edu.agh.goodsim.client.ClientAgent
set producer=pl.edu.agh.goodsim.producer.ProducerAgent
java -cp %cp% pl.edu.agh.goodsim.jmx.JademxAgentTesting -Dcom.sun.management.jmxremote.port=1098 -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false 