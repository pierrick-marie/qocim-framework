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
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import mucontext.datamodel.context.ContextObservation;
import mucontext.datamodel.context.ContextReport;
import qocim.datamodel.QoCIMFacade;
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
import qocim.tool.functions.impl.ComputeQoCMetricValue;
import qocim.tool.functions.impl.CurrentDate;;

/**
 * UpdateQoCIndicator updates all the values of a QoC indicator. The function
 * uses the tool function <b>UpdateQoCMetricValue</b> to do it.
 *
 * @see mucontext.datamodel.qocim.tool.functions.impl.ComputeQoCMetricValue
 * @see mucontext.datamodel.qocim.tool.functions.impl.CurrentDate
 * @see mucontext.datamodel.context.ContextReport
 * @see mucontext.datamodel.context.ContextObservation
 * @see mucontext.datamodel.qocim.QoCIndicator
 * @see mucontext.datamodel.qocim.QoCMetricValue
 *
 * @author Pierrick MARIE
 */
public class UpdateQoCIndicator implements IQoCManagementFunction {

	// # # # # # CONSTANTS # # # # #

	/**
	 * The name of the function.
	 */
	public static final String FUNCTION_NAME = EQoCManagementFunction.UPDATEQOCINDICATOR.toString();
	/**
	 * The name of the parameter 1.
	 */
	public static final String PARAM_QOC_INDICATOR_ID = "qoc_indicator_id";

	// # # # # # PRIVATE VARIABLES # # # # #

	/**
	 * Verifies if the configuration of the function has been done.
	 */
	private Boolean setUpIsDone;
	/**
	 * The <i>id</i> of the QoC indicator that reference the QoC metric value
	 * that is going to be updated.
	 */
	private Integer qoCIndicatorId;
	/**
	 * The tool function <i>computeQoCMetricValue</i> used to compute the new
	 * value of the QoC.
	 */
	private final ComputeQoCMetricValue computeQoCMetricValue;
	/**
	 * The tool function <i>currentDate</i> used to get the current date.
	 */
	private final CurrentDate currentDate;

	// # # # # # CONSTRUCTORS # # # # #

	public UpdateQoCIndicator(final Map<QoCMetricDefinition, IQoCIMFactory> _map_availableQoCIMFacade) {
		// - - - - - CHECK THE VALUE OF THE ARGUMENTS - - - - -
		try {
			String message = "UpdateQoCMetricValue.constructor(Map<String, IQoCIMFacade>): the argument _list_availableQoCIndicator is null";
			ConstraintChecker.notNull(_map_availableQoCIMFacade, message);
			message = "UpdateQoCMetricValue.constructor(Map<String, IQoCIMFacade>): the argument _list_availableQoCIndicator is empty";
			ConstraintChecker.doubleIsBetweenBounds(new Double(_map_availableQoCIMFacade.size()), 0.0, Double.MAX_VALUE,
					message);
		} catch (final ConstraintCheckerException _exception) {
			final String message = "UpdateQoCMetricValue.constructor(Map<String, IQoCIMFacade>): Fatal error.";
			QoCIMLogger.logger.log(Level.SEVERE, message, _exception);
			System.exit(-1);
		}
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		setUpIsDone = false;
		computeQoCMetricValue = new ComputeQoCMetricValue(
				new ArrayList<QoCMetricDefinition>(_map_availableQoCIMFacade.keySet()));
		currentDate = new CurrentDate();
		QoCIMLogger.functionLog(FUNCTION_NAME, LogMessages.NEW_FUNCTION_INSTANCE);
	}

	// # # # # # PUBLIC METHODS # # # # #

	/**
	 * The method executes the function <i>updateQoCIndicator</i>. The method
	 * browses all the context observations of the context report and then,
	 * calls the private method "updateQoCIndicator".
	 */
	@Override
	public ContextReport exec(final ContextReport _contextReport) {
		// - - - - - CHECK THE VALUE OF THE ARGUMENTS - - - - -
		try {
			String message = "UpdateQoCMetricValueexec() method .setup(Integer, Integer) have to be called before.";
			ConstraintChecker.assertTrue(setUpIsDone, message);
			message = "UpdateQoCMetricValue.exec(ContextReport): the argument _contextReport is null.";
			ConstraintChecker.notNull(_contextReport, message);
		} catch (final ConstraintCheckerException e) {
			return _contextReport;
		}
		// - - - - - CORE OF THE METHOD - - - - -
		QoCIMLogger.functionLog(FUNCTION_NAME, LogMessages.BEGIN_EXECUTION_FUNCTION);
		for (final ContextObservation<?> loop_contextObservation : _contextReport.observations) {
			updateQoCIndicator(loop_contextObservation);
		}
		QoCIMLogger.functionLog(FUNCTION_NAME, LogMessages.END_EXECUTION_FUNCTION);
		// - - - - - RETURN STATEMENT - - - - -
		return _contextReport;
	}

