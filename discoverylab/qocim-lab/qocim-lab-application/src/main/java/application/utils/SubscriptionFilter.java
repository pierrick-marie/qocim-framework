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

import java.util.ArrayList;
import java.util.List;

import qocim.datamodel.QoCMetaData;
import qocim.datamodel.freshnessindicator.FreshnessFactory;
import qocim.datamodel.freshnessindicator.FreshnessQoCMetricDefinition;
import qocim.datamodel.precisionindicator.PercentPrecisionFactory;
import qocim.datamodel.precisionindicator.PercentPrecisionQoCMetricDefinition;
import qocim.datamodel.precisionindicator.PerthousandPrecisionFactory;
import qocim.datamodel.precisionindicator.PerthousandPrecisionQoCMetricDefinition;
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
public class SubscriptionFilter {

	/**
	 * The routing filter created by the class.
	 */
	QoCIMRoutingFilter routing_filter;
	/**
	 * To create a routing filter the <i>id</i> of the QoC metric value does not
	 * matter.
	 */
	private static final String DEFAULT_METRICVALUE_ID = "0";

	public SubscriptionFilter() {

		// Initialization of the freshness QoC meta-data and production of the
		// resulting constraint.
		final QoCMetaData freshness = new QoCMetaData(FreshnessFactory.instance()
				.newQoCIndicator(DEFAULT_METRICVALUE_ID, FreshnessQoCMetricDefinition.MIN_VALUE_DEFAULTVALUE));

		// Initialization of the precision (evaluated in percent) QoC meta-data
		// and production of the resulting constraint.
		final QoCMetaData percentPrecision = new QoCMetaData(PercentPrecisionFactory.instance()
				.newQoCIndicator(DEFAULT_METRICVALUE_ID, PercentPrecisionQoCMetricDefinition.MIN_VALUE_DEFAULTVALUE));

		// Initialization of the precision (evaluated in per-thousand) QoC
		// meta-data and production of the resulting constraint.
		final QoCMetaData perthousandPrecision = new QoCMetaData(PerthousandPrecisionFactory.instance().newQoCIndicator(
				DEFAULT_METRICVALUE_ID, PerthousandPrecisionQoCMetricDefinition.MIN_VALUE_DEFAULTVALUE));

		// The filter generator used to transform QoC meta-data into
		// constraints.
		final IQoCIMRoutingFilterGenerator qocimFilterGenerator = new JavaScriptQoCIMRoutingFilterGenerator();

		// The list of QoC meta-data handled by <i>qocimFilterGenerator</i>.
		final List<QoCMetaData> constraints = new ArrayList<QoCMetaData>();

		// The subscription routing filter.
		routing_filter = new QoCIMRoutingFilter();

		// Adding the QoC meta-data to the list of constraints.
		constraints.add(freshness);
		constraints.add(percentPrecision);
		constraints.add(perthousandPrecision);

		// Creation of the routing filter.
		qocimFilterGenerator.addQoCCriterionConstraints(constraints);
		routing_filter.addFilterGenerator(new JavaScriptBeginRoutingFilterGenerator());
		routing_filter.addFilterGenerator(qocimFilterGenerator);
		routing_filter.addFilterGenerator(new JavaScriptEndRoutingFilterGenerator());
	}

	@Override
	public String toString() {
		return routing_filter.toString();
	}
}
