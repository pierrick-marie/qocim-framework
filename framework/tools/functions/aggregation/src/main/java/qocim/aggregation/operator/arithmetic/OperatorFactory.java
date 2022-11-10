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
package qocim.aggregation.operator.arithmetic;

import qocim.aggregation.IAgregationOperator;
import qocim.aggregation.operator.arithmetic.impl.GeometricMeanAggregator;
import qocim.aggregation.operator.arithmetic.impl.MaxSelectionAggregator;
import qocim.aggregation.operator.arithmetic.impl.MeanAggregator;
import qocim.aggregation.operator.arithmetic.impl.MinSelectionAggregator;
import qocim.aggregation.operator.utils.OperatorNotSupportedException;

/**
 *
 * AggregationOperatorFactory will be used to create the operator of
 * IAggregationOperator type and return it using a static function.
 *
 * @author Atif MANZOOR, Pierrick MARIE
 */
public class OperatorFactory {

    public static IAgregationOperator createOperator(final EOperator operator) throws OperatorNotSupportedException {

	IAgregationOperator aggregator = null;

	switch (operator) {
	case MEAN:
	    aggregator = new MeanAggregator();
	    break;
	case GEOMETRICMEAN:
	    aggregator = new GeometricMeanAggregator();
	    break;
	case MIN:
	    aggregator = new MinSelectionAggregator();
	    break;
	case MAX:
	    aggregator = new MaxSelectionAggregator();
	    break;
	default:
	    throw new OperatorNotSupportedException("Expected operator: " + aggregator.toString());
	}

	return aggregator;
    }
}