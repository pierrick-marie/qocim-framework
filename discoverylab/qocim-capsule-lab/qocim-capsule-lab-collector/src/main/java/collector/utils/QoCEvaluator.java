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

import mucontext.datamodel.context.ContextObservation;
import qocim.datamodel.precisionindicator.PercentPrecisionFactory;
import qocim.datamodel.precisionindicator.PercentPrecisionQoCMetricDefinition;

public class QoCEvaluator {

	private Integer counter;
	final PercentPrecisionQoCMetricDefinition precisionDefinition;
	final PercentPrecisionFactory precisionFactory;

	public QoCEvaluator() {
		counter = 0;
		precisionDefinition = new PercentPrecisionQoCMetricDefinition();
		precisionFactory = PercentPrecisionFactory.instance();
	}

	public void addQoCMetaData(final ContextObservation<Double> contextObservation) {

		// Compute the precision value.
		final Double precisionValue = precisionDefinition.computeQoCMetricValue("", "", contextObservation.date,
				contextObservation.value, null);
		// Create and add a new precision QoC indicator in the QoC
		// meta-data.
		contextObservation.list_qoCIndicator.add(precisionFactory.newQoCIndicator("" + counter, precisionValue));

		counter++;
	}
}
