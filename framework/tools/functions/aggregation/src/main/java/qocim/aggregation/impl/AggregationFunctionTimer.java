/**
 * This file is part of the QoCIM middleware.
 * <p>
 * Copyright (C) 2014 IRIT, Télécom SudParis
 * <p>
 * The QoCIM software is free software: you can redistribute it and/or modify
 * It under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * The QoCIM software platform is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * <p>
 * See the GNU Lesser General Public License
 * for more details: http://www.gnu.org/licenses
 * <p>
 * Initial developer(s): Pierrick MARIE
 * Contributor(s):
 */
package qocim.aggregation.impl;

import org.apache.log4j.Level;
import qocim.aggregation.IAggregationFunction;
import qocim.aggregation.IAgregationOperator;
import qocim.aggregation.IResultListener;
import qocim.aggregation.operator.utils.NotValidInformationException;
import qocim.information.QInformation;
import qocim.utils.logs.QoCIMLogger;

import java.util.LinkedList;
import java.util.List;

/**
 * AggregationFunctionTimer waits nb milliseconds before executing the function.
 *
 * @author Pierrick MARIE
 */
public class AggregationFunctionTimer implements IAggregationFunction {

	// # # # # # PROTECTED VARIABLES # # # # #

	private List<QInformation<?>> informationList;
	/**
	 * The listener to notify when a new context report is available.
	 */
	private IResultListener resultListener;
	/**
	 * The operator executed by the function.
	 */
	private IAgregationOperator operator;
	/**
	 * A boolean to allow the execution of the method <i>run</i>.
	 */
	private volatile Boolean run;
	/**
	 * Determine the number of seconds to wait.
	 */
	private volatile Integer nbWaintingMiliSecond;

	private Runnable runner;

	// # # # # # CONSTRUCTORS # # # # #

	public AggregationFunctionTimer(final Integer nbWaintingMiliSecond, final IAgregationOperator operator, final IResultListener listener) {
		// - - - - - CORE OF THE METHOD - - - - -
		run = false;
		this.nbWaintingMiliSecond = nbWaintingMiliSecond;
		informationList = new LinkedList<>();
		resultListener = listener;
		this.operator = operator;
		runner = new Runnable() {
			@Override
			public void run() {
				while (run) {
					try {
						Thread.sleep(nbWaintingMiliSecond);
						execFunction();
					} catch (final InterruptedException exception) {
						QoCIMLogger.logger.log(Level.WARN, "Error in AggregationFunctionTimer.run()", exception);
					}
				}
			}
		};
		new Thread(runner).start();
	}

	// # # # # # PUBLIC METHODS # # # # #

	public void start() {
		run = true;
	}

	public void stop() {
		run = false;
	}

	@Override
	public IAggregationFunction addInformation(QInformation<?> information) {
		informationList.add(information);
		return this;
	}

	@Override
	public void execFunction() {
		// - - - - - CORE OF THE METHOD - - - -
		try {
			resultListener.newInformation(operator.applyOperator(informationList));
		} catch (final NotValidInformationException exception) {
			QoCIMLogger.error(exception.getMessage());
		} finally {
			informationList.clear();
		}
	}

	@Override
	public IAggregationFunction setOperator(IAgregationOperator operator) {
		this.operator = operator;
		return this;
	}

	@Override
	public IAggregationFunction setResultListener(IResultListener listener) {
		resultListener = listener;
		return this;
	}

	@Override
	public IAgregationOperator operator() {
		return operator;
	}

	@Override
	public IResultListener resultListener() {
		return resultListener;
	}
}
