#!/bin/bash

HOME_SCRIPTS="$(cd $(dirname "$0")/../scripts && pwd)"
. ${HOME_SCRIPTS}/utils.sh

echo "Start the broker A"
ARGS=" --uri mudebs://localhost:2000/BrokerA" # --log ROUTING.TRACE

# check and create a user muDEBS directory
if [ ! -d ~/.mudebs ]; then
	mkdir ~/.mudebs
fi

# if the JAVA_HOME is not set, set to the home directory of the jvm
if [ -z $JAVA_HOME ]
then
    # jvm is in $JAVA_HOME/jre/bin/; therefore three 'dirname'
    JAVA_HOME=$(dirname $(dirname $(dirname $(readlink -f '/usr/bin/java'))))
fi

CMD="java -Xms${MEMORY_MIN}m -Xmx${MEMORY_MAX}m 
          -cp $BROKER_CLASSPATH mudebs.broker.BrokerMain ${ARGS}"

$CMD &
BROKER_PID=$!

echo "Hit return when you want to stop the broker"
read x

# kill both processes; may not be necessary, just to be sure
killed=0
if [ ! -z $BROKER_PID ]; then
	kill -9 $BROKER_PID &> /dev/null
	killed=1
fi
