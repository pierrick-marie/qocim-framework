/**
 * This file is part of the QoCIM middleware.
 * <p>
 * Copyright (C) 2014 IRIT, Télécom SudParis
 * <p>
 * The QoCIM software is free software: you can redistribute it and/or modify
 * It under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * The QoCIM software platform is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * <p>
 * See the GNU Lesser General Public License
 * for more details: http://www.gnu.org/licenses
 * <p>
 * Initial developer(s): Pierrick MARIE
 * Contributor(s):
 */
package qocim.aggregation.operator.impl;

import qocim.aggregation.IAgregationOperator;
import qocim.cdfm.operator.utils.EOperator;
import qocim.cdfm.operator.utils.NotValidInformationException;
import qocim.information.QInformation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * MinSelection is the operator used to select the minimal value of a numeric
 * information.
 *
 * @author Pierrick MARIE
 */
public class MinSelectionAggregator implements IAgregationOperator {

//    @Override
//    public Double aggregateListValue(final List<Double> listValue) {
//	final Min min = new Min();
//	final double[] arrayValue = new double[listValue.size()];
//	Integer index_arrayValue = 0;
//	for (final Double loop_value : listValue) {
//	    arrayValue[index_arrayValue++] = loop_value;
//	}
//	return min.evaluate(arrayValue);
//    }

	@Override
	public QInformation applyOperator(List<QInformation> input) throws NotValidInformationException {
		return null;
	}

	@Override
	public IAgregationOperator setParameters(Map<String, String> parameters) {
		return this;
	}

	@Override
	public Map<String, String> parameters() {
		return new HashMap<>();
	}

	@Override
	public String getName() {
		return EOperator.MIN.toString();
	}
}
