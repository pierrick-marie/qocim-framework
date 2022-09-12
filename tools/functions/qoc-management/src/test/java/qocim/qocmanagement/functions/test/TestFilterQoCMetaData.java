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
package qocim.qocmanagement.functions.test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import qocim.datamodel.QoCIndicator;
import qocim.datamodel.QoCMetricDefinition;
import qocim.datamodel.QoCMetricValue;
import qocim.datamodel.information.InformationImpl;
import qocim.datamodel.information.QInformation;
import qocim.datamodel.test.criterion.one.TestCriterionOneFactory;
import qocim.datamodel.test.criterion.one.TestCriterionOneQoCCriterion;
import qocim.datamodel.test.criterion.one.TestCriterionOneQoCIndicator;
import qocim.datamodel.test.criterion.one.TestCriterionOneQoCMetricDefinition;
import qocim.datamodel.test.criterion.three.TestCriterionThreeFactory;
import qocim.datamodel.test.criterion.three.TestCriterionThreeQoCMetricDefinition;
import qocim.datamodel.test.criterion.two.TestCriterionTwoFactory;
import qocim.datamodel.test.criterion.two.TestCriterionTwoQoCMetricDefinition;
import qocim.datamodel.test.criterion.zero.TestCriterionZeroFactory;
import qocim.datamodel.test.criterion.zero.TestCriterionZeroQoCCriterion;
import qocim.datamodel.test.criterion.zero.TestCriterionZeroQoCIndicator;
import qocim.datamodel.test.criterion.zero.TestCriterionZeroQoCMetricDefinition;
import qocim.datamodel.utils.IQoCIMFactory;
import qocim.datamodel.utils.QoCIMLogger;
import qocim.qocmanagement.functions.impl.AddQoCIndicator;
import qocim.qocmanagement.functions.impl.FilterQoCMetaData;
import qocim.tool.functions.utils.EBinaryComparator;
import qocim.tool.functions.utils.EBinaryOperator;
import qocim.tool.functions.utils.EQoCIdentificator;
import qocim.tool.functions.utils.QoCFilter;
import qocim.tool.functions.utils.QoCFilterExpression;

public class TestFilterQoCMetaData {

	// # # # # # CONSTANTS # # # # #

	private final static String TEST_CONTEXT_ENTITY_NAME = "test_contextEntityName";
	private final static String TEST_CONTEXT_ENTITY_URI = "test_contextEntityUri";
	private final static String TEST_CONTEXT_OBSERVABLE_NAME = "test_contextObservableName";
	private final static String TEST_CONTEXT_OBSERVABLE_URI = "test_contextObservableUri";
	private final static String TEST_CONTEXT_OBSERVATION_UNIT = "test_contextObservationUnit";
	private static final int contextObservationId = 69;
	private static final int contextObservationValue = 42;

	// # # # # # PRIVATE VARIABLES # # # # #

	private static QInformation<Integer> expectedInformation;
	private static QInformation<Integer> testedInformation;
	private static List<QoCMetricValue> list_testedQoCMetricValue;
	private static List<QoCMetricValue> list_expectedQoCMetricValue;
	private static FilterQoCMetaData filterQoCMetaData;
	private static AddQoCIndicator addQoCIndicator;

	private QoCFilter qoCFilter;

