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
package qocim.datamodel.test;

import java.util.List;

import qocim.datamodel.Order;
import qocim.datamodel.QoCMetaData;
import qocim.datamodel.QoCMetricDefinition;
import qocim.datamodel.information.QInformation;

public class TestQoCMetricDefinition extends QoCMetricDefinition {

    protected static final String ID_DEFAULTVALUE = "0.1";
    protected static final Boolean IS_INVARIANT_DEFAULTVALUE = false;
    protected static final String UNIT_DEFAULTVALUE = "unit";
    protected static final String PROVIDER_URI_DEFAULTVALUE = "//test";
    protected static final Double MIN_VALUE_DEFAULTVALUE = Double.MIN_VALUE;
    protected static final Double MAX_VALUE_DEFAULTVALUE = Double.MAX_VALUE;
    protected static final Order DIRECTION_DEFAULTVALUE = Order.UNKNOWN;

    protected TestQoCMetricDefinition() {
	super();
	id(ID_DEFAULTVALUE);
	isInvariant(IS_INVARIANT_DEFAULTVALUE);
	unit(UNIT_DEFAULTVALUE);
	minValue(MIN_VALUE_DEFAULTVALUE);
	maxValue(MAX_VALUE_DEFAULTVALUE);
	direction(DIRECTION_DEFAULTVALUE);
	providerUri(PROVIDER_URI_DEFAULTVALUE);
	description().container(this);
    }

    @Override
    public Double computeQoCMetricValue(final QInformation<?> information, final List<QoCMetaData> _list_qoCMetaData) {

	return MIN_VALUE_DEFAULTVALUE;
    }
}
