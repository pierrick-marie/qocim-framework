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
package qocim.datamodel.freshnessindicator;

import java.util.Date;
import java.util.List;

import qocim.datamodel.Order;
import qocim.datamodel.QoCMetaData;
import qocim.datamodel.QoCMetricDefinition;

public class RandomFreshnessQoCMetricDefinition extends QoCMetricDefinition {

	// # # # # # CONSTANTS # # # # #

	public static final String ID_DEFAULTVALUE = "15.2";
	public static final boolean IS_INVARIANT_DEFAULTVALUE = false;
	public static final String UNIT_DEFAULTVALUE = "second";
	public static final String PROVIDER_URI_DEFAULTVALUE = "//sensor";
	public static final double MIN_VALUE_DEFAULTVALUE = 0;
	public static final double MAX_VALUE_DEFAULTVALUE = 60;
	public static final Order DIRECTION_DEFAULTVALUE = Order.LOWER;

	// # # # # # CONSTRUCTORS # # # # #

	public RandomFreshnessQoCMetricDefinition() {
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		super();
		id(ID_DEFAULTVALUE);
		isInvariant(IS_INVARIANT_DEFAULTVALUE);
		unit(UNIT_DEFAULTVALUE);
		minValue(MIN_VALUE_DEFAULTVALUE);
		maxValue(MAX_VALUE_DEFAULTVALUE);
		direction(DIRECTION_DEFAULTVALUE);
		providerUri(PROVIDER_URI_DEFAULTVALUE);
		description(new FreshnessDescription());
		description().container(this);
	}

	// # # # # # PUBLIC METHODS # # # # #

	@Override
	public Double computeQoCMetricValue(final String _contextEntityUri, final String _contextObservableUri,
			final Date _contextObservationDate, final Double _contextObservationValue,
			final List<QoCMetaData> _list_qoCMetaData) {
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		final Double coefficient = FreshnessQoCMetricDefinition.MAX_VALUE_DEFAULTVALUE
				- FreshnessQoCMetricDefinition.MIN_VALUE_DEFAULTVALUE;
		final Double ret_randomValue = Math.random() * coefficient
				+ FreshnessQoCMetricDefinition.MIN_VALUE_DEFAULTVALUE;
		// - - - - - RETURN STATEMENT - - - - -
		return ret_randomValue;
	}
}
