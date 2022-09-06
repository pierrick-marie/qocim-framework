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
package qocim.datamodel.utils.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import qocim.datamodel.QoCCriterion;
import qocim.datamodel.QoCIndicator;
import qocim.datamodel.QoCMetaData;
import qocim.datamodel.QoCMetricDefinition;
import qocim.datamodel.QoCMetricValue;
import qocim.datamodel.test.TestFactory;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestQoCMetaData {

    private static Double generateRandomQoCMetricValue(final Double _minRandomValue, final Double _maxRandomValue) {
	// - - - - - INITIALIZE THE VARIABLES - - - - -
	final Double coefficient = _maxRandomValue - _minRandomValue;
	// - - - - - RETURN STATEMENT - - - - -
	return Math.random() * coefficient + _minRandomValue;
    }

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {

	randomQoCValue = generateRandomQoCMetricValue(DEFAULT_MINRANDOMVALUE, DEFAULT_MAXRANDOMVALUE);
    }

    private QoCMetaData qoCMetaData;
    private Integer couter_qoCMetricValueId;
    private static Double randomQoCValue;
    private static final double DEFAULT_MINRANDOMVALUE = 0;
    private static final double DEFAULT_MAXRANDOMVALUE = Integer.MAX_VALUE;

    @Before
    public void setUp() throws Exception {

	couter_qoCMetricValueId = 0;
	qoCMetaData = new QoCMetaData(TestFactory.getInstance().newQoCIndicator("" + couter_qoCMetricValueId++, randomQoCValue));
    }

    @Test
    public final void testEquals() {

	QoCMetaData testQoCMetaData;

	/**
	 * Compares same objects
	 */
	testQoCMetaData = qoCMetaData;
	assertTrue(qoCMetaData.equals(testQoCMetaData));

	/**
	 * Compares different objects with different values
	 */
	testQoCMetaData = new QoCMetaData(TestFactory.getInstance().newQoCIndicator("" + couter_qoCMetricValueId++,
		generateRandomQoCMetricValue(DEFAULT_MINRANDOMVALUE, DEFAULT_MAXRANDOMVALUE)));

	assertFalse(qoCMetaData.equals(testQoCMetaData));
    }

    @Test
    public final void testGetQoCCriterion() {

	QoCMetaData testQoCMetaData;
	QoCIndicator testQoCIndicator;
	QoCCriterion testQoCCriterion;

	/**
	 * Compares two different objects
	 */
	testQoCMetaData = new QoCMetaData(TestFactory.getInstance().newQoCIndicator("" + couter_qoCMetricValueId++, randomQoCValue));
	testQoCIndicator = TestFactory.getInstance().newQoCIndicator("" + couter_qoCMetricValueId++, randomQoCValue);
	testQoCCriterion = testQoCIndicator.qoCCriterion();
	assertEquals(testQoCMetaData.qoCCriterion().id(), testQoCCriterion.id());
	assertEquals(testQoCMetaData.qoCCriterion().name(), testQoCCriterion.name());
	assertEquals(testQoCMetaData.qoCCriterion().list_qoCMetricDefinition.size(), testQoCCriterion.list_qoCMetricDefinition.size());

	/**
	 * Compares same objects
	 */
	testQoCMetaData = new QoCMetaData(TestFactory.getInstance().newQoCIndicator("" + couter_qoCMetricValueId++, randomQoCValue));
	testQoCIndicator = testQoCMetaData.qoCIndicator();
	testQoCCriterion = testQoCIndicator.qoCCriterion();

	assertEquals(testQoCMetaData.qoCCriterion().id(), testQoCCriterion.id());
	assertEquals(testQoCMetaData.qoCCriterion().name(), testQoCCriterion.name());
	assertEquals(testQoCMetaData.qoCCriterion().list_qoCMetricDefinition.size(), testQoCCriterion.list_qoCMetricDefinition.size());
    }

    @Test
    public final void testGetQocCriterionId() {

	assertEquals(qoCMetaData.qoCCriterionId(), "[0.1]");
    }

    @Test
    public final void testGetQoCIndicator() {

	QoCMetaData testQoCMetaData;
	QoCIndicator testQoCIndicator;

	/**
	 * Compares two different objects
	 */
	testQoCMetaData = new QoCMetaData(TestFactory.getInstance().newQoCIndicator("" + couter_qoCMetricValueId++, randomQoCValue));
	testQoCIndicator = TestFactory.getInstance().newQoCIndicator("" + couter_qoCMetricValueId++, randomQoCValue);
	assertEquals(testQoCMetaData.qoCIndicator().id(), testQoCIndicator.id());
	assertEquals(testQoCMetaData.qoCIndicator().name(), testQoCIndicator.name());
	assertEquals(testQoCMetaData.qoCIndicator().list_qoCMetricValue.size(), testQoCIndicator.list_qoCMetricValue.size());

	/**
	 * Compares same objects
	 */
	testQoCMetaData = new QoCMetaData(TestFactory.getInstance().newQoCIndicator("" + couter_qoCMetricValueId++, randomQoCValue));
	testQoCIndicator = testQoCMetaData.qoCIndicator();

	assertEquals(testQoCMetaData.qoCIndicator().id(), testQoCIndicator.id());
	assertEquals(testQoCMetaData.qoCIndicator().name(), testQoCIndicator.name());
	assertEquals(testQoCMetaData.qoCIndicator().list_qoCMetricValue.size(), testQoCIndicator.list_qoCMetricValue.size());
    }

    @Test
    public final void testGetQocIndicatorId() {

	assertEquals(qoCMetaData.qoCIndicatorId(), Integer.valueOf(0));
    }

    @Test
    public final void testGetQoCMetricDefinition() {

	QoCMetaData testQoCMetaData;
	QoCIndicator testQoCIndicator;
	QoCMetricDefinition testQoCMetricDefinition;

	/**
	 * Compares two different objects
	 */
	testQoCMetaData = new QoCMetaData(TestFactory.getInstance().newQoCIndicator("" + couter_qoCMetricValueId++, randomQoCValue));
	testQoCIndicator = TestFactory.getInstance().newQoCIndicator("" + couter_qoCMetricValueId++, randomQoCValue);
	testQoCMetricDefinition = testQoCIndicator.qoCCriterion().list_qoCMetricDefinition.getFirst();
	assertEquals(testQoCMetaData.qoCMetricDefinition().id(), testQoCMetricDefinition.id());
	assertEquals(testQoCMetaData.qoCMetricDefinition().isInvariant(), testQoCMetricDefinition.isInvariant());
	assertEquals(testQoCMetaData.qoCMetricDefinition().maxValue(), testQoCMetricDefinition.maxValue());
	assertEquals(testQoCMetaData.qoCMetricDefinition().minValue(), testQoCMetricDefinition.minValue());
	assertEquals(testQoCMetaData.qoCMetricDefinition().name(), testQoCMetricDefinition.name());
	assertEquals(testQoCMetaData.qoCMetricDefinition().providerUri(), testQoCMetricDefinition.providerUri());
	assertEquals(testQoCMetaData.qoCMetricDefinition().unit(), testQoCMetricDefinition.unit());
	assertEquals(testQoCMetaData.qoCMetricDefinition().list_qoCMetricValue.size(), testQoCMetricDefinition.list_qoCMetricValue.size());

	/**
	 * Compares same objects
	 */
	testQoCMetaData = new QoCMetaData(TestFactory.getInstance().newQoCIndicator("" + couter_qoCMetricValueId++, randomQoCValue));
	testQoCIndicator = testQoCMetaData.qoCIndicator();
	testQoCMetricDefinition = testQoCIndicator.qoCCriterion().list_qoCMetricDefinition.getFirst();
	assertEquals(testQoCMetaData.qoCMetricDefinition().id(), testQoCMetricDefinition.id());
	assertEquals(testQoCMetaData.qoCMetricDefinition().isInvariant(), testQoCMetricDefinition.isInvariant());
	assertEquals(testQoCMetaData.qoCMetricDefinition().maxValue(), testQoCMetricDefinition.maxValue());
	assertEquals(testQoCMetaData.qoCMetricDefinition().minValue(), testQoCMetricDefinition.minValue());
	assertEquals(testQoCMetaData.qoCMetricDefinition().name(), testQoCMetricDefinition.name());
	assertEquals(testQoCMetaData.qoCMetricDefinition().providerUri(), testQoCMetricDefinition.providerUri());
	assertEquals(testQoCMetaData.qoCMetricDefinition().unit(), testQoCMetricDefinition.unit());
	assertEquals(testQoCMetaData.qoCMetricDefinition().list_qoCMetricValue.size(), testQoCMetricDefinition.list_qoCMetricValue.size());
    }

    @Test
    public final void testGetQocMetricDefinitionId() {

	assertEquals(qoCMetaData.qoCMetricDefinitionId(), "0.1");
    }

    @Test
    public final void testGetQoCMetricValue() {

	QoCMetaData testQoCMetaData;
	QoCIndicator testQoCIndicator;
	QoCMetricValue testQoCMetricValue;

	/**
	 * Compares two different objects
	 */
	testQoCMetaData = new QoCMetaData(TestFactory.getInstance().newQoCIndicator("" + couter_qoCMetricValueId++, randomQoCValue));
	testQoCIndicator = TestFactory.getInstance().newQoCIndicator("" + couter_qoCMetricValueId++, randomQoCValue);
	testQoCMetricValue = testQoCIndicator.list_qoCMetricValue.getFirst();
	assertEquals(testQoCMetaData.qoCMetricValue().name(), testQoCMetricValue.name());
	assertEquals(testQoCMetaData.qoCMetricValue().value(), testQoCMetricValue.value());

	/**
	 * Compares same objects
	 */
	testQoCMetaData = new QoCMetaData(TestFactory.getInstance().newQoCIndicator("" + couter_qoCMetricValueId++, randomQoCValue));
	testQoCIndicator = testQoCMetaData.qoCIndicator();
	testQoCMetricValue = testQoCIndicator.list_qoCMetricValue.getFirst();
	assertEquals(testQoCMetaData.qoCMetricValue().id(), testQoCMetricValue.id());
	assertEquals(testQoCMetaData.qoCMetricValue().creationDate(), testQoCMetricValue.creationDate());
	assertEquals(testQoCMetaData.qoCMetricValue().modificationDate(), testQoCMetricValue.modificationDate());
	assertEquals(testQoCMetaData.qoCMetricValue().name(), testQoCMetricValue.name());
	assertEquals(testQoCMetaData.qoCMetricValue().value(), testQoCMetricValue.value());
    }

    @Test
    public final void testGetQocMetricValueValue() {
	// - - - - - INITIALIZE THE VARIABLES - - - - -
	final Double qoCValue = 42.0;
	final QoCMetaData testQoCMetaData = new QoCMetaData(TestFactory.getInstance().newQoCIndicator("" + couter_qoCMetricValueId++, qoCValue));
	// - - - - - CORE OF THE METHOD - - - - -
	assertEquals(testQoCMetaData.qoCMetricValueValue(), qoCValue);
    }
}
