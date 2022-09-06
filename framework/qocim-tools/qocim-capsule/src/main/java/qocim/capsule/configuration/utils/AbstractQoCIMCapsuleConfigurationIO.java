/**
 * This file is part of the QoCIM middleware.
 *
 * Copyright (C) 2014 IRIT, Télécom SudParis
 *
 * The QoCIM software is free software: you can redistribute it and/or modify
 * It under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * The QoCIM software platform is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *
 * See the GNU Lesser General Public License 
 * for more details: http://www.gnu.org/licenses
 *
 * Initial developer(s): Pierrick MARIE
 * Contributor(s): 
 */
package qocim.capsule.configuration.utils;


import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

import org.apache.commons.configuration.XMLConfiguration;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.SimpleLayout;

import qocim.datamodel.utils.ConstraintChecker;
import qocim.datamodel.utils.ConstraintCheckerException;
import qocim.datamodel.utils.QoCIMLogger;

/**
 * This class provides the common configuration of the QoCIM capsule
 * configuration serializer and unserializer.
 *
 * @author Pierrick MARIE
 */
abstract class AbstractQoCIMCapsuleConfigurationIO {

    // # # # # # CONSTANTS # # # # #

    /**
     * The encoding type of the configuration files.
     */
    public static final String ENCODING = "UTF-8";

    // # # # # # PROTECTED VARIABLES # # # # #

    /**
     * The xml file reader and writer.
     */
    protected final XMLConfiguration xmlConfigurationIO;

    /**
     * The configuration file.
     */
    protected final File configurationFile;

    // # # # # # CONSTRUCTORS # # # # #

    AbstractQoCIMCapsuleConfigurationIO(final String _configurationFilePath) {
	// - - - - - CHECK THE VALUE OF THE ARGUMENTS - - - - -
	try {
	    final String message = "QoCIMCapsuleConfigurationIO.constructor(String): the argument _configurationFilePath is null.";
	    ConstraintChecker.notNull(_configurationFilePath, message);
	} catch (final ConstraintCheckerException e) {
	    final String message = "QoCIMCapsuleConfigurationIO.constructor(String): Fatal error, impossible to create a QoCIMCapsuleConfigurationIO.";
	    QoCIMLogger.logger.severe(message);
	    xmlConfigurationIO = null;
	    configurationFile = null;
	    return;
	}
	// - - - - - INITIALIZE THE VARIABLES - - - - -
	/*
	 * Configuring log4j: required for the xml IO
	 * (<i>xmlConfigurationIO</i>). Adding only one Appender.
	 */
	if (!org.apache.log4j.Logger.getRootLogger().getAllAppenders().hasMoreElements()) {
	    org.apache.log4j.Logger.getRootLogger().addAppender(new ConsoleAppender(new SimpleLayout()));
	    org.apache.log4j.Logger.getRootLogger().setLevel(org.apache.log4j.Level.WARN);
	    org.apache.log4j.Logger.getRootLogger().setAdditivity(false);
	}
	configurationFile = new File(_configurationFilePath);
	xmlConfigurationIO = new XMLConfiguration();
	// - - - - - CORE OF THE METHOD - - - - -
	QoCIMLogger.logger.log(Level.INFO, "New QoCIM capsule configuration IO: Configuration file path = " + _configurationFilePath);
	/*
	 * Creating the file if it does not exist. Otherwise, an exception is
	 * thrown by the xml reader.
	 */
	if (!configurationFile.exists()) {
	    try {
		QoCIMLogger.logger.log(Level.INFO, "Creating a new configuration file: " + _configurationFilePath);
		configurationFile.createNewFile();
	    } catch (final IOException _exception) {
		QoCIMLogger.logger.log(Level.WARNING, "Fail to create a new configuration file: " + _exception);
	    }
	}
	/*
	 * Configuring the xml IO.
	 */
	xmlConfigurationIO.setBasePath(configurationFile.getAbsolutePath());
	xmlConfigurationIO.setFile(configurationFile);
	xmlConfigurationIO.setEncoding(ENCODING);
	xmlConfigurationIO.setDelimiterParsingDisabled(true);
    }
}
