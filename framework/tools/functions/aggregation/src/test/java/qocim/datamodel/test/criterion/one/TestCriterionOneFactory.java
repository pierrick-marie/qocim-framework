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
		final TestCriterionOneQoCIndicator retqocIndicator = new TestCriterionOneQoCIndicator();
		final TestCriterionOneQoCCriterion qocCriterion = new TestCriterionOneQoCCriterion();
		// - - - - - CORE OF THE METHOD - - - - -
		QoCIMFacade.addQoCCriterion(qocCriterion, retqocIndicator);
		// - - - - - RETURN STATEMENT - - - - -
		return retqocIndicator;
	}

	@Override
	public synchronized TestCriterionOneQoCMetricValue newQoCMetricValue(final String qocMetricValueId,
			final Double qocMetricValue) {
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		final TestCriterionOneQoCMetricValue retqocMetricValue = new TestCriterionOneQoCMetricValue();
		final TestCriterionOneQoCMetricDefinition qocMetricDefinition = new TestCriterionOneQoCMetricDefinition();
		// - - - - - CORE OF THE METHOD - - - - -
		retqocMetricValue.id(qocMetricValueId);
		retqocMetricValue.value(qocMetricValue);
		QoCIMFacade.addQoCMetricValue(retqocMetricValue, qocMetricDefinition);
		// - - - - - RETURN STATEMENT - - - - -
		return retqocMetricValue;
	}

	@Override
	public TestCriterionOneQoCIndicator newQoCIndicator(final String qocMetricValueId, final Double qocMetricValue) {
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		final TestCriterionOneQoCIndicator retqocIndicator = newQoCIndicator();
		final TestCriterionOneQoCMetricValue qocMetricValue = newQoCMetricValue(qocMetricValueId, qocMetricValue);
		// - - - - - CORE OF THE METHOD - - - - -
		QoCIMFacade.addQoCMetricValue(qocMetricValue, retqocIndicator);
		QoCIMFacade.addQoCMetricDefinition(qocMetricValue.qocMetricDefinition(), retqocIndicator.qocCriterion());
		// - - - - - RETURN STATEMENT - - - - -
		return retqocIndicator;
	}
}
