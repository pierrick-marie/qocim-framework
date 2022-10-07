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

/**
 * EOperator class presents the enum presentation of all the available operators
 * for summary and aggregation functions. Defining an enumeration type for those
 * operators facilitate their usage with other classes.
 *
 * @author Atif MANZOOR
 */
public enum EOperator {

    MEAN("Mean"), GEOMETRICMEAN("GeometricMean"), MIN("Min"), MAX("Max");
    private String operator;

    private EOperator(final String operator) {
	this.operator = operator;
    }

    @Override
    public String toString() {
	return operator;
    }
}
