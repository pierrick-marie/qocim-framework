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
package qocim.aggregation.operator.utils;


import org.apache.log4j.Level;
import qocim.utils.logs.QoCIMLogger;

/**
 * An exception to inform the expected operator is not supported.
 *
 * @author Atif MANZOOR, Pierrick MARIE
 */
public class OperatorNotSupportedException extends Exception {

	// # # # # # CONSTANTS # # # # #

	private static final long serialVersionUID = 1407645767759481211L;

	// # # # # # PRIVATE VARIABLES # # # # #

	private final String message;

	// # # # # # CONSTRUCTORS # # # # #

	public OperatorNotSupportedException(final String _message) {
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		message = "Operator not supported exception: " + _message;
		// - - - - - CORE OF THE METHOD - - - - -
		QoCIMLogger.logger.log(Level.WARN, message, this);
	}

	// # # # # # PUBLIC METHODS # # # # #

	@Override
	public String toString() {
		return message;
	}
}
