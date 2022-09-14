/**
 * This file is part of the QoCIM middleware.
 * <p>
 * Copyright (C) 2014 IRIT, Télécom SudParis
 * <p>
 * The QoCIM software is free software: you can redistribute it and/or modify
 * It under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * The QoCIM software platform is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * <p>
 * See the GNU Lesser General Public License
 * for more details: http://www.gnu.org/licenses
 * <p>
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

import qocim.datamodel.QoCIndicator;
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
import qocim.datamodel.utils.log.QoCIMLogger;
import qocim.qocmanagement.functions.impl.AddQoCIndicator;

public class TestAddMultipleQoCIndicator {

    // # # # # # CONSTANTS # # # # #

    private final static String TEST_CONTEXT_ENTITY_NAME = "test_contextEntityName";
    private final static String TEST_CONTEXT_ENTITY_URI = "test_contextEntityUri";
    private final static String TEST_CONTEXT_OBSERVABLE_NAME = "test_contextObservableName";
    private final static String TEST_CONTEXT_OBSERVABLE_URI = "test_contextObservableUri";
    private final static int TEST_CONTEXT_OBSERVATION_ID = 69;
    private final static int TEST_CONTEXT_OBSERVATION_VALUE = 42;
    private final static String CONTEXT_OBSERVATION_UNIT = "test_contextObservationUnit";

    // # # # # # PRIVATE VARIABLES # # # # #

    private static AddQoCIndicator addQoCIndicator;
    private static Map<QoCMetricDefinition, IQoCIMFactory> map_availableQoCIMFacade;

    private Integer expectedNumberOfQoCMetricValue;
    private Integer expectedNumberOfQoCIndicator;
    private QInformation<?> information = null;

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
        assertEquals(expectedNumberOfQoCIndicator, (Integer) information.indicators().size());
        for (final QoCIndicator loop_qoCIndicator : information.indicators()) {
            assertEquals(expectedNumberOfQoCMetricValue, (Integer) loop_qoCIndicator.list_qoCMetricValue.size());
        }
    }

    @Test
    public final void testTwoDifferentIndicators() {
        // - - - - - CORE OF THE METHOD - - - - -
        addQoCIndicator.setUp(TestCriterionOneQoCIndicator.ID_DEFAULTVALUE,
                TestCriterionOneQoCCriterion.ID_DEFAULTVALUE, TestCriterionOneQoCMetricDefinition.ID_DEFAULTVALUE);
        addQoCIndicator.exec(information);
        addQoCIndicator.setUp(TestCriterionZeroQoCIndicator.ID_DEFAULTVALUE,
                TestCriterionZeroQoCCriterion.ID_DEFAULTVALUE, TestCriterionZeroQoCMetricDefinition.ID_DEFAULTVALUE);
        addQoCIndicator.exec(information);
        expectedNumberOfQoCIndicator = 2;
        expectedNumberOfQoCMetricValue = 1;
    }

    @Test
    public final void testTwoObservations() {
        // - - - - - INITIALIZE THE VARIABLES - - - - -
        QInformation<?> new_information;
        // - - - - - CORE OF THE METHOD - - - - -
        new_information = new InformationImpl<>("Test new information", new Integer(43));
        addQoCIndicator.setUp(TestCriterionOneQoCIndicator.ID_DEFAULTVALUE,
                TestCriterionOneQoCCriterion.ID_DEFAULTVALUE, TestCriterionOneQoCMetricDefinition.ID_DEFAULTVALUE);
        addQoCIndicator.exec(information);
        expectedNumberOfQoCIndicator = 1;
        expectedNumberOfQoCMetricValue = 1;
    }

    @Test
    public final void testTwoQoCIndicators() {
        // - - - - - CORE OF THE METHOD - - - - -
        addQoCIndicator.setUp(TestCriterionOneQoCIndicator.ID_DEFAULTVALUE,
                TestCriterionOneQoCCriterion.ID_DEFAULTVALUE, TestCriterionOneQoCMetricDefinition.ID_DEFAULTVALUE);
        addQoCIndicator.exec(information);
        addQoCIndicator.exec(information);
        expectedNumberOfQoCIndicator = 1;
        expectedNumberOfQoCMetricValue = 2;
    }
}
