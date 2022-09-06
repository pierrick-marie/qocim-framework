#!/bin/bash

MAVEN_REPOS=${HOME}/.m2/repository
MUCONTEXT_VERSION="0.15.2"
MUDEBS_VERSION="0.17.3"
QOCIM_VERSION="0.14.2-SNAPSHOT"

LOG4J_VERSION="1.2.17"
GSON_VERSION="2.3.1"
BALANA_VERSION="1.0.0-wso2v7"
HAMCREST_VERSION="1.3"
LOGGING_VERSION="1.1.1"

# default values
MEMORY_MIN=128	
MEMORY_MAX=512
	
BROKER_URI=""
START_BG="&"
PATHSEP=':'

COMMON_JAR=${MAVEN_REPOS}/muDEBS/muDEBS-common/${MUDEBS_VERSION}/muDEBS-common-${MUDEBS_VERSION}.jar
COMMUNICATION_JAR=${MAVEN_REPOS}/muDEBS/muDEBS-communication-javanio/${MUDEBS_VERSION}/muDEBS-communication-javanio-${MUDEBS_VERSION}.jar
BROKER_JAR=${MAVEN_REPOS}/muDEBS/muDEBS-broker/${MUDEBS_VERSION}/muDEBS-broker-${MUDEBS_VERSION}.jar
CLIENT_JAR=${MAVEN_REPOS}/muDEBS/muDEBS-client/${MUDEBS_VERSION}/muDEBS-client-${MUDEBS_VERSION}.jar
CLIENT_DELEGATE_JAR=
SCRIPTING_JAR=${MAVEN_REPOS}/muDEBS/muDEBS-scripting/${MUDEBS_VERSION}/muDEBS-scripting-${MUDEBS_VERSION}.jar
LOG4J_JAR=${MAVEN_REPOS}/log4j/log4j/${LOG4J_VERSION}/log4j-${LOG4J_VERSION}.jar
GSON_JAR=${MAVEN_REPOS}/com/google/code/gson/gson/${GSON_VERSION}/gson-${GSON_VERSION}.jar
XACML_JAR=${MAVEN_REPOS}/org/wso2/balana/org.wso2.balana/${BALANA_VERSION}/org.wso2.balana-${BALANA_VERSION}.jar
HAMCREST_JAR=${MAVEN_REPOS}/org/hamcrest/hamcrest-core/${HAMCREST_VERSION}/hamcrest-core-${HAMCREST_VERSION}.jar
COMMONS_LOGGING_JAR=${MAVEN_REPOS}/commons-logging/commons-logging/${LOGGING_VERSION}/commons-logging-${LOGGING_VERSION}.jar

BROKER_CLASSPATH=${COMMON_JAR}${PATHSEP}${COMMUNICATION_JAR}${PATHSEP}${COMMONS_LOGGING_JAR}${PATHSEP}${BALANA_JAR}${PATHSEP}${BROKER_JAR}${PATHSEP}${LOG4J_JAR}${PATHSEP}${GSON_JAR}${PATHSEP}${XACML_JAR}

MUCONTEXT_API=${MAVEN_REPOS}/muContext/muContext-api/${MUCONTEXT_VERSION}/muContext-api-${MUCONTEXT_VERSION}.jar
MUCONTEXT_DATAMODELS=${MAVEN_REPOS}/muContext/data-model/muContext-data-model-context/${MUCONTEXT_VERSION}/muContext-data-model-context-${MUCONTEXT_VERSION}.jar${PATHSEP}${MAVEN_REPOS}/muContext/data-model/muContext-data-model-contract/${MUCONTEXT_VERSION}/muContext-data-model-contract-${MUCONTEXT_VERSION}.jar${PATHSEP}${MAVEN_REPOS}/muContext/data-model/muContext-data-model-multiscalability/${MUCONTEXT_VERSION}/muContext-data-model-multiscalability-${MUCONTEXT_VERSION}.jar${PATHSEP}${MAVEN_REPOS}/muContext/data-model/muContext-data-model-recommendation/${MUCONTEXT_VERSION}/muContext-data-model-recommendation-${MUCONTEXT_VERSION}.jar
MUCONTEXT_COLLECTOR=${MAVEN_REPOS}/muContext/muContext-collector/${MUCONTEXT_VERSION}/muContext-collector-${MUCONTEXT_VERSION}.jar
MUCONTEXT_CAPSULE=${MAVEN_REPOS}/muContext/muContext-capsule/${MUCONTEXT_VERSION}/muContext-capsule-${MUCONTEXT_VERSION}.jar
MUCONTEXT_APPLICATION=${MAVEN_REPOS}/muContext/muContext-application/${MUCONTEXT_VERSION}/muContext-application-${MUCONTEXT_VERSION}.jar

