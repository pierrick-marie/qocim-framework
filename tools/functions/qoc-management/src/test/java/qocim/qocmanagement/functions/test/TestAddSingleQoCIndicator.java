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

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import qocim.datamodel.QoCMetricDefinition;
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

public class TestAddSingleQoCIndicator {

	// # # # # # CONSTANTS # # # # #

	private final static String TEST_CONTEXT_ENTITY_NAME = "test_contextEntityName";
	private final static String TEST_CONTEXT_ENTITY_URI = "test_contextEntityUri";
	private final static String TEST_CONTEXT_OBSERVABLE_NAME = "test_contextObservableName";
	private final static String TEST_CONTEXT_OBSERVABLE_URI = "test_contextObservableUri";
	private final static String TEST_OBSERVATION_UNIT = "test_contextObservationUnit";
	private final static Integer contextObservationId = 69;
	private final static Integer contextObservationValue = 42;

	// # # # # # PRIVATE VARIABLES # # # # #

	private static AddQoCIndicator addQoCIndicator;
	private static Map<QoCMetricDefinition, IQoCIMFactory> map_availableQoCIMFacade;

	private QInformation<?> information;
	private Integer qoCIndicatorId;
	private String qoCCriterionId;
	private String qoCMetricDefinitionId;

	// # # # # # PUBLIC METHODS # # # # #

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		QoCIMLogger.logger.setLevel(Level.OFF);
		map_availableQoCIMFacade = new HashMap<QoCMetricDefinition, IQoCIMFactory>();
		map_availableQoCIMFacade.put(new TestCriterionZeroQoCMetricDefinition(),
				TestCriterionZeroFactory.getInstance());
		map_availableQoCIMFacade.put(new TestCriterionOneQoCMetricDefinition(), TestCriterionOneFactory.getInstance());
		map_availableQoCIMFacade.put(new TestCriterionTwoQoCMetricDefinition(), TestCriterionTwoFactory.getInstance());
		map_availableQoCIMFacade.put(new TestCriterionThreeQoCMetricDefinition(),
				TestCriterionThreeFactory.getInstance());
		addQoCIndicator = new AddQoCIndicator(map_availableQoCIMFacade);
	}

	@Before
	public void setUp() throws Exception {
		// - - - - - CORE OF THE METHOD - - - - -
		information = new InformationImpl<>("Test information", new Integer(42));
	}

	@After
	public final void execTest() {
		// - - - - - CORE OF THE METHOD - - - - -
		assertEquals(0, information.indicators().size());
		addQoCIndicator.setUp(qoCIndicatorId, qoCCriterionId, qoCMetricDefinitionId);
		addQoCIndicator.exec(information);
		assertEquals(1, information.indicators().size());
		assertEquals(qoCIndicatorId,
				information.indicators().iterator().next().id());
		assertEquals(qoCMetricDefinitionId, information.indicators().get(0)
				.qoCCriterion().list_qoCMetricDefinition.getFirst().id());
		assertEquals("0", information.indicators().get(0).list_qoCMetricValue
				.getFirst().id());

	}

	@Test
	public final void testCriterionOne() {
		// - - - - - CORE OF THE METHOD - - - - -
		qoCIndicatorId = TestCriterionOneQoCIndicator.ID_DEFAULTVALUE;
		qoCCriterionId = TestCriterionOneQoCCriterion.ID_DEFAULTVALUE;
		qoCMetricDefinitionId = TestCriterionOneQoCMetricDefinition.ID_DEFAULTVALUE;
	}

	@Test
	public final void testCriterionZero() {
		// - - - - - CORE OF THE METHOD - - - - -
		qoCIndicatorId = TestCriterionZeroQoCIndicator.ID_DEFAULTVALUE;
		qoCCriterionId = TestCriterionZeroQoCCriterion.ID_DEFAULTVALUE;
		qoCMetricDefinitionId = TestCriterionZeroQoCMetricDefinition.ID_DEFAULTVALUE;
	}
}
