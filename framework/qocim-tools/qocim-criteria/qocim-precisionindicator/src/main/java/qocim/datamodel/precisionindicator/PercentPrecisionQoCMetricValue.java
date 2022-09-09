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
package qocim.datamodel.precisionindicator;

import qocim.datamodel.QoCMetricValue;
import qocim.datamodel.utils.ConstraintChecker;
import qocim.datamodel.utils.ConstraintCheckerException;

public class PercentPrecisionQoCMetricValue extends QoCMetricValue {

	// # # # # # CONSTRUCTORS # # # # #

	protected PercentPrecisionQoCMetricValue() {

		super();
	}

	// # # # # # PUBLIC METHODS # # # # #

	public void setValue(final Double _value) {
		// - - - - - CHECK THE VALUE OF THE ARGUMENTS - - - - -
		try {
			ConstraintChecker.doubleIsBetweenBounds(_value, PercentPrecisionQoCMetricDefinition.MIN_VALUE_DEFAULTVALUE,
					PercentPrecisionQoCMetricDefinition.MAX_VALUE_DEFAULTVALUE,
					"The value of FreshnessMetricValue is illegal");
		} catch (final ConstraintCheckerException e) {
			return;
		}
		// - - - - - CORE OF THE METHOD - - - - -
		super.value(_value);
	}
}
