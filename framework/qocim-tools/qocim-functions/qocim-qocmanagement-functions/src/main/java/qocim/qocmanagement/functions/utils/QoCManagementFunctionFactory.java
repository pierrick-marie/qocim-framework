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

import java.util.HashMap;
import java.util.Map;

import qocim.datamodel.QoCMetricDefinition;
import qocim.datamodel.utils.IQoCIMFactory;
import qocim.qocmanagement.functions.IQoCManagementFunction;
import qocim.qocmanagement.functions.impl.AddQoCIndicator;
import qocim.qocmanagement.functions.impl.RemoveQoCIndicator;
import qocim.qocmanagement.functions.impl.RemoveQoCMetaData;
import qocim.qocmanagement.functions.impl.RemoveQoCMetricValue;
import qocim.qocmanagement.functions.impl.UpdateQoCIndicator;
import qocim.qocmanagement.functions.impl.UpdateQoCMetaData;
import qocim.qocmanagement.functions.impl.UpdateQoCMetricValue;

/**
 * AggregationOperatorFactory will be used to create the operator of
 * IAggregationOperator type and return it using a static function.
 *
 * @author Atif MANZOOR
 */
public class QoCManagementFunctionFactory {

	/**
	 * This map is used to initialize the QoC management functions with all
	 * available QoCIM factory. Its key is the <i>id</i> of the
	 * <b>QoCMetricDefinition</b> and its value is the corresponding
	 * <b>QoCIMFactory</b> used to produce the QoC meta-data.
	 */
	public static final Map<QoCMetricDefinition, IQoCIMFactory> map_availableQoCIMFacade = new HashMap<QoCMetricDefinition, IQoCIMFactory>();

	public static IQoCManagementFunction createQoCManagementFunction(final EQoCManagementFunction _function)
			throws QoCManagementFunctionNotSupportedException {

		IQoCManagementFunction ret_function = null;

		switch (_function) {
		case ADDQOCINDICATOR:
			ret_function = new AddQoCIndicator(map_availableQoCIMFacade);
			break;
		case REMOVEQOCINDICATOR:
			ret_function = new RemoveQoCIndicator();
			break;
		case REMOVEQOCMETADATA:
			ret_function = new RemoveQoCMetaData();
			break;
		case REMOVEQOCMETRICVALUE:
			ret_function = new RemoveQoCMetricValue();
			break;
		case UPDATEQOCINDICATOR:
			ret_function = new UpdateQoCIndicator(map_availableQoCIMFacade);
			break;
		case UPDATEQOCMETADATA:
			ret_function = new UpdateQoCMetaData(map_availableQoCIMFacade);
			break;
		case UPDATEQOCMETRICVALUE:
			ret_function = new UpdateQoCMetricValue(map_availableQoCIMFacade);
			break;
		default:
			throw new QoCManagementFunctionNotSupportedException("Expected operator: " + _function.toString());
		}

		return ret_function;
	}
}