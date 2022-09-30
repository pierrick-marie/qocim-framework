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
package qocim.datamodel.precision;

import qocim.datamodel.precision.simple.criteria.PrecisionCriterion;
import qocim.datamodel.precision.simple.definitions.SimpleDefinition;
import qocim.model.*;
import qocim.model.tools.AbstractQoCFacade;

public class PrecisionFacade extends AbstractQoCFacade {

	public PrecisionFacade() {
		super();
	}

	@Override
	public String indicatorName() {
		return PrecisionIndicator.NAME;
	}

	@Override
	public QoCIndicator newQoCIndicator() {
		PrecisionIndicator indicator  = new PrecisionIndicator();
		PrecisionCriterion criterion = new PrecisionCriterion();
		SimpleDefinition definition = new SimpleDefinition();

		indicator.addQoCCriterion(criterion);
		criterion.addQoCDefinition(definition);

		return indicator;
	}
}
