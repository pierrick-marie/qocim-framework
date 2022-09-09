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

public class QoCEvaluator {

    private Integer counter;

    public QoCEvaluator() {
	counter = 0;
    }

    public void addQoCMetaData(final ContextObservation<?> contextObservation) {

	{
	    // TODO: to answer to the exercise 2, read the class
	    // collector.utils.QoCEvaluator of the maven project
	    // "qocim-lab-collector" and complete the following instructions.

	    // (1) Get the value of the precision with the class
	    // mucontext.datamodel.qocim.precisionindicator.PerthousandPrecisionFactory
	    // into the maven project "qocim-precisionindicator".

	    // (2) Create a precision QoC indicator with the class
	    // PerthousandPrecisionFactory and add it into the list of
	    // QoC meta-data of the contextObservation.

	    // (3) Get the value of the precision with the class
	    // mucontext.datamodel.qocim.freshnessindicator.FreshnessFactory
	    // in the maven module "qocim-freshnessindicator".

	    // (4) Create a precision QoC indicator with the class
	    // FreshnessFactory and add it into the list of QoC
	    // meta-data of the contextObservation.
	}

	counter++;
    }
}
