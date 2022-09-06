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
import qocim.datamodel.QoCIndicator;
import qocim.datamodel.QoCMetricDefinition;
import qocim.datamodel.QoCMetricValue;
import qocim.datamodel.utils.ConstraintChecker;
import qocim.datamodel.utils.ConstraintCheckerException;
import qocim.datamodel.utils.IQoCIMFactory;
import qocim.datamodel.utils.QoCIMLogger;
import qocim.qocmanagement.functions.IQoCManagementFunction;
import qocim.qocmanagement.functions.utils.EQoCManagementFunction;
import qocim.qocmanagement.functions.utils.LogMessages;

/**
 * UpdateQoCMetaData updates all QoC metric values associated to all the context
 * observations contained into a context report. To do it, the function uses the
 * QoC management function <i>updateQoCIndicator</i>.
 *
 * @see qocim.qocmanagement.functions.impl.UpdateQoCMetricValue
 * @see mucontext.datamodel.context.ContextReport
 * @see mucontext.datamodel.context.ContextObservation
 * @see mucontext.datamodel.qocim.QoCIndicator
 * @see mucontext.datamodel.qocim.QoCMetricValue
 *
 * @author Pierrick MARIE
 */
public class UpdateQoCMetaData implements IQoCManagementFunction {

	// # # # # # CONSTANTS # # # # #

	/**
	 * The name of the function.
	 */
	public static final String FUNCTION_NAME = EQoCManagementFunction.UPDATEQOCMETADATA.toString();

	// # # # # # PRIVATE VARIABLES # # # # #

	/**
	 * The tool function <i>updateQoCIndicator</i> used to update one QoC metric
	 * value of a QoC indicator of the context report
	 */
	private final UpdateQoCMetricValue updateQoCIndicator;

	// # # # # # CONSTRUCTORS # # # # #

	public UpdateQoCMetaData(final Map<QoCMetricDefinition, IQoCIMFactory> _map_availableQoCIMFacade) {
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		updateQoCIndicator = new UpdateQoCMetricValue(_map_availableQoCIMFacade);
		QoCIMLogger.functionLog(FUNCTION_NAME, LogMessages.NEW_FUNCTION_INSTANCE);
	}

	// # # # # # PUBLIC METHODS # # # # #

	/**
	 * The method executes the function <i>updateQoCMetaData</i>. The method
	 * browses all the context observations of the context report, then browses
	 * all the QoC indicators of the context observations and finally calls the
	 * private method <i>updateQoCIndicator</i>.
	 */
	@Override
	public ContextReport exec(final ContextReport _contextReport) {
		// - - - - - CHECK THE VALUE OF THE ARGUMENTS - - - - -
		try {
			String message = "UpdateQoCMetaData.exec(ContextReport): the argument _contextReport is null";
			ConstraintChecker.notNull(_contextReport, message);
			message = "UpdateQoCMetaData.exec(ContextReport): the argument _contextReport is null.";
			ConstraintChecker.notNull(_contextReport, message);
		} catch (final ConstraintCheckerException e) {
			return _contextReport;
		}
		// - - - - - CORE OF THE METHOD - - - - -
		QoCIMLogger.functionLog(FUNCTION_NAME, LogMessages.BEGIN_EXECUTION_FUNCTION);
		for (final ContextObservation<?> loop_contextObservation : _contextReport.observations) {
			for (final QoCIndicator loop_qoCIndicator : loop_contextObservation.list_qoCIndicator) {
				updateQoCIndicator(_contextReport, loop_qoCIndicator);
			}
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

	// # # # # # PRIVATE METHODS # # # # #

	/**
	 * The method browses all the QoC metric values associated to the QoC
	 * indicator and then uses the QoC management function
	 * <i>updateQoCIndicator</i> to update the values.
	 *
	 * @param _contextReport
	 *            the context report that is going to be modified
	 * @param _qoCIndicator
	 *            the QoCIndicator updated by the method
	 */
	private void updateQoCIndicator(final ContextReport _contextReport, final QoCIndicator _qoCIndicator) {
		// - - - - - CORE OF THE METHOD - - - - -
		for (final QoCMetricValue loop_qoCMetricValue : _qoCIndicator.list_qoCMetricValue) {
			updateQoCIndicator.setUp(loop_qoCMetricValue.id(), _qoCIndicator.id());
			updateQoCIndicator.exec(_contextReport);
		}
	}
}
