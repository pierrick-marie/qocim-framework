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

import qocim.aggregation.IAggregationFunction;
import qocim.aggregation.IAgregationOperator;
import qocim.aggregation.IResultListener;
import qocim.aggregation.operator.utils.NotValidInformationException;
import qocim.information.QInformation;
import qocim.utils.logs.QoCIMLogger;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Pierrick MARIE
 */
public class SimpleAggregationFunction implements IAggregationFunction {

	// # # # # # CONSTANTS # # # # #

	/**
	 * <i>functionThread</i> is used as a deamon thread.
	 */
	public static final boolean IS_A_DEAMON = true;

	// # # # # # PRIVATE VARIABLES # # # # #

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
	 * The number of context report that have to be collected by the function
	 * before the execution of the operator.
	 */
	private Integer nbHandledInformation;

	// # # # # # CONSTRUCTORS # # # # #

	public SimpleAggregationFunction(final Integer nbHandledInformation, final IAgregationOperator operator, final IResultListener listener) {
		// - - - - - CORE OF THE METHOD - - - - -
		informationList = new LinkedList<>();
		this.nbHandledInformation = nbHandledInformation;
		resultListener = listener;
		this.operator = operator;
	}

	// # # # # # PUBLIC METHODS # # # # #

	@Override
	public IAggregationFunction addInformation(QInformation<?> information) {

		informationList.add(information);

		if(informationList.size() >= nbHandledInformation) {
			execFunction();
		}

		return this;
	}

	@Override
	public synchronized void execFunction() {
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
	public IAggregationFunction setOperator(final IAgregationOperator operator) {
		this.operator = operator;
		return this;
	}

	@Override
	public IAggregationFunction setResultListener(final IResultListener listener) {
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

	public Integer nbHandledInformation() {
		return nbHandledInformation;
	}

	public IAggregationFunction setNbHandledInformation(Integer nbHandledInformation) {
		this.nbHandledInformation = nbHandledInformation;
		return this;
	}
}
