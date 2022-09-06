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
package qocim.qocmanagement.functions.impl;

import java.util.HashMap;
import java.util.Map;

import mucontext.datamodel.context.ContextObservation;
import mucontext.datamodel.context.ContextReport;
import qocim.datamodel.utils.ConstraintChecker;
import qocim.datamodel.utils.ConstraintCheckerException;
import qocim.datamodel.utils.QoCIMLogger;
import qocim.qocmanagement.functions.IQoCManagementFunction;
import qocim.qocmanagement.functions.utils.EQoCManagementFunction;
import qocim.qocmanagement.functions.utils.LogMessages;

/**
 * The method removes all the QoC meta-data of all the context observations of a
 * context report.
 *
 * @author Pierrick MARIE
 */
public class RemoveQoCMetaData implements IQoCManagementFunction {

	// # # # # # CONSTANTS # # # # #

	/**
	 * The name of the function.
	 */
	public static final String FUNCTION_NAME = EQoCManagementFunction.REMOVEQOCMETADATA.toString();

	// # # # # # PUBLIC METHODS # # # # #

	public RemoveQoCMetaData() {
		// - - - - - CORE OF THE METHOD - - - - -
		QoCIMLogger.functionLog(FUNCTION_NAME, LogMessages.NEW_FUNCTION_INSTANCE);
	}

	/**
	 * The method executes the function <i>removeQoCMetaData</i>. To do it, the
	 * method just uses the method <i>java.util.Set.clear</i> on each QoC
	 * indicators of all the context observations contained into the context
	 * report.
	 */
	@Override
	public ContextReport exec(final ContextReport _contextReport) {
		// - - - - - CHECK THE VALUE OF THE ARGUMENTS - - - - -
		try {
			final String message = "RemoveQoCMetaData.exec(ContextReport): the argument _contextReport is null.";
			ConstraintChecker.notNull(_contextReport, message);
		} catch (final ConstraintCheckerException e) {
			return _contextReport;
		}
		// - - - - - CORE OF THE METHOD - - - - -
		QoCIMLogger.functionLog(FUNCTION_NAME, LogMessages.BEGIN_EXECUTION_FUNCTION);
		for (final ContextObservation<?> loop_contextObservation : _contextReport.observations) {
			loop_contextObservation.list_qoCIndicator.clear();
		}
		QoCIMLogger.functionLog(FUNCTION_NAME, LogMessages.END_EXECUTION_FUNCTION);
		// - - - - - RETURN STATEMENT - - - - -
		return _contextReport;
	}

	@Override
	public void setParameters(final Map<String, String> _map_paramaters) {
		// Do nothing.
	}

	@Override
	public Map<String, String> parameters() {
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		final Map<String, String> ret_mapParameter = new HashMap<String, String>();
		// - - - - - RETURN STATEMENT - - - - -
		return ret_mapParameter;
	}

	@Override
	public String getName() {
		return FUNCTION_NAME;
	}
}
