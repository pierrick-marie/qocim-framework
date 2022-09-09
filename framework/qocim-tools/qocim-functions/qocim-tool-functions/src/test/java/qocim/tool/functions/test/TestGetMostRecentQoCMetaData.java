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

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import qocim.datamodel.QoCMetaData;
import qocim.datamodel.test.criterion.one.TestCriterionOneFactory;
import qocim.datamodel.test.criterion.zero.TestCriterionZeroFactory;
import qocim.tool.functions.impl.GetMostRecentQoCMetaData;

public class TestGetMostRecentQoCMetaData {

	// # # # # # CONSTANTS # # # # #

	private static final int DEFAULT_NBQOCMETADATA = 5;
	private static final double DEFAULT_QOCVALUE = 1.0;

	// # # # # # PRIVATE VARIABLES # # # # #

	private static GetMostRecentQoCMetaData getMostRecentQoCMetaData;
	private static List<QoCMetaData> list_qoCMetaData;

	private QoCMetaData mostRecentQoCMetaData;
	private Integer counter_qocMetricValueId = 0;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		getMostRecentQoCMetaData = new GetMostRecentQoCMetaData();
		list_qoCMetaData = new ArrayList<QoCMetaData>();
	}

	@Before
	public void setUp() throws Exception {
		// - - - - - CORE OF THE METHOD - - - - -
		list_qoCMetaData.clear();
		for (int i = 0; i <= DEFAULT_NBQOCMETADATA; i++) {
			list_qoCMetaData.add(new QoCMetaData(TestCriterionZeroFactory.getInstance()
					.newQoCIndicator("" + counter_qocMetricValueId++, DEFAULT_QOCVALUE)));
		}
		try {
			// Wait few seconds to be sure it is the next QoCMetaData is the
			// most recent
			Thread.sleep(50);
		} catch (final InterruptedException e) {
			e.printStackTrace();
		}
	}

	@After
	public void execTest() {
		// - - - - - CORE OF THE METHOD - - - - -
		list_qoCMetaData.add(mostRecentQoCMetaData);
		getMostRecentQoCMetaData.setUp(mostRecentQoCMetaData.qoCMetricDefinitionId(), list_qoCMetaData);
		assertTrue(((QoCMetaData) getMostRecentQoCMetaData.exec()).equals(mostRecentQoCMetaData));
	}

	@Test
	public final void testDifferentQoCIndicatorList() {
		// - - - - - CORE OF THE METHOD - - - - -
		mostRecentQoCMetaData = new QoCMetaData(TestCriterionOneFactory.getInstance()
				.newQoCIndicator("" + counter_qocMetricValueId++, DEFAULT_QOCVALUE));
	}

	@Test
	public final void testSameQoCIndicatorList() {
		// - - - - - CORE OF THE METHOD - - - - -
		mostRecentQoCMetaData = new QoCMetaData(TestCriterionZeroFactory.getInstance()
				.newQoCIndicator("" + counter_qocMetricValueId++, DEFAULT_QOCVALUE));
	}
}
