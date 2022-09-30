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
package qocim.datamodel.freshness.definitions.random;

import javax.measure.unit.SI;

import qocim.datamodel.freshness.definitions.FreshnessDescription;
import qocim.datamodel.freshness.definitions.values.MaxFreshnessValue;
import qocim.datamodel.freshness.definitions.values.MinFreshnessValue;
import qocim.model.Direction;
import qocim.model.QoCDefinition;

import java.net.URI;
import java.net.URISyntaxException;


public class RandomDefinition extends QoCDefinition {

	public static final String NAME = "Random freshness definition";
	public static final String ID = "15.1.2";

	public RandomDefinition() {
		super(NAME, ID);
		setIsInvariant(false);
		try {
			setProviderUri(new URI("//stub"));
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
		setDirection(Direction.LOWER);
		setIsDefault(false);
		setDescription(new FreshnessDescription());
		setUnit(SI.SECOND.toString());
		setMaxValue(new MaxFreshnessValue());
		setMinValue(new MinFreshnessValue());
	}
}
