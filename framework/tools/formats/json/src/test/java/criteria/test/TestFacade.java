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
package criteria.test;

import criteria.test.simple.criteria.TestCriterion;
import criteria.test.simple.definitions.SimpleDefinition;
import qocim.model.QoCIndicator;
import qocim.model.tools.AbstractQoCFacade;

public class TestFacade extends AbstractQoCFacade {

	public TestFacade() {
		super();
	}

	@Override
	public String indicatorName() {
		return TestIndicator.NAME;
	}

	@Override
	public QoCIndicator newQoCIndicator() {
		TestIndicator indicator  = new TestIndicator();
		TestCriterion criterion = new TestCriterion();
		SimpleDefinition definition = new SimpleDefinition();

		indicator.addQoCCriterion(criterion);
		criterion.addQoCDefinition(definition);

		return indicator;
	}
}
