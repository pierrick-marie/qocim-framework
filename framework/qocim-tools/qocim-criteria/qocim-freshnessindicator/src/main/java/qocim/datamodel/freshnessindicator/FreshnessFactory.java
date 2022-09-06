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
package qocim.datamodel.freshnessindicator;

import qocim.datamodel.QoCIMFacade;
import qocim.datamodel.utils.ConstraintChecker;
import qocim.datamodel.utils.ConstraintCheckerException;
import qocim.datamodel.utils.IQoCIMFactory;

public class FreshnessFactory implements IQoCIMFactory {

	// # # # # # PRIVATE VARIABLES # # # # #

	public synchronized static FreshnessFactory instance() {
		// - - - - - CORE OF THE METHOD - - - - -
		if (instance == null) {
			instance = new FreshnessFactory();
		}
		// - - - - - RETURN STATEMENT - - - - -
		return instance;
	}

	// # # # # # CONSTRUCTORS # # # # #

	private static FreshnessFactory instance = null;

	// # # # # # PUBLIC METHODS # # # # #

	private FreshnessFactory() {
	}

	@Override
	public FreshnessQoCIndicator newQoCIndicator() {
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		final FreshnessQoCIndicator ret_qoCIndicator = new FreshnessQoCIndicator();
		final FreshnessQoCCriterion qoCCriterion = new FreshnessQoCCriterion();
		// - - - - - CORE OF THE METHOD - - - - -
		ret_qoCIndicator.qoCCriterion(qoCCriterion);
		ret_qoCIndicator.container(ret_qoCIndicator);
		qoCCriterion.container(ret_qoCIndicator);
		// - - - - - RETURN STATEMENT - - - - -
		return ret_qoCIndicator;
	}

	@Override
	public synchronized FreshnessQoCMetricValue newQoCMetricValue(final String _qoCMetricValueId,
			final Double _qoCMetricValue) {
		// - - - - - CHECK THE VALUE OF THE ARGUMENTS - - - - -
		try {
			String message = "FreshnessFactory.newQoCMetricValue(Integer, Double): the argument _qoCMetricValueId is null.";
			ConstraintChecker.notNull(_qoCMetricValueId, message);
			message = "FreshnessFactory.newQoCMetricValue(Integer, Double): the argument _qoCMetricValue is null.";
			ConstraintChecker.notNull(_qoCMetricValue, message);
		} catch (final ConstraintCheckerException _exception) {
			return new FreshnessQoCMetricValue();
		}
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		final FreshnessQoCMetricValue ret_qoCMetricValue = new FreshnessQoCMetricValue();
		final FreshnessQoCMetricDefinition qoCMetricDefinition = new FreshnessQoCMetricDefinition();
		final FreshnessDescription description = new FreshnessDescription();
		// - - - - - CORE OF THE METHOD - - - - -
		ret_qoCMetricValue.id(_qoCMetricValueId);
		ret_qoCMetricValue.setValue(_qoCMetricValue);
		QoCIMFacade.addQoCMetricValue(ret_qoCMetricValue, qoCMetricDefinition);
		qoCMetricDefinition.description(description);
		description.container(qoCMetricDefinition);
		// - - - - - RETURN STATEMENT - - - - -
		return ret_qoCMetricValue;
	}

	@Override
	public FreshnessQoCIndicator newQoCIndicator(final String _qoCMetricValueId, final Double _qoCMetricValue) {
		// - - - - - CHECK THE VALUE OF THE ARGUMENTS - - - - -
		try {
			String message = "FreshnesFactory.newQoCIndicator(Integer, Double): the argument _qoCMetricValueId is null";
			ConstraintChecker.notNull(_qoCMetricValueId, message);
			message = "FreshnesFactory.newQoCIndicator(Integer, Double): the argument _qoCMetricValue is null";
			ConstraintChecker.notNull(_qoCMetricValue, message);
		} catch (final ConstraintCheckerException _exception) {
			return new FreshnessQoCIndicator();
		}
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		final FreshnessQoCIndicator ret_qoCIndicator = newQoCIndicator();
		final FreshnessQoCMetricValue qoCMetricValue = newQoCMetricValue(_qoCMetricValueId, _qoCMetricValue);
		// - - - - - CORE OF THE METHOD - - - - -
		QoCIMFacade.addQoCMetricValue(qoCMetricValue, ret_qoCIndicator);
		QoCIMFacade.addQoCMetricDefinition(qoCMetricValue.qoCMetricDefinition(), ret_qoCIndicator.qoCCriterion());
		// - - - - - RETURN STATEMENT - - - - -
		return ret_qoCIndicator;
	}
}
