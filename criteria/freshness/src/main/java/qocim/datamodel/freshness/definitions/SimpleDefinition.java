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
package qocim.datamodel.freshness.definitions;

import java.net.URI;
import java.net.URISyntaxException;

import javax.measure.quantity.Duration;
import javax.measure.unit.BaseUnit;
import javax.measure.unit.SI;

import qocim.datamodel.freshness.FreshnessDescription;
import qocim.datamodel.freshness.values.FreshnessMetricValue;
import qocim.datamodel.freshness.values.MaxFreshnessMetricValue;
import qocim.datamodel.freshness.values.MinFreshnessMetricValue;
import qocim.model.Order;
import qocim.model.QoCDescription;
import qocim.model.QoCMetricDefinition;

public class SimpleDefinition extends QoCMetricDefinition {

	public static final String ID = "15.1";
	public static final String NAME = "Simple Freshness Definition";
	public static final Boolean IS_INVARIANT = false;
	public static final BaseUnit<Duration> UNIT = SI.SECOND;
	public static final String PROVIDER_URI = "//sensor";
	public static final FreshnessMetricValue MIN_VALUE = new MinFreshnessMetricValue();
	public static final FreshnessMetricValue MAX_VALUE = new MaxFreshnessMetricValue();
	public static final Order DIRECTION = Order.LOWER;
	public static final Boolean DEFAULT_DEFINITION = true;
	public static final QoCDescription DESCRIPTION = new FreshnessDescription();
}
