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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import qocim.datamodel.QoCIMFacade;
import qocim.datamodel.QoCIndicator;
import qocim.datamodel.QoCMetaData;
import qocim.datamodel.information.QInformation;
import qocim.datamodel.utils.ConstraintChecker;
import qocim.datamodel.utils.ConstraintCheckerException;
import qocim.datamodel.utils.log.QoCIMLogger;
import qocim.qocmanagement.functions.IQoCManagementFunction;
import qocim.qocmanagement.functions.utils.EQoCManagementFunction;
import qocim.qocmanagement.functions.utils.LogMessages;
import qocim.tool.functions.impl.MatchingQoCFilter;
import qocim.tool.functions.utils.QoCFilter;

/**
 * FilterQoCMetaData removes QoC meta-data in a context report following the
 * constraints expressed in a QoC filter. To do it the function uses the tool
 * functions <i>matchingQoCFilter</i> and <i>removeQoCIndicator</i>.
 *
 * @author Pierrick MARIE
 */
public class FilterQoCMetaData implements IQoCManagementFunction {

	// # # # # # CONSTANTS # # # # #

	/**
	 * The name of the function.
	 */
	public static final String FUNCTION_NAME = EQoCManagementFunction.FILTERQOCMETADATA.toString();

	// # # # # # PRIVATE VARIABLES # # # # #

	/**
	 * Verifies if the configuration of the function has been done.
	 */
	private Boolean setUpIsDone;
	/**
	 * The QoC filter that contains the conditions to remove QoC meta-data on a
	 * context report.
	 */
	private QoCFilter qoCFilter;
	/**
	 * The tool function used to verify if the value of the context report
	 * respect the constraints of the QoC filter.
	 */
	private final MatchingQoCFilter matchingQoCFilter;
	/**
	 * The tool function used to remove the unexpected QoC indicator in the
	 * context report.
	 */
	private final RemoveQoCMetricValue removeQoCIndicator;

	// # # # # # CONSTRUCTORS # # # # #

	public FilterQoCMetaData() {
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		setUpIsDone = false;
		matchingQoCFilter = new MatchingQoCFilter();
		removeQoCIndicator = new RemoveQoCMetricValue();
		QoCIMLogger.function(FUNCTION_NAME, LogMessages.NEW_FUNCTION_INSTANCE);
	}

	// # # # # # PUBLIC METHODS # # # # #

	/**
	 * The method executes the function <i>filterQoCMetaData</i>. For each
	 * context observation of the context report, the method creates the
	 * corresponding list of QoC meta-data and verifies if every QoC meta-data
	 * respects the constraints of the QoC filter. If not, the QoC meta-data is
	 * removed from the context report.
	 */
	@Override
	public QInformation exec(final QInformation information) {
		// - - - - - CHECK THE VALUE OF THE ARGUMENTS - - - - -
		try {
			String message = "FilterQoCMetaData.exec() method setUp(QoCFilter) have to be called before.";
			ConstraintChecker.assertTrue(setUpIsDone, message);
			message = "FilterQoCMetaData.exec(ContextReport): the argument information is null.";
			ConstraintChecker.notNull(information, message);
		} catch (final ConstraintCheckerException e) {
			return information;
		}
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		/*
		 * The list of QoC meta-data associated to a context observation.
		 */
		List<QoCMetaData> list_qoCMetaData;
		// - - - - - CORE OF THE METHOD - - - - -
		QoCIMLogger.function(FUNCTION_NAME, LogMessages.BEGIN_EXECUTION_FUNCTION);
			list_qoCMetaData = getListQoCMetaData(information);
			for (final QoCMetaData loop_qoCMetaData : list_qoCMetaData) {
				filterQoCMetaData(information, loop_qoCMetaData);
			}
		QoCIMLogger.function(FUNCTION_NAME, LogMessages.END_EXECUTION_FUNCTION);
		// - - - - - RETURN STATEMENT - - - - -
		return information;
	}

	/**
	 * The method initializes the argument of the function
	 * <i>filterQoCMetaData</i>.
	 *
	 * @param _qoCFilter
	 *            The QoC filter used to filter the QoC meta-data of a context
	 *            report.
	 * @return <b>this</b>
	 */
	public FilterQoCMetaData setUp(final QoCFilter _qoCFilter) {
		// - - - - - CHECK THE VALUE OF THE ARGUMENTS - - - - -
		try {
			ConstraintChecker.notNull(_qoCFilter,
					"FilterQoCMetaData.setUp(QoCFilter): the argument _qoCFilter is null");
		} catch (final ConstraintCheckerException e) {
			setUpIsDone = false;
			return this;
		}
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		qoCFilter = _qoCFilter;
		setUpIsDone = true;
		QoCIMLogger.function(FUNCTION_NAME, LogMessages.SETUP_FUNCTION);
		// - - - - - RETURN STATEMENT - - - - -
		return this;
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
	 * The method verifies if the values of a QoC meta-data respects the
	 * constraints expressed in the private field <i>qoCFilter</i>. If not, the
	 * method removes the QoC meta-data from the context report.
	 *
	 * @param information
	 *            The context report which is modified if the QoC meta-data does
	 *            not respects the constraints of the QoC filter.
	 * @param _qoCMetaData
	 *            The QoC meta-data analyzed by the method.
	 */
	private void filterQoCMetaData(final QInformation information, final QoCMetaData _qoCMetaData) {
		// - - - - - CORE OF THE METHOD - - - - -
		matchingQoCFilter.setUp(_qoCMetaData, qoCFilter);
		if (!(Boolean) matchingQoCFilter.exec()) {
			removeQoCIndicator.setUp(_qoCMetaData.qoCMetricValueId(), _qoCMetaData.qoCIndicatorId());
			removeQoCIndicator.exec(information);
		}
	}

	/**
	 * The method returns the list of <b>QoCMetaData</b> associated to a context
	 * observation. To do it, the method <i>createListQoCMetaData</i> of the
	 * class <b>QoCIMFacade</b> is used.
	 *
	 * @param information
	 *            The context observation used to produce the list of QoC
	 *            meta-data.
	 * @return The list of QoC meta-data.
	 */
	private List<QoCMetaData> getListQoCMetaData(final QInformation<?> information) {
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		/*
		 * The returned list of QoC meta-data.
		 */
		final List<QoCMetaData> ret_list_QoCMetaData = new ArrayList<QoCMetaData>();
		// - - - - - CORE OF THE METHOD - - - - -
		for (final QoCIndicator loop_qoCIndicator : information.indicators()) {
			ret_list_QoCMetaData.addAll(QoCIMFacade.createListQoCMetaData(loop_qoCIndicator));
		}
		// - - - - - RETURN STATEMENT - - - - -
		return ret_list_QoCMetaData;
	}
}
