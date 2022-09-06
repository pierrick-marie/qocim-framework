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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import qocim.datamodel.QoCIndicator;
import qocim.datamodel.test.criterion.one.TestCriterionOneFactory;
import qocim.datamodel.test.criterion.two.TestCriterionTwoFactory;
import qocim.datamodel.test.criterion.zero.TestCriterionZeroFactory;
import qocim.tool.functions.impl.IsCompositeQoCMetricDefinition;

public class TestIsCompositeQoCMetricDefinition {

	// # # # # # CONSTANTS # # # # #

	private static final double DEFAULT_QOCVALUE = 1.0;

	// # # # # # PRIVATE VARIABLES # # # # #

	private static IsCompositeQoCMetricDefinition isCompositeCriterion;
	private static QoCIndicator qoCIndicatorZero;
	private static QoCIndicator qoCIndicatorOne;
	private static QoCIndicator qoCIndicatorTwo;
	private static List<QoCIndicator> list_qoCIndicator;
	private static Map<String, Boolean> map_qoCMetricDefinition;
	private static Integer counter_qocMetricValueId = 0;

	// # # # # # PUBLIC METHODS # # # # #

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		list_qoCIndicator = new ArrayList<QoCIndicator>();
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
		isCompositeCriterion = new IsCompositeQoCMetricDefinition(list_qoCIndicator);
		map_qoCMetricDefinition = new HashMap<String, Boolean>();
	}

	@Before
	public void setUp() throws Exception {
		// - - - - - CORE OF THE METHOD - - - - -
		map_qoCMetricDefinition.clear();
	}

	@After
	public final void execTest() {
		// - - - - - CORE OF THE METHOD - - - - -
		for (final Map.Entry<String, Boolean> entry_definitionId : map_qoCMetricDefinition.entrySet()) {
			isCompositeDefinition(entry_definitionId.getKey(), entry_definitionId.getValue());
		}
	}

	@Test
	public final void testPrimitiveAndCompositeQoCIndicator() {
		// - - - - - CORE OF THE METHOD - - - - -
		/**
		 * Test with the primitive indicator "zero"
		 */
		map_qoCMetricDefinition.put(qoCIndicatorZero.qoCCriterion().list_qoCMetricDefinition.getFirst().id(), false);
		/**
		 * Test with the primitive indicator "one"
		 */
		map_qoCMetricDefinition.put(qoCIndicatorOne.qoCCriterion().list_qoCMetricDefinition.getFirst().id(), false);
		/**
		 * Test with the composite indicator "two"
		 */
		map_qoCMetricDefinition.put(qoCIndicatorTwo.qoCCriterion().list_qoCMetricDefinition.getFirst().id(), true);
	}

	// # # # # # PRIVATE METHODS # # # # #

	private final void isCompositeDefinition(final String _qoCMetricDefinition, final Boolean _expectedExceptionValue) {
		// - - - - - CORE OF THE METHOD - - - - -
		isCompositeCriterion.setUp(_qoCMetricDefinition);
		assertEquals(_expectedExceptionValue, isCompositeCriterion.exec());
	}
}
