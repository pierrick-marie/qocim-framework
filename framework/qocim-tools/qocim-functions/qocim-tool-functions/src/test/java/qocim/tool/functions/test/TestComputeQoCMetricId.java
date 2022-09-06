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
package qocim.tool.functions.test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import qocim.datamodel.QoCIMFacade;
import qocim.datamodel.QoCIndicator;
import qocim.datamodel.QoCMetaData;
import qocim.datamodel.test.criterion.one.TestCriterionOneFactory;
import qocim.datamodel.test.criterion.zero.TestCriterionZeroFactory;
import qocim.tool.functions.impl.ComputeQoCMetricId;

public class TestComputeQoCMetricId {

	// # # # # # CONSTANTS # # # # #

	private static final double DEFAULT_QOCVALUE = 0.0;
	private static final int NB_MAXTESTS = 4;

	// # # # # # PRIVATE VARIABLES # # # # #

	private static ComputeQoCMetricId computeQoCMetricId;
	private static List<QoCMetaData> list_metaData;
	private static QoCIndicator qoCIndicatorZero;
	private static QoCIndicator qoCIndicatorOne;
	private static Map<Integer, Integer> map_testedQoCMetricValueId;
	private static Integer counter_qocMetricValueId = 0;

	// # # # # # PUBLIC METHODS # # # # #

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		list_metaData = new ArrayList<QoCMetaData>();
		computeQoCMetricId = new ComputeQoCMetricId();
		map_testedQoCMetricValueId = new HashMap<Integer, Integer>();
		// - - - - - CORE OF THE METHOD - - - - -
		/**
		 * Using the test criterion zero
		 */
		qoCIndicatorZero = TestCriterionZeroFactory.getInstance().newQoCIndicator("" + counter_qocMetricValueId++,
				DEFAULT_QOCVALUE);
		qoCIndicatorOne = TestCriterionOneFactory.getInstance().newQoCIndicator("" + counter_qocMetricValueId++,
				DEFAULT_QOCVALUE);
	}

	@Before
	public void setUp() throws Exception {
		// - - - - - CORE OF THE METHOD - - - - -
		list_metaData.clear();
		map_testedQoCMetricValueId.clear();
	}

	@After
	public final void execTest() {
		// - - - - - CORE OF THE METHOD - - - - -
		for (final Map.Entry<Integer, Integer> entry_qoCMetricValue : map_testedQoCMetricValueId.entrySet()) {
			assertEquals(entry_qoCMetricValue.getValue(), entry_qoCMetricValue.getKey());
		}
	}

	@Test
	public final void computeDifferentListQoCCriterion() {
		// - - - - - CORE OF THE METHOD - - - - -
		for (int counter_nbCriterionZeroQoCMetaData = 0; counter_nbCriterionZeroQoCMetaData < NB_MAXTESTS; counter_nbCriterionZeroQoCMetaData++) {
			list_metaData.addAll(QoCIMFacade.createListQoCMetaData(qoCIndicatorZero));
		}
		for (int counter_nbCriterionZeroQoCMetaData = 0; counter_nbCriterionZeroQoCMetaData < NB_MAXTESTS; counter_nbCriterionZeroQoCMetaData++) {
			list_metaData.addAll(QoCIMFacade.createListQoCMetaData(qoCIndicatorOne));
		}
		computeQoCMetricId.setUp(list_metaData, qoCIndicatorZero.id());
		map_testedQoCMetricValueId.put(NB_MAXTESTS, (Integer) computeQoCMetricId.exec());
	}

	@Test
	public final void computeDifferentQoCCriterion() {
		// - - - - - CORE OF THE METHOD - - - - -
		for (int counter_nbQoCMetaData = 0; counter_nbQoCMetaData < NB_MAXTESTS; counter_nbQoCMetaData++) {
			list_metaData.addAll(QoCIMFacade.createListQoCMetaData(qoCIndicatorOne));
		}
		computeQoCMetricId.setUp(list_metaData, qoCIndicatorZero.id());
		map_testedQoCMetricValueId.put(0, (Integer) computeQoCMetricId.exec());
	}

	@Test
	public final void computeSameQoCCriterion() {
		// - - - - - CORE OF THE METHOD - - - - -
		for (int counter_nbQoCMetaData = 0; counter_nbQoCMetaData < NB_MAXTESTS; counter_nbQoCMetaData++) {
			list_metaData.clear();
			for (int counter_qoCMetaData = 0; counter_qoCMetaData < counter_nbQoCMetaData; counter_qoCMetaData++) {
				list_metaData.addAll(QoCIMFacade.createListQoCMetaData(qoCIndicatorZero));
			}
			computeQoCMetricId.setUp(list_metaData, qoCIndicatorZero.id());
			map_testedQoCMetricValueId.put(counter_nbQoCMetaData, (Integer) computeQoCMetricId.exec());
		}
	}
}
