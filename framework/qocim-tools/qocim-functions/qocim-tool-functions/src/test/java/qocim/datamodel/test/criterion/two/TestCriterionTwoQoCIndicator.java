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

import qocim.datamodel.QoCIndicator;
import qocim.datamodel.test.criterion.one.TestCriterionOneQoCIndicator;
import qocim.datamodel.test.criterion.zero.TestCriterionZeroQoCIndicator;

/**
 *
 * TestCriterionTwoQoCIndicator is a composite indicator based on the
 * TestCriterionZeroQoCIndicator and TestCriterionOneQoCIndicator
 *
 * @see TestCriterionZeroQoCIndicator
 * @see TestCriterionOneQoCIndicator
 *
 * @author Pierrick MARIE
 *
 */
public class TestCriterionTwoQoCIndicator extends QoCIndicator {

	// # # # # # CONSTANTS # # # # #

	public static final Integer ID_DEFAULTVALUE = 2;

	// # # # # # PUBLIC METHODS # # # # #

	public TestCriterionTwoQoCIndicator() {
		// - - - - - CORE OF THE METHOD - - - - -
		super();
		id(ID_DEFAULTVALUE);
	}

}
