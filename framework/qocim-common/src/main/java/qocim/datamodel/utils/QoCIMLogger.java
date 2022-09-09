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
package qocim.datamodel.utils;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.logging.ConsoleHandler;
import java.util.logging.Logger;

/**
 * The QoCIMLogger class is used to log messages with log4j.
 *
 * @author Pierrick MARIE
 */
public class QoCIMLogger {

    // # # # # # CONSTANTS # # # # #

    /**
     * The logger used to write messages when exceptions are thrown.
     */
    public final static Logger logger = Logger.getLogger(QoCIMLogger.class.getName());

    /**
     * The name of the method used to get information concerning the JVM CPU
     * usage.
     */
    private static final String JVM_CPU_USAGE = "getProcessCpuLoad";
    /**
     * The name of the method used to get the left free memory space.
     */
    private static final String MEMORY_USAGE = "getFreePhysicalMemorySize";

    // # # # # # PRIVATE VARIABLES # # # # #

    private static Boolean isConfigured = false;

    // # # # # # PUBLIC METHODS # # # # #

    /**
     * This method logs information concerning events that appears during the
     * QoC management.
     *
     * @param _message
     *            The message to log.
     */
    public static synchronized void event(final String _message) {
	// - - - - - CHECK THE VALUE OF THE ARGUMENTS - - - - -
	try {
	    ConstraintChecker.notNull(_message, "QoCIMLogger.event(String): the argument _message is null.");
	} catch (final ConstraintCheckerException e) {
	    return;
	}
	// - - - - - CORE OF THE METHOD - - - - -
	logger.log(QoCIMPerformanceLogLevel.EVENT, _message);
    }

    /**
     * This method logs information with the current date.
     *
     * @param _message
     *            The message to log.
     */
    public static synchronized void time(final String _message) {
	// - - - - - CHECK THE VALUE OF THE ARGUMENTS - - - - -
	try {
	    ConstraintChecker.notNull(_message, "QoCIMLogger.time(String): the argument _message is null.");
	} catch (final ConstraintCheckerException e) {
	    return;
	}
	// - - - - - CORE OF THE METHOD - - - - -
	logger.log(QoCIMPerformanceLogLevel.TIME, new Date() + " (" + System.currentTimeMillis() + ") - " + _message);
    }

    /**
     * This method logs information with the current JVM CPU usage and free left
     * memory space.
     *
     * @param _message
     *            The message to log.
     */
    public static synchronized void hardware(final String _message) {
	// - - - - - CHECK THE VALUE OF THE ARGUMENTS - - - - -
	try {
	    ConstraintChecker.notNull(_message, "QoCIMLogger.hardware(String): the argument _message is null.");
	} catch (final ConstraintCheckerException e) {
	    return;
	}
	// - - - - - CORE OF THE METHOD - - - - -
	final String hardwareMessage = "PROCESS_CPU_USAGE: " + getUsage(JVM_CPU_USAGE) + " % ; FREE_MEMORY: " + getUsage(MEMORY_USAGE) + " bytes - ";
	logger.log(QoCIMPerformanceLogLevel.HARDWARE, hardwareMessage + _message);
    }

    /**
     * This method logs information with the three log levels.
     *
     * @param _message
     *            The message to log.
     */
    public static synchronized void functionLog(final String _functionName, final String _message) {
	// - - - - - CHECK THE VALUE OF THE ARGUMENTS - - - - -
	try {
	    ConstraintChecker.notNull(_message, "QoCIMLogger.functionLog(String, String): the argument _message is null.");
	} catch (final ConstraintCheckerException e) {
	    return;
	}
	// - - - - - CORE OF THE METHOD - - - - -
	event(_functionName + _message);
	time(_functionName + _message);
	hardware(_functionName + _message);
    }

    /**
     * This method is used to load the default configuration of the logger. Only
     * one configuration is allowed for the logger to provide only one console
     * handler.
     */
    public static synchronized void loadDefaultConfig() {
	if (!isConfigured) {
	    final ConsoleHandler consoleHandler = new ConsoleHandler();
	    consoleHandler.setFormatter(new LogFormatter());
	    QoCIMLogger.logger.addHandler(consoleHandler);
	    isConfigured = true;
	}
    }

    // # # # # # PRIVATE METHODS # # # # #

    /**
     * This method provides information concerning a part of the system.
     *
     * @param usageName
     *            The name of the expected information.
     *
     * @return The value corresponding to the expected information.
     *
     * @see java.lang.management.OperatingSystemMXBean
     */
    private static String getUsage(final String usageName) {
	// - - - - - INITIALIZE THE VARIABLES - - - - -
	final OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean();
	Object value;
	// - - - - - CORE OF THE METHOD - - - - -
	for (final Method method : operatingSystemMXBean.getClass().getDeclaredMethods()) {
	    method.setAccessible(true);
	    if (method.getName().equals(usageName)) {
		try {
		    value = method.invoke(operatingSystemMXBean);
		} catch (final Exception e) {
		    value = e;
		}
		return "" + value;
	    }
	}
	// - - - - - RETURN STATEMENT - - - - -
	return "";
    }
}
