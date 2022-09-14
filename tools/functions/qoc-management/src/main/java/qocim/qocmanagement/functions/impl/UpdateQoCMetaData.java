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

import qocim.datamodel.QoCIndicator;
import qocim.datamodel.QoCMetricDefinition;
import qocim.datamodel.QoCMetricValue;
import qocim.datamodel.information.QInformation;
import qocim.datamodel.utils.ConstraintChecker;
import qocim.datamodel.utils.ConstraintCheckerException;
import qocim.datamodel.utils.IQoCIMFactory;
import qocim.datamodel.utils.log.QoCIMLogger;
import qocim.qocmanagement.functions.IQoCManagementFunction;
import qocim.qocmanagement.functions.utils.EQoCManagementFunction;
import qocim.qocmanagement.functions.utils.LogMessages;

/**
 * UpdateQoCMetaData updates all QoC metric values associated to all the context
 * observations contained into a context report. To do it, the function uses the
 * QoC management function <i>updateQoCIndicator</i>.
 *
 * @see qocim.qocmanagement.functions.impl.UpdateQoCMetricValue
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
		QoCIMLogger.function(FUNCTION_NAME, LogMessages.NEW_FUNCTION_INSTANCE);
	}

	// # # # # # PUBLIC METHODS # # # # #

	/**
	 * The method executes the function <i>updateQoCMetaData</i>. The method
	 * browses all the context observations of the context report, then browses
	 * all the QoC indicators of the context observations and finally calls the
	 * private method <i>updateQoCIndicator</i>.
	 */
	@Override
	public QInformation<?> exec(final QInformation<?> information) {
		// - - - - - CHECK THE VALUE OF THE ARGUMENTS - - - - -
		try {
			String message = "UpdateQoCMetaData.exec(ContextReport): the argument information is null";
			ConstraintChecker.notNull(information, message);
			message = "UpdateQoCMetaData.exec(ContextReport): the argument information is null.";
			ConstraintChecker.notNull(information, message);
		} catch (final ConstraintCheckerException e) {
			return information;
		}
		// - - - - - CORE OF THE METHOD - - - - -
		QoCIMLogger.function(FUNCTION_NAME, LogMessages.BEGIN_EXECUTION_FUNCTION);
			for (final QoCIndicator loop_qoCIndicator : information.indicators()) {
				updateQoCIndicator(information, loop_qoCIndicator);
			}
		QoCIMLogger.function(FUNCTION_NAME, LogMessages.END_EXECUTION_FUNCTION);
		// - - - - - RETURN STATEMENT - - - - -
		return information;
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
	 * @param information
	 *            the context information
	 * @param indicator
	 *            the QoCIndicator updated by the method
	 */
	private void updateQoCIndicator(final QInformation<?> information, final QoCIndicator indicator) {
		// - - - - - CORE OF THE METHOD - - - - -
		for (final QoCMetricValue loop_qoCMetricValue : indicator.list_qoCMetricValue) {
			updateQoCIndicator.setUp(loop_qoCMetricValue.id(), indicator.id());
			updateQoCIndicator.exec(information);
		}
	}
}
