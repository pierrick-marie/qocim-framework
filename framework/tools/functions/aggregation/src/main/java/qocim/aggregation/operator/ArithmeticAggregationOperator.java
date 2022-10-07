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
package qocim.aggregation.operator;

import qocim.aggregation.IAgregationOperator;

@Deprecated
public abstract class ArithmeticAggregationOperator implements IAgregationOperator {

//	private Map<String, String> parameters;
//
//	ArithmeticAggregationOperator() {
//		parameters = new HashMap<>();
//	}
//
//	@Override
//	public Map<String, String> parameters() {
//		return parameters;
//	}
//
//	/**
//	 * This method aggregate a list of of QoC metric value.
//	 *
//	 * @param listQocMetricValue
//	 *            The list of QoC metric value that will be aggregated.
//	 * @param qocMetricDefinition
//	 *            The Qoc metric definition used to create the QoC metric
//	 *            values.
//	 * @param _aggregationOperator
//	 *            The operator used to aggregate the QoC metric value.
//	 * @return The QoC indicator that will contains the resulting value.
//	 */
//	protected QoCIndicator aggregateListQoCMetricValue(final List<QoCMetricValue> listQocMetricValue,
//			final QoCMetricDefinition qocMetricDefinition) {
//		// - - - - - INITIALIZE THE VARIABLES - - - - -
//		/*
//		 * The result of the aggregation of the QoC metric value.
//		 */
//		final Double aggregatedValue;
//		/*
//		 * The list of the QoC metric value.
//		 */
//		final List<Double> listQocMetricValue = new ArrayList<Double>();
//		/*
//		 * The first QoC metric value of the list. This instance will be used to
//		 * contain the resulting aggregated value.
//		 */
//		final QoCMetricValue firstQoCMetricValue = new QoCMetricValue(listQocMetricValue.get(0));
//		/*
//		 * The QoC indicator that reference the <i>firstQoCMetricValue</i>.
//		 */
//		final QoCIndicator retqocIndicator = (QoCIndicator) firstQoCMetricValue.container();
//		// - - - - - CORE OF THE METHOD - - - - -
//		for (final QoCMetricValue loopqocMetricValue : listQocMetricValue) {
//			listQocMetricValue.add(loopqocMetricValue.value());
//		}
//		aggregatedValue = aggregateListValue(listQocMetricValue);
//		firstQoCMetricValue.value(aggregatedValue);
//		firstQoCMetricValue.modificationDate(new Date());
//		QoCIMFacade.removeAllQoCMetricValue(retqocIndicator);
//		QoCIMFacade.removeAllQoCMetricValue(qocMetricDefinition);
//		QoCIMFacade.addQoCMetricValue(firstQoCMetricValue, retqocIndicator);
//		QoCIMFacade.addQoCMetricValue(firstQoCMetricValue, qocMetricDefinition);
//		// - - - - - RETURN STATEMENT - - - - -
//		return retqocIndicator;
//	}
}
