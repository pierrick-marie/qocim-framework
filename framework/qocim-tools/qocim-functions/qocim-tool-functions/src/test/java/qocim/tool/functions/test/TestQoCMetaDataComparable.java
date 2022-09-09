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
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import qocim.datamodel.QoCMetaData;
import qocim.datamodel.test.criterion.one.TestCriterionOneFactory;
import qocim.datamodel.test.criterion.zero.TestCriterionZeroFactory;
import qocim.tool.functions.impl.QoCMetaDataComparable;

public class TestQoCMetaDataComparable {

	// # # # # # CONSTANTS # # # # #

	private static final String DEFAULT_QOCID = "1";
	private static final double DEFAULT_QOCVALUE = 42.0;

	// # # # # # PRIVATE VARIABLES # # # # #

	private static QoCMetaDataComparable qoCMetaDataComparable;

	private QoCMetaData qoCMetaData1;
	private QoCMetaData qoCMetaData2;
	private Boolean isQoCMetaDataComparable;

	// # # # # # PUBLIC METHODS # # # # #

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		qoCMetaDataComparable = new QoCMetaDataComparable();
	}

	@Before
	public void setUp() throws Exception {
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		/**
		 * Using test criterion zero
		 */
		qoCMetaData1 = new QoCMetaData(
				TestCriterionZeroFactory.getInstance().newQoCIndicator(DEFAULT_QOCID, DEFAULT_QOCVALUE));
		qoCMetaData2 = new QoCMetaData(
				TestCriterionZeroFactory.getInstance().newQoCIndicator(DEFAULT_QOCID, DEFAULT_QOCVALUE));
	}

	@After
	public final void execTest() {
		// - - - - - CORE OF THE METHOD - - - - -
		qoCMetaDataComparable.setUp(qoCMetaData1, qoCMetaData2);
		assertEquals(isQoCMetaDataComparable, qoCMetaDataComparable.exec());
	}

	@Test
	public final void compareComparableQoCMetaData() {
		// - - - - - CORE OF THE METHOD - - - - -
		isQoCMetaDataComparable = true;
	}

	@Test
	public final void compareDifferentQoCMetaData() {
		// - - - - - CORE OF THE METHOD - - - - -
		/**
		 * Using test criterion one
		 */
		qoCMetaData1 = new QoCMetaData(
				TestCriterionOneFactory.getInstance().newQoCIndicator(DEFAULT_QOCID, DEFAULT_QOCVALUE));
		isQoCMetaDataComparable = false;
	}

	@Test
	public final void compareSameQoCMetaData() {
		// - - - - - CORE OF THE METHOD - - - - -
		qoCMetaData1 = qoCMetaData2;
		isQoCMetaDataComparable = true;
	}
}
