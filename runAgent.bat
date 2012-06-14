@echo off

set basedir=%~dp0

set cp=%basedir%goodsim\target\goodsim-0.1-jar-with-dependencies.jar
set client=pl.edu.agh.goodsim.client.ClientAgent
set producer=pl.edu.agh.goodsim.producer.ProducerAgent
java -cp %cp% jade.Boot -agents "client1:%client%;client2:%client%;producer1:%producer%;producer2:%producer%"