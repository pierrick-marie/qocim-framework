#!/bin/bash

HOME_SCRIPTS="$(cd $(dirname "$0")/../../scripts && pwd)"
. ${HOME_SCRIPTS}/utils.sh

CLIENT_ID=CapsuleHelloQoCIM
PORT_ID=2102
CLIENT="capsule.Main"

ARGS=" --uri mudebs://localhost:"${PORT_ID}"/"${CLIENT_ID}" --broker mudebs://localhost:2000/BrokerCollector --configuration-path "$(pwd)"/qocim_capsule_configuration.xml" 

# if the JAVA_HOME is not set, set to the home directory of the jvm
if [ -z $JAVA_HOME ]
then
    # jvm is in $JAVA_HOME/jre/bin/; therefore three 'dirname'
    JAVA_HOME=$(dirname $(dirname $(dirname $(readlink -f '/usr/bin/java'))))
fi

# default values
MEMORY_MIN=16
MEMORY_MAX=64

CLASSPATH=$CAPSULE_CLASSPATH${PATHSEP}"$(cd $(dirname "$0")/target/classes && pwd)"

JVM_ARGS="-Xms${MEMORY_MIN}m -Xmx${MEMORY_MAX}m -cp ${CLASSPATH}"

# start the client
CMD="java $JVM_ARGS $CLIENT ${ARGS}"

$CMD &
JAVA_PID=$!

echo "Hit return when you want to stop the demonstration"
read x

# kill both processes; may not be necessary, just to be sure
killed=0
if [ ! -z $JAVA_PID ]; then
	kill -9 $JAVA_PID &> /dev/null
	killed=1
fi

# print message
if [ $killed -eq 0 ]; then
    echo "The application $CLIENT_ID is not running"
else
    echo "The application $CLIENT_ID is stopped"
fi

