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
package qocim.datamodel.precisionindicator;

import qocim.datamodel.QoCIMFacade;
import qocim.datamodel.utils.ConstraintChecker;
import qocim.datamodel.utils.ConstraintCheckerException;
import qocim.datamodel.utils.IQoCIMFactory;

public class PerthousandPrecisionFactory implements IQoCIMFactory {

	// # # # # # PRIVATE VARIABLES # # # # #

	private static PerthousandPrecisionFactory instance = null;

	// # # # # # CONSTRUCTORS # # # # #

	private PerthousandPrecisionFactory() {
	}

	// # # # # # PUBLIC METHODS # # # # #

	public synchronized static PerthousandPrecisionFactory instance() {
		// - - - - - CORE OF THE METHOD - - - - -
		if (instance == null) {
			instance = new PerthousandPrecisionFactory();
		}
		// - - - - - RETURN STATEMENT - - - - -
		return instance;
	}

	@Override
	public PrecisionQoCIndicator newQoCIndicator() {
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		final PrecisionQoCIndicator ret_qoCIndicator = new PrecisionQoCIndicator();
		final PrecisionQoCCriterion qoCCriterion = new PrecisionQoCCriterion();
		// - - - - - CORE OF THE METHOD - - - - -
		QoCIMFacade.addQoCCriterion(qoCCriterion, ret_qoCIndicator);
		// - - - - - RETURN STATEMENT - - - - -
		return ret_qoCIndicator;
	}

	@Override
	public synchronized PerthousandPrecisionQoCMetricValue newQoCMetricValue(final String _qoCMetricValueId,
			final Double _qoCMetricValue) {
		// - - - - - CHECK THE VALUE OF THE ARGUMENTS - - - - -
		try {
			String message = "PerthousandPrecisionFactory.newQoCMetricValue(Integer, Double): the argument _qoCMetricValueId is null.";
			ConstraintChecker.notNull(_qoCMetricValueId, message);
			message = "PerthousandPrecisionFactory.newQoCMetricValue(Integer, Double): the argument _qoCMetricValue is null.";
			ConstraintChecker.notNull(_qoCMetricValue, message);
		} catch (final ConstraintCheckerException _exception) {
			return new PerthousandPrecisionQoCMetricValue();
		}
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		final PerthousandPrecisionQoCMetricValue ret_qoCMetricValue = new PerthousandPrecisionQoCMetricValue();
		final PerthousandPrecisionQoCMetricDefinition qoCMetricDefinition = new PerthousandPrecisionQoCMetricDefinition();
		final PrecisionDescription description = new PrecisionDescription();
		// - - - - - CORE OF THE METHOD - - - - -
		ret_qoCMetricValue.id(_qoCMetricValueId);
		ret_qoCMetricValue.value(_qoCMetricValue);
		QoCIMFacade.addQoCMetricValue(ret_qoCMetricValue, qoCMetricDefinition);
		QoCIMFacade.addDescription(description, qoCMetricDefinition);
		// - - - - - RETURN STATEMENT - - - - -
		return ret_qoCMetricValue;
	}

	@Override
	public PrecisionQoCIndicator newQoCIndicator(final String _qoCMetricValueId, final Double _qoCMetricValue) {
		// - - - - - CHECK THE VALUE OF THE ARGUMENTS - - - - -
		try {
			String message = "PerthousandPrecisionFactory.createQoCIndicator(Integer, Double): the argument _qoCMetricValueId is null";
			ConstraintChecker.notNull(_qoCMetricValueId, message);
			message = "PerthousandPrecisionFactory.createQoCMetaData(Integer, Double): the argument _qoCMetricValue is null";
			ConstraintChecker.notNull(_qoCMetricValue, message);
		} catch (final ConstraintCheckerException _exception) {
			return new PrecisionQoCIndicator();
		}
		// - - - - - INITIALIZE THE VARIABLES - - - - -
		final PrecisionQoCIndicator ret_qoCIndicator = newQoCIndicator();
		final PerthousandPrecisionQoCMetricValue qoCMetricValue = newQoCMetricValue(_qoCMetricValueId, _qoCMetricValue);
		// - - - - - CORE OF THE METHOD - - - - -
		QoCIMFacade.addQoCMetricValue(qoCMetricValue, ret_qoCIndicator);
		QoCIMFacade.addQoCMetricDefinition(qoCMetricValue.qoCMetricDefinition(), ret_qoCIndicator.qoCCriterion());
		// - - - - - RETURN STATEMENT - - - - -
		return ret_qoCIndicator;
	}
}
