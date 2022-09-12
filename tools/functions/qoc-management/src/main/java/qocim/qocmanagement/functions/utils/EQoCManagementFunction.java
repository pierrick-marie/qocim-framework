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
package qocim.qocmanagement.functions.utils;

import qocim.qocmanagement.functions.impl.AddQoCIndicator;
import qocim.qocmanagement.functions.impl.FilterQoCMetaData;
import qocim.qocmanagement.functions.impl.RemoveQoCIndicator;
import qocim.qocmanagement.functions.impl.RemoveQoCMetaData;
import qocim.qocmanagement.functions.impl.RemoveQoCMetricValue;
import qocim.qocmanagement.functions.impl.UpdateQoCIndicator;
import qocim.qocmanagement.functions.impl.UpdateQoCMetaData;
import qocim.qocmanagement.functions.impl.UpdateQoCMetricValue;

/**
 * EOperator class presents the enum presentation of all the available operators
 * for summary and aggregation functions. Defining an enumeration type for those
 * operators facilitate their usage with other classes.
 *
 * @author Atif MANZOOR
 */
public enum EQoCManagementFunction {

    NONE("None"), ADDQOCINDICATOR(AddQoCIndicator.class.getSimpleName()), REMOVEQOCINDICATOR(RemoveQoCIndicator.class.getSimpleName()), REMOVEQOCMETADATA(
	    RemoveQoCMetaData.class.getSimpleName()), REMOVEQOCMETRICVALUE(RemoveQoCMetricValue.class.getSimpleName()), UPDATEQOCINDICATOR(
	    UpdateQoCIndicator.class.getSimpleName()), UPDATEQOCMETADATA(UpdateQoCMetaData.class.getSimpleName()), UPDATEQOCMETRICVALUE(
	    UpdateQoCMetricValue.class.getSimpleName()), FILTERQOCMETADATA(FilterQoCMetaData.class.getSimpleName());

    private String function;

    private EQoCManagementFunction(final String operator) {
	function = operator;
    }

    @Override
    public String toString() {
	return function;
    }
}
