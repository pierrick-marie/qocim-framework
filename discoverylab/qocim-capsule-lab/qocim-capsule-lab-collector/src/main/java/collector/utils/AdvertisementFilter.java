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
 * @author Pierrick MARIE
 */
public class AdvertisementFilter {

	/**
	 * The routing filter created by the method.
	 */
	private final QoCIMRoutingFilter routingFilter;

	/**
	 * To create a routing filter the id of the QoC metric value does not
	 * matter.
	 */
	private static final String DEFAULT_METRICVALUE_ID = "0";

	public AdvertisementFilter() {

		/*
		 * The precision QàC meta-data used to add a constraint into the filter.
		 */
		QoCMetaData percentPrecision;
		/*
		 * The filter generator used to transform QoC meta-data into
		 * constraints.
		 */
		IQoCIMRoutingFilterGenerator qocimFilterGenerator;
		/*
		 * The list of QoC meta-data handled by <i>qocimFilterGenerator</i>.
		 */
		List<QoCMetaData> constraints;

		constraints = new ArrayList<QoCMetaData>();
		qocimFilterGenerator = new JavaScriptQoCIMRoutingFilterGenerator();
		routingFilter = new QoCIMRoutingFilter();

		/*
		 * Initialization of the precision (evaluated in percent) QoC meta-data
		 * and production of the resulting constraint.
		 */
		percentPrecision = new QoCMetaData(PercentPrecisionFactory.instance().newQoCIndicator(DEFAULT_METRICVALUE_ID,
				PercentPrecisionQoCMetricDefinition.MIN_VALUE_DEFAULTVALUE));
		constraints.add(percentPrecision);

		/*
		 * Creation of the routing filter.
		 */
		qocimFilterGenerator.addQoCCriterionConstraints(constraints);
		routingFilter.addFilterGenerator(new JavaScriptBeginRoutingFilterGenerator());
		routingFilter.addFilterGenerator(qocimFilterGenerator);
		routingFilter.addFilterGenerator(new JavaScriptEndRoutingFilterGenerator());
	}

	@Override
	public String toString() {
		return routingFilter.getRoutingFilter();
	}

}
