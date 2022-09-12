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

import java.util.Arrays;

import qocim.datamodel.precisionindicator.utils.FifoQueue;
import qocim.datamodel.utils.ConstraintChecker;
import qocim.datamodel.utils.ConstraintCheckerException;

/**
 * This class computes the precision of a next value from a list of existing
 * values. The min value of the precision is 1 and max value is 1000. The
 * precision increases when the next value is close to the mean of the existing
 * values. The list of values is sorted form the oldest values to the newest. To
 * compute the mean, the list is divided in three parts and a coefficient is
 * used. The coefficient for the oldest values is one, the coefficient of the
 * most recent values is 4 and the coefficient for the other values is 2.
 *
 * @author Pierrick MARIE
 */
public class PerthousandPrecisionQoCMetricValueGenerator {

	// # # # # # CONSTANTS # # # # #
	/**
	 * The size of the list of values.
	 */
	private static final int MAX_NUMBER_OF_OBSERVATIONS = 50;
	/**
	 * The delimiter to slit the the list of values in three parts.
	 */
	private static final double NUMBER_OF_LIMIT_VALUES = 20.0 / 100.0;
	/**
	 * The coefficient for the old values.
	 */
	private static final double COEFFICIENT_OLD_VALUES = 2.0;
	/**
	 * The coefficient for the "current" values.
	 */
	private static final double COEFFICIENT_CURRENT_VALUES = 4.0;
	/**
	 * The coefficient for the most recent values.
	 */
	private static final double COEFFICIENT_LAST_VALUES = 3.0;

	// # # # # # PRIVATE VARIABLES # # # # #
	/**
	 * The list values.
	 */
	private final FifoQueue<Double> observationValueList;

	// # # # # # CONSTRUCTORS # # # # #

	protected PerthousandPrecisionQoCMetricValueGenerator() {
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		observationValueList = new FifoQueue<Double>(MAX_NUMBER_OF_OBSERVATIONS);
	}

	// # # # # # PUBLIC METHODS # # # # #

	/**
	 * The method evaluates the precision of a next value from a list of
	 * previous value. Once the precision is estimated, the next value is
	 * inserted into the list of values.
	 */
	public synchronized Double generateMetricValue(final Double _contextObservationValue) {
		// - - - - - CHECK THE VALUE OF THE ARGUMENTS - - - - -
		try {
			final String message = "PerthousandPrecisionQoCMetricValueGenerator.generateMetricValue(Integer): the argument _contextObservationValue is null";
			ConstraintChecker.notNull(_contextObservationValue, message);
		} catch (final ConstraintCheckerException e) {
			return 0.0;
		}
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		/*
		 * The estimated value of the precision.
		 */
		Double ret_precisionValue = Math.abs(getCoefficientedMean()) * getCoefficient();

		// - - - - - CORE OF THE METHOD - - - - -
		if (ret_precisionValue > PerthousandPrecisionQoCMetricDefinition.MAX_VALUE_DEFAULTVALUE) {
			ret_precisionValue = PerthousandPrecisionQoCMetricDefinition.MAX_VALUE_DEFAULTVALUE;
		}
		if (ret_precisionValue < PerthousandPrecisionQoCMetricDefinition.MIN_VALUE_DEFAULTVALUE
				|| ret_precisionValue.isNaN()) {
			ret_precisionValue = PerthousandPrecisionQoCMetricDefinition.MIN_VALUE_DEFAULTVALUE;
		}
		observationValueList.add(_contextObservationValue);
		// - - - - - RETURN STATEMENT - - - - -
		return ret_precisionValue;
	}

	// # # # # # PRIVATE METHODS # # # # #

	/**
	 * The method computes the coefficient used to evaluate the precision.
	 *
	 * @return The coefficient.
	 */
	private Double getCoefficient() {
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		/*
		 * The difference between the min value of the precision and the max
		 * value.
		 */
		final Double diffDefaultValues = PerthousandPrecisionQoCMetricDefinition.MAX_VALUE_DEFAULTVALUE
				- PerthousandPrecisionQoCMetricDefinition.MIN_VALUE_DEFAULTVALUE;

		/*
		 * The difference between the mean of the most minimal values and the
		 * most maximal values.
		 */
		final Double diffMeanValues = getMeanMaxValues() - getMeanMinValues();

		// - - - - - RETURN STATEMENT - - - - -
		return diffDefaultValues / diffMeanValues;
	}

	/**
	 * The method computes the coefficiented mean of the list of values.
	 *
	 * @return The mean of the values.
	 */
	private Double getCoefficientedMean() {
		// - - - - - RETURN STATEMENT - - - - -
		return (getMeanOldValues() + getMeanCurrentValues() + getMeanLastValues()) / 3;
	}

