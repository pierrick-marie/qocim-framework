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
import qocim.cdfm.function.ICDFMFunction;
import qocim.functions.IQoCIMFunction;
import qocim.functions.IQoCIMFunctionListener;
import qocim.qocmanagement.functions.IQoCManagementFunction;

/**
 * QoCIMCapsuleFunction implements <b>IQoCIMFunctionListener</b>. It is a tool
 * class used to create a chain of functions executed by a context capsule. Its
 * public fields are one <b>IQoCIMFunction</b> and one
 * <b>QoCIMCapsuleFunction</b>. The first one represents the function executed
 * by the class. The second one is the next function that receive
 *
 * @author Pierrick MARIE
 */
public class QoCIMCapsuleFunction implements IQoCIMFunctionListener {

	// # # # # # CONSTANTS # # # # #

	/**
	 * The current function handled by the class.
	 */
	public final IQoCIMFunction function;

	// # # # # # PRIVATE VARIABLES # # # # #

	/**
	 * The next function executed by the capsule after <i>function</i>.
	 */
	private QoCIMCapsuleFunction nextFunction;

	// # # # # # CONSTRUCTORS # # # # #

	public QoCIMCapsuleFunction(final IQoCIMFunction _function) {
		function = _function;
		nextFunction = null;
	}

	// # # # # # PUBLIC METHODS # # # # #

	/**
	 * This method sets the next function handled by the class. Null value is
	 * accepted to remove the listener.
	 *
	 * @param _nextFunction
	 *            The next function handled by the class.
	 * @return this.
	 */
	public QoCIMCapsuleFunction setNextFunction(final QoCIMCapsuleFunction _nextFunction) {
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		nextFunction = _nextFunction;
		// - - - - - CORE OF THE METHOD - - - - -
		if (isICDFMFunction()) {
			if (nextFunction != null) {
				((ICDFMFunction) function).setResultListener(nextFunction);
			} else {
				/*
				 * Next function is null => remove the listener.
				 */
				((ICDFMFunction) function).setResultListener(new IQoCIMFunctionListener() {
					@Override
					public void newInformation(final ContextReport _contextReport) {
						// Do nothing.
					}
				});
			}
		}
		// - - - - - RETURN STATEMENT - - - - -
		return this;
	}

	/**
	 * @return The next function handled by the class.
	 */
	public QoCIMCapsuleFunction nextFunction() {
		// - - - - - RETURN STATEMENT - - - - -
		return nextFunction;
	}

	/**
	 * @return True if <i>function</i> is an instance of <b>ICDFMFunction</b>.
	 */
	public Boolean isICDFMFunction() {
		// - - - - - RETURN STATEMENT - - - - -
		return function instanceof ICDFMFunction;
	}

	/**
	 * @return True if <i>function</i> is an instance of
	 *         <b>IQoCManagementFunction</b>.
	 */
	public Boolean isIQoCManagementFunction() {
		// - - - - - RETURN STATEMENT - - - - -
		return function instanceof IQoCManagementFunction;
	}

	@Override
	public void newInformation(final ContextReport _contextReport) {
		if (isICDFMFunction()) {
			/*
			 * Adding the '_contextReport' in the buffer of the 'function'.
			 */
			((ICDFMFunction) function).addContextReport(_contextReport);
		} else {
			/*
			 * Step 1: execute the 'IQoCManagementFunction' with the
			 * '_contextReport'.
			 * 
			 * Step 2: notify the 'nextFunction' with the resulting new context
			 * report.
			 */
			if (nextFunction != null) {
				nextFunction.newInformation(((IQoCManagementFunction) function).exec(_contextReport));
			}
		}
	}
}
