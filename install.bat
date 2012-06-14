@echo off

set basedir=%~dp0

cd %basedir%\goodsim 
call mvn clean install assembly:single
cd %basedir%