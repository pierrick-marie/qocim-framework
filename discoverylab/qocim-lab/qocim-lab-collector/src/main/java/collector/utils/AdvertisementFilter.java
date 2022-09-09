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
package collector.utils;

import java.util.ArrayList;
import java.util.List;

import qocim.datamodel.QoCMetaData;
import qocim.datamodel.precisionindicator.PercentPrecisionFactory;
import qocim.datamodel.precisionindicator.PercentPrecisionQoCMetricDefinition;
import qocim.routingfilters.IQoCIMRoutingFilterGenerator;
import qocim.routingfilters.impl.JavaScriptBeginRoutingFilterGenerator;
import qocim.routingfilters.impl.JavaScriptEndRoutingFilterGenerator;
import qocim.routingfilters.impl.JavaScriptQoCIMRoutingFilterGenerator;
import qocim.routingfilters.impl.QoCIMRoutingFilter;

/**
 * Method used to generate an advertisement routing filter.
 *
 * The structure of the resulting filter is:
 *
 * <pre>
 * if(QoC meta-data DOES NOT contains the freshness indicator) return FALSE
 * if(QoC meta-data DOES NOT contains the precision indicator (evaluated in
 * percent)) return FALSE
 * </pre>
 *
 * So, the QoC meta-data produced by this collector have to contain the
 * freshness AND the precision indicator.
 *
 * @author Pierrick MARIE
 */
public class AdvertisementFilter {

	/**
	 * The routing filter created by the method.
	 */
	private final QoCIMRoutingFilter ret_filter;

	/**
	 * To create a routing filter the id of the QoC metric value does not
	 * matter.
	 */
	private static final String DEFAULT_METRICVALUE_ID = "0";

	public AdvertisementFilter() {

		/**
		 * The precision QoC meta-data used to add a constraint into the filter.
		 */
		QoCMetaData precision;

		/**
		 * The filter generator used to transform QoC meta-data into
		 * constraints.
		 */
		IQoCIMRoutingFilterGenerator qocimFilterGenerator;

		/**
		 * The list of QoC meta-data handled by <i>qocimFilterGenerator</i>.
		 */
		List<QoCMetaData> constraints;

		ret_filter = new QoCIMRoutingFilter();
		qocimFilterGenerator = new JavaScriptQoCIMRoutingFilterGenerator();
		constraints = new ArrayList<QoCMetaData>();

		{
			// TODO: To answer to exercise 2, read the following code
			// Initialization of the precision QoC meta-data used to create the
			// constraint.
			precision = new QoCMetaData(PercentPrecisionFactory.instance().newQoCIndicator(DEFAULT_METRICVALUE_ID,
					PercentPrecisionQoCMetricDefinition.MIN_VALUE_DEFAULTVALUE));
			// Adding the constraint concerning the precision.
			constraints.add(precision);
		}

		// Generating the Javascript code corresponding to the list of
		// constraints
		qocimFilterGenerator.addQoCCriterionConstraints(constraints);

		// Generating the entire QoCIM-based routing filter.
		ret_filter.addFilterGenerator(new JavaScriptBeginRoutingFilterGenerator());
		ret_filter.addFilterGenerator(qocimFilterGenerator);
		ret_filter.addFilterGenerator(new JavaScriptEndRoutingFilterGenerator());
	}

	@Override
	public String toString() {
		return ret_filter.getRoutingFilter();
	}

}
