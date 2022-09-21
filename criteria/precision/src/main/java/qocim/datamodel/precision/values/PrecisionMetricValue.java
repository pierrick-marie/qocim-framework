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
package qocim.datamodel.precision.values;

import java.util.Date;

import qocim.model.QoCMetricValue;
import qocim.utils.logs.QoCIMLogger;

public class PrecisionMetricValue extends QoCMetricValue {

	public static final Integer ID = 0;
	public static final Date CREATION_DATE = new Date();
	public static final Date MODIFICATION_DATE = new Date();

	@Override
	public int compareTo(final QoCMetricValue comparable) {

		int retValue = 0;

		final Double value = parseValue(value());
		final Double comparableValue = parseValue(comparable.value());

		retValue = value.compareTo(comparableValue);

		return retValue;
	}

	private Double parseValue(Object value) {

		if (value instanceof Number) {
			return new Double((double) value);
		}

		try {
			if (value instanceof String) {
				return Double.parseDouble((String) value);
			}
		} catch (final Exception exception) {
			QoCIMLogger.error("PrecisionMetricValue.parseValue: " + value.getClass().getName()
					+ " cannot be cast to java.lang.Double");
		}
		return 0.0;
	}
}
