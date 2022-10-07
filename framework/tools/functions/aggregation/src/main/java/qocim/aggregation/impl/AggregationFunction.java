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
package qocim.aggregation.impl;

import java.util.LinkedList;
import java.util.List;

import qocim.aggregation.IAggregationFunction;
import qocim.aggregation.IAggregationListener;
import qocim.aggregation.IAgregationOperator;
import qocim.information.QInformation;

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
public class AggregationFunction implements IAggregationFunction {

	// # # # # # CONSTANTS # # # # #

//	/**
//	 * The name of the parameter 1.
//	 */
//	public static final String PARAM_TIME_TO_WAIT = "time to wait";
//	/**
//	 * The name of the parameter 2.
//	 */
//	public static final String PARAM_NB_HANDLED_INFORMATION = "nb handled information";
//	/**
//	 * The name of the parameter 3.
//	 */
//	public static final String PARAM_TIMER_ONLY = "timer only";

//	/**
//	 * <i>functionThread</i> is used as a deamon thread.
//	 */
//	public static final boolean IS_A_DEAMON = true;

//	/**
//	 * It the <i>timeToWait</i> equals this value, the <i>functionThread</i> is
//	 * stopped.
//	 */
//	public static final int STOP_TIMER_FUNCTION = 0;

	// # # # # # PRIVATE VARIABLES # # # # #

	private List<QInformation<?>> informationList;

	/**
	 * The thread used to execute <i>functionTimer</i>.
	 */
	private final Thread functionThread;
	/**
	 * A timer to periodically execute the function.
	 */
	private final AggregationFunctionTimer functionTimer;
	/**
	 * The listener to notify when a new context report is available.
	 */
	private IAggregationListener resultListener;
	/**
	 * The operator executed by the function.
	 */
	private IAgregationOperator operator;
	/**
	 * The number of context report that have to be collected by the function
	 * before the execution of the operator.
	 */
	private Integer nbHandledInformation;

	/**
	 * Set if only the timer is take into account to trigger the operator.
	 */
	private Boolean param_timerOnly;
	private Integer param_nbHandledInformation;

	// # # # # # CONSTRUCTORS # # # # #

	public AggregationFunction() {
		// - - - - - CORE OF THE METHOD - - - - -
		informationList = new LinkedList<>();
		functionTimer = new AggregationFunctionTimer(this);
		functionThread = new Thread(functionTimer);
		functionThread.setDaemon(true);
		nbHandledInformation = 0;
		param_nbHandledInformation = -1;
		param_timerOnly = false;
	}

	// # # # # # PUBLIC METHODS # # # # #

	@Override
	public IAggregationFunction addInformation(QInformation<?> information) {
		return null;
	}

	@Override
	public synchronized void execFunction() {
//		// - - - - - CORE OF THE METHOD - - - -
//		try {
//			for (final ContextReport loop_resultContextReport : operator.applyOperator(list_contextReport)) {
//				resultListener.newInformation(loop_resultContextReport);
//			}
//		} catch (final NotValidInformationException _exception) {
//			// Do nothing.
//		} finally {
//			list_contextReport.clear();
//		}
	}

	@Override
	public IAggregationFunction setOperator(final IAgregationOperator _operator) {
		operator = _operator;
		return this;
	}

	@Override
	public IAggregationFunction setResultListener(final IAggregationListener listener) {
		resultListener = listener;
		return this;
	}

	@Override
	public IAgregationOperator operator() {
		return operator;
	}

	@Override
	public IAggregationListener resultListener() {
		return resultListener;
	}

	@Override
	public Integer timeToWait() {
		return functionTimer.nbWaintingMiliSecond;
	}

	@Override
	public IAggregationFunction setTimeToWait(final Integer timeToWait) {
		if (timeToWait.equals(0)) {
			functionTimer.run = false;
		} else {
			functionTimer.nbWaintingMiliSecond = timeToWait;
			functionTimer.run = true;
		}
		functionThread.start();
		return this;
	}

	@Override
	public Integer nbHandledInformation() {
		return param_nbHandledInformation;
	}

	@Override
	public IAggregationFunction setNbHandledInformation(Integer nbHandledInformation) {
		param_nbHandledInformation = nbHandledInformation;
		return this;
	}

	@Override
	public AggregationFunction setTimerOnly(final Boolean timerOnly) {
		param_timerOnly = timerOnly;
		return this;
	}

	@Override
	public Boolean isTimerOnly() {
		return param_timerOnly;
	}
}
