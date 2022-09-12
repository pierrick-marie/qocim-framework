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
package qocim.datamodel.test.criterion.two;

import qocim.datamodel.QoCIMFacade;
import qocim.datamodel.test.criterion.one.TestCriterionOneQoCMetricDefinition;
import qocim.datamodel.test.criterion.zero.TestCriterionZeroQoCMetricDefinition;
import qocim.datamodel.utils.IQoCIMFactory;

/**
 * <!-- begin-user-doc --> A representation of the model object
 * <em><b>FreshnessMetricDefinitionValueGenerator</b></em>. <!-- end-user-doc
 * -->
 *
 **/
public class TestCriterionTwoFactory implements IQoCIMFactory {

	// # # # # # CONSTANTS # # # # #

	public static final int DEFAULT_QOCVALUE = 1;

	// # # # # # PRIVATE VARIABLES # # # # #

	private static TestCriterionTwoFactory instance = null;

	// # # # # # CONSTRUCTORS # # # # #

	private TestCriterionTwoFactory() {
	}

	// # # # # # PUBLIC METHODS # # # # #

	public synchronized static TestCriterionTwoFactory getInstance() {
		// - - - - - CORE OF THE METHOD - - - - -
		if (instance == null) {
			instance = new TestCriterionTwoFactory();
		}
		// - - - - - RETURN STATEMENT - - - - -
		return instance;
	}

	@Override
	public TestCriterionTwoQoCIndicator newQoCIndicator() {
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		final TestCriterionTwoQoCIndicator ret_qoCIndicator = new TestCriterionTwoQoCIndicator();
		final TestCriterionTwoQoCCriterion qoCCriterion = new TestCriterionTwoQoCCriterion();
		// - - - - - CORE OF THE METHOD - - - - -
		QoCIMFacade.addQoCCriterion(qoCCriterion, ret_qoCIndicator);
		// - - - - - RETURN STATEMENT - - - - -
		return ret_qoCIndicator;
	}

	@Override
	public synchronized TestCriterionTwoQoCMetricValue newQoCMetricValue(final String _qoCMetricValueId,
			final Double _qoCMetricValue) {
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		final TestCriterionTwoQoCMetricValue ret_qoCMetricValue = new TestCriterionTwoQoCMetricValue();
		final TestCriterionTwoQoCMetricDefinition qoCMetricDefinition = new TestCriterionTwoQoCMetricDefinition();
		QoCIMFacade.addPrimitiveQoCMetricDefinition(new TestCriterionZeroQoCMetricDefinition(), qoCMetricDefinition);
		QoCIMFacade.addPrimitiveQoCMetricDefinition(new TestCriterionOneQoCMetricDefinition(), qoCMetricDefinition);
		// - - - - - CORE OF THE METHOD - - - - -
		ret_qoCMetricValue.id(_qoCMetricValueId);
		ret_qoCMetricValue.value(_qoCMetricValue);
		QoCIMFacade.addQoCMetricValue(ret_qoCMetricValue, qoCMetricDefinition);
		// - - - - - RETURN STATEMENT - - - - -
		return ret_qoCMetricValue;
	}

	@Override
	public TestCriterionTwoQoCIndicator newQoCIndicator(final String _qoCMetricValueId, final Double _qoCMetricValue) {
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		final TestCriterionTwoQoCIndicator ret_qoCIndicator = newQoCIndicator();
		final TestCriterionTwoQoCMetricValue qoCMetricValue = newQoCMetricValue(_qoCMetricValueId, _qoCMetricValue);
		// - - - - - CORE OF THE METHOD - - - - -
		QoCIMFacade.addQoCMetricValue(qoCMetricValue, ret_qoCIndicator);
		QoCIMFacade.addQoCMetricDefinition(qoCMetricValue.qoCMetricDefinition(), ret_qoCIndicator.qoCCriterion());
		// - - - - - RETURN STATEMENT - - - - -
		return ret_qoCIndicator;
	}
}
