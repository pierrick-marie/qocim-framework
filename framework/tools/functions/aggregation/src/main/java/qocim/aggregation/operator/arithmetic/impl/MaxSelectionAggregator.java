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
package qocim.aggregation.operator.arithmetic.impl;

import qocim.aggregation.IAgregationOperator;
import qocim.aggregation.operator.arithmetic.EOperator;
import qocim.aggregation.operator.utils.NotValidInformationException;
import qocim.information.QInformation;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * MaxSelection is the operator used to select the maximal value of a numeric
 * information.
 *
 * @author Pierrick MARIE
 */
public class MaxSelectionAggregator implements IAgregationOperator {

	@Override
	public QInformation<?> applyOperator(List<QInformation<?>> input) throws NotValidInformationException {
		Number comparedValue = null;
		QInformation<?> selectedInformation = null;

		for( QInformation<?> information: input) {
			if( information.data() instanceof Number) {
				Number value = (Number) information.data();
				if( null == comparedValue ) {
					comparedValue = value;
				} else {
					BigDecimal nb1 = BigDecimal.valueOf(comparedValue.doubleValue());
					BigDecimal nb2 = BigDecimal.valueOf(value.doubleValue());
					if( nb1.compareTo(nb2) <= 1 ) {
						selectedInformation = information;
						comparedValue = value;
					}
				}
			} else {
				throw new NotValidInformationException("information data not comparable");
			}
		}

		return selectedInformation;
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
		return EOperator.MAX.toString();
	}
}
