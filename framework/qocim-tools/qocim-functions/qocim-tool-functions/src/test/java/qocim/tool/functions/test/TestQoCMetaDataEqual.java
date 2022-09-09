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

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import qocim.datamodel.QoCMetaData;
import qocim.datamodel.test.criterion.three.TestCriterionThreeFactory;
import qocim.datamodel.test.criterion.zero.TestCriterionZeroFactory;
import qocim.tool.functions.impl.QoCMetaDataEqual;

public class TestQoCMetaDataEqual {

	// # # # # # CONSTANTS # # # # #

	private static final double DEFAULT_QOCVALUE = 42.0;
	private static final int QOC1_EQUALS_QOC2 = 0;
	private static final int QOC1_UPPER_QOC2 = 1;
	private static final int QOC1_LOWER_QOC2 = -1;

	// # # # # # PRIVATE VARIABLES # # # # #

	private static QoCMetaDataEqual qoCMetaDataEqual;

	private QoCMetaData qoCMetaData1;
	private QoCMetaData qoCMetaData2;
	private Boolean qoCMetaDataEquals;
	private Integer counter_qocMetricValueId = 0;

	// # # # # # PUBLIC METHODS # # # # #

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		qoCMetaDataEqual = new QoCMetaDataEqual();
	}

	@After
	public final void execTest() {
		// - - - - - CORE OF THE METHOD - - - - -
		if (qoCMetaDataEquals) {
			testQoCMetaDataEqual(qoCMetaData1, qoCMetaData2, QOC1_EQUALS_QOC2);
			testQoCMetaDataEqual(qoCMetaData2, qoCMetaData1, QOC1_EQUALS_QOC2);
		} else {
			testQoCMetaDataEqual(qoCMetaData1, qoCMetaData2, QOC1_LOWER_QOC2);
			testQoCMetaDataEqual(qoCMetaData2, qoCMetaData1, QOC1_UPPER_QOC2);
		}
	}

	@Test
	public final void compareEquivalentQoCMetaDataWithDifferentCreationDate() {
		// - - - - - CORE OF THE METHOD - - - - -
		qoCMetaData1 = new QoCMetaData(TestCriterionZeroFactory.getInstance()
				.newQoCIndicator("" + counter_qocMetricValueId++, DEFAULT_QOCVALUE));
		try {
			Thread.sleep(100);
		} catch (final InterruptedException e) {
			e.printStackTrace();
		}
		qoCMetaData2 = new QoCMetaData(TestCriterionZeroFactory.getInstance()
				.newQoCIndicator("" + counter_qocMetricValueId++, DEFAULT_QOCVALUE));
		qoCMetaDataEquals = false;
	}

	@Test
	public final void compareEquivalentQoCMetaDataWithDifferentQoC() {
		// - - - - - CORE OF THE METHOD - - - - -
		qoCMetaData1 = new QoCMetaData(TestCriterionZeroFactory.getInstance()
				.newQoCIndicator("" + counter_qocMetricValueId++, DEFAULT_QOCVALUE));
		qoCMetaData2 = new QoCMetaData(TestCriterionZeroFactory.getInstance()
				.newQoCIndicator("" + counter_qocMetricValueId++, DEFAULT_QOCVALUE + 1));
		qoCMetaDataEquals = false;
	}

	@Test
	public final void compareSameQoCMetaData() {
		// - - - - - CORE OF THE METHOD - - - - -
		qoCMetaData1 = new QoCMetaData(TestCriterionThreeFactory.getInstance()
				.newQoCIndicator("" + counter_qocMetricValueId++, DEFAULT_QOCVALUE));
		qoCMetaData2 = qoCMetaData1;
		qoCMetaDataEquals = true;
	}

	public final void testQoCMetaDataEqual(final QoCMetaData _qoCMetaData1, final QoCMetaData _qoCMetaData2,
			final Integer _expectedValue) {
		// - - - - - CORE OF THE METHOD - - - - -
		try {
			qoCMetaDataEqual.setUp(_qoCMetaData1, _qoCMetaData2);
			assertEquals(_expectedValue, qoCMetaDataEqual.exec());
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}
}
