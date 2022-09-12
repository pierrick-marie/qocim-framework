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
package qocim.cdfm.operator.aggregation.impl;

import org.apache.commons.math3.stat.descriptive.rank.Min;
import qocim.cdfm.operator.aggregation.SelectionAggregationOperator;
import qocim.cdfm.operator.utils.EOperator;
import qocim.datamodel.information.QInformation;

import java.util.List;

/**
 * MinSelection is the operator used to select the minimal value of a numeric
 * information.
 *
 * @author Pierrick MARIE
 */
public class MinSelectionAggregator extends SelectionAggregationOperator {

	@Override
	public QInformation<?> aggregateInformation(List<QInformation<?>> informationList) {
		QInformation<?> minData = null;
		for (QInformation<?> information: informationList) {
			if (information.data() instanceof Number) {
				if ( (null == minData) || ( ((Number)information.data()).doubleValue() < ((Number)minData.data()).doubleValue() ) ) {
					minData = information;
				}
			}
		}
		return minData;
	}

    @Override
    public String name() {
		return EOperator.MIN.toString();
    }
}
