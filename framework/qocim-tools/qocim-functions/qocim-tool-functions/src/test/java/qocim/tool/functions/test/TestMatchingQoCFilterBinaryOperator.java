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
import qocim.datamodel.test.criterion.zero.TestCriterionZeroFactory;
import qocim.tool.functions.impl.MatchingQoCFilter;
import qocim.tool.functions.utils.EBinaryComparator;
import qocim.tool.functions.utils.EBinaryOperator;
import qocim.tool.functions.utils.EQoCIdentificator;
import qocim.tool.functions.utils.QoCFilter;
import qocim.tool.functions.utils.QoCFilterExpression;

public class TestMatchingQoCFilterBinaryOperator {

	// # # # # # CONSTANTS # # # # #

	private static final String DEFAULT_QOCID = "42";
	private static final double DEFAULT_QOCVALUE = 69.0;

	// # # # # # PRIVATE VARIABLES # # # # #

	private static QoCMetaData qoCMetaData;
	private static MatchingQoCFilter matchingQoCFilter;

	private Boolean expectedAndOperatorResult;
	private Boolean expectedOrOperatorResult;
	private QoCFilterExpression qoCFilterExpressionOne;
	private QoCFilterExpression qoCFilterExpressionTwo;
	private QoCFilter qoCFilter;

	// # # # # # PUBLIC METHODS # # # # #

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		/**
		 * qoCMetaData is based on the "QoC criterion Zero"
		 */
		qoCMetaData = new QoCMetaData(
				TestCriterionZeroFactory.getInstance().newQoCIndicator(DEFAULT_QOCID, DEFAULT_QOCVALUE));
		matchingQoCFilter = new MatchingQoCFilter();
	}

	@After
	public final void execTest() {
		// - - - - - CORE OF THE METHOD - - - - -
		qoCFilter = new QoCFilter(EBinaryOperator.AND);
		qoCFilter.addQocFilterExpression(qoCFilterExpressionOne);
		qoCFilter.addQocFilterExpression(qoCFilterExpressionTwo);
		matchingQoCFilter.setUp(qoCMetaData, qoCFilter);
		assertEquals(expectedAndOperatorResult, matchingQoCFilter.exec());
		qoCFilter = new QoCFilter(EBinaryOperator.OR);
		qoCFilter.addQocFilterExpression(qoCFilterExpressionOne);
		qoCFilter.addQocFilterExpression(qoCFilterExpressionTwo);
		matchingQoCFilter.setUp(qoCMetaData, qoCFilter);
		assertEquals(expectedOrOperatorResult, matchingQoCFilter.exec());
	}

	@Test
	public final void testDifferentQoCValueAndDifferentQoCId() {
		// - - - - - CORE OF THE METHOD - - - - -
		qoCFilterExpressionOne = new QoCFilterExpression(EQoCIdentificator.QOC_METRICVALUE_VALUE,
				EBinaryComparator.EQUALS, String.valueOf(qoCMetaData.qoCMetricValueValue() + 1));
		qoCFilterExpressionTwo = new QoCFilterExpression(EQoCIdentificator.QOC_METRICVALUE_ID, EBinaryComparator.EQUALS,
				String.valueOf(qoCMetaData.qoCMetricValueId() + 1));
		expectedAndOperatorResult = false;
		expectedOrOperatorResult = false;
	}

	@Test
	public final void testDifferentQoCValueAndSameQoCId() {
		// - - - - - CORE OF THE METHOD - - - - -
		qoCFilterExpressionOne = new QoCFilterExpression(EQoCIdentificator.QOC_METRICVALUE_VALUE,
				EBinaryComparator.EQUALS, String.valueOf(qoCMetaData.qoCMetricValueValue() + 1));
		qoCFilterExpressionTwo = new QoCFilterExpression(EQoCIdentificator.QOC_METRICVALUE_ID, EBinaryComparator.EQUALS,
				String.valueOf(qoCMetaData.qoCMetricValueId()));
		expectedAndOperatorResult = false;
		expectedOrOperatorResult = true;
	}

	@Test
	public final void testSameQoCValueAndDifferentQoCId() {
		// - - - - - CORE OF THE METHOD - - - - -
		qoCFilterExpressionOne = new QoCFilterExpression(EQoCIdentificator.QOC_METRICVALUE_VALUE,
				EBinaryComparator.EQUALS, String.valueOf(qoCMetaData.qoCMetricValueValue()));
		qoCFilterExpressionTwo = new QoCFilterExpression(EQoCIdentificator.QOC_METRICVALUE_ID, EBinaryComparator.EQUALS,
				String.valueOf(qoCMetaData.qoCMetricValueId() + 1));
		expectedAndOperatorResult = false;
		expectedOrOperatorResult = true;
	}

	@Test
	public final void testSameQoCValueAndSameQoCId() {
		// - - - - - CORE OF THE METHOD - - - - -
		qoCFilterExpressionOne = new QoCFilterExpression(EQoCIdentificator.QOC_METRICVALUE_VALUE,
				EBinaryComparator.EQUALS, String.valueOf(qoCMetaData.qoCMetricValueValue()));
		qoCFilterExpressionTwo = new QoCFilterExpression(EQoCIdentificator.QOC_METRICVALUE_ID, EBinaryComparator.EQUALS,
				String.valueOf(qoCMetaData.qoCMetricValueId()));
		expectedAndOperatorResult = true;
		expectedOrOperatorResult = true;
	}
}
