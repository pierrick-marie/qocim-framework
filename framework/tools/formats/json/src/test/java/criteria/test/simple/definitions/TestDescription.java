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
package criteria.test.simple.definitions;

import qocim.model.QoCDescription;

public class TestDescription extends QoCDescription {

	public static final String NAME = "Test description";

	public TestDescription() {
		super(NAME);

		setDescription("This is an informal description");

		keywords().add("test 0");
		keywords().add("test 1");
		keywords().add("test 2");
	}
}
