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
import qocim.datamodel.precisionindicator.PerthousandPrecisionFactory;
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
public class SubscriptionFilterQuestion2 {

	/**
	 * The routing filter created by the class.
	 */
	QoCIMRoutingFilter routing_filter;

	/**
	 * To create a routing filter the <i>id</i> of the QoC metric value does not
	 * matter.
	 */
	private static final String DEFAULT_METRICVALUE_ID = "0";

	/**
	 * The minimal required value of freshness QoC meta-data in second.
	 */
	private static final double MIN_REQUIRED_FRESHNESS_VALUE = 4.0;

	/**
	 * The minimal required value of precision QoC meta-data in per-thousand.
	 */
	private static final double MIN_REQUIRED_PRECISION_VALUE = 600.0;

	public SubscriptionFilterQuestion2() {

		// The freshness QoC meta-data used to add a constraint into the filter.
		final QoCMetaData freshness = new QoCMetaData(
				FreshnessFactory.instance().newQoCIndicator(DEFAULT_METRICVALUE_ID, MIN_REQUIRED_FRESHNESS_VALUE));

		// The precision QoC meta-data used to add a constraint into the filter.
		final QoCMetaData precision = new QoCMetaData(PerthousandPrecisionFactory.instance()
				.newQoCIndicator(DEFAULT_METRICVALUE_ID, MIN_REQUIRED_PRECISION_VALUE));

		// The filter generator used to transform QoC meta-data into
		// constraints.
		final IQoCIMRoutingFilterGenerator qocimFilterGenerator = new JavaScriptQoCIMRoutingFilterGenerator();

		// The map of QoC meta-data handled by <i>qocimFilterGenerator</i>.
		final Map<QoCMetaData, EComparator> constraints = new HashMap<QoCMetaData, EComparator>();

		// The subscription routing filter.
		routing_filter = new QoCIMRoutingFilter();

		{
			// TODO: There is a difference between the following code
			// blocks. Use the first code block to generate the routing filter
			// and
			// observe the behavior of the application. Then, use the second
			// code
			// block to generate the routing filter and observe the behavior of
			// the
			// application.

			// Code block number 1:
			{
				constraints.put(freshness, EComparator.LOWER_EQUALS);
				qocimFilterGenerator.addQoCValueConstraints(constraints);
				constraints.clear();
				constraints.put(precision, EComparator.UPPER);
				qocimFilterGenerator.addQoCValueConstraints(constraints);
			}

			// Code block number 2:
			{
				constraints.put(freshness, EComparator.LOWER_EQUALS);
				constraints.put(precision, EComparator.UPPER);
				qocimFilterGenerator.addQoCValueConstraints(constraints);
			}

			// With the first code block, the application requires QoC meta-data
			// with freshness value LOWER_EQUALS to 4 <b>AND</b> precision value
			// UPPER to 600 while with the second code block the application the
			// application requires QoC meta-data with freshness value
			// LOWER_EQUALS to 4 <b>OR</b> precision value
			// UPPER to 600.
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
