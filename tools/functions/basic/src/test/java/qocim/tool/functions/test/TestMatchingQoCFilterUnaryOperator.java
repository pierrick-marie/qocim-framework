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

public class TestMatchingQoCFilterUnaryOperator {

	// # # # # # CONSTANTS # # # # #

	private static final String DEFAULT_QOCID = "42";
	private static final double DEFAULT_QOCVALUE = 69.0;

	// # # # # # PRIVATE VARIABLES # # # # #

	private static QoCMetaData qoCMetaData;
	private static MatchingQoCFilter matchingQoCFilter;

	private Boolean expectedResult;
	private QoCFilterExpression qoCFilterExpression;
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
		qoCFilter = new QoCFilter(EBinaryOperator.UNARY_OPERATOR);
		qoCFilter.addQocFilterExpression(qoCFilterExpression);
		matchingQoCFilter.setUp(qoCMetaData, qoCFilter);
		assertEquals(expectedResult, matchingQoCFilter.exec());
	}

	@Test
	public final void testOperatorDifferentQoCMetricValueValueReturnFalse() {
		// - - - - - CORE OF THE METHOD - - - - -
		qoCFilterExpression = new QoCFilterExpression(EQoCIdentificator.QOC_METRICVALUE_VALUE,
				EBinaryComparator.DIFFERENT, String.valueOf(qoCMetaData.qoCMetricValueValue()));
		expectedResult = false;
	}

	@Test
	public final void testOperatorDifferentQoCMetricValueValueReturnTrue() {
		// - - - - - CORE OF THE METHOD - - - - -
		qoCFilterExpression = new QoCFilterExpression(EQoCIdentificator.QOC_METRICVALUE_VALUE,
				EBinaryComparator.DIFFERENT, String.valueOf(qoCMetaData.qoCMetricValueValue() + 1));
		expectedResult = true;
	}

	@Test
	public final void testOperatorEqualQoCCriterionIdReturnFalse() {
		// - - - - - CORE OF THE METHOD - - - - -
		qoCFilterExpression = new QoCFilterExpression(EQoCIdentificator.QOC_INDICATOR_ID, EBinaryComparator.EQUALS,
				String.valueOf(qoCMetaData.qoCCriterionId() + 1));
		expectedResult = false;
	}

	@Test
	public final void testOperatorEqualQoCCriterionIdReturnTrue() {
		// - - - - - CORE OF THE METHOD - - - - -
		qoCFilterExpression = new QoCFilterExpression(EQoCIdentificator.QOC_CRITERION_ID, EBinaryComparator.EQUALS,
				String.valueOf(qoCMetaData.qoCCriterionId()));
		expectedResult = true;
	}

	@Test
	public final void testOperatorEqualQoCIndicatorIdReturnFalse() {
		// - - - - - CORE OF THE METHOD - - - - -
		qoCFilterExpression = new QoCFilterExpression(EQoCIdentificator.QOC_INDICATOR_ID, EBinaryComparator.EQUALS,
				String.valueOf(qoCMetaData.qoCIndicatorId() + 1));
		expectedResult = false;
	}

	@Test
	public final void testOperatorEqualQoCIndicatorIdReturnTrue() {
		// - - - - - CORE OF THE METHOD - - - - -
		qoCFilterExpression = new QoCFilterExpression(EQoCIdentificator.QOC_INDICATOR_ID, EBinaryComparator.EQUALS,
				String.valueOf(qoCMetaData.qoCIndicatorId()));
		expectedResult = true;
	}

	@Test
	public final void testOperatorEqualQoCMetricDefinitionIdReturnFalse() {
		// - - - - - CORE OF THE METHOD - - - - -
		qoCFilterExpression = new QoCFilterExpression(EQoCIdentificator.QOC_METRICDEFINITION_ID,
				EBinaryComparator.EQUALS, String.valueOf(qoCMetaData.qoCMetricDefinitionId() + 1));
		expectedResult = false;
	}

	@Test
	public final void testOperatorEqualQoCMetricDefinitionIdReturnTrue() {
		// - - - - - CORE OF THE METHOD - - - - -
		qoCFilterExpression = new QoCFilterExpression(EQoCIdentificator.QOC_METRICDEFINITION_ID,
				EBinaryComparator.EQUALS, String.valueOf(qoCMetaData.qoCMetricDefinitionId()));
		expectedResult = true;
	}

	@Test
	public final void testOperatorEqualQoCMetricValueCreationDateReturnFalse() {
		// - - - - - CORE OF THE METHOD - - - - -
		qoCFilterExpression = new QoCFilterExpression(EQoCIdentificator.QOC_METRICVALUE_CREATIONDATE,
				EBinaryComparator.EQUALS, String.valueOf(qoCMetaData.qoCMetricValueCreationDate() + 1));
		expectedResult = false;
	}

	@Test
	public final void testOperatorEqualQoCMetricValueCreationDateReturnTrue() {
		// - - - - - CORE OF THE METHOD - - - - -
		qoCFilterExpression = new QoCFilterExpression(EQoCIdentificator.QOC_METRICVALUE_CREATIONDATE,
				EBinaryComparator.EQUALS, String.valueOf(qoCMetaData.qoCMetricValueCreationDate()));
		expectedResult = true;
	}

	@Test
	public final void testOperatorEqualQoCMetricValueIdReturnFalse() {
		// - - - - - CORE OF THE METHOD - - - - -
		qoCFilterExpression = new QoCFilterExpression(EQoCIdentificator.QOC_METRICVALUE_ID, EBinaryComparator.EQUALS,
				String.valueOf(qoCMetaData.qoCMetricValueId() + 1));
		expectedResult = false;
	}

	@Test
	public final void testOperatorEqualQoCMetricValueIdReturnTrue() {
		// - - - - - CORE OF THE METHOD - - - - -
		qoCFilterExpression = new QoCFilterExpression(EQoCIdentificator.QOC_METRICVALUE_ID, EBinaryComparator.EQUALS,
				String.valueOf(qoCMetaData.qoCMetricValueId()));
		expectedResult = true;
	}

	@Test
	public final void testOperatorEqualQoCMetricValueModificationDateReturnFalse() {
		// - - - - - CORE OF THE METHOD - - - - -
		qoCFilterExpression = new QoCFilterExpression(EQoCIdentificator.QOC_METRICVALUE_MODIFICATIONDATE,
				EBinaryComparator.EQUALS, String.valueOf(qoCMetaData.qoCMetricValueModificationDate() + 1));
		expectedResult = false;
	}

	@Test
	public final void testOperatorEqualQoCMetricValueModificationDateReturnTrue() {
		// - - - - - CORE OF THE METHOD - - - - -
		qoCFilterExpression = new QoCFilterExpression(EQoCIdentificator.QOC_METRICVALUE_MODIFICATIONDATE,
				EBinaryComparator.EQUALS, String.valueOf(qoCMetaData.qoCMetricValueModificationDate()));
		expectedResult = true;
	}

	@Test
	public final void testOperatorEqualQoCMetricValueValueReturnFalse() {
		// - - - - - CORE OF THE METHOD - - - - -
		qoCFilterExpression = new QoCFilterExpression(EQoCIdentificator.QOC_METRICVALUE_VALUE, EBinaryComparator.EQUALS,
				String.valueOf(qoCMetaData.qoCMetricValueValue() + 1));
		expectedResult = false;
	}

	@Test
	public final void testOperatorEqualQoCMetricValueValueReturnTrue() {
		// - - - - - CORE OF THE METHOD - - - - -
		qoCFilterExpression = new QoCFilterExpression(EQoCIdentificator.QOC_METRICVALUE_VALUE, EBinaryComparator.EQUALS,
				String.valueOf(qoCMetaData.qoCMetricValueValue()));
		expectedResult = true;
	}

	@Test
	public final void testOperatorLowerEqualsQoCMetricValueValueReturnFalse() {
		// - - - - - CORE OF THE METHOD - - - - -
		qoCFilterExpression = new QoCFilterExpression(EQoCIdentificator.QOC_METRICVALUE_VALUE,
				EBinaryComparator.LOWER_EQUALS, String.valueOf(qoCMetaData.qoCMetricValueValue() - 1));
		expectedResult = false;
	}

	@Test
	public final void testOperatorLowerEqualsQoCMetricValueValueReturnTrue() {
		// - - - - - CORE OF THE METHOD - - - - -
		qoCFilterExpression = new QoCFilterExpression(EQoCIdentificator.QOC_METRICVALUE_VALUE,
				EBinaryComparator.LOWER_EQUALS, String.valueOf(qoCMetaData.qoCMetricValueValue()));
		expectedResult = true;
	}

	@Test
	public final void testOperatorLowerEqualsQoCMetricValueValueReturnTrue2() {
		// - - - - - CORE OF THE METHOD - - - - -
		qoCFilterExpression = new QoCFilterExpression(EQoCIdentificator.QOC_METRICVALUE_VALUE,
				EBinaryComparator.LOWER_EQUALS, String.valueOf(qoCMetaData.qoCMetricValueValue() + 1));
		expectedResult = true;
	}

	@Test
	public final void testOperatorLowerQoCMetricValueValueReturnFalse() {
		// - - - - - CORE OF THE METHOD - - - - -
		qoCFilterExpression = new QoCFilterExpression(EQoCIdentificator.QOC_METRICVALUE_VALUE, EBinaryComparator.LOWER,
				String.valueOf(qoCMetaData.qoCMetricValueValue()));
		expectedResult = false;
	}

	@Test
	public final void testOperatorLowerQoCMetricValueValueReturnTrue() {
		// - - - - - CORE OF THE METHOD - - - - -
		qoCFilterExpression = new QoCFilterExpression(EQoCIdentificator.QOC_METRICVALUE_VALUE, EBinaryComparator.LOWER,
				String.valueOf(qoCMetaData.qoCMetricValueValue() + 1));
		expectedResult = true;
	}

	@Test
	public final void testOperatorUpperEqualsQoCMetricValueValueReturnFalse() {
		// - - - - - CORE OF THE METHOD - - - - -
		qoCFilterExpression = new QoCFilterExpression(EQoCIdentificator.QOC_METRICVALUE_VALUE,
				EBinaryComparator.UPPER_EQUALS, String.valueOf(qoCMetaData.qoCMetricValueValue() + 1));
		expectedResult = false;
	}

	@Test
	public final void testOperatorUpperEqualsQoCMetricValueValueReturnTrue() {
		// - - - - - CORE OF THE METHOD - - - - -
		qoCFilterExpression = new QoCFilterExpression(EQoCIdentificator.QOC_METRICVALUE_VALUE,
				EBinaryComparator.UPPER_EQUALS, String.valueOf(qoCMetaData.qoCMetricValueValue()));
		expectedResult = true;
	}

	@Test
	public final void testOperatorUpperEqualsQoCMetricValueValueReturnTrue2() {
		// - - - - - CORE OF THE METHOD - - - - -
		qoCFilterExpression = new QoCFilterExpression(EQoCIdentificator.QOC_METRICVALUE_VALUE,
				EBinaryComparator.UPPER_EQUALS, String.valueOf(qoCMetaData.qoCMetricValueValue() - 1));
		expectedResult = true;
	}

	@Test
	public final void testOperatorUpperQoCMetricValueValueReturnFalse() {
		// - - - - - CORE OF THE METHOD - - - - -
		qoCFilterExpression = new QoCFilterExpression(EQoCIdentificator.QOC_METRICVALUE_VALUE, EBinaryComparator.UPPER,
				String.valueOf(qoCMetaData.qoCMetricValueValue()));
		expectedResult = false;
	}

	@Test
	public final void testOperatorUpperQoCMetricValueValueReturnTrue() {
		// - - - - - CORE OF THE METHOD - - - - -
		qoCFilterExpression = new QoCFilterExpression(EQoCIdentificator.QOC_METRICVALUE_VALUE, EBinaryComparator.UPPER,
				String.valueOf(qoCMetaData.qoCMetricValueValue() - 1));
		expectedResult = true;
	}
}
