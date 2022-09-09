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

	// The QoC indicator used to evaluate the quality of the context
	// information.
	// In this exercice, the "per-thousand precision indicator" is used.
	// TODO: To answer to exercise 2, change the indicator used by the
	// collector.
	private final PercentPrecisionQoCMetricDefinition precisionDefinition;
	private final PercentPrecisionFactory precisionFactory;

	public QoCEvaluator() {
		counter = 0;
		precisionDefinition = new PercentPrecisionQoCMetricDefinition();
		precisionFactory = PercentPrecisionFactory.instance();
	}

	public void addQoCMetaData(final ContextObservation<Double> contextObservation) {

		Double precisionValue;

		{
			// TODO: To answer to exercise 2, read the following code
			// Evaluation and creation of the precision indicator.
			precisionValue = precisionDefinition.computeQoCMetricValue("", "", contextObservation.date,
					contextObservation.value, null);
			// Insert the precision indicator into the QoC meta-data of the
			// context information.
			System.out.println("THE PRECISION VALUE IS: " + precisionValue);
			contextObservation.list_qoCIndicator.add(precisionFactory.newQoCIndicator(counter + "", precisionValue));
		}

		counter++;
	}
}
