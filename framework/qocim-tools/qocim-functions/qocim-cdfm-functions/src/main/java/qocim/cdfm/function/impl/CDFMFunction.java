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

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import mucontext.datamodel.context.ContextReport;
import qocim.cdfm.function.ICDFMFunction;
import qocim.cdfm.function.ICDFMOperator;
import qocim.cdfm.function.utils.LogMessages;
import qocim.cdfm.operator.utils.NotValidInformationException;
import qocim.datamodel.utils.ConstraintChecker;
import qocim.datamodel.utils.ConstraintCheckerException;
import qocim.datamodel.utils.QoCIMLogger;
import qocim.functions.IQoCIMFunctionListener;

/**
 * CDFMFunction implements a Context Data Flow Management function. It is a
 * function that apply transformations on a list of context reports. The result
 * of the transformations is, at least, one context report. When a new context
 * report available, a <b>ICDFMFunctionResultListener</b> is notified.
 *
 * The transformations applied by the function are executed by an operator. This
 * function executes the operator when
 * <i>TemporalCDFMFunctionTimer.nbWaintingSecond</i> seconds have been waited.
 *
 * @author Pierrick MARIE
 */
public class CDFMFunction implements ICDFMFunction {

	// # # # # # CONSTANTS # # # # #

	/**
	 * The name of the function.
	 */
	public static final String FUNCTION_NAME = "Context data flow management function";
	/**
	 * The name of the parameter 1.
	 */
	public static final String PARAM_TIME_TO_WAIT = "time_to_wait";
	/**
	 * The name of the parameter 2.
	 */
	public static final String PARAM_NB_HANDLED_CONTEXT_REPORT = "nb_handled_context_report";
	/**
	 * The name of the parameter 3.
	 */
	public static final String PARAM_TIMER_ONLY = "timer_only";

	/**
	 * <i>functionThread</i> is used as a deamon thread.
	 */
	public static final boolean IS_A_DEAMON = true;

	/**
	 * It the <i>timeToWait</i> equals this value, the <i>functionThread</i> is
	 * stopped.
	 */
	public static final int VALUE_TO_STOP_TIMER_FUNCTION = 0;

	// # # # # # PRIVATE VARIABLES # # # # #

	/**
	 * The list of context report buffered before the execution of the operator.
	 */
	private final LinkedList<ContextReport> list_contextReport;
	/**
	 * The thread used to execute <i>functionTimer</i>.
	 */
	private final Thread functionThread;
	/**
	 * A timer to periodically execute the function.
	 */
	private final CDFMFunctionTimer functionTimer;
	/**
	 * The listener to notify when a new context report is available.
	 */
	private IQoCIMFunctionListener resultListener;
	/**
	 * The operator executed by the function.
	 */
	private ICDFMOperator operator;
	/**
	 * The number of context report that have to be collected by the function
	 * before the execution of the operator.
	 */
	private Integer nbHandledContextReport;

	/**
	 * Set if only the timer is take into account to trigger the operator.
	 */
	private Boolean timerOnly;

	// # # # # # CONSTRUCTORS # # # # #

	public CDFMFunction() {
		// - - - - - CORE OF THE METHOD - - - - -
		list_contextReport = new LinkedList<ContextReport>();
		functionTimer = new CDFMFunctionTimer(this);
		functionThread = new Thread(functionTimer);
		functionThread.setDaemon(IS_A_DEAMON);
		timerOnly = false;
		QoCIMLogger.functionLog(FUNCTION_NAME, LogMessages.NEW_FUNCTION_INSTANCE);
	}

	// # # # # # PUBLIC METHODS # # # # #

