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

import org.apache.commons.math3.stat.descriptive.moment.GeometricMean;
import org.apache.commons.math3.stat.descriptive.moment.Mean;
import qocim.cdfm.function.ICDFMOperator;
import qocim.cdfm.operator.aggregation.ArithmeticAggregationOperator;
import qocim.cdfm.operator.utils.EOperator;
import qocim.datamodel.information.InformationImpl;
import qocim.datamodel.information.QInformation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * MeanSelection is the operator used to compute the geometric mean of a list of
 * information.
 *
 * @author Pierrick MARIE
 */
public class GeometricMeanAggregator extends ArithmeticAggregationOperator {

    @Override
    public QInformation<?> aggregateInformation(List<QInformation<?>> informationList) {
        final GeometricMean geometricMean = new GeometricMean();
        final List<Double> values = new ArrayList<>();
        Integer index_arrayValue = 0;

        for (final QInformation<?> information : informationList) {
            if (information.data() instanceof Double) {
                values.add((Double) information.data());
            }
        }

        final double[] doubleArray = new double[values.size()];
        int indexArray = 0;
        for (double value : values) {
            doubleArray[indexArray] = value;
        }

        return new InformationImpl<Double>("Aggregated geometric mean information", new Double(geometricMean.evaluate(doubleArray)));
    }

    @Override
    public ICDFMOperator setParameters(Map<String, String> _map_parameter) {
        // Do nothing.
        return this;
    }

    @Override
    public String name() {
        return EOperator.GEOMETRICMEAN.toString();
    }
}
