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
package qocim.datamodel.freshness.definitions;

import java.util.LinkedList;
import java.util.List;

import qocim.model.QoCDescription;

public class FreshnessDescription extends QoCDescription {

	public static final String NAME = "Freshness description";

	public FreshnessDescription() {
		super(NAME);

		setDescription("This is an informal description of the freshness criterion.");

		keywords().add("time");
		keywords().add("measurement");
		keywords().add("interval");
		keywords().add("second");
	}
}
