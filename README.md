The QoCIM repository.

This repository contains two folders:
 * framework: the source the QoCIM framework used in muContext;
 * discoverylab: examples to illustrate how use QoCIM.

WARNING: This framework depends on the muContext data model.

For more information, see the web page: https://fusionforge.int-evry.fr/www/qocim/

To compile the framework or the discovery lab, use the following command: 
 $> mvn clean install -Djsse.enableSNIExtension=false