	@Override
	public CDFMFunction addContextReport(final ContextReport _contextReport) {
		// - - - - - CHECK THE VALUE OF THE ARGUMENTS - - - - -
		try {
			final String message = "CDFMFunction.addContextReport(ContextReport): the argument _contextReport is null.";
			ConstraintChecker.notNull(_contextReport, message);
		} catch (final ConstraintCheckerException e) {
			return this;
		}
		// - - - - - CORE OF THE METHOD - - - -
		/*
		 * Removing the first received context report if <i>timerOnly</i> is
		 * True AND the buffer is full (list_contextReport.size() ==
		 * nbHandledContextReport). By this way, the size of the buffer is not
		 * more than <i>nbHandledContextReport</i>.
		 */
		if (timerOnly && list_contextReport.size() == nbHandledContextReport) {
			list_contextReport.remove(0);
		}
		list_contextReport.add(_contextReport);
		QoCIMLogger.functionLog(FUNCTION_NAME, LogMessages.ADD_CONTEXT_REPORT);
		/*
		 * Trigger the operator only if <i>timerOnly</i> is False AND the buffer
		 * is full.
		 */
		if (list_contextReport.size() == nbHandledContextReport && !timerOnly) {
			execFunction();
		}
		// - - - - - RETURN STATEMENT - - - - -
		return this;
	}

	@Override
	public synchronized void execFunction() {
		// - - - - - CORE OF THE METHOD - - - -
		QoCIMLogger.functionLog(FUNCTION_NAME, LogMessages.BEGIN_EXECUTION_FUNCTION);
		try {
			for (final ContextReport loop_resultContextReport : operator.applyOperator(list_contextReport)) {
				resultListener.newInformation(loop_resultContextReport);
			}
		} catch (final NotValidInformationException _exception) {
			// Do nothing.
		} finally {
			list_contextReport.clear();
		}
		QoCIMLogger.functionLog(FUNCTION_NAME, LogMessages.END_EXECUTION_FUNCTION);
	}

	@Override
	public ICDFMFunction setOperator(final ICDFMOperator _operator) {
		// - - - - - CHECK THE VALUE OF THE ARGUMENTS - - - - -
		try {
			final String message = "CDFMFunction.setOperator(ICDFMOperator<List<ContextReport>, ContextReport>): the argument _operator is null";
			ConstraintChecker.notNull(_operator, message);
		} catch (final ConstraintCheckerException e) {
			return this;
		}
		// - - - - - CORE OF THE METHOD - - - - -
		QoCIMLogger.functionLog(FUNCTION_NAME, LogMessages.SET_OPERATOR + _operator);
		operator = _operator;
		// - - - - - RETURN STATEMENT - - - - -
		return this;
	}

	@Override
	public CDFMFunction setResultListener(final IQoCIMFunctionListener _resultListener) {
		// - - - - - CHECK THE VALUE OF THE ARGUMENTS - - - - -
		try {
			final String message = "CDFMFunction.setResultListener(ICDFMFunctionResultListener<ContextReport>): the argument _resultListener is null";
			ConstraintChecker.notNull(_resultListener, message);
		} catch (final ConstraintCheckerException e) {
			return this;
		}
		// - - - - - CORE OF THE METHOD - - - - -
		QoCIMLogger.functionLog(FUNCTION_NAME, LogMessages.SET_RESULT_LISTENER + _resultListener);
		resultListener = _resultListener;
		// - - - - - RETURN STATEMENT - - - - -
		return this;
	}

	@Override
	public CDFMFunction setTimeToWait(final Integer _timeToWait) {
		// - - - - - CHECK THE VALUE OF THE ARGUMENTS - - - - -
		try {
			String message = "CDFMFunction.setTimeToWait(Integer): the argument _timeToWait is null.";
			ConstraintChecker.notNull(_timeToWait, message);
			message = "CDFMFunction.setTimeToWait(Integer): the value of the argument _timeToWait is negative.";
			ConstraintChecker.integerIsBetweenBounds(_timeToWait, 0, Integer.MAX_VALUE, message);
		} catch (final ConstraintCheckerException e) {
			return this;
		}
		// - - - - - CORE OF THE METHOD - - - - -
		QoCIMLogger.functionLog(FUNCTION_NAME, LogMessages.SET_TIME_TO_WAIT + _timeToWait);
		if (_timeToWait.equals(VALUE_TO_STOP_TIMER_FUNCTION)) {
			functionTimer.run = false;
		} else {
			functionTimer.nbWaintingMiliSecond = _timeToWait;
			functionTimer.run = true;
		}
		functionThread.start();
		// - - - - - RETURN STATEMENT - - - - -
		return this;
	}