QOCIM_COMMON=${MAVEN_REPOS}/qocim-common/qocim-common/${QOCIM_VERSION}/qocim-common-${QOCIM_VERSION}.jar
QOCIM_TOOLS=${MAVEN_REPOS}/qocim-tools/qocim-capsule/${QOCIM_VERSION}/qocim-capsule-${QOCIM_VERSION}.jar${PATHSEP}${MAVEN_REPOS}/qocim-tools/qocim-common-functions/${QOCIM_VERSION}/qocim-common-functions-${QOCIM_VERSION}.jar${PATHSEP}${MAVEN_REPOS}/qocim-tools/qocim-freshnessindicator/${QOCIM_VERSION}/qocim-freshnessindicator-${QOCIM_VERSION}.jar${PATHSEP}${MAVEN_REPOS}/qocim-tools/qocim-precisionindicator/${QOCIM_VERSION}/qocim-precisionindicator-${QOCIM_VERSION}.jar${PATHSEP}${MAVEN_REPOS}/qocim-tools/qocim-routing-filter/${QOCIM_VERSION}/qocim-routing-filter-${QOCIM_VERSION}.jar${PATHSEP}${MAVEN_REPOS}/qocim-tools/qocim-cdfm-functions/${QOCIM_VERSION}/qocim-cdfm-functions-${QOCIM_VERSION}.jar${PATHSEP}${MAVEN_REPOS}/qocim-tools/qocim-qocmanagement-functions/${QOCIM_VERSION}/qocim-qocmanagement-functions-${QOCIM_VERSION}.jar${PATHSEP}${MAVEN_REPOS}/qocim-tools/qocim-tool-functions/${QOCIM_VERSION}/qocim-tool-functions-${QOCIM_VERSION}.jar

APACHE_CAPSULE=${MAVEN_REPOS}/commons-configuration/commons-configuration/1.10/commons-configuration-1.10.jar${PATHSEP}${MAVEN_REPOS}/commons-lang/commons-lang/2.4/commons-lang-2.4.jar${PATHSEP}${MAVEN_REPOS}/commons-logging/commons-logging/1.1.1/commons-logging-1.1.1.jar${PATHSEP}${MAVEN_REPOS}/commons-collections/commons-collections/3.2.1/commons-collections-3.2.1.jar${PATHSEP}${MAVEN_REPOS}/org/apache/commons/commons-math3/3.2/commons-math3-3.2.jar

echo ${QOCIM_COMMON}

CLIENT_CLASSPATH=${COMMON_JAR}${PATHSEP}${COMMUNICATION_JAR}${PATHSEP}${CLIENT_JAR}${PATHSEP}${LOG4J_JAR}${PATHSEP}${GSON_JAR}${PATHSEP}${XACML_JAR}${PATHSEP}${MUCONTEXT_DATAMODELS}${PATHSEP}${MUCONTEXT_API}${PATHSEP}${QOCIM_COMMON}${PATHSEP}${QOCIM_TOOLS}

COLLECTOR_CLASSPATH=$CLIENT_CLASSPATH${PATHSEP}${MUCONTEXT_COLLECTOR}
CAPSULE_CLASSPATH=$CLIENT_CLASSPATH${PATHSEP}${MUCONTEXT_CAPSULE}${PATHSEP}${APACHE_CAPSULE}
APPLICATION_CLASSPATH=$CLIENT_CLASSPATH${PATHSEP}${MUCONTEXT_APPLICATION}

MUCONTEXT_CAPSULE_ONE_CONSUMER=${MAVEN_REPOS}/muContext/muContext-capsule-one-consumer/${MUCONTEXT_VERSION}/muContext-capsule-one-consumer-${MUCONTEXT_VERSION}.jar
MUCONTEXT_APPLICATION_ONE_CONSUMER=${MAVEN_REPOS}/muContext/muContext-application-one-consumer/${MUCONTEXT_VERSION}/muContext-application-one-consumer-${MUCONTEXT_VERSION}.jar

CAPSULE_ONE_CONSUMER_CLASSPATH=$CLIENT_CLASSPATH${PATHSEP}${MUCONTEXT_CAPSULE_ONE_CONSUMER}
APPLICATION_ONE_CONSUMER_CLASSPATH=$CLIENT_CLASSPATH${PATHSEP}${MUCONTEXT_APPLICATION_ONE_CONSUMER}
