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
package qocim.cdfm.function.impl;

import java.util.logging.Level;
import qocim.datamodel.utils.log.QoCIMLogger;

/**
 * TemporalCDFMFunctionTimer is used by the class <b>TemporalCDFMFunction</b> to
 * wait before executing the function.
 *
 * @see qocim.cdfm.function.impl.CDFMFunction
 *
 * @author Pierrick MARIE
 */
class CDFMFunctionTimer implements Runnable {

	// # # # # # PROTECTED VARIABLES # # # # #

	/**
	 * A boolean to allow the execution of the method <i>run</i>.
	 */
	protected volatile Boolean run;
	/**
	 * Determine the number of seconds to wait.
	 */
	protected volatile Integer nbWaintingMiliSecond;
	/**
	 * The temporal function that have to be executed after
	 * <i>nbWaintingSecond</i> seconds.
	 */
	protected final CDFMFunction function;

	// # # # # # CONSTRUCTORS # # # # #

	public CDFMFunctionTimer(final CDFMFunction _function) {
		// - - - - - CORE OF THE METHOD - - - - -
		run = false;
		nbWaintingMiliSecond = 0;
		function = _function;
	}

	// # # # # # PUBLIC METHODS # # # # #

	@Override
	public void run() {
		// - - - - - CORE OF THE METHOD - - - - -
		while (run) {
			try {
				Thread.sleep(nbWaintingMiliSecond);
				function.execFunction();
			} catch (final InterruptedException _exception) {
				QoCIMLogger.logger.log(Level.WARNING, "Error in TemporalCDFMFunctionTimer.run()", _exception);
			}
		}
	}
}