	// # # # # # PUBLIC METHODS # # # # #

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		QoCIMLogger.logger.setLevel(Level.OFF);
		final Map<QoCMetricDefinition, IQoCIMFactory> map_availableQoCIMFacade = new HashMap<QoCMetricDefinition, IQoCIMFactory>();
		map_availableQoCIMFacade.put(new TestCriterionZeroQoCMetricDefinition(),
				TestCriterionZeroFactory.getInstance());
		map_availableQoCIMFacade.put(new TestCriterionOneQoCMetricDefinition(), TestCriterionOneFactory.getInstance());
		map_availableQoCIMFacade.put(new TestCriterionTwoQoCMetricDefinition(), TestCriterionTwoFactory.getInstance());
		map_availableQoCIMFacade.put(new TestCriterionThreeQoCMetricDefinition(),
				TestCriterionThreeFactory.getInstance());
		filterQoCMetaData = new FilterQoCMetaData();
		addQoCIndicator = new AddQoCIndicator(map_availableQoCIMFacade);
		list_testedQoCMetricValue = new ArrayList<QoCMetricValue>();
		list_expectedQoCMetricValue = new ArrayList<QoCMetricValue>();
		testedInformation = new InformationImpl<Integer>("tested information", new Integer(42));
		expectedInformation = new InformationImpl<Integer>("expected information", new Integer(42));
	}

	@Before
	public void setUp() throws Exception {
		// - - - - - CORE OF THE METHOD - - - - -
		expectedInformation.indicators().clear();
		testedInformation.indicators().clear();
	}

	@After
	public final void execTest() {
		// - - - - - CORE OF THE METHOD - - - - -
		list_testedQoCMetricValue.clear();
		list_expectedQoCMetricValue.clear();
		for (final QoCIndicator loop_qoCIndicator : testedInformation.indicators()) {
			list_testedQoCMetricValue.addAll(loop_qoCIndicator.list_qoCMetricValue);
		}
		for (final QoCIndicator loop_qoCIndicator : expectedInformation.indicators()) {
			list_expectedQoCMetricValue.addAll(loop_qoCIndicator.list_qoCMetricValue);
		}
		assertEquals(list_expectedQoCMetricValue.size(), list_testedQoCMetricValue.size());
		for (final QoCMetricValue loop_testedQoCMetricValue : list_testedQoCMetricValue) {
			Assert.assertTrue(list_expectedQoCMetricValue.contains(loop_testedQoCMetricValue));
		}
	}

	@Test
	public final void testOneQoCIndicatorWithOneQoCMetaDataFilterReturnFalse() {
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		qoCFilter = new QoCFilter(EBinaryOperator.UNARY_OPERATOR);
		// - - - - - CORE OF THE METHOD - - - - -
		addQoCIndicator.setUp(TestCriterionZeroQoCIndicator.ID_DEFAULTVALUE,
				TestCriterionZeroQoCCriterion.ID_DEFAULTVALUE, TestCriterionZeroQoCMetricDefinition.ID_DEFAULTVALUE);
		addQoCIndicator.exec(testedInformation);
		qoCFilter.addQocFilterExpression(new QoCFilterExpression(EQoCIdentificator.QOC_INDICATOR_ID,
				EBinaryComparator.DIFFERENT, TestCriterionZeroQoCIndicator.ID_DEFAULTVALUE.toString()));
		filterQoCMetaData.setUp(qoCFilter);
		filterQoCMetaData.exec(testedInformation);
	}

	@Test
	public final void testOneQoCIndicatorWithOneQoCMetaDataFilterReturnTrue() {
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		qoCFilter = new QoCFilter(EBinaryOperator.UNARY_OPERATOR);
		// - - - - - CORE OF THE METHOD - - - - -
		addQoCIndicator.setUp(TestCriterionZeroQoCIndicator.ID_DEFAULTVALUE,
				TestCriterionZeroQoCCriterion.ID_DEFAULTVALUE, TestCriterionZeroQoCMetricDefinition.ID_DEFAULTVALUE);
		addQoCIndicator.exec(testedInformation);
		expectedInformation.indicators().addAll(testedInformation.indicators());
		qoCFilter.addQocFilterExpression(new QoCFilterExpression(EQoCIdentificator.QOC_INDICATOR_ID,
				EBinaryComparator.EQUALS, TestCriterionZeroQoCIndicator.ID_DEFAULTVALUE.toString()));
		filterQoCMetaData.setUp(qoCFilter);
		filterQoCMetaData.exec(testedInformation);
	}

	@Test
	public final void testOneQoCIndicatorWithTwoQoCMetaDataFilterReturnFalse() {
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		qoCFilter = new QoCFilter(EBinaryOperator.UNARY_OPERATOR);
		// - - - - - CORE OF THE METHOD - - - - -
		addQoCIndicator.setUp(TestCriterionZeroQoCIndicator.ID_DEFAULTVALUE,
				TestCriterionZeroQoCCriterion.ID_DEFAULTVALUE, TestCriterionZeroQoCMetricDefinition.ID_DEFAULTVALUE);
		addQoCIndicator.exec(testedInformation);
		addQoCIndicator.exec(testedInformation);
		qoCFilter.addQocFilterExpression(new QoCFilterExpression(EQoCIdentificator.QOC_INDICATOR_ID,
				EBinaryComparator.DIFFERENT, TestCriterionZeroQoCIndicator.ID_DEFAULTVALUE.toString()));
		filterQoCMetaData.setUp(qoCFilter);
		filterQoCMetaData.exec(testedInformation);
	}

	@Test
	public final void testOneQoCIndicatorWithTwoQoCMetaDataFilterReturnTrue() {
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		qoCFilter = new QoCFilter(EBinaryOperator.UNARY_OPERATOR);
		// - - - - - CORE OF THE METHOD - - - - -
		addQoCIndicator.setUp(TestCriterionZeroQoCIndicator.ID_DEFAULTVALUE,
				TestCriterionZeroQoCCriterion.ID_DEFAULTVALUE, TestCriterionZeroQoCMetricDefinition.ID_DEFAULTVALUE);
		addQoCIndicator.exec(testedInformation);
		addQoCIndicator.exec(testedInformation);
		expectedInformation.indicators().addAll(testedInformation.indicators());
		qoCFilter.addQocFilterExpression(new QoCFilterExpression(EQoCIdentificator.QOC_INDICATOR_ID,
				EBinaryComparator.EQUALS, TestCriterionZeroQoCIndicator.ID_DEFAULTVALUE.toString()));
		filterQoCMetaData.setUp(qoCFilter);
		filterQoCMetaData.exec(testedInformation);
	}

	@Test
	public final void testTwoQoCIndicatorWithOneQoCMetaDataFilterReturnFalse() {
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		qoCFilter = new QoCFilter(EBinaryOperator.AND);
		// - - - - - CORE OF THE METHOD - - - - -
		addQoCIndicator.setUp(TestCriterionZeroQoCIndicator.ID_DEFAULTVALUE,
				TestCriterionZeroQoCCriterion.ID_DEFAULTVALUE, TestCriterionZeroQoCMetricDefinition.ID_DEFAULTVALUE);
		addQoCIndicator.exec(testedInformation);
		addQoCIndicator.setUp(TestCriterionOneQoCIndicator.ID_DEFAULTVALUE,
				TestCriterionOneQoCCriterion.ID_DEFAULTVALUE, TestCriterionOneQoCMetricDefinition.ID_DEFAULTVALUE);
		addQoCIndicator.exec(testedInformation);
		qoCFilter.addQocFilterExpression(new QoCFilterExpression(EQoCIdentificator.QOC_INDICATOR_ID,
				EBinaryComparator.EQUALS, TestCriterionZeroQoCIndicator.ID_DEFAULTVALUE.toString()));
		qoCFilter.addQocFilterExpression(new QoCFilterExpression(EQoCIdentificator.QOC_INDICATOR_ID,
				EBinaryComparator.EQUALS, TestCriterionOneQoCIndicator.ID_DEFAULTVALUE.toString()));
		filterQoCMetaData.setUp(qoCFilter);
		filterQoCMetaData.exec(testedInformation);
	}

	@Test
	public final void testTwoQoCIndicatorWithOneQoCMetaDataFilterReturnTrue() {
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		qoCFilter = new QoCFilter(EBinaryOperator.OR);
		// - - - - - CORE OF THE METHOD - - - - -
		addQoCIndicator.setUp(TestCriterionZeroQoCIndicator.ID_DEFAULTVALUE,
				TestCriterionZeroQoCCriterion.ID_DEFAULTVALUE, TestCriterionZeroQoCMetricDefinition.ID_DEFAULTVALUE);
		addQoCIndicator.exec(testedInformation);
		addQoCIndicator.setUp(TestCriterionOneQoCIndicator.ID_DEFAULTVALUE,
				TestCriterionOneQoCCriterion.ID_DEFAULTVALUE, TestCriterionOneQoCMetricDefinition.ID_DEFAULTVALUE);
		addQoCIndicator.exec(testedInformation);
		expectedInformation.indicators().addAll(testedInformation.indicators());
		qoCFilter.addQocFilterExpression(new QoCFilterExpression(EQoCIdentificator.QOC_INDICATOR_ID,
				EBinaryComparator.EQUALS, TestCriterionZeroQoCIndicator.ID_DEFAULTVALUE.toString()));
		qoCFilter.addQocFilterExpression(new QoCFilterExpression(EQoCIdentificator.QOC_INDICATOR_ID,
				EBinaryComparator.EQUALS, TestCriterionOneQoCIndicator.ID_DEFAULTVALUE.toString()));
		filterQoCMetaData.setUp(qoCFilter);
		filterQoCMetaData.exec(testedInformation);
	}

	@Test
	public final void testTwoQoCIndicatorWithTwoQoCMetaDataFilterReturnFalse() {
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		qoCFilter = new QoCFilter(EBinaryOperator.AND);
		// - - - - - CORE OF THE METHOD - - - - -
		addQoCIndicator.setUp(TestCriterionZeroQoCIndicator.ID_DEFAULTVALUE,
				TestCriterionZeroQoCCriterion.ID_DEFAULTVALUE, TestCriterionZeroQoCMetricDefinition.ID_DEFAULTVALUE);
		addQoCIndicator.exec(testedInformation);
		addQoCIndicator.exec(testedInformation);
		addQoCIndicator.setUp(TestCriterionOneQoCIndicator.ID_DEFAULTVALUE,
				TestCriterionOneQoCCriterion.ID_DEFAULTVALUE, TestCriterionOneQoCMetricDefinition.ID_DEFAULTVALUE);
		addQoCIndicator.exec(testedInformation);
		addQoCIndicator.exec(testedInformation);
		qoCFilter.addQocFilterExpression(new QoCFilterExpression(EQoCIdentificator.QOC_INDICATOR_ID,
				EBinaryComparator.EQUALS, TestCriterionZeroQoCIndicator.ID_DEFAULTVALUE.toString()));
		qoCFilter.addQocFilterExpression(new QoCFilterExpression(EQoCIdentificator.QOC_INDICATOR_ID,
				EBinaryComparator.EQUALS, TestCriterionOneQoCIndicator.ID_DEFAULTVALUE.toString()));
		filterQoCMetaData.setUp(qoCFilter);
		filterQoCMetaData.exec(testedInformation);
	}

	@Test
	public final void testTwoQoCIndicatorWithTwoQoCMetaDataFilterReturnTrue() {
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		qoCFilter = new QoCFilter(EBinaryOperator.OR);
		// - - - - - CORE OF THE METHOD - - - - -
		addQoCIndicator.setUp(TestCriterionZeroQoCIndicator.ID_DEFAULTVALUE,
				TestCriterionZeroQoCCriterion.ID_DEFAULTVALUE, TestCriterionZeroQoCMetricDefinition.ID_DEFAULTVALUE);
		addQoCIndicator.exec(testedInformation);
		addQoCIndicator.exec(testedInformation);
		addQoCIndicator.setUp(TestCriterionOneQoCIndicator.ID_DEFAULTVALUE,
				TestCriterionOneQoCCriterion.ID_DEFAULTVALUE, TestCriterionOneQoCMetricDefinition.ID_DEFAULTVALUE);
		addQoCIndicator.exec(testedInformation);
		addQoCIndicator.exec(testedInformation);
		expectedInformation.indicators().addAll(testedInformation.indicators());
		qoCFilter.addQocFilterExpression(new QoCFilterExpression(EQoCIdentificator.QOC_INDICATOR_ID,
				EBinaryComparator.EQUALS, TestCriterionZeroQoCIndicator.ID_DEFAULTVALUE.toString()));
		qoCFilter.addQocFilterExpression(new QoCFilterExpression(EQoCIdentificator.QOC_INDICATOR_ID,
				EBinaryComparator.EQUALS, TestCriterionOneQoCIndicator.ID_DEFAULTVALUE.toString()));
		filterQoCMetaData.setUp(qoCFilter);
		filterQoCMetaData.exec(testedInformation);
	}

	@Test
	public final void testZeroQoCIndicator() {
		// Do nothing.
	}
}
