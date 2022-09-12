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
package qocim.datamodel.spatialresolutionindicator;

import qocim.datamodel.QoCIMFacade;
import qocim.datamodel.utils.ConstraintChecker;
import qocim.datamodel.utils.ConstraintCheckerException;
import qocim.datamodel.utils.IQoCIMFactory;

public class SpatialResolutionFactory implements IQoCIMFactory {

	// # # # # # PRIVATE VARIABLES # # # # #

	private static SpatialResolutionFactory instance = null;

	// # # # # # CONSTRUCTORS # # # # #

	private SpatialResolutionFactory() {
	}

	// # # # # # PUBLIC METHODS # # # # #

	public synchronized static SpatialResolutionFactory instance() {
		// - - - - - CORE OF THE METHOD - - - - -
		if (instance == null) {
			instance = new SpatialResolutionFactory();
		}
		// - - - - - RETURN STATEMENT - - - - -
		return instance;
	}

	@Override
	public SpatialResolutionQoCIndicator newQoCIndicator() {
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		final SpatialResolutionQoCIndicator ret_qoCIndicator = new SpatialResolutionQoCIndicator();
		final SpatialResolutionQoCCriterion qoCCriterion = new SpatialResolutionQoCCriterion();
		// - - - - - CORE OF THE METHOD - - - -
		QoCIMFacade.addQoCCriterion(qoCCriterion, ret_qoCIndicator);
		// - - - - - RETURN STATEMENT - - - - -
		return ret_qoCIndicator;
	}

	@Override
	public synchronized SpatialResolutionQoCMetricValue newQoCMetricValue(final String _qoCMetricValueId,
			final Double _qoCMetricValue) {
		// - - - - - CHECK THE VALUE OF THE ARGUMENTS - - - - -
		try {
			String message = "SpatialResolutionFactory.newQoCMetricValue(Integer, Double): the argument _qoCMetricValueId is null.";
			ConstraintChecker.notNull(_qoCMetricValueId, message);
			message = "SpatialResolutionFactory.newQoCMetricValue(Integer, Double): the argument _qoCMetricValue is null.";
			ConstraintChecker.notNull(_qoCMetricValue, message);
		} catch (final ConstraintCheckerException _exception) {
			return new SpatialResolutionQoCMetricValue();
		}
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		final SpatialResolutionQoCMetricValue ret_qoCMetricValue = new SpatialResolutionQoCMetricValue();
		final SpatialResolutionQoCMetricDefinition qoCMetricDefinition = new SpatialResolutionQoCMetricDefinition();
		final SpatialResolutionDescription description = new SpatialResolutionDescription();
		// - - - - - CORE OF THE METHOD - - - - -
		ret_qoCMetricValue.id(_qoCMetricValueId);
		ret_qoCMetricValue.setValue(_qoCMetricValue);
		QoCIMFacade.addQoCMetricValue(ret_qoCMetricValue, qoCMetricDefinition);
		QoCIMFacade.addDescription(description, qoCMetricDefinition);
		// - - - - - RETURN STATEMENT - - - - -
		return ret_qoCMetricValue;
	}

	@Override
	public SpatialResolutionQoCIndicator newQoCIndicator(final String _qoCMetricValueId, final Double _qoCMetricValue) {
		// - - - - - CHECK THE VALUE OF THE ARGUMENTS - - - - -
		try {
			String message = "SpatialResolutionFactory.newQoCMetaData(Integer, Double): the argument _qoCMetricValueId is null";
			ConstraintChecker.notNull(_qoCMetricValueId, message);
			message = "SpatialResolutionFactory.newQoCMetaData(Integer, Doubler): the argument _qoCMetricValue is null";
			ConstraintChecker.notNull(_qoCMetricValue, message);
		} catch (final ConstraintCheckerException _exception) {
			return new SpatialResolutionQoCIndicator();
		}
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		final SpatialResolutionQoCIndicator ret_qoCIndicator = newQoCIndicator();
		final SpatialResolutionQoCMetricValue qoCMetricValue = newQoCMetricValue(_qoCMetricValueId, _qoCMetricValue);
		// - - - - - CORE OF THE METHOD - - - - -
		QoCIMFacade.addQoCMetricValue(qoCMetricValue, ret_qoCIndicator);
		QoCIMFacade.addQoCMetricDefinition(qoCMetricValue.qoCMetricDefinition(), ret_qoCIndicator.qoCCriterion());
		// - - - - - RETURN STATEMENT - - - - -
		return ret_qoCIndicator;
	}
}
