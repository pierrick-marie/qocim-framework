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
package qocim.datamodel.spatialresolutionindicator;

import qocim.datamodel.QoCMetricValue;
import qocim.datamodel.utils.ConstraintChecker;
import qocim.datamodel.utils.ConstraintCheckerException;

public class SpatialResolutionQoCMetricValue extends QoCMetricValue {

	// # # # # # CONSTRUCTORS # # # # #

	protected SpatialResolutionQoCMetricValue() {
		// - - - - - CORE OF THE METHOD - - - - -
		super();
	}

	// # # # # # PUBLIC METHODS # # # # #

	public void setValue(final Double _value) {
		// - - - - - CHECK THE VALUE OF THE ARGUMENTS - - - - -
		try {
			final String message = "The value of PercentPrecisionMetricValue is illegal";
			ConstraintChecker.doubleIsBetweenBounds(_value, SpatialResolutionQoCMetricDefinition.MIN_VALUE_DEFAULTVALUE,
					SpatialResolutionQoCMetricDefinition.MAX_VALUE_DEFAULTVALUE, message);
		} catch (final ConstraintCheckerException e) {
			return;
		}
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		super.value(_value);
	}
}
