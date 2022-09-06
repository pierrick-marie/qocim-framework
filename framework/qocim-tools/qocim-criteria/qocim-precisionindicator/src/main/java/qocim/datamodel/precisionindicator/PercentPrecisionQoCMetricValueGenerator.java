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

import qocim.datamodel.precisionindicator.utils.SortedBlockingList;
import qocim.datamodel.utils.ConstraintChecker;
import qocim.datamodel.utils.ConstraintCheckerException;

public class PercentPrecisionQoCMetricValueGenerator {

	// # # # # # CONSTANTS # # # # #

	private static final int MAX_NUMBER_OF_OBSERVATIONS = 200;
	private static final double LOW_PRECISION = 10;
	private static final double LOW_REPEATABLE_VALUES = 5.0 / 100;
	private static final double MEDIUM_PRECISION = 25;
	private static final double MEDIUM_REPEATABLE_VALUES = 35.0 / 100.0;
	private static final double HIGH_PRECISION = 55;
	private static final double HIGH_REPEATABLE_VALUES = 75.0 / 100.0;
	private static final double VERY_HIGH_PRECISION = 80;
	private static final double VERY_HIGH_REPEATABLE_VALUES = 100.0 / 100.0;

	// # # # # # PRIVATE VARIABLES # # # # #

	private final SortedBlockingList<Double> observationValueSortedList;

	// # # # # # CONSTRUCTORS # # # # #

	protected PercentPrecisionQoCMetricValueGenerator() {
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		observationValueSortedList = new SortedBlockingList<Double>(MAX_NUMBER_OF_OBSERVATIONS);
	}

	// # # # # # PUBLIC METHODS # # # # #

	public synchronized Double generateMetricValue(final Double _contextObservationValue) {
		// - - - - - CHECK THE VALUE OF THE ARGUMENTS - - - - -
		try {
			final String message = "PercentPrecisionQoCMetricValueGenerator.generateMetricValue(Integer): the argument _contextObservationValue is null";
			ConstraintChecker.notNull(_contextObservationValue, message);
		} catch (final ConstraintCheckerException e) {
			return 0.0;
		}
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		Double ret_precisionValue = PercentPrecisionQoCMetricDefinition.MIN_VALUE_DEFAULTVALUE;
		Double relativeInsertionPosition = 0.0;
		Integer insertionPositionContextObservationValue = observationValueSortedList.put(_contextObservationValue);
		// - - - - - CORE OF THE METHOD - - - - -
		if (insertionPositionContextObservationValue > observationValueSortedList.size() / 2.0) {
			insertionPositionContextObservationValue = observationValueSortedList.size()
					- insertionPositionContextObservationValue;
		}
		relativeInsertionPosition = insertionPositionContextObservationValue
				/ ((observationValueSortedList.size() - 1) / 2.0);
		if (relativeInsertionPosition <= LOW_REPEATABLE_VALUES) {
			ret_precisionValue = LOW_PRECISION;
		} else {
			if (relativeInsertionPosition > LOW_REPEATABLE_VALUES
					&& relativeInsertionPosition <= MEDIUM_REPEATABLE_VALUES) {
				ret_precisionValue = MEDIUM_PRECISION;
			} else {
				if (relativeInsertionPosition > MEDIUM_REPEATABLE_VALUES
						&& relativeInsertionPosition <= HIGH_REPEATABLE_VALUES) {
					ret_precisionValue = HIGH_PRECISION;
				} else {
					if (relativeInsertionPosition > HIGH_REPEATABLE_VALUES
							&& relativeInsertionPosition <= VERY_HIGH_REPEATABLE_VALUES) {
						ret_precisionValue = VERY_HIGH_PRECISION;
					}
				}
			}
		}
		// - - - - - RETURN STATEMENT - - - - -
		return ret_precisionValue;
	}
}