	/**
	 * The method compute the mean of the "current" values of the list. The
	 * method uses the coefficient <i>COEFFICIENT_CURRENT_VALUES</i> to the
	 * values of the middle of the list.
	 *
	 * @return The mean of the middle of the value of the list.
	 */
	private Double getMeanCurrentValues() {
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		/*
		 * The size of the list of values.
		 */
		final Integer observationsSize = observationValueList.size();
		/*
		 * The number of values evaluated by the method.
		 */
		final Integer nbLimitValues = (int) (NUMBER_OF_LIMIT_VALUES * observationValueList.size());
		/*
		 * An array to export the list of values.
		 */
		final Object[] observationValueArray = observationValueList.toArray();
		/*
		 * The sum of the evaluated values.
		 */
		Double sum = 0.0;
		// - - - - - CORE OF THE METHOD - - - - -
		for (Integer observationIndex = nbLimitValues; observationIndex < observationsSize
				- (1 + nbLimitValues); observationIndex++) {
			sum += Double.valueOf(observationValueArray[observationIndex] + "") * COEFFICIENT_CURRENT_VALUES;
		}
		// - - - - - RETURN STATEMENT - - - - -
		return sum / ((observationsSize - 1.0 - nbLimitValues * 2.0) * COEFFICIENT_CURRENT_VALUES);
	}

	/**
	 * The method compute the mean of the last values of the list. The method
	 * uses the coefficient <i>COEFFICIENT_LAST_VALUES</i> to the values of the
	 * middle of the list.
	 *
	 * @return The mean of the last values of the list.
	 */
	private Double getMeanLastValues() {
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		/*
		 * The number of values evaluated by the method.
		 */
		final Integer nbLimitValues = (int) (NUMBER_OF_LIMIT_VALUES * observationValueList.size());
		/*
		 * An array to export the list of values.
		 */
		final Object[] observationValueArray = observationValueList.toArray();
		/*
		 * The sum of the evaluated values.
		 */
		Double sum = 0.0;
		// - - - - - CORE OF THE METHOD - - - - -
		for (int observationIndex = observationValueList.size() - nbLimitValues; observationIndex < observationValueList
				.size(); observationIndex++) {
			sum += Double.valueOf(observationValueArray[observationIndex] + "") * COEFFICIENT_LAST_VALUES;
		}
		// - - - - - RETURN STATEMENT - - - - -
		return sum / (COEFFICIENT_LAST_VALUES * nbLimitValues);
	}

	/**
	 * The method computes the mean of the greatest values of the list. The
	 * method does not apply any coefficient.
	 *
	 * @return The mean of the greatest values.
	 */
	private Double getMeanMaxValues() {
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		/*
		 * The number of values evaluated by the method.
		 */
		final Integer nbMaxValues = (int) (NUMBER_OF_LIMIT_VALUES * observationValueList.size());
		/*
		 * An array to export the list of values.
		 */
		final Object[] observationsArray = observationValueList.toArray();
		/*
		 * The sum of the evaluated values.
		 */
		Double sum = 0.0;
		// - - - - - CORE OF THE METHOD - - - - -
		Arrays.sort(observationsArray);
		for (int maxValuesIndex = observationValueList.size() - 1; maxValuesIndex > observationValueList.size()
				- (1 + nbMaxValues); maxValuesIndex--) {
			sum += Double.valueOf(observationsArray[maxValuesIndex] + "");
		}
		// - - - - - RETURN STATEMENT - - - - -
		return sum / nbMaxValues;
	}

	/**
	 * The method computes the mean of the lowest values of the list. The method
	 * does not apply any coefficient.
	 *
	 * @return The mean of the lowest values.
	 */
	private Double getMeanMinValues() {
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		/*
		 * The number of values evaluated by the method.
		 */
		final Integer nbMinValues = (int) (NUMBER_OF_LIMIT_VALUES * observationValueList.size());
		/*
		 * An array to export the list of values.
		 */
		final Object[] observationsArray = observationValueList.toArray();
		/*
		 * The sum of the evaluated values.
		 */
		Double sum = 0.0;
		// - - - - - CORE OF THE METHOD - - - - -
		Arrays.sort(observationsArray);
		for (int minValuesIndex = 0; minValuesIndex < nbMinValues; minValuesIndex++) {
			sum += Double.valueOf(observationsArray[minValuesIndex] + "");
		}
		// - - - - - RETURN STATEMENT - - - - -
		return sum / nbMinValues;
	}

	/**
	 * The method compute the mean of the old values of the list. The method
	 * uses the coefficient <i>COEFFICIENT_OLD_VALUES</i> to the values of the
	 * middle of the list.
	 *
	 * @return The mean of the oldest values of the list.
	 */
	private Double getMeanOldValues() {
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		/*
		 * The number of values evaluated by the method.
		 */
		final Integer nbLimitValues = (int) (NUMBER_OF_LIMIT_VALUES * observationValueList.size());
		/*
		 * An array to export the list of values.
		 */
		final Object[] observationsArray = observationValueList.toArray();
		/*
		 * The sum of the evaluated values.
		 */
		Double sum = 0.0;
		// - - - - - CORE OF THE METHOD - - - - -
		for (int observationIndex = 0; observationIndex < nbLimitValues; observationIndex++) {
			sum += Double.valueOf(observationsArray[observationIndex] + "") * COEFFICIENT_LAST_VALUES;
		}
		// - - - - - RETURN STATEMENT - - - - -
		return sum / (COEFFICIENT_OLD_VALUES * nbLimitValues);
	}
}
