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
package qocim.datamodel.utils;

import qocim.datamodel.QoCIndicator;
import qocim.datamodel.QoCMetricValue;

public interface IQoCIMFactory {

    // # # # # # PUBLIC METHODS # # # # #

    /**
     * This method creates a QoC indicator and a QoC criterion.
     *
     * @return A new QoC indicator associated to a QoC criterion.
     */
    QoCIndicator newQoCIndicator();

    /**
     * This method create a QoC indicator associated to a QoC criterion, a QoC
     * metric definition and a QoC metric definition.
     *
     * @param _qoCMetricValueId
     *            The id of the QoC metric Value.
     * @param _qoCMetricValueValue
     *            The value of the of the QoC metric.
     * @return A new QoC indicator
     */
    QoCIndicator newQoCIndicator(final String _qoCMetricValueId, final Double _qoCMetricValueValue);

    /**
     * This method creates a QoC metric Value associated to a QoC metric
     * Definition.
     *
     * @param _qoCMetricValueId
     *            The id of the QoC metric value.
     * @param _qoCMetricValueValue
     *            The value of of the QoC metric.
     * @return A new QoC metric value.
     */
    QoCMetricValue newQoCMetricValue(final String _qoCMetricValueId, final Double _qoCMetricValueValue);
}
