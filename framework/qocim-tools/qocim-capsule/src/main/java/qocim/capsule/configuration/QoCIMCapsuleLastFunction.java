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
package qocim.capsule.configuration;

import mucontext.datamodel.context.ContextReport;
import qocim.capsule.IQoCIMCapsule;

/**
 * QoCIMCapsuleLastFunction is a specialization of the class
 * QoCIMCapsuleFunction. It provides a solution to end the chain of functions
 * executed by the capsule. Then, the context report received by the class is
 * published by the capsule.
 *
 * @author Pierrick MARIE
 */
public class QoCIMCapsuleLastFunction extends QoCIMCapsuleFunction {

	// # # # # # PRIVATE VARIABLES # # # # #

	/**
	 * The current QoCIM capsule used to publish a new context report.
	 */
	private final IQoCIMCapsule qoCIMCapsule;

	// # # # # # CONSTRUCTORS # # # # #

	public QoCIMCapsuleLastFunction(final IQoCIMCapsule _qoCIMCapsule) {
		super(null);
		// - - - - - CORE OF THE METHOD - - - - -
		qoCIMCapsule = _qoCIMCapsule;
	}

	// # # # # # PUBLIC METHODS # # # # #

	@Override
	public void newInformation(final ContextReport _contextReport) {
		// - - - - - CORE OF THE METHOD - - - - -
		qoCIMCapsule.publish(_contextReport);
	}
}
