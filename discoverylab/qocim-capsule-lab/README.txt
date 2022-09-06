QoCIM discovery lab.

This repository contains four folders:
 * qocim-capsule-lab-application: the application that receive the information comming from the capsule
 * qocim-capsule-lab-capsule: the capsule to transform the information comming from the collector
 * qocim-capsule-lab-collector: the collector to produce information

WARNING: This framework depends on the muContext data model.

For more information, see the web page: https://fusionforge.int-evry.fr/www/qocim/lab/qocim-capsule-lab-exercices.html

To compile the discovery lab, use the following command: 
 $> mvn clean install -Djsse.enableSNIExtension=false
