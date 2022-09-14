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
 * Contributor(s): Elliot FELGINES
 */
package qocim.datamodel.utils.log;

import com.sun.management.OperatingSystemMXBean;

import java.lang.management.ManagementFactory;
import java.util.Date;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The QoCIMLogger class is used to log messages with log4j.
 *
 * @author Pierrick MARIE
 *
 * @author Elliot FELGINES
 */
public class QoCIMLogger {

	// # # # # # CONSTANTS # # # # #

	/**
	 * The logger used to write messages when exceptions are thrown.
	 */
	public final static Logger logger = Logger.getLogger(QoCIMLogger.class.getName());
	/**
	 * JVM CPU usage method name
	 */
	private static final String JVM_CPU_USAGE_METHOD_NAME = "getProcessCpuLoad";
	/**
	 * JVM CPU usage message
	 */
	private static final String JVM_CPU_USAGE_MESSAGE = "PROCESS_CPU_USAGE";
	/**
	 * JVM CPU usage unit
	 */
	private static final String JVM_CPU_USAGE_UNIT = "%";
	/**
	 * Ram usage message
	 */
	private static final String RAM_USAGE_MESSAGE = "FREE_MEMORY";
	/**
	 * Ram usage unit
	 */
	private static final String RAM_USAGE_UNIT = "bytes";

	// # # # # # PRIVATE VARIABLES # # # # #

	private static Boolean isConfigured = false;

	// # # # # # PUBLIC METHODS # # # # #

	/**
	 * This method prints some debug messages.
	 *
	 * @param message
	 *            The message to print.
	 */
	public static synchronized void debug(final String message) {
		// - - - - - CORE OF THE METHOD - - - - -
		logger.log(Level.INFO, " *DEBUG* " + message);
	}

	/**
	 * This method prints error messages.
	 *
	 * @param message
	 *            The error message.
	 */
	public static synchronized void error(final String message) {
		// - - - - - CORE OF THE METHOD - - - - -
		logger.log(Level.SEVERE, message);
	}

	/**
	 * This method logs information concerning events that appears during the QoC
	 * management.
	 *
	 * @param message
	 *            The message to log.
	 */
	public static synchronized void event(final String message) {
		// - - - - - CORE OF THE METHOD - - - - -
		logger.log(QoCIMPerformanceLogLevel.EVENT, message);
	}

	/**
	 * This method logs information with the three log levels.
	 *
	 * @param message
	 *            The message to log.
	 */
	public static synchronized void function(final String functionName, final String message) {
		// - - - - - CORE OF THE METHOD - - - - -
		event(functionName + message);
		time(functionName + message);
		hardware(functionName + message);
	}

	/**
	 * This method provides information concerning a part of the system.
	 *
	 * @return The value corresponding to the expected information.
	 */
	private static String getJvmCpuUsage() {
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		final OperatingSystemMXBean operatingSystemMXBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
		// - - - - - RETURN STATEMENT - - - - -
		return "" + (operatingSystemMXBean.getCpuLoad() * 100.0);
	}

	/**
	 * @return The memory used by the JVM in percent.
	 */
	private static String getRuntimeInfo() {
		// - - - - - RETURN STATEMENT - - - - -
		return String.valueOf(Runtime.getRuntime().maxMemory()
				- (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
	}

	// # # # # # PRIVATE METHODS # # # # #

	/**
	 * This method logs information with the current JVM CPU usage and free left
	 * memory space.
	 *
	 * @param message
	 *            The message to log.
	 */
	public static synchronized void hardware(final String message) {
		// - - - - - CORE OF THE METHOD - - - - -
		final String hardwareMessage = JVM_CPU_USAGE_MESSAGE + ": " + getJvmCpuUsage() + JVM_CPU_USAGE_UNIT + " ; "
				+ RAM_USAGE_MESSAGE + ": " + getRuntimeInfo() + RAM_USAGE_UNIT + " - ";
		logger.log(QoCIMPerformanceLogLevel.HARDWARE, hardwareMessage + message);
	}

	/**
	 * This method prints some messages.
	 *
	 * @param message
	 *            The message to print.
	 */
	public static synchronized void info(final String message) {
		// - - - - - CORE OF THE METHOD - - - - -
		logger.log(Level.FINEST, " *INFO* " + message);
	}

	/**
	 * This method is used to load the default configuration of the logger. Only one
	 * configuration is allowed for the logger to provide only one console handler.
	 */
	public static synchronized void loadDefaultConfig() {
		if (!isConfigured) {
			final ConsoleHandler consoleHandler = new ConsoleHandler();
			consoleHandler.setFormatter(new LogFormatter());
			QoCIMLogger.logger.addHandler(consoleHandler);
			isConfigured = true;
		}
	}

	/**
	 * This method prints some messages for serious errors.
	 *
	 * @param message
	 *            The message to print.
	 */
	public static synchronized void severe(final String message) {
		// - - - - - CORE OF THE METHOD - - - - -
		logger.log(Level.SEVERE, " *SEVERE* " + message);
	}

	/**
	 * This method logs information with the current date.
	 *
	 * @param _message
	 *            The message to log.
	 */
	public static synchronized void time(final String _message) {
		// - - - - - CORE OF THE METHOD - - - - -
		logger.log(QoCIMPerformanceLogLevel.TIME, new Date() + " (" + System.currentTimeMillis() + ") - " + _message);
	}
}
