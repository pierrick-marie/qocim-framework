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
package qocim.cdfm.operator.aggregation.impl;

import java.util.List;
import java.util.Map;

import qocim.cdfm.operator.aggregation.ArithmeticAggregationOperator;
import qocim.cdfm.operator.utils.EOperator;

import org.apache.commons.math3.stat.descriptive.moment.GeometricMean;

/**
 * MeanSelection is the operator used to compute the geometric mean of a list of
 * information.
 *
 * @author Pierrick MARIE
 */
public class GeometricMeanAggregator extends ArithmeticAggregationOperator {

    @Override
    public Double aggregateListValue(final List<Double> listValue) {
	final GeometricMean geometricMean = new GeometricMean();
	final double[] arrayValue = new double[listValue.size()];
	Integer index_arrayValue = 0;
	for (final Double loop_value : listValue) {
	    arrayValue[index_arrayValue++] = loop_value;
	}
	return geometricMean.evaluate(arrayValue);
    }

    @Override
    public qocim.cdfm.function.IAgregationOperator setParameters(Map<String, String> _map_parameter) {
	// Do nothing.
	return this;
    }

    @Override
    public String getName() {
	return EOperator.GEOMETRICMEAN.toString();
    }
}
