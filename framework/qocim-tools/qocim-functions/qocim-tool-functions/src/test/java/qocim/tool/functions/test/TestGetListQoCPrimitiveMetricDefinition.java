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
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import qocim.datamodel.QoCIndicator;
import qocim.datamodel.QoCMetricDefinition;
import qocim.datamodel.test.criterion.one.TestCriterionOneFactory;
import qocim.datamodel.test.criterion.two.TestCriterionTwoFactory;
import qocim.datamodel.test.criterion.zero.TestCriterionZeroFactory;
import qocim.tool.functions.impl.GetListPrimitiveQoCMetricDefinition;

public class TestGetListQoCPrimitiveMetricDefinition {

	// # # # # # CONSTANTS # # # # #

	private static final double DEFAULT_QOCVALUE = 1.0;

	// # # # # # PRIVATE VARIABLES # # # # #

	private static GetListPrimitiveQoCMetricDefinition getListQoCPrimitiveMetricDefinition;
	private static QoCIndicator qoCIndicatorZero;
	private static QoCIndicator qoCIndicatorOne;
	private static QoCIndicator qoCIndicatorTwo;
	private static List<QoCIndicator> list_qoCIndicator;
	private static List<QoCMetricDefinition> list_primitiveQoCMetricDefinition;
	private static List<QoCMetricDefinition> list_compositeQoCMetricDefinition;
	private static Integer counter_qocMetricValueId = 0;

	// # # # # # PUBLIC METHODS # # # # #

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		list_qoCIndicator = new ArrayList<QoCIndicator>();
		list_primitiveQoCMetricDefinition = new ArrayList<QoCMetricDefinition>();
		list_compositeQoCMetricDefinition = new ArrayList<QoCMetricDefinition>();
		// - - - - - CORE OF THE METHOD - - - - -
		qoCIndicatorZero = TestCriterionZeroFactory.getInstance().newQoCIndicator("" + counter_qocMetricValueId++,
				DEFAULT_QOCVALUE);
		qoCIndicatorOne = TestCriterionOneFactory.getInstance().newQoCIndicator("" + counter_qocMetricValueId++,
				DEFAULT_QOCVALUE);
		qoCIndicatorTwo = TestCriterionTwoFactory.getInstance().newQoCIndicator("" + counter_qocMetricValueId++,
				DEFAULT_QOCVALUE);
		list_qoCIndicator.add(qoCIndicatorZero);
		list_qoCIndicator.add(qoCIndicatorOne);
		list_qoCIndicator.add(qoCIndicatorTwo);
		// The argument list_qoCIndicator must not be empty.
		getListQoCPrimitiveMetricDefinition = new GetListPrimitiveQoCMetricDefinition(list_qoCIndicator);
	}

	@Before
	public void setUp() throws Exception {
		// - - - - - CORE OF THE METHOD - - - - -
		list_primitiveQoCMetricDefinition.clear();
		list_compositeQoCMetricDefinition.clear();
	}

	@After
	@SuppressWarnings("unchecked")
	public final void execTest() {
		// - - - - - CORE OF THE METHOD - - - - -
		/**
		 * Evaluate primitive definition
		 */
		for (final QoCMetricDefinition loop_testedQoCMetricDefinition : list_compositeQoCMetricDefinition) {
			try {
				getListQoCPrimitiveMetricDefinition.setUp(loop_testedQoCMetricDefinition.id());
				final List<QoCMetricDefinition> list_primitiveQoCMetricDefinition = (List<QoCMetricDefinition>) getListQoCPrimitiveMetricDefinition
						.exec();
				assertEquals(list_primitiveQoCMetricDefinition.size(),
						loop_testedQoCMetricDefinition.list_primitiveQoCMetricDefinition.size());
				for (final QoCMetricDefinition loop_qoCMetricDefinition : loop_testedQoCMetricDefinition.list_primitiveQoCMetricDefinition) {
					assertTrue(list_primitiveQoCMetricDefinition.contains(loop_qoCMetricDefinition));
				}
			} catch (final Exception exception) {
				exception.printStackTrace();
				fail("Fail during the test of composite QoCMetricDefinition");
			}
		}
		/**
		 * Evaluate composite definition
		 */
		for (final QoCMetricDefinition loop_testedQoCMetricDefinition : list_primitiveQoCMetricDefinition) {
			try {
				getListQoCPrimitiveMetricDefinition.setUp(loop_testedQoCMetricDefinition.id());
			} catch (final IllegalArgumentException exception) {
				if (!exception.getMessage().equals(
						"GetListQoCPrimitiveMetricDefinition.setUp(String): the definition identified with the argument _qoCMetricDefinitionId is a primitive definition")) {
					exception.printStackTrace();
					fail("Fail during the test of primitive QoCMetricDefinition");
				}
			}
		}
	}

	@Test
	public final void testPrimitiveAndCompositeQoCMetricDefinitions() {
		// - - - - - CORE OF THE METHOD - - - - -
		/**
		 * Test with the primitive indicator "zero"
		 */
		list_primitiveQoCMetricDefinition.add(qoCIndicatorZero.qoCCriterion().list_qoCMetricDefinition.getFirst());
		/**
		 * Test with the primitive indicator "one"
		 */
		list_primitiveQoCMetricDefinition.add(qoCIndicatorOne.qoCCriterion().list_qoCMetricDefinition.getFirst());
		/**
		 * Test with the composite indicator "two"
		 */
		list_compositeQoCMetricDefinition.add(qoCIndicatorTwo.qoCCriterion().list_qoCMetricDefinition.getFirst());
	}
}
