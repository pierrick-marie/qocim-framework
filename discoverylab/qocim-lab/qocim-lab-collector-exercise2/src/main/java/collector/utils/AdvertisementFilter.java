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

	public AdvertisementFilter() {

		// The filter generator used to transform QoC meta-data into
		// constraints.
		final IQoCIMRoutingFilterGenerator qocimFilterGenerator = new JavaScriptQoCIMRoutingFilterGenerator();

		// The list of QoC meta-data handled by <i>qocimFilterGenerator</i>.
		final List<QoCMetaData> constraints = new ArrayList<QoCMetaData>();

		ret_filter = new QoCIMRoutingFilter();

		{
			// TODO: to answer to the exercise 2, read the class
			// collector.utils.AdvertisementFilter of the maven project
			// "qocim-lab-collector" and complete the following instructions.

			// (1) Create a precision QoC meta-data produced with the class
			// mucontext.datamodel.qocim.precisionindicator.PerthousandPrecisionFactory
			// into the maven project "qocim-precisionindicator".

			// (2) Add the precision QoC meta-data into the list of QoCMetaData
			// (the variable constraints).

			// (3) Create a freshness QoC meta-data produced with the class
			// mucontext.datamodel.qocim.freshnessindicator.FreshnessFactory
			// in the maven module "qocim-freshnessindicator".

			// (4) Add the freshness QoC meta-data into the list of QoCMetaData
			// (the variable constraints).
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
