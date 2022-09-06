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
package qocim.routingfilters.test;

import java.util.HashMap;
import java.util.Map;

import org.junit.Ignore;
import org.junit.Test;

import qocim.datamodel.QoCMetaData;
import qocim.datamodel.test.criterion.one.TestCriterionOneFactory;
import qocim.datamodel.test.criterion.two.TestCriterionTwoFactory;
import qocim.datamodel.test.criterion.zero.TestCriterionZeroFactory;
import qocim.routingfilters.EComparator;
import qocim.routingfilters.impl.JavaScriptBeginRoutingFilterGenerator;
import qocim.routingfilters.impl.JavaScriptEndRoutingFilterGenerator;
import qocim.routingfilters.impl.JavaScriptQoCIMRoutingFilterGenerator;
import qocim.routingfilters.impl.QoCIMRoutingFilter;

public class TestFilterToString {

	private QoCMetaData qoCMetaDataFreshnessIndicator;
	private QoCMetaData qoCMetaDataPrecisionIndicator;
	private QoCMetaData qoCMetaDataSpatialResolutionIndicator;
	private Map<QoCMetaData, EComparator> map_constraintValue;
	private JavaScriptBeginRoutingFilterGenerator beginFilterGenerator;
	private JavaScriptQoCIMRoutingFilterGenerator qoCFilterGenerator;
	private JavaScriptEndRoutingFilterGenerator endFilterGenerator;
	private QoCIMRoutingFilter routingFilter;

	private static final String DEFAULT_QOCMETRIC_ID = "0";
	private static final double FRESHNESS_BUS_QOCVALUE = 20;
	private static final double FRESHNESS_BUS_STOP_QOCVALUE = 40;
	private static final double PERCENT_PRECISION_QOCVALUE = 60;
	private static final double PERTHOUSAND_PRECISION_QOCVALUE = 750;
	private static final double SPATIALRESOLUTION_BUS_QOCVALUE = 30;
	private static final double SPATIALRESOLUTION_BUS_STOP_QOCVALUE = 1;

	@Ignore
	@Test
	public final void printAdvertisementFilterCollectorBus() {

		qoCMetaDataFreshnessIndicator = new QoCMetaData(
				TestCriterionZeroFactory.instance().newQoCIndicator(DEFAULT_QOCMETRIC_ID, FRESHNESS_BUS_QOCVALUE));
		qoCMetaDataPrecisionIndicator = new QoCMetaData(
				TestCriterionOneFactory.instance().newQoCIndicator(DEFAULT_QOCMETRIC_ID, PERCENT_PRECISION_QOCVALUE));
		qoCMetaDataSpatialResolutionIndicator = new QoCMetaData(TestCriterionTwoFactory.instance()
				.newQoCIndicator(DEFAULT_QOCMETRIC_ID, SPATIALRESOLUTION_BUS_QOCVALUE));
		qoCFilterGenerator = new JavaScriptQoCIMRoutingFilterGenerator();
		beginFilterGenerator = new JavaScriptBeginRoutingFilterGenerator();
		endFilterGenerator = new JavaScriptEndRoutingFilterGenerator();
		routingFilter = new QoCIMRoutingFilter();
		map_constraintValue = new HashMap<QoCMetaData, EComparator>();

		map_constraintValue.put(qoCMetaDataFreshnessIndicator, EComparator.LOWER_EQUALS);
		map_constraintValue.put(qoCMetaDataPrecisionIndicator, EComparator.UPPER_EQUALS);
		map_constraintValue.put(qoCMetaDataSpatialResolutionIndicator, EComparator.LOWER_EQUALS);
		qoCFilterGenerator.addQoCValueConstraints(map_constraintValue);
		routingFilter.addFilterGenerator(beginFilterGenerator);
		routingFilter.addFilterGenerator(qoCFilterGenerator);
		routingFilter.addFilterGenerator(endFilterGenerator);
		System.out.println("# Bus advertisement filer");
		System.out.println(routingFilter);
	}

	@Ignore
	@Test
	public final void printAdvertisementFilterCollectorBusStop() {

		qoCMetaDataFreshnessIndicator = new QoCMetaData(
				TestCriterionTwoFactory.instance().newQoCIndicator(DEFAULT_QOCMETRIC_ID, FRESHNESS_BUS_STOP_QOCVALUE));
		qoCMetaDataPrecisionIndicator = new QoCMetaData(TestCriterionZeroFactory.instance()
				.newQoCIndicator(DEFAULT_QOCMETRIC_ID, PERTHOUSAND_PRECISION_QOCVALUE));
		qoCMetaDataSpatialResolutionIndicator = new QoCMetaData(TestCriterionOneFactory.instance()
				.newQoCIndicator(DEFAULT_QOCMETRIC_ID, SPATIALRESOLUTION_BUS_STOP_QOCVALUE));
		qoCFilterGenerator = new JavaScriptQoCIMRoutingFilterGenerator();
		beginFilterGenerator = new JavaScriptBeginRoutingFilterGenerator();
		endFilterGenerator = new JavaScriptEndRoutingFilterGenerator();
		routingFilter = new QoCIMRoutingFilter();
		map_constraintValue = new HashMap<QoCMetaData, EComparator>();

		map_constraintValue.put(qoCMetaDataFreshnessIndicator, EComparator.LOWER_EQUALS);
		map_constraintValue.put(qoCMetaDataPrecisionIndicator, EComparator.UPPER_EQUALS);
		map_constraintValue.put(qoCMetaDataSpatialResolutionIndicator, EComparator.LOWER_EQUALS);
		qoCFilterGenerator.addQoCValueConstraints(map_constraintValue);
		routingFilter.addFilterGenerator(beginFilterGenerator);
		routingFilter.addFilterGenerator(qoCFilterGenerator);
		routingFilter.addFilterGenerator(endFilterGenerator);
		System.out.println("# Bus stop advertisement filer");
		System.out.println(routingFilter);
	}
}