	@Override
	public ICDFMOperator operator() {
		// - - - - - RETURN STATEMENT - - - - -
		QoCIMLogger.functionLog(FUNCTION_NAME, LogMessages.GET_OPERATOR + operator);
		return operator;
	}

	@Override
	public IQoCIMFunctionListener resultListener() {
		// - - - - - RETURN STATEMENT - - - - -
		QoCIMLogger.functionLog(FUNCTION_NAME, LogMessages.GET_RESULT_LISTENER + resultListener);
		return resultListener;
	}

	@Override
	public Integer timeToWait() {
		// - - - - - RETURN STATEMENT - - - - -
		QoCIMLogger.functionLog(FUNCTION_NAME, LogMessages.GET_TIME_TO_WAIT + functionTimer.nbWaintingMiliSecond);
		return functionTimer.nbWaintingMiliSecond;
	}

	/**
	 * @return The number of context report stored by the function.
	 */
	@Override
	public Integer nbHandledContextReport() {
		// - - - - - RETURN STATEMENT - - - - -
		QoCIMLogger.functionLog(FUNCTION_NAME, LogMessages.GET_NB_HANDLED_MESSAGES + nbHandledContextReport);
		return nbHandledContextReport;
	}

	@Override
	public ICDFMFunction setTimerOnly(final Boolean _timerOnly) {
		// - - - - - CORE OF THE METHOD - - - - -
		timerOnly = _timerOnly;
		// - - - - - RETURN STATEMENT - - - - -
		return this;
	}

	@Override
	public Boolean isTimerOnly() {
		return timerOnly;
	}

	@Override
	public ICDFMFunction setNbHandledContextReport(final Integer _nbHandledContextReport) {
		// - - - - - CHECK THE VALUE OF THE ARGUMENTS - - - - -
		try {
			final String message = "CDFMFunction.setNbHandledContextReport(Integer): the argument _nbHandledContextReport.";
			ConstraintChecker.notNull(_nbHandledContextReport, message);
		} catch (final ConstraintCheckerException e) {
			return this;
		}
		// - - - - - CORE OF THE METHOD - - - - -
		nbHandledContextReport = _nbHandledContextReport;
		QoCIMLogger.functionLog(FUNCTION_NAME, LogMessages.SET_NB_HANDLED_MESSAGES + _nbHandledContextReport);
		// - - - - - RETURN STATEMENT - - - - -
		return this;
	}

	@Override
	public void setParameters(final Map<String, String> _map_paramaters) {
		// - - - - - CHECK THE VALUE OF THE ARGUMENTS - - - - -
		try {
			final String message = "CDFMFunction.setParameters(Map<String, String>): the argument _map_paramaters is null";
			ConstraintChecker.notNull(_map_paramaters, message);
		} catch (final ConstraintCheckerException e) {
			return;
		}
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		String parameterValue = "";
		// - - - - - CORE OF THE METHOD - - - - -
		if ((parameterValue = _map_paramaters.get(PARAM_NB_HANDLED_CONTEXT_REPORT)) != null) {
			setNbHandledContextReport(new Integer(parameterValue));
		}
		if ((parameterValue = _map_paramaters.get(PARAM_TIME_TO_WAIT)) != null) {
			setTimeToWait(new Integer(parameterValue));
		}
		if ((parameterValue = _map_paramaters.get(PARAM_TIMER_ONLY)) != null) {
			setTimerOnly(new Boolean(parameterValue));
		}
	}

	@Override
	public Map<String, String> parameters() {
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		final Map<String, String> ret_mapParameter = new HashMap<String, String>();
		// - - - - - CORE OF THE METHOD - - - - -
		ret_mapParameter.put(PARAM_NB_HANDLED_CONTEXT_REPORT, "" + nbHandledContextReport);
		ret_mapParameter.put(PARAM_TIME_TO_WAIT, "" + functionTimer.nbWaintingMiliSecond);
		ret_mapParameter.put(PARAM_TIMER_ONLY, "" + timerOnly);
		// - - - - - RETURN STATEMENT - - - - -
		return ret_mapParameter;
	}
}
