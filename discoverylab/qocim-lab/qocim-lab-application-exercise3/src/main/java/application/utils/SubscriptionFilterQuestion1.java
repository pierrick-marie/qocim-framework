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
package application.utils;

import java.util.HashMap;
import java.util.Map;

import qocim.datamodel.QoCMetaData;
import qocim.datamodel.freshnessindicator.FreshnessFactory;
import qocim.routingfilters.EComparator;
import qocim.routingfilters.IQoCIMRoutingFilterGenerator;
import qocim.routingfilters.impl.JavaScriptBeginRoutingFilterGenerator;
import qocim.routingfilters.impl.JavaScriptEndRoutingFilterGenerator;
import qocim.routingfilters.impl.JavaScriptQoCIMRoutingFilterGenerator;
import qocim.routingfilters.impl.QoCIMRoutingFilter;

/**
 * Method used to generate a subscription routing filter.
 *
 * The structure of the resulting filter is:
 *
 * <pre>
 * if(QoC meta-data DOES NOT contains the freshness indicator OR QoC meta-data DOES NOT contains the precision indicator (evaluated in
 * percent) OR QoC meta-data DOES NOT contains the precision indicator (evaluated in
 * per thousand)) return FALSE
 * </pre>
 *
 * So, the QoC meta-data received by this application have to contain at least
 * the freshness OR the precision indicator.evaluated in percent or in per
 * thousand.
 *
 * @author Pierrick MARIE
 */
public class SubscriptionFilterQuestion1 {

	/**
	 * The routing filter created by the class.
	 */
	private final QoCIMRoutingFilter routing_filter;

	/**
	 * To create a routing filter the <i>id</i> of the QoC metric value does not
	 * matter.
	 */
	private static final String DEFAULT_METRICVALUE_ID = "0";

	/**
	 * The minimal required value of freshness QoC meta-data in second.
	 */
	private static final double MIN_REQUIRED_FRESHNESS_VALUE = 4;

	public SubscriptionFilterQuestion1() {

		// The freshness QoC meta-data used to add a constraint into the filter.
		QoCMetaData freshness;

		// The filter generator used to transform QoC meta-data into
		// constraints.
		final IQoCIMRoutingFilterGenerator qocimFilterGenerator = new JavaScriptQoCIMRoutingFilterGenerator();

		// The map of QoC meta-data handled by <i>qocimFilterGenerator</i>.
		final Map<QoCMetaData, EComparator> constraints = new HashMap<QoCMetaData, EComparator>();

		// The subscription routing filter.
		routing_filter = new QoCIMRoutingFilter();

		{
			// TODO: To answer to the question 1 of the exercise 3 read the
			// following code and execute the instruction presented in the next
			// "to do" tag.

			// Initialization of the freshness QoC meta-data.
			freshness = new QoCMetaData(
					FreshnessFactory.instance().newQoCIndicator(DEFAULT_METRICVALUE_ID, MIN_REQUIRED_FRESHNESS_VALUE));

			// Creating a new constraint: the freshness QoC meta-data must be
			// LOWER_EQUALS than the MIN_REQUIRED_FRESHNESS_VALUE.
			constraints.put(freshness, EComparator.LOWER_EQUALS);

			// Translation of the constraint into Javascript code.
			qocimFilterGenerator.addQoCValueConstraints(constraints);
			constraints.clear();

			// TODO: Based on the previous code, complete this class by
			// executing the following instructions:

			// (1) Create a new QoCMetaData with the class
			// mucontext.datamodel.qocim.precisionindicator.PerthousandPrecisionFactory
			// and the appropriate value (600).

			// (2) Add the QoCMetaData into in the variable <i>constraints</i>
			// with the appropriate comparator (UPPER).

			// (3) Generate the corresponding Javascript code with the method
			// qocimFilterGenerator.addQoCValueConstraints.
		}

		routing_filter.addFilterGenerator(new JavaScriptBeginRoutingFilterGenerator());
		routing_filter.addFilterGenerator(qocimFilterGenerator);
		routing_filter.addFilterGenerator(new JavaScriptEndRoutingFilterGenerator());
	}

	@Override
	public String toString() {
		return routing_filter.toString();
	}
}
