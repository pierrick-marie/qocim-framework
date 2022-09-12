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
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import org.junit.After;
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
import qocim.qocmanagement.functions.impl.UpdateQoCMetaData;

public class TestUpdateQoCMetaData {

	// # # # # # CONSTANTS # # # # #

	private final static String TEST_CONTEXT_ENTITY_NAME = "test_contextEntityName";
	private final static String TEST_CONTEXT_ENTITY_URI = "test_contextEntityUri";
	private final static String TEST_CONTEXT_OBSERVABLE_NAME = "test_contextObservableName";
	private final static String TEST_CONTEXT_OBSERVABLE_URI = "test_contextObservableUri";
	private final static String TEST_CONTEXT_OBSERVATION_UNIT = "test_contextObservationUnit";

	// # # # # # PRIVATE VARIABLES # # # # #

	private static List<QoCMetricValue> old_list_qoCMetricValue;
	private static List<QoCMetricValue> new_list_qoCMetricValue;
	private static UpdateQoCMetaData updateQoCMetaData;
	private static AddQoCIndicator addQoCIndicator;
	private static Integer contextObservationId = 69;
	private static Integer contextObservationValue = 42;

	private QInformation<?> information;

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
		updateQoCMetaData = new UpdateQoCMetaData(map_availableQoCIMFacade);
		addQoCIndicator = new AddQoCIndicator(map_availableQoCIMFacade);
		old_list_qoCMetricValue = new ArrayList<QoCMetricValue>();
		new_list_qoCMetricValue = new ArrayList<QoCMetricValue>();
	}

	@Before
	public void setUp() throws Exception {
		// - - - - - CORE OF THE METHOD - - - - -
		information = new InformationImpl<>("Test information", new Integer(42));
		old_list_qoCMetricValue.clear();
		new_list_qoCMetricValue.clear();
	}

	@After
	public final void execTest() {
		// - - - - - CORE OF THE METHOD - - - - -
		initOldListQoCMetricValue();
		information = updateQoCMetaData.exec(information);
		initNewListQoCMetricValue();
		compareListQoCMetricValues();
	}

	@Test
	public final void testOneQoCIndicatorsWithOneQoCMetaData() {
		// - - - - - CORE OF THE METHOD - - - - -
		addQoCIndicator.setUp(TestCriterionZeroQoCIndicator.ID_DEFAULTVALUE,
				TestCriterionZeroQoCCriterion.ID_DEFAULTVALUE, TestCriterionZeroQoCMetricDefinition.ID_DEFAULTVALUE);
		information = addQoCIndicator.exec(information);
	}

	@Test
	public final void testOneQoCIndicatorsWithThreeQoCMetaData() {
		// - - - - - CORE OF THE METHOD - - - - -
		addQoCIndicator.setUp(TestCriterionZeroQoCIndicator.ID_DEFAULTVALUE,
				TestCriterionZeroQoCCriterion.ID_DEFAULTVALUE, TestCriterionZeroQoCMetricDefinition.ID_DEFAULTVALUE);
		information = addQoCIndicator.exec(information);
		information = addQoCIndicator.exec(information);
		information = addQoCIndicator.exec(information);
	}

	@Test
	public final void testTwoQoCIndicatorsWithTwoQoCMetaData() {
		// - - - - - CORE OF THE METHOD - - - - -
		addQoCIndicator.setUp(TestCriterionZeroQoCIndicator.ID_DEFAULTVALUE,
				TestCriterionZeroQoCCriterion.ID_DEFAULTVALUE, TestCriterionZeroQoCMetricDefinition.ID_DEFAULTVALUE);
		information = addQoCIndicator.exec(information);
		information = addQoCIndicator.exec(information);
		addQoCIndicator.setUp(TestCriterionOneQoCIndicator.ID_DEFAULTVALUE,
				TestCriterionOneQoCCriterion.ID_DEFAULTVALUE, TestCriterionOneQoCMetricDefinition.ID_DEFAULTVALUE);
		information = addQoCIndicator.exec(information);
		information = addQoCIndicator.exec(information);
	}

	// # # # # # PRIVATE METHODS # # # # #

	private final void compareListQoCMetricValues() {
		// - - - - - CORE OF THE METHOD - - - - -
		/*
		 * Very simple tests. To see most complete test, look after the
		 * TestUpdateQoCIndicator Unit test.
		 */
		assertEquals(old_list_qoCMetricValue.size(), new_list_qoCMetricValue.size());
		for (final QoCMetricValue loop_old_qoCMetricValue : old_list_qoCMetricValue) {
			if (new_list_qoCMetricValue.contains(loop_old_qoCMetricValue)) {
				fail("Fail test update value, missing unchanged value: " + loop_old_qoCMetricValue);
			}
		}
	}

	private final void initNewListQoCMetricValue() {
		// - - - - - CORE OF THE METHOD - - - - -
		for (final QoCIndicator loop_qoCIndicator : information.indicators()) {
			new_list_qoCMetricValue.addAll(loop_qoCIndicator.listQoCMetricValue());
		}
	}

	private final void initOldListQoCMetricValue() {
		// - - - - - CORE OF THE METHOD - - - - -
		for (final QoCIndicator loop_qoCIndicator : information.indicators()) {
			old_list_qoCMetricValue.addAll(loop_qoCIndicator.listQoCMetricValue());
		}
	}
}
