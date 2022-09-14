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
package qocim.datamodel;

/**
 *
 * Order represents the direction of the QoC criterion. It brings semantic
 * interpretation of the value of the QoC.
 *
 * <ul>
 * <li>LOWER means when the value of the attribute QoCMetricValue.value
 * increases, the QoC decreases.</li>
 * <li>UPPER means when the value of the attribute QoCMetricValue.value
 * increases, the QoC increases.</li>
 * <li>UNKNOWN is a default value when no direction is set</li>
 * </ul>
 *
 * @see qocim.datamodel.QoCMetricDefinition
 * @see qocim.datamodel.QoCMetricValue
 *
 * @author Pierrick MARIE
 */
public enum Order {
    LOWER("Lower"), UPPER("Upper"), UNKNOWN("Unknown");

    String order;

    Order(final String _order) {
        order = _order;
    }

    @Override
    public String toString() {
        return order;
    }
}