	/**
	 * The method initializes the arguments of the function
	 * <i>updateQoCIndicator</i>.
	 *
	 * @param _qoCMetricValueId
	 *            The <i>id</i> of the QoC metric value that have to be updated.
	 * @param _qoCIndicatorId
	 *            The <i>id</i> of the QoC indicator that references the QoC
	 *            metric value that is going to be updated.
	 * @return <b>this</b>
	 */
	public UpdateQoCIndicator setUp(final Integer _qoCIndicatorId) {
		// - - - - - CHECK THE VALUE OF THE ARGUMENTS - - - - -
		try {
			final String message = "UpdateQoCMetricValue.setup(Integer, Integer): the argument _qoCIndicatorId is null";
			ConstraintChecker.notNull(_qoCIndicatorId, message);
		} catch (final ConstraintCheckerException e) {
			setUpIsDone = false;
			return this;
		}
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		qoCIndicatorId = _qoCIndicatorId;
		setUpIsDone = true;
		// - - - - - RETURN STATEMENT - - - - -
		QoCIMLogger.functionLog(FUNCTION_NAME, LogMessages.SETUP_FUNCTION);
		return this;
	}

	@Override
	public void setParameters(final Map<String, String> _map_paramaters) {
		// - - - - - CHECK THE VALUE OF THE ARGUMENTS - - - - -
		try {
			final String message = "UpdateQoCIndicator.setParameters(Map<String, String>): the argument _map_paramaters is null";
			ConstraintChecker.notNull(_map_paramaters, message);
		} catch (final ConstraintCheckerException e) {
			return;
		}
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		final String qoCIndicatorId = _map_paramaters.get(PARAM_QOC_INDICATOR_ID);
		// - - - - - CORE OF THE METHOD - - - - -
		if (qoCIndicatorId != null) {
			setUp(new Integer(qoCIndicatorId));
		}
	}

	@Override
	public Map<String, String> parameters() {
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		final Map<String, String> ret_mapParameter = new HashMap<String, String>();
		// - - - - - CORE OF THE METHOD - - - - -
		ret_mapParameter.put(PARAM_QOC_INDICATOR_ID, "" + qoCIndicatorId);
		// - - - - - RETURN STATEMENT - - - - -
		return ret_mapParameter;
	}

	@Override
	public String getName() {
		return FUNCTION_NAME;
	}

	// # # # # # PRIVATE METHODS # # # # #

	/**
	 * The method searches the appropriate QoC metric value that have to be
	 * updated and then calls the private method <i>updateQoCMetricValues</i>.
	 *
	 * @param _contextObservation
	 *            The context observation where the QoCMetricValue have to be
	 *            updated.
	 */
	private void updateQoCIndicator(final ContextObservation<?> _contextObservation) {
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		/*
		 * The list of the QoC indicator of the context observation. This
		 * attribute is used to wrap the field <i>qoCIndicators</i> of the class
		 * <b>ContextObservation</b> and correctly use the method
		 * <i>searchFirstQoCIndicator</i> of the class <b>QoCIMFacade</b>.
		 */
		final Collection<QoCIndicator> list_qoCIndicator = _contextObservation.list_qoCIndicator;
		/*
		 * The QoC indicator that contains the QoC metric value that have to be
		 * updated.
		 */
		final QoCIndicator found_qoCIndicator = QoCIMFacade
				.searchFirstQoCIndicator(new ArrayList<QoCIndicator>(list_qoCIndicator), qoCIndicatorId);
		/*
		 * The old QoCMetricValue that will be updated.
		 */
		QoCMetricValue old_qoCMetricValue = null;
		// - - - - - CORE OF THE METHOD - - - - -
		if (found_qoCIndicator != null) {
			for (final QoCMetricValue loop_qoCMetricValue : found_qoCIndicator.list_qoCMetricValue) {
				old_qoCMetricValue = QoCIMFacade.searchFirstQoCMetricValue(found_qoCIndicator,
						loop_qoCMetricValue.id());
				if (old_qoCMetricValue != null) {
					updateQoCMetricValues(_contextObservation, found_qoCIndicator, old_qoCMetricValue);
				}
			}
		}
	}

	/**
	 * The method computes the new value of the QoC and set the private fields
	 * <i>value</i> and <i>modificationDate</i> of the QoC metric value that
	 * have to be update.
	 *
	 * @param _contextObservation
	 *            The context observation that contains the QoC metric value
	 *            that is updated.
	 * @param _qoCIndicator
	 *            The QoC indicator that contains the QoC metric value that will
	 *            be update.
	 * @param _old_QoCMetricValue
	 *            The old QoC metric value that will be update.
	 */
	private void updateQoCMetricValues(final ContextObservation<?> _contextObservation,
			final QoCIndicator _qoCIndicator, final QoCMetricValue _old_QoCMetricValue) {
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		/*
		 * The new value of the QoC metric value.
		 */
		Double new_qoCMetricValue;
		// - - - - - CORE OF THE METHOD - - - - -
		computeQoCMetricValue.setUp(_contextObservation.observable.entity.uri.toString(),
				_contextObservation.observable.uri.toString(), _contextObservation.date.getTime(),
				new Double(_contextObservation.value + ""), _old_QoCMetricValue.qoCMetricDefinition().id(),
				QoCIMFacade.createListQoCMetaData(_qoCIndicator));
		new_qoCMetricValue = (Double) computeQoCMetricValue.exec();
		_old_QoCMetricValue.value(new_qoCMetricValue);
		_old_QoCMetricValue.modificationDate((Date) currentDate.exec());
	}
}
