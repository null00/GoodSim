
#!/bin/bash

cd `dirname $0`
BASEDIR=`pwd`
cd -

cd $BASEDIR/goodsim
mvn clean install assembly:single
cd $BASEDIR
