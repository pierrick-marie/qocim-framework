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
package qocim.cdfm.operator.utils;

import qocim.cdfm.operator.aggregation.impl.GeometricMeanAggregator;
import qocim.cdfm.operator.aggregation.impl.MaxSelectionAggregator;
import qocim.cdfm.operator.aggregation.impl.MeanAggregator;
import qocim.cdfm.operator.aggregation.impl.MinSelectionAggregator;

/**
 *
 * AggregationOperatorFactory will be used to create the operator of
 * IAggregationOperator type and return it using a static function.
 *
 * @author Atif MANZOOR
 */
public class OperatorFactory {

    public static qocim.cdfm.function.IAgregationOperator createOperator(final EOperator _operator) throws OperatorNotSupportedException {

	qocim.cdfm.function.IAgregationOperator ret_aggregationOperator = null;

	switch (_operator) {
	case MEAN:
	    ret_aggregationOperator = new MeanAggregator();
	    break;
	case GEOMETRICMEAN:
	    ret_aggregationOperator = new GeometricMeanAggregator();
	    break;
	case MIN:
	    ret_aggregationOperator = new MinSelectionAggregator();
	    break;
	case MAX:
	    ret_aggregationOperator = new MaxSelectionAggregator();
	    break;
	default:
	    throw new OperatorNotSupportedException("Expected operator: " + _operator.toString());
	}

	return ret_aggregationOperator;
    }
}