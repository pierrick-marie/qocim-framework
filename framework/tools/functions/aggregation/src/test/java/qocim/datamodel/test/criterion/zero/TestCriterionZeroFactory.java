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
package qocim.datamodel.test.criterion.zero;

import qocim.datamodel.QoCIMFacade;
import qocim.datamodel.utils.IQoCIMFactory;

/**
 * <!-- begin-user-doc --> A representation of the model object
 * <em><b>FreshnessMetricDefinitionValueGenerator</b></em>. <!-- end-user-doc
 * -->
 *
 **/
public class TestCriterionZeroFactory implements IQoCIMFactory {

	// # # # # # CONSTANTS # # # # #

	public static final int DEFAULT_QOCVALUE = 1;

	// # # # # # PRIVATE VARIABLES # # # # #

	private static TestCriterionZeroFactory instance = null;

	// # # # # # CONSTRUCTORS # # # # #

	private TestCriterionZeroFactory() {
	}

	// # # # # # PUBLIC METHODS # # # # #

	public synchronized static TestCriterionZeroFactory getInstance() {
		// - - - - - CORE OF THE METHOD - - - - -
		if (instance == null) {
			instance = new TestCriterionZeroFactory();
		}
		// - - - - - RETURN STATEMENT - - - - -
		return instance;
	}

	@Override
	public TestCriterionZeroQoCIndicator newQoCIndicator() {
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		final TestCriterionZeroQoCIndicator retqocIndicator = new TestCriterionZeroQoCIndicator();
		final TestCriterionZeroQoCCriterion qocCriterion = new TestCriterionZeroQoCCriterion();
		// - - - - - CORE OF THE METHOD - - - - -
		QoCIMFacade.addQoCCriterion(qocCriterion, retqocIndicator);
		// - - - - - RETURN STATEMENT - - - - -
		return retqocIndicator;
	}

	@Override
	public synchronized TestCriterionZeroQoCMetricValue newQoCMetricValue(final String qocMetricValueId,
			final Double qocMetricValue) {
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		final TestCriterionZeroQoCMetricValue retqocMetricValue = new TestCriterionZeroQoCMetricValue();
		final TestCriterionZeroQoCMetricDefinition qocMetricDefinition = new TestCriterionZeroQoCMetricDefinition();
		// - - - - - CORE OF THE METHOD - - - - -
		retqocMetricValue.id(qocMetricValueId);
		retqocMetricValue.value(qocMetricValue);
		QoCIMFacade.addQoCMetricValue(retqocMetricValue, qocMetricDefinition);
		// - - - - - RETURN STATEMENT - - - - -
		return retqocMetricValue;
	}

	@Override
	public TestCriterionZeroQoCIndicator newQoCIndicator(final String qocMetricValueId, final Double qocMetricValue) {
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		final TestCriterionZeroQoCIndicator retqocIndicator = newQoCIndicator();
		final TestCriterionZeroQoCMetricValue qocMetricValue = newQoCMetricValue(qocMetricValueId, qocMetricValue);
		// - - - - - CORE OF THE METHOD - - - - -
		QoCIMFacade.addQoCMetricValue(qocMetricValue, retqocIndicator);
		QoCIMFacade.addQoCMetricDefinition(qocMetricValue.qocMetricDefinition(), retqocIndicator.qocCriterion());
		// - - - - - RETURN STATEMENT - - - - -
		return retqocIndicator;
	}
}
