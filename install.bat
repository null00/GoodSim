@echo off

set basedir=%~dp0

cd %basedir%\goodsim 
call mvn org.apache.maven.plugins:maven-install-plugin:2.3.1:install-file ^
                         -Dfile=${project.basedir}\..\sources\jademx.jar -DgroupId=jade.jademx ^
                         -DartifactId=jademx -Dversion=0.4.1 ^
                         -Dpackaging=jar -DlocalRepositoryPath=${basedir}/local-repo
call mvn org.apache.maven.plugins:maven-install-plugin:2.3.1:install-file ^
                         -Dfile=${project.basedir}\..\sources\jade.jar -DgroupId=com.tilab.jade ^
                         -DartifactId=jade -Dversion=3.4.0 ^
                         -Dpackaging=jar -DlocalRepositoryPath=${basedir}/local-repo						 

call mvn clean install assembly:single
cd %basedir%