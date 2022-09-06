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
package qocim.datamodel.test.criterion.one;
import qocim.datamodel.QoCIMFacade;
import qocim.datamodel.utils.IQoCIMFactory;;

/**
 * <!-- begin-user-doc --> A representation of the model object
 * <em><b>FreshnessMetricDefinitionValueGenerator</b></em>. <!-- end-user-doc
 * -->
 *
 **/
public class TestCriterionOneFactory implements IQoCIMFactory {

	// # # # # # CONSTANTS # # # # #

	public static final int DEFAULT_QOCVALUE = 1;

	// # # # # # PRIVATE VARIABLES # # # # #

	private static TestCriterionOneFactory instance = null;

	// # # # # # CONSTRUCTORS # # # # #

	private TestCriterionOneFactory() {
	}

	// # # # # # PUBLIC METHODS # # # # #

	public synchronized static TestCriterionOneFactory getInstance() {
		// - - - - - CORE OF THE METHOD - - - - -
		if (instance == null) {
			instance = new TestCriterionOneFactory();
		}
		// - - - - - RETURN STATEMENT - - - - -
		return instance;
	}

	@Override
	public TestCriterionOneQoCIndicator newQoCIndicator() {
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		final TestCriterionOneQoCIndicator ret_qoCIndicator = new TestCriterionOneQoCIndicator();
		final TestCriterionOneQoCCriterion qoCCriterion = new TestCriterionOneQoCCriterion();
		// - - - - - CORE OF THE METHOD - - - - -
		QoCIMFacade.addQoCCriterion(qoCCriterion, ret_qoCIndicator);
		// - - - - - RETURN STATEMENT - - - - -
		return ret_qoCIndicator;
	}

	@Override
	public synchronized TestCriterionOneQoCMetricValue newQoCMetricValue(final String _qoCMetricValueId,
			final Double _qoCMetricValue) {
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		final TestCriterionOneQoCMetricValue ret_qoCMetricValue = new TestCriterionOneQoCMetricValue();
		final TestCriterionOneQoCMetricDefinition qoCMetricDefinition = new TestCriterionOneQoCMetricDefinition();
		// - - - - - CORE OF THE METHOD - - - - -
		ret_qoCMetricValue.id(_qoCMetricValueId);
		ret_qoCMetricValue.value(_qoCMetricValue);
		QoCIMFacade.addQoCMetricValue(ret_qoCMetricValue, qoCMetricDefinition);
		// - - - - - RETURN STATEMENT - - - - -
		return ret_qoCMetricValue;
	}

	@Override
	public TestCriterionOneQoCIndicator newQoCIndicator(final String _qoCMetricValueId, final Double _qoCMetricValue) {
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		final TestCriterionOneQoCIndicator ret_qoCIndicator = newQoCIndicator();
		final TestCriterionOneQoCMetricValue qoCMetricValue = newQoCMetricValue(_qoCMetricValueId, _qoCMetricValue);
		// - - - - - CORE OF THE METHOD - - - - -
		QoCIMFacade.addQoCMetricValue(qoCMetricValue, ret_qoCIndicator);
		QoCIMFacade.addQoCMetricDefinition(qoCMetricValue.qoCMetricDefinition(), ret_qoCIndicator.qoCCriterion());
		// - - - - - RETURN STATEMENT - - - - -
		return ret_qoCIndicator;
	}
}
